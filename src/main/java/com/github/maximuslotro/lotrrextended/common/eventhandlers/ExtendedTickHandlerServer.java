package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import lotr.common.data.ExtendedLevelData;
import lotr.common.init.LOTRDimensions;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class ExtendedTickHandlerServer {

	public ExtendedTickHandlerServer() {
		MinecraftForge.EVENT_BUS.register(this);
	}

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            ServerWorld world = (ServerWorld) event.world;
            TickEvent.Phase phase = event.phase;
            ExtendedLevelData levelData = ExtendedLevelData.serverInstance();

			// make sure data is loaded at beginning of tick
			if (phase == TickEvent.Phase.START) {

				//Ensure we only load level data when the main dimension ticks
				if (LOTRDimensions.isDimension(world, World.OVERWORLD)) {
					if (levelData.needsLoad()) {
						levelData.load(world);
					}
				}
			}

            // Ensure we append all our actions to the "queue" at the end of the server tick
            if (phase == TickEvent.Phase.END) {

				//Ensure we only save level data when the main dimension ticks
				if (LOTRDimensions.isDimension(world, World.OVERWORLD)) {

					// Save when needs saving
					if (levelData.doesNeedSaving()) {
						levelData.save(world);
					} // Save every 30 seconds
					else if (world.getGameTime() % 600L == 0L) {
						levelData.save(world);
					}
                } else if (LOTRDimensions.isDimension(world, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
                	// Ensure we run these functions only when Middle Earth ticks

					// only decrement player cooldowns once a second (stored in seconds not ticks)
					if (world.getGameTime() % 20L == 0L) {
						levelData.decrementPlayerTraderCooldowns(world);
						levelData.incrementPlayerTimeInBarrows(world);
						levelData.decrementPlayerBanditCooldowns(world);
						levelData.reducePlayerInteractedWithInvasionTimes();
					}

					// run tolkien spawns every five minutes
					if (world.getGameTime() % 6000L == 0L) {
						levelData.checkForTolkienSpawns(world);
					}

					// wandering trader and bandit spawns should be done every tick
                	levelData.checkForWanderingTraderSpawns(world);
					levelData.checkForBanditSpawns(world);
				}
            }
        }
    }
}