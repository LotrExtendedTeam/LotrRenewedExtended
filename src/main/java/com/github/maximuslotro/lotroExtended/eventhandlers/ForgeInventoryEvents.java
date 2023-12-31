package com.github.maximuslotro.lotroExtended.eventhandlers;

import lotr.common.inv.ExtendedCoinExchangeContainer;
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
       if (event.getContainer() instanceof ExtendedCoinExchangeContainer && !event.getPlayer().level.isClientSide) {
           System.out.println("CoinExchangeContainer closed!");

           ExtendedCoinExchangeContainer container = (ExtendedCoinExchangeContainer) event.getContainer();
           PlayerEntity player = event.getPlayer();
           World world = player.level;
           if (container.exchanged) {
               if (!container.leftSlot.getItem().equals(ItemStack.EMPTY)) {
                   world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), container.leftSlot.getItem()));
               }
               if (!container.rightSlot.getItem().equals(ItemStack.EMPTY)) {
                   world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), container.rightSlot.getItem()));
               }
           }
           if (!container.inputSlot.getItem().equals(ItemStack.EMPTY)) {
               world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), container.inputSlot.getItem()));
           }
       }
    }

}
