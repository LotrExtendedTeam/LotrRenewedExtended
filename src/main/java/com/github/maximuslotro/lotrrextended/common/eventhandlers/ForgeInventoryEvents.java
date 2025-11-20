package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedCoinExchangeContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * For {@link net.minecraftforge.eventbus.api.Event} that are fired on the MinecraftForge.EVENT_BUS
 * */
public class ForgeInventoryEvents {

	@SubscribeEvent
	public static void coinExchangeClose(final PlayerContainerEvent.Close event) {
		if (event.getContainer() instanceof ExtendedCoinExchangeContainer) {
			ExtendedCoinExchangeContainer container = (ExtendedCoinExchangeContainer) event.getContainer();
			container.trader.removePlayerFromInteracting(event.getPlayer());
		} else if (event.getContainer() instanceof ExtendedTradeContainer) {
			ExtendedTradeContainer container = (ExtendedTradeContainer) event.getContainer();
			container.trader.removePlayerFromInteracting(event.getPlayer());
		}
	}
}