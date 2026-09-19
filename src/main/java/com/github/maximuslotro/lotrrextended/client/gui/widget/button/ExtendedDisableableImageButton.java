package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/**
 * Copied from ImageButton to add three states for the button, hovered/un-hovered + disabled
 * @see ImageButton
 * */
public class ExtendedDisableableImageButton extends Button {
	private final ResourceLocation resourceLocation;
	private final int xTexStart;
	protected final int yTexStart;
	protected final int yDiffTex;
	private final int textureWidth;
	private final int textureHeight;

	public ExtendedDisableableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, Button.IPressable pOnPress) {
		this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, 256, 256, pOnPress);
	}

	public ExtendedDisableableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress) {
		this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, StringTextComponent.EMPTY);
	}

	public ExtendedDisableableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress, ITextComponent pMessage) {
		this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, NO_TOOLTIP, pMessage);
	}

	public ExtendedDisableableImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress, Button.ITooltip pOnTooltip, ITextComponent pMessage) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
		this.textureWidth = pTextureWidth;
		this.textureHeight = pTextureHeight;
		this.xTexStart = pXTexStart;
		this.yTexStart = pYTexStart;
		this.yDiffTex = pYDiffTex;
		this.resourceLocation = pResourceLocation;
	}

	@Override
	protected int getYImage(boolean pIsHovered) {
		int i = yTexStart;
		if (!active) {
			i += yDiffTex*2;
		}else if (this.isHovered()) {
			i += yDiffTex*1;
		}
		return i;
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(this.resourceLocation);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		blit(pMatrixStack, this.x, this.y, this.xTexStart, getYImage(this.isHovered()), this.width, this.height, this.textureWidth, this.textureHeight);
		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}
	}
}