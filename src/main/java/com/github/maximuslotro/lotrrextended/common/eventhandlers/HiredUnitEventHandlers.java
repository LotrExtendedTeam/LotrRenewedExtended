package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import lotr.common.entity.npc.ExtendedHiredBreeGuard;
import lotr.common.util.CoinUtils;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
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

}
