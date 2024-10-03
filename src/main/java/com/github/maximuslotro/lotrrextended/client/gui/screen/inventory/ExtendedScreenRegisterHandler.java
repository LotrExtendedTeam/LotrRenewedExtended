package com.github.maximuslotro.lotrrextended.client.gui.screen.inventory;

import lotr.common.init.ExtendedContainers;
import net.minecraft.client.gui.ScreenManager;

public class ExtendedScreenRegisterHandler {
	public static void register() {
		ScreenManager.register(ExtendedContainers.MILLSTONE.get(), ExtendedGrinderScreen::new);
	}
}