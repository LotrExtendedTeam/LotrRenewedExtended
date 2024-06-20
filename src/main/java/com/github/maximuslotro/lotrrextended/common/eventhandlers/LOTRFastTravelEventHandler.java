package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.event.LOTRFastTravelWaypointEvent;
import lotr.common.util.ExtendedTeleportHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LOTRFastTravelEventHandler {

    @SubscribeEvent
    public static void onPlayerLOTRWaypoint(LOTRFastTravelWaypointEvent event) {
        ExtendedTeleportHelper.teleportUnitsToPlayer(event.getOriginalPos(), event.getTravelPos(), event.getWorld(), event.getPlayer());
    }

}
