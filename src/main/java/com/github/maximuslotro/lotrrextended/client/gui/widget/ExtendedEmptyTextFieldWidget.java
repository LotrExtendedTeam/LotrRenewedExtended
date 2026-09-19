package com.github.maximuslotro.lotrrextended.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;

public class ExtendedEmptyTextFieldWidget extends TextFieldWidget {
	private final ITextComponent emptyMessage;

	public ExtendedEmptyTextFieldWidget(FontRenderer pFont, int pX, int pY, int pWidth, int pHeight, ITextComponent pMessage, ITextComponent pEmptyMessage) {
		super(pFont, pX, pY, pWidth, pHeight, pMessage);
		emptyMessage = pEmptyMessage;
	}

	@Override
	public void renderButton(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
		if(!this.isFocused() && this.getValue().isEmpty()) {
			this.setValue(emptyMessage.getString());
			this.setTextColor(10526880);
			super.renderButton(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
			this.setValue("");
			this.setTextColor(14737632);
			return;
		}
		super.renderButton(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
	}
}
