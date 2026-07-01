package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitOverviewContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import lotr.common.entity.npc.ExtendedINPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * For {@link net.minecraftforge.eventbus.api.Event} that are fired on the MinecraftForge.EVENT_BUS
 * */
@Deprecated
public class ExtendedForgeInventoryEvents {

	@SubscribeEvent
	public static void coinExchangeClose(final PlayerContainerEvent.Close event) {
		if (event.getContainer() instanceof ExtendedTradeContainer) {
			ExtendedTradeContainer container = (ExtendedTradeContainer) event.getContainer();
			container.trader.removePlayerFromInteracting(event.getPlayer());
		}
	}

    @SubscribeEvent
    public static void playerCloseInventory(final PlayerContainerEvent.Close event) {
        if (!((event.getContainer() instanceof ExtendedHiredUnitOverviewContainer) ||
                (event.getContainer() instanceof ExtendedHiredUnitEquipmentContainer))) {
            return;
        }

        Entity entity;
        if (event.getContainer() instanceof ExtendedHiredUnitOverviewContainer) {
            entity = event.getPlayer().level.getEntity(((ExtendedHiredUnitOverviewContainer) event.getContainer()).getEntityId());
        } else {
            entity = event.getPlayer().level.getEntity(((ExtendedHiredUnitEquipmentContainer) event.getContainer()).getEntityId());
        }

        // make companion no longer stationary
        if (event.getContainer() instanceof ExtendedHiredUnitOverviewContainer) {
            if (entity instanceof NPCEntity) {
            	((ExtendedINPCEntity)entity).getUnitInfo().isInteracting = false;
            }
        } else if (event.getContainer() instanceof ExtendedHiredUnitEquipmentContainer) {
            if (entity instanceof NPCEntity) {
            	((ExtendedINPCEntity)entity).getUnitInfo().isInteracting = false;
            }
        }
    }
}