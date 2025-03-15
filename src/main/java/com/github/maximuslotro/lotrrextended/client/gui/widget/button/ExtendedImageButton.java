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
 * Copied from ImageButton to add four states for the button, pressed/un-pressed + hovered/un-hovered
 * @see ImageButton
 * */
public class ExtendedImageButton extends Button {
	private final ResourceLocation resourceLocation;
	private final int xTexStart;
	private final int yTexStart;
	private final int yDiffTex;
	private final int textureWidth;
	private final int textureHeight;

	public ExtendedImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, Button.IPressable pOnPress) {
		this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, 256, 256, pOnPress);
	}

	public ExtendedImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress) {
		this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, StringTextComponent.EMPTY);
	}

	public ExtendedImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress, ITextComponent pMessage) {
		this(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, NO_TOOLTIP, pMessage);
	}

	public ExtendedImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress, Button.ITooltip pOnTooltip, ITextComponent pMessage) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
		this.textureWidth = pTextureWidth;
		this.textureHeight = pTextureHeight;
		this.xTexStart = pXTexStart;
		this.yTexStart = pYTexStart;
		this.yDiffTex = pYDiffTex;
		this.resourceLocation = pResourceLocation;
	}

	public void setPosition(int pX, int pY) {
		this.x = pX;
		this.y = pY;
	}

	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(this.resourceLocation);
		int i = this.yTexStart;
		if (this.isHovered()&&!shouldShowEnabled()) {
			i += this.yDiffTex;
		}else if (!isHovered()&&shouldShowEnabled()) {
			i += (yDiffTex*2);
		}else if (this.isHovered()&&shouldShowEnabled()) {
			i += (yDiffTex*3);
		}

		RenderSystem.enableDepthTest();
		blit(pMatrixStack, this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, this.textureWidth, this.textureHeight);
		if (this.isHovered()) {
			this.renderToolTip(pMatrixStack, pMouseX, pMouseY);
		}
	}

	public boolean shouldShowEnabled() {
		return false;
	}
}