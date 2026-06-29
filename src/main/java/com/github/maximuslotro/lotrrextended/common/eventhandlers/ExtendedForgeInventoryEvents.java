package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedHiredUnitEquipmentContainer;
import com.github.maximuslotro.lotrrextended.common.inventory.container.ExtendedTradeContainer;

import lotr.common.entity.npc.ExtendedNPCEntity;
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
            if (entity instanceof NPCEntity) {
            	((ExtendedNPCEntity)entity).getUnitInfo().isInteracting = false;
            }
        } else if (event.getContainer() instanceof ExtendedHiredUnitEquipmentContainer) {
            if (entity instanceof NPCEntity) {
            	((ExtendedNPCEntity)entity).getUnitInfo().isInteracting = false;
            }
        }
    }
}