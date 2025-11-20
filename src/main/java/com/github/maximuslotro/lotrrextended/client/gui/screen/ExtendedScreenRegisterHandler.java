package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedAnvilScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedGrindstoneScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedSmithingTableScreen;
import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.ExtendedStoneGrinderScreen;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import net.minecraft.client.gui.ScreenManager;

public class ExtendedScreenRegisterHandler {
	public static void register() {
		ScreenManager.register(ExtendedContainerTypes.STONE_GRINDER.get(), ExtendedStoneGrinderScreen::new);
		ScreenManager.register(ExtendedContainerTypes.EXTENDED_ANVIL.get(), ExtendedAnvilScreen::new);
		ScreenManager.register(ExtendedContainerTypes.EXTENDED_SMITHING.get(), ExtendedSmithingTableScreen::new);
		ScreenManager.register(ExtendedContainerTypes.EXTENDED_GRINDSTONE.get(), ExtendedGrindstoneScreen::new);
	}
}