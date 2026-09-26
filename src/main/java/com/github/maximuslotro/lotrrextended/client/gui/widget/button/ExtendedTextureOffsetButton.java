package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExtendedTextureOffsetButton extends ExtendedSimpleButton {
	private int xLoc;
	private int yLoc;
	private final ResourceLocation guiTexture;
	private int textureWidth = 256;
	private int textureHeight = 256;

	public ExtendedTextureOffsetButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, IPressable pOnPress, ResourceLocation guiTexture) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
		xLoc = tX;
		yLoc = tY;
		this.guiTexture = guiTexture;
	}

	public ExtendedTextureOffsetButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, IPressable pOnPress, 
		ResourceLocation guiTexture, int pTexturewidth, int pTextureheight) {
		this(pX, pY, tX, tY, pWidth, pHeight, pMessage, pOnPress, guiTexture);
		this.setTextureSize(pTexturewidth, pTextureheight);
	}

	public void setTextureSize(int width, int height) {
		this.textureWidth = width;
		this.textureHeight = height;
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(guiTexture);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		int amount = shouldOffset() ? this.height + hoverOffset() + (this.isHovered() ? hoverOffset() : 0): (this.isHovered() ? hoverOffset() : 0);
		if(shouldDoDifferentOffset()){
			amount = this.height + additionalOffset() + (this.shouldOffset() ? additionalOffset() : 0);
		}
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		blit(pMatrixStack, this.x, this.y, this.getBlitOffset(), (float)xLoc, (float)yLoc+amount, this.width, this.height, textureWidth, textureHeight);
		this.renderBg(pMatrixStack, minecraft, pMouseX, pMouseY);
		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}
	}

	public int hoverOffset() {
		return 0;
	}

	public boolean shouldOffset() {
		return false;
	}

	public boolean shouldDoDifferentOffset() {
		return false;
	}

	public int additionalOffset() {
		return 0;
	}
}
