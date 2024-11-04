package com.github.maximuslotro.lotrrextended.client.gui.widget;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedBannerConfigurationScreen.TextureOffsetButton;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;

public class ExtendedTextFieldWidget extends TextFieldWidget{
	public final int id;
	public List<TextureOffsetButton> permButtons = new ArrayList<>();
	public TextureOffsetButton permShowButton;
	public boolean showPerms = false;

	public ExtendedTextFieldWidget(FontRenderer pFont, int pX, int pY, int pWidth, int pHeight, ITextComponent pMessage, int id) {
		super(pFont, pX, pY, pWidth, pHeight, pMessage);
		this.id = id;
	}

	public void isVisable(boolean isVisable) {
		this.visible = isVisable;
		permShowButton.visible = isVisable;
	}
	public void isPermVisable(boolean isVisable) {
		permButtons.forEach((button) -> button.visible = isVisable);
	}

	public void togglePermVis() {
		showPerms = !showPerms;
		isPermVisable(showPerms);
	}
}
