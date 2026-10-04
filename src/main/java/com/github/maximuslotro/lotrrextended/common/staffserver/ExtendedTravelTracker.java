package com.github.maximuslotro.lotrrextended.common.staffserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedPlayerInfoHolder;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
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

	public static void handleServerTravelSecond() {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server == null) {
			return;
		}
		ExtendedPlayerInfoHolder playerData = ExtendedLevelData.serverInstance().getPlayerData();
		for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
			if(playerData.getPlayerInfo(player.getUUID()).isJailed()) {
				teleportToJail(server, player);
				continue;
			}
			ResourceLocation dim = player.level.dimension().location();
			checkDimensionRestrictions(player, dim);

			if (dim.equals(World.OVERWORLD.location()) && !playerData.getPlayerInfo(player.getUUID()).isJailed() && ExtendedModTracker.autoBanMap.containsKey("overworld_travel_spawn")) {
				checkOverworldSpawnBoundary(playerData, player);
			}

			if(playerData.getPlayerInfo(player.getUUID()).isJailed()) {
				teleportToJail(server, player);
			}
		}
	}

	protected static void checkDimensionRestrictions(ServerPlayerEntity player, ResourceLocation dim) {
		if (dim.equals(World.NETHER.location())) {
			handleJailingPlayer(player, "nether_travel", "NETHER");
		} else if (dim.equals(World.END.location())) {
			handleJailingPlayer(player, "end_travel", "END");
		} else if (dim.equals(World.OVERWORLD.location())) {
			handleJailingPlayer(player, "overworld_travel", "OVERWORLD");
		}
	}

	private static void teleportToJail(MinecraftServer server, ServerPlayerEntity player) {
		player.teleportTo(server.overworld(), -86.0F, 114.0F, 6.0F, 8.0F, 8.0F);
	}

	private static void checkOverworldSpawnBoundary(ExtendedPlayerInfoHolder playerData, ServerPlayerEntity player) {
		double dx = player.getX() - spawn.getX();
		double dz = player.getZ() - spawn.getZ();
		if ((dx * dx + dz * dz) > (SPAWN_RADIUS * SPAWN_RADIUS)) {
			playerData.setJailed(player.getUUID(), true);
			String logMsg = String.format("%s left overworld spawn at %s", player.getGameProfile().getName(), player.blockPosition());
			ExtendedTracker.logToFile(LOGGER, LOG_FILE, logMsg);
		}
	}

	private static void handleJailingPlayer(ServerPlayerEntity player, String key, String dimName) {
		if (!ExtendedModTracker.autoBanMap.containsKey(key)) {
			return;
		}
		ExtendedLevelData.serverInstance().getPlayerData().setJailed(player.getUUID(), true);
		String logMessage = String.format("%s entered %s at %s", player.getGameProfile().getName(), dimName, player.blockPosition());
		ExtendedTracker.logToFile(LOGGER, LOG_FILE, logMessage);

	}
}
