package com.github.maximuslotro.lotrrextended.client.gui.screen;

import com.github.maximuslotro.lotrrextended.client.gui.screen.inventory.*;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import net.minecraft.client.gui.ScreenManager;

public class ExtendedScreenRegisterHandler {
	public static void register() {
		ScreenManager.register(ExtendedContainerTypes.COIN_TRADING.get(), ExtendedCoinExchangeScreen::new);
		ScreenManager.register(ExtendedContainerTypes.TRADING.get(), ExtendedTradeScreen::new);
		ScreenManager.register(ExtendedContainerTypes.HIRED_UNIT_EQUIPMENT.get(), ExtendedHiredUnitEquipmentScreen::new);
		ScreenManager.register(ExtendedContainerTypes.HIRED_UNIT_CONTAINER.get(), ExtendedHiredUnitScreen::new);
		ScreenManager.register(ExtendedContainerTypes.STONE_GRINDER.get(), ExtendedStoneGrinderScreen::new);
		ScreenManager.register(ExtendedContainerTypes.EXTENDED_ANVIL.get(), ExtendedAnvilScreen::new);
		ScreenManager.register(ExtendedContainerTypes.EXTENDED_SMITHING.get(), ExtendedSmithingTableScreen::new);
		ScreenManager.register(ExtendedContainerTypes.EXTENDED_GRINDSTONE.get(), ExtendedGrindstoneScreen::new);
		ScreenManager.register(ExtendedContainerTypes.BOOKSHELF.get(), ExtendedBookshelfScreen::new);
		ScreenManager.register(ExtendedContainerTypes.CRUCIBLE.get(), ExtendedCrucibleScreen::new);
	}
}