package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;

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
	public void onWorldTick$Extended(TickEvent.WorldTickEvent event) {
		if (event.side == LogicalSide.SERVER) {
			ServerWorld world = (ServerWorld) event.world;
			TickEvent.Phase phase = event.phase;
			ExtendedLevelData levelData = ExtendedLevelData.serverInstance();
			// Load data at beginning of server tick
			if (phase == TickEvent.Phase.START && world.dimension().equals(World.OVERWORLD)) {
				if (levelData.needsLoad()) {
					levelData.load(world);
				}
				return;
			}
			// Append all actions at end of the server tick
			if (phase == TickEvent.Phase.END) {
				// Fix data potentially being ticked or modified before loaded
				if (levelData.needsLoad()) {
					return;
				}
				if (world.dimension().equals(World.OVERWORLD)) {
					// Save when needs saving or every 30 seconds
					if (levelData.isDirty() || world.getGameTime() % 600L == 0L) {
						levelData.save(world);
					}
				} else if (world.dimension().equals(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
					levelData.tick(world);
				}
			}
		}
	}
}