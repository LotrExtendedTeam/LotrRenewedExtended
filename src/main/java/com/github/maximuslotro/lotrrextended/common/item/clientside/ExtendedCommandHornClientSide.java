package com.github.maximuslotro.lotrrextended.common.item.clientside;

import lotr.client.gui.ExtendedHornSelectScreen;
import net.minecraft.client.Minecraft;

public class ExtendedCommandHornClientSide {
	public void openMenu() {
		Minecraft.getInstance().setScreen(new ExtendedHornSelectScreen());
	}
}
