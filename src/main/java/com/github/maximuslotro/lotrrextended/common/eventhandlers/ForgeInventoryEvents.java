package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.inventory.ExtendedCoinExchangeContainer;
import lotr.common.inventory.ExtendedTradeContainer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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

			if (!event.getPlayer().level.isClientSide) {
				PlayerEntity player = event.getPlayer();
				World world = player.level;

				if (!container.leftOutputSlot.getItem().isEmpty()) {
					world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), container.leftOutputSlot.getItem()));
				}
				if (!container.rightOutputSlot.getItem().isEmpty()) {
					world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), container.rightOutputSlot.getItem()));
				}
				if (!container.inputSlot.getItem().isEmpty()) {
					world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), container.inputSlot.getItem()));
				}
			}

			container.trader.removePlayerFromInteracting(event.getPlayer());
		} else if (event.getContainer() instanceof ExtendedTradeContainer) {
			ExtendedTradeContainer container = (ExtendedTradeContainer) event.getContainer();

			if (!event.getPlayer().level.isClientSide) {
				PlayerEntity player = event.getPlayer();
				World world = player.level;

				if (!container.sellInventory.isEmpty()) {
					for (int i = 0; i < container.sellInventory.getContainerSize(); i++) {
						ItemStack stack = container.sellInventory.getItem(i);
						if (stack.isEmpty()) return;

						world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), stack));
					}
				}
			}

			container.trader.removePlayerFromInteracting(event.getPlayer());
		}
	}
}