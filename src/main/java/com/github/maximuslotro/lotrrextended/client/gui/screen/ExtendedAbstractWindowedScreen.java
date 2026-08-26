package com.github.maximuslotro.lotrrextended.client.gui.screen;

import net.minecraft.util.text.ITextComponent;

public abstract class ExtendedAbstractWindowedScreen extends ExtendedAbstractBasicScreen {
	/** The X size of the inventory window in pixels. */
	protected int imageWidth;
	/** The Y size of the inventory window in pixels. */
	protected int imageHeight;
	/** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
	protected int leftPos;
	/** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
	protected int topPos;

	public ExtendedAbstractWindowedScreen(ITextComponent pTitle, int imageWidth, int imageHeight) {
		super(pTitle);
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	@Override
	public void init() {
		super.init();
		this.leftPos = (this.width - imageWidth) / 2;
		this.topPos = (this.height - imageHeight) / 2;
	}
}
