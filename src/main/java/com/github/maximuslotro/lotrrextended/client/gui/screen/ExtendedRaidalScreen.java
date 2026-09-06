package com.github.maximuslotro.lotrrextended.client.gui.screen;

import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedUnitHornTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCSelectHornPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedRaidalScreen extends Screen {
	private final List<ExtendedUnitHornTypes> options;
	private final int innerRadius = 20;
	private int outerRadius = 120;
	private int hoveredIndex = -1;
	int centerX;
	int centerY;

	public ExtendedRaidalScreen() {
		super(new TranslationTextComponent("gui.lotrextended.horn_select.title"));
		this.options = ExtendedUnitHornTypes.getSelectableHornTypes().stream().collect(Collectors.toList());
	}

	@Override
	public void init() {
		super.init();
		centerX = this.width / 2;
		centerY = this.height / 2;
		outerRadius = Math.min(120, (height-30)/2);
	}


	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		int count = options.size();
		if (count == 0) {
			return;
		}
		double mouseXcenter = mouseX - centerX;
		double mouseYcenter = mouseY - centerY;
		double dist = Math.hypot(mouseXcenter, mouseYcenter);
		double angle = Math.atan2(mouseYcenter, mouseXcenter) + Math.PI / 2;
		if (angle < 0) {
			angle += 2 * Math.PI;
		}
		double segmentAngle = (2 * Math.PI) / count;
		if (dist >= innerRadius) {
			hoveredIndex = (int) (angle / segmentAngle) % count;
		} else {
			hoveredIndex = -1;
		}
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		for (int i = 0; i < count; i++) {
			double startAngle = i * segmentAngle - (Math.PI / 2);
			double endAngle = (i + 1) * segmentAngle - (Math.PI / 2);
			boolean isHovered = (i == hoveredIndex);
			int r = 40;
		    int g = 40;
		    int b = 40;
		    int a = 230;
		    Integer color = options.get(i).getFormatting().getColor();
		    if (color != null) {
		        r = (color >> 16) & 0xFF;
		        g = (color >> 8) & 0xFF;
		        b = color & 0xFF;
		    }
		    if (!isHovered) {
		    	r = (int) (r * 0.5F);
		        g = (int) (g * 0.5F);
		        b = (int) (b * 0.5F);
		        a = 150;
		    }
			drawPieSegment(matrixStack, centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, r, g, b, 255);
		}
		RenderSystem.enableTexture();
		for (int i = 0; i < count; i++) {
			double midAngle = (i + 0.5) * segmentAngle - (Math.PI / 2);
			int textRadius = (innerRadius + outerRadius) / 2;
			int textX = (int) (centerX + Math.cos(midAngle) * textRadius);
			int textY = (int) (centerY + Math.sin(midAngle) * textRadius);
			TranslationTextComponent text = new TranslationTextComponent(options.get(i).getType());
			int color = (i == hoveredIndex) ? 0xFFFFFF : 0xAAAAAA;
			drawCenteredString(matrixStack, this.font, text, textX, textY - 4, color);
		}
		this.font.draw(matrixStack, title, (width - font.width(title)) / 2, 20, 16777215);
	}

	private void drawPieSegment(MatrixStack matrixStack, float cx, float cy, float innerR, float outerR, double startAngle, double endAngle, int r, int g, int b, int a) {
		matrixStack.pushPose();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		int precision = 16;
		double step = (endAngle - startAngle) / precision;
		RenderSystem.enableBlend();
	    RenderSystem.disableTexture();
	    RenderSystem.defaultBlendFunc();
	    RenderSystem.disableCull();
		buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
		for (int i = 0; i <= precision; i++) {
			double currentAngle = startAngle + (i * step);
			float cos = (float) Math.cos(currentAngle);
			float sin = (float) Math.sin(currentAngle);
			buffer.vertex(matrixStack.last().pose(), cx + innerR * cos, cy + innerR * sin, 0).color(r, g, b, a).endVertex();
			buffer.vertex(matrixStack.last().pose(), cx + outerR * cos, cy + outerR * sin, 0).color(r, g, b, a).endVertex();
		}
		tessellator.end();
		RenderSystem.enableCull();
	    RenderSystem.enableTexture();
	    matrixStack.popPose();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0 && hoveredIndex != -1) {
			ExtendedPacketHandler.sendToServer(new ExtendedCSelectHornPacket(options.get(hoveredIndex)));
			this.onClose();
			return true;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		ItemStack itemstack = this.minecraft.player.getMainHandItem();
		if (itemstack == null || itemstack.getItem() != ExtendedItems.COMMANDHORN.get()) {
			this.onClose();
			return;
		}
	}
}
