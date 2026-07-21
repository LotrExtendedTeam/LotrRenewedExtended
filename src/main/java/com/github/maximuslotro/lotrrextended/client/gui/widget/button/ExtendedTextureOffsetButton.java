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
	public ExtendedTextureOffsetButton(int pX, int pY, int tX, int tY, int pWidth, int pHeight, ITextComponent pMessage, IPressable pOnPress, ResourceLocation guiTexture) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
		xLoc = tX;
		yLoc = tY;
		this.guiTexture = guiTexture;
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
		this.blit(pMatrixStack, this.x, this.y, xLoc, yLoc+amount, this.width, this.height);
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
