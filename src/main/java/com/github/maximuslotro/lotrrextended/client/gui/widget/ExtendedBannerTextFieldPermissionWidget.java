package com.github.maximuslotro.lotrrextended.client.gui.widget;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.client.gui.widget.button.ExtendedTextureOffsetButton;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;

public class ExtendedBannerTextFieldPermissionWidget extends TextFieldWidget {
	public final int id;
	public List<ExtendedTextureOffsetButton> permButtons = new ArrayList<>();
	public ExtendedTextureOffsetButton permShowButton;
	public ExtendedTextureOffsetButton deleteButton;
	public boolean showPerms = false;

	public ExtendedBannerTextFieldPermissionWidget(FontRenderer pFont, int pX, int pY, int pWidth, int pHeight, ITextComponent pMessage, int id) {
		super(pFont, pX, pY, pWidth, pHeight, pMessage);
		this.id = id;
	}

	public void isVisible(boolean isVisible) {
		this.visible = isVisible;
		permShowButton.visible = isVisible;
		deleteButton.visible = isVisible;
	}
	public void isPermVisible(boolean isVisible) {
		permButtons.forEach((button) -> button.visible = isVisible);
	}

	public void togglePermVis() {
		showPerms = !showPerms;
		isPermVisible(showPerms);
	}
}
