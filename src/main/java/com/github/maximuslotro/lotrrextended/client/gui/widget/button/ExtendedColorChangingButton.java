package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponent;

public class ExtendedColorChangingButton extends Button {
	private final float selectedRed;
	private final float selectedGreen;
	private final float selectedBlue;

	public ExtendedColorChangingButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, ITooltip pOnTooltip, float pRed, float pGreen, float pBlue) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
		this.selectedRed = pRed;
		this.selectedGreen = pGreen;
		this.selectedBlue = pBlue;
	}

	public ExtendedColorChangingButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, float pRed, float pGreen, float pBlue) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress);		
		this.selectedRed = pRed;
		this.selectedGreen = pGreen;
		this.selectedBlue = pBlue;
	}

	public boolean setColor() {
		return false;
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.font;
		minecraft.getTextureManager().bind(WIDGETS_LOCATION);
		if(setColor()) {
			RenderSystem.color4f(selectedRed, selectedGreen, selectedBlue, this.alpha);
		}else {
			RenderSystem.color4f(1.0f, 1.0f, 1.0f, this.alpha);
		}
		int i = this.getYImage(this.isHovered());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		this.blit(pMatrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
		this.blit(pMatrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
		this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);
		int j = getFGColor();
		drawCenteredString(pMatrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}
	}
}
