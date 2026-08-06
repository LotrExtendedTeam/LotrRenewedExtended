package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class ExtendedTradeSellButton extends ImageButton {

	private final int xTexStart;
	private final int yTexStart;
	private final int textureWidth;
	private final int textureHeight;

	private static final ResourceLocation TEXTURE = new ResourceLocation("lotr:gui/trade.png");
	private ExtendedTradeContainer container;

	public ExtendedTradeSellButton(int x, int y, ExtendedTradeContainer container, IPressable pressable) {
		super(x, y, 18, 18, 176, 18, 18, TEXTURE, 280, 280, pressable);
		this.container = container;
		xTexStart = 176;
		yTexStart = 0;
		textureWidth = 280;
		textureHeight = 280;
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(TEXTURE);
		int yLocation = yTexStart;
		if (container.getSellValue() > 0) {
			yLocation += 18;
			if (this.isHovered()) {
				yLocation += 18;
			}
		}

		RenderSystem.enableDepthTest();
		blit(pMatrixStack, this.x, this.y, this.xTexStart, yLocation, this.width, this.height, this.textureWidth, this.textureHeight);
		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}

	}
}
