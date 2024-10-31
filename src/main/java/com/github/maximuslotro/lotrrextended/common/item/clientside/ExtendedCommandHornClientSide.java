package com.github.maximuslotro.lotrrextended.common.item.clientside;

import com.github.maximuslotro.lotrrextended.client.gui.screen.ExtendedHornSelectScreen;

import net.minecraft.client.Minecraft;

public class ExtendedCommandHornClientSide {
	public void openMenu() {
		Minecraft.getInstance().setScreen(new ExtendedHornSelectScreen());
	}
}
