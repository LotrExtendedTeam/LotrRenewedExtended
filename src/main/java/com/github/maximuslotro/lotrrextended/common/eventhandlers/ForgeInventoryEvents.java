package com.github.maximuslotro.lotrrextended.common.eventhandlers;


import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.inv.ExtendedCoinExchangeContainer;
import lotr.common.inv.ExtendedTradeContainer;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

            container.trader.removePlayerFromTrading(event.getPlayer());
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

            container.trader.removePlayerFromTrading(event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        if (event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof SpawnEggItem) {
           if (event.getTarget() instanceof ExtendedTraderEntity) {
               ExtendedTraderEntity trader = (ExtendedTraderEntity) event.getTarget();

               if (trader.canTradeWith(event.getPlayer())) {
                   ExtendedTraderHelper.openTraderGui((NPCEntity) event.getTarget(), event.getPlayer(), trader);
               } else {
                   if (!event.getPlayer().level.isClientSide)
                       event.getPlayer().sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), event.getPlayer().getUUID());
               }
               event.setCanceled(true);
           }
        }
    }

}
