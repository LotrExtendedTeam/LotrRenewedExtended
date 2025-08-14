package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedStoneGrinderScreen;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainers;

import net.minecraft.client.gui.ScreenManager;

public class ExtendedScreenRegisterHandler {
	public static void register() {
		ScreenManager.register(ExtendedContainers.STONE_GRINDER.get(), ExtendedStoneGrinderScreen::new);
	}
}