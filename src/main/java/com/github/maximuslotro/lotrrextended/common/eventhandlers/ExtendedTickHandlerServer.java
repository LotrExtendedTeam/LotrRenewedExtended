package com.github.maximuslotro.lotrrextended.common.eventhandlers;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;

import lotr.common.init.LOTRDimensions;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class ExtendedTickHandlerServer {

	public ExtendedTickHandlerServer() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onWorldTick$Extended(TickEvent.WorldTickEvent event) {
		if (event.side != LogicalSide.SERVER) {
			return;
		}
		ServerWorld world = (ServerWorld) event.world;
		ExtendedLevelData levelData = ExtendedLevelData.serverInstance();
		// Load data at beginning of server tick
		if (event.phase == TickEvent.Phase.START) {
            if (levelData.needsLoad()) {
                levelData.loadAndLock(world.getServer());
            }
            return;
        }
		// Don't allow ticking or saving if loading has not happened
		if (levelData.needsLoad()) {
			return;
		}
		if (world.dimension().equals(World.OVERWORLD)) {
			// Save when needs saving or every 30 seconds
			if (levelData.isDirty() || world.getGameTime() % 600L == 0L) {
				levelData.save(world.getServer());
			}
		} else if (world.dimension().equals(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
			levelData.tick(world);
		}
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
	    if (!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld) {
	        ServerWorld world = (ServerWorld) event.getWorld();
	        // Save data on world save
	        if (world.dimension().equals(World.OVERWORLD)) {
	            ExtendedLevelData levelData = ExtendedLevelData.serverInstance();
	            if (!levelData.needsLoad()) {
	                levelData.save(world.getServer());
	            }
	        }
	    }
	}

	@SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.getWorld();
            if (world.dimension().equals(World.OVERWORLD)) {
            	// IMPORTANT! Clear levelData on world unload, so data does not presist across saves!
                ExtendedLevelData.serverInstance().resetOnServerExit();
            }
        }
    }
}