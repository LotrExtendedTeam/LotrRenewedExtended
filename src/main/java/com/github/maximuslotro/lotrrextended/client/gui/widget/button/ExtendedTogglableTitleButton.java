package com.github.maximuslotro.lotrrextended.client.gui.widget.button;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedTogglableTitleButton extends Button {
	private final TextComponent baseMessage;
	private final String enabled;
	private final String disabled;

	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, ITooltip pOnTooltip, String enabled, String disabled) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
		this.baseMessage = pMessage;
		this.enabled = enabled;
		this.disabled = disabled;
	}

	public ExtendedTogglableTitleButton(int pX, int pY, int pWidth, int pHeight, TextComponent pMessage, IPressable pOnPress, String enabled, String disabled) {
		super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
		this.baseMessage = pMessage;
		this.enabled = enabled;
		this.disabled = disabled;
	}

	public void tickTitle(boolean toggle) {
		setMessage(baseMessage.copy().append(new TranslationTextComponent(toggle ? enabled : disabled)));
	}
}
