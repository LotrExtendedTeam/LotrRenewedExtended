package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.data.ExtendedLevelData;
import lotr.common.init.LOTRDimensions;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class ExtendedTickHandlerServer {

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side == LogicalSide.SERVER) {

            ServerWorld world = (ServerWorld) event.world;
            TickEvent.Phase phase = event.phase;
            ExtendedLevelData levelData = ExtendedLevelData.serverInstance();
            if (phase == TickEvent.Phase.START) {
                if (LOTRDimensions.isDimension(world, World.OVERWORLD)) {
                    if (levelData.needsLoad()) {
                        levelData.load(world);
                    }
                }
            }
        }
    }

}
