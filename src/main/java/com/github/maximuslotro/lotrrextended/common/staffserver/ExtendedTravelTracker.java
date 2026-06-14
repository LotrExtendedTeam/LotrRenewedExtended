package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedTravelTracker implements ExtendedTracker {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Path TRACKING_DIR = ExtendedServerData.EXTENDED_DIR.resolve("travelchecker/");
	private static final Path LOG_FILE = TRACKING_DIR.resolve("violations.log");

	private static final int SPAWN_RADIUS = 70;
	private static final BlockPos spawn = new BlockPos(-86, 114, 6);

	public static void load() {
		try {
			Files.createDirectories(TRACKING_DIR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void handlePlayerDimChange(ServerPlayerEntity player, RegistryKey<World> dim) {
		ResourceLocation toDim = dim.location();
		if (toDim.equals(World.NETHER.location())) {
			handleNetherPlayer(player.getGameProfile().getId(), player);
		} else if (toDim.equals(World.END.location())) {
			handleEndPlayer(player.getGameProfile().getId(), player);
		} else if (toDim.equals(World.OVERWORLD.location())) {
			handleOverworldPlayer(player.getGameProfile().getId(), player);
		}
	}

	public static void handleServerTravelTick() {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server == null) {
			return;
		}
		for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(player.getGameProfile().getId());
			if(info.isInJail()) {
				player.teleportTo(server.overworld(), -86, 114, 6, 8, 8);
				return;
			}
			ResourceLocation dim = player.level.dimension().location();
			if (dim.equals(World.NETHER.location())) {
				handleNetherPlayer(player.getGameProfile().getId(), player);
			} else if (dim.equals(World.END.location())) {
				handleEndPlayer(player.getGameProfile().getId(), player);
			} else if (dim.equals(World.OVERWORLD.location())) {
				handleOverworldPlayer(player.getGameProfile().getId(), player);
			}else if (dim.equals(World.OVERWORLD.location()) && ExtendedModTracker.autoBanMap.containsKey("overworld_travel_spawn")) {
				double dx = player.getX() - spawn.getX();
				double dz = player.getZ() - spawn.getZ();
				double distSq = dx * dx + dz * dz;
				if (distSq > SPAWN_RADIUS * SPAWN_RADIUS) {
					info.sendToJail(true);
					ExtendedTracker.logToFile(LOGGER, LOG_FILE, player.getGameProfile().getName() + " left overworld spawn at " + player.blockPosition());
				}
			}
			if(info.isInJail()) {
				player.teleportTo(server.overworld(), -86, 114, 6, 8, 8);
			}
		}
	}

	private static void handleNetherPlayer(UUID uuid, ServerPlayerEntity player) {
		if (ExtendedModTracker.autoBanMap.containsKey("nether_travel")) {
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(uuid);
			info.sendToJail(true);
			ExtendedTracker.logToFile(LOGGER, LOG_FILE, player.getGameProfile().getName() + " entered NETHER at " + player.blockPosition());
		}
	}
	private static void handleEndPlayer(UUID uuid, ServerPlayerEntity player) {
		if (ExtendedModTracker.autoBanMap.containsKey("end_travel")) {
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(uuid);
			info.sendToJail(true);
			ExtendedTracker.logToFile(LOGGER, LOG_FILE, player.getGameProfile().getName() + " entered END at " + player.blockPosition());
		}
	}
	private static void handleOverworldPlayer(UUID uuid, ServerPlayerEntity player) {
		if (ExtendedModTracker.autoBanMap.containsKey("overworld_travel")) {
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(uuid);
			info.sendToJail(true);
			ExtendedTracker.logToFile(LOGGER, LOG_FILE, player.getGameProfile().getName() + " entered OVERWORLD at " + player.blockPosition());
		}
	}
}
