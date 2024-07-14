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
        System.out.println("onWorldTickEvent run");
        if (event.side == LogicalSide.SERVER) {
            System.out.println("onWorldTickEvent: side = SERVER");
            ServerWorld world = (ServerWorld) event.world;
            TickEvent.Phase phase = event.phase;
            ExtendedLevelData levelData = ExtendedLevelData.serverInstance();

            // make sure data is loaded at beginning of tick
            if (phase == TickEvent.Phase.START) {
                System.out.println("onWorldTickEvent: phase = START");
                // TODO: Confused on this part whether it would only get run in the Overworld or in the ME dimension
                //  doesn't seem to get here either way currently so I am not sure
                if (LOTRDimensions.isDimension(world, World.OVERWORLD)) {
                    System.out.println("onWorldTickEvent: dimension = LOTR");
                    if (levelData.needsLoad()) {
                        levelData.load(world);
                    }
                }
            }

            // perform spawning at end of tick
            if (phase == TickEvent.Phase.END && LOTRDimensions.isDimension(world, World.OVERWORLD)) {
                levelData.performTravellingTraderSpawns(world);
            }

            // save at end of tick
            if (phase == TickEvent.Phase.END && LOTRDimensions.isDimension(world, World.OVERWORLD)) {
                levelData.save(world);
            }
        } else {
            System.out.println("ClientTickEvent: side = " + event.side);
        }
    }

}
