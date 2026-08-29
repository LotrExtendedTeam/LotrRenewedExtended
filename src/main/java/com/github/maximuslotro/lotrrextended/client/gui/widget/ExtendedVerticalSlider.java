package com.github.maximuslotro.lotrrextended.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class ExtendedVerticalSlider extends ExtendedSlider {
	public ExtendedVerticalSlider(int pX, int pY, int pWidth, int pHeight, String localKey, double minVal, double maxVal, float steps, 
		IGetValue valueGetter, ISetValue valueSetter, ResourceLocation texture, int handleLength, int scrollU, int scrollV, int backU, int backV) {
		super(pX, pY, pWidth, pHeight, localKey, minVal, maxVal, steps, valueGetter, valueSetter);
		this.setSliderTexture(texture, handleLength);
		this.setSliderTextureUV(scrollU, scrollV);
		this.setBackgroundTextureUV(backU, backV);
	}

	@Override
	public void onClick(double pMouseX, double pMouseY) {
		this.setValueFromMouseY(pMouseY);
	}
	@Override
	protected void onDrag(double pMouseX, double pMouseY, double pDragX, double pDragY) {
		this.setValueFromMouseY(pMouseY);
	}
	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		boolean isUp = pKeyCode == 265;
		if (isUp || pKeyCode == 264) {
			float stepAmount = isUp ? -1.0F : 1.0F;
            this.setSliderValue(this.value + (stepAmount / Math.max(1.0D, this.height - this.handleLength)));
            return true;
		}
		return false;
	}

	@Override
	protected void renderBg(MatrixStack pMatrixStack, Minecraft pMinecraft, int pMouseX, int pMouseY) {
		pMinecraft.getTextureManager().bind(customTexture);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		int hoverOffset = (this.isHovered() ? 2 : 1) * handleLength;
	    int handleY = this.y + (int) (this.value * (this.height - handleLength));
	    blit(pMatrixStack, this.x, handleY, this.getBlitOffset(), this.textureU + hoverOffset, this.textureV, this.width, handleLength, textureWidth, textureHeight);
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer fontrenderer = minecraft.font;
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		if (this.drawBackground) {
			minecraft.getTextureManager().bind(customTexture);
			blit(pMatrixStack, this.x, this.y, this.getBlitOffset(), this.textureUBackground, this.textureVBackground, this.width, this.height, textureWidth, textureHeight);
		}
		this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);

		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}
	}
}
