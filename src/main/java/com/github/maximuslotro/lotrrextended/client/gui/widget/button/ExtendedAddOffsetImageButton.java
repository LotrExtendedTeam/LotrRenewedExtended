package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/**
 * Copied from ImageButton to add four states for the button, pressed/un-pressed + hovered/un-hovered
 * @see ImageButton
 * */
public class ExtendedAddOffsetImageButton extends ExtendedImageButtonButton {

	public ExtendedAddOffsetImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, Button.IPressable pOnPress) {
		super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, 256, 256, pOnPress);
	}

	public ExtendedAddOffsetImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress) {
		super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, StringTextComponent.EMPTY);
	}

	public ExtendedAddOffsetImageButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, Button.IPressable pOnPress, ITextComponent pMessage) {
		super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, NO_TOOLTIP, pMessage);
	}

	@Override
	protected int getYImage(boolean pIsHovered) {
		int i = yTexStart;
		if (!active) {
			i += yDiffTex*4;
		}else if (this.isHovered()&&!shouldShowEnabled()) {
			i += yDiffTex*1;
		}else if (!isHovered()&&shouldShowEnabled()) {
			i += (yDiffTex*2);
		}else if (isHovered()&&shouldShowEnabled()) {
			i += (yDiffTex*3);
		}
		return i;
	}

	public boolean shouldShowEnabled() {
		return false;
	}
}