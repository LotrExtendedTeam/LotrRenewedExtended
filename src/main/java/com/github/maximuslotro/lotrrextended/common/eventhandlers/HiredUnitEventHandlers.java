package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.ExtendedHiredUnitEquipmentContainer;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.util.CoinUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class HiredUnitEventHandlers {

    /**
     * TODO: This is only temporary to allow guards to be hired until I write a mixin for the LOTRSpawnEggItem
     * @param event
     */
    @SubscribeEvent
    public static void hireBreeGuard(PlayerInteractEvent.EntityInteract event) {
        // only allow this event to run on the server
        if (!(event.getWorld() instanceof ServerWorld)) {
            return;
        }

        if (!(event.getTarget() instanceof ExtendedHiredBreeGuard)) {
            return;
        }

        ExtendedHiredBreeGuard guard = (ExtendedHiredBreeGuard) event.getTarget();
        ExtendedLog.info("Event called on ExtendedHiredBreeGuard");

        // check that they have a coin in their hand
        if (!CoinUtils.isValidCoin(event.getItemStack())) {
            return;
        }

        int coins = CoinUtils.totalValueInInventory(event.getPlayer().inventory);
        if (coins < 40) {
            event.getPlayer().sendMessage(new StringTextComponent("I require 40 coins in payment to be hired."), event.getPlayer().getUUID());
            return;
        }

        guard.tame(event.getPlayer());
        ExtendedLog.info("EntityBreeGuard tamed");
    }

    @SubscribeEvent
    public static void playerCloseInventory(final PlayerContainerEvent.Close event) {
        if (!((event.getContainer() instanceof ExtendedHiredUnitContainer) ||
                (event.getContainer() instanceof ExtendedHiredUnitEquipmentContainer))) {
            return;
        }

        Entity entity;
        if (event.getContainer() instanceof ExtendedHiredUnitContainer) {
            entity = event.getPlayer().level.getEntity(((ExtendedHiredUnitContainer) event.getContainer()).getEntityId());
        } else {
            entity = event.getPlayer().level.getEntity(((ExtendedHiredUnitEquipmentContainer) event.getContainer()).getEntityId());
        }

        // make companion no longer stationary
        if (event.getContainer() instanceof ExtendedHiredUnitContainer) {
            if (entity instanceof ExtendedHiredBreeGuard) {
                ((ExtendedHiredBreeGuard) entity).setInventoryOpen(false);
            }
        } else if (event.getContainer() instanceof ExtendedHiredUnitEquipmentContainer) {
            if (entity instanceof ExtendedHiredBreeGuard) {
                ((ExtendedHiredBreeGuard) entity).setEquipmentOpen(false);
            }
        }
    }

}
