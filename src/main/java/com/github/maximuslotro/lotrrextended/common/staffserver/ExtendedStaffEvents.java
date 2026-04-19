package com.github.maximuslotro.lotrrextended.common.staffserver;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedStaffEvents {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Path TRACKING_DIR = Paths.get("extended/travelchecker/");
	private static final Path LOG_FILE = TRACKING_DIR.resolve("violations.log");
	@SubscribeEvent
	public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!(event.getPlayer() instanceof ServerPlayerEntity)) return;
		if (!ExtendedServerConfig.enableStaffServer.get()) return;
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		ResourceLocation toDim = event.getTo().location();
		if (toDim.equals(World.NETHER.location())) {
			handleNetherPlayer(player.getGameProfile().getId(), player);
		} else if (toDim.equals(World.END.location())) {
			handleEndPlayer(player.getGameProfile().getId(), player);
		} else if (toDim.equals(World.OVERWORLD.location())) {
			handleOverworldPlayer(player.getGameProfile().getId(), player);
		}
	}

	private static int tickCounter = 0;
	private static final int SPAWN_RADIUS = 70;
	private static final BlockPos spawn = new BlockPos(-86, 114, 6);

	public static void logToFile(String message) {
		try {
			Files.createDirectories(TRACKING_DIR);
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.warn(message);
		try {
			Files.write(LOG_FILE, (message + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE,  StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;
		if (!ExtendedServerConfig.enableStaffServer.get()) return;
		tickCounter++;
		if (tickCounter < 20) {
			return;
		}
		tickCounter = 0;
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server == null) return;
		for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
			ResourceLocation dim = player.level.dimension().location();
			if (dim.equals(World.NETHER.location())) {
				handleNetherPlayer(player.getGameProfile().getId(), player);
			} else if (dim.equals(World.END.location())) {
				handleEndPlayer(player.getGameProfile().getId(), player);
			} else if (dim.equals(World.OVERWORLD.location())) {
				handleOverworldPlayer(player.getGameProfile().getId(), player);
			}
			if (dim.equals(World.OVERWORLD.location()) && ExtendedSpecialConfig.autoBanMap.containsKey("overworld_travel_spawn")) {
				double dx = player.getX() - spawn.getX();
				double dz = player.getZ() - spawn.getZ();
				double distSq = dx * dx + dz * dz;
				if (distSq > SPAWN_RADIUS * SPAWN_RADIUS) {
					ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(player.getGameProfile().getId());
					info.setInTrouble(true);
					logToFile(player.getGameProfile().getName() + " left overworld spawn at " + player.blockPosition());
					player.teleportTo(server.overworld(), -86, 114, 6, 8, 8);
				}
			}
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(player.getGameProfile().getId());
			if(info.isInTrouble()) {
				player.teleportTo(server.overworld(), -86, 114, 6, 8, 8);
			}
		}
	}

	private static void handleNetherPlayer(UUID uuid, ServerPlayerEntity player) {
		if (ExtendedSpecialConfig.autoBanMap.containsKey("nether_travel")) {
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(uuid);
			info.setInTrouble(true);
			logToFile(player.getGameProfile().getName() + " entered NETHER at " + player.blockPosition());
		}
	}
	private static void handleEndPlayer(UUID uuid, ServerPlayerEntity player) {
		if (ExtendedSpecialConfig.autoBanMap.containsKey("end_travel")) {
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(uuid);
			info.setInTrouble(true);
			logToFile(player.getGameProfile().getName() + " entered END at " + player.blockPosition());
		}
	}
	private static void handleOverworldPlayer(UUID uuid, ServerPlayerEntity player) {
		if (ExtendedSpecialConfig.autoBanMap.containsKey("overworld_travel")) {
			ExtendedPlayerInfo info = ExtendedPlayerInfo.getPlayerInfo(uuid);
			info.setInTrouble(true);
			logToFile(player.getGameProfile().getName() + " entered OVERWORLD at " + player.blockPosition());
		}
	}

	@SubscribeEvent
	public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getPlayer() instanceof ServerPlayerEntity && ExtendedServerConfig.enableStaffServer.get()) {
			ExtendedPlayerInfo.login(event.getPlayer().getGameProfile().getId());
		}
	}

	@SubscribeEvent
	public static void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
		if (event.getPlayer() instanceof ServerPlayerEntity && ExtendedServerConfig.enableStaffServer.get()) {
			ExtendedPlayerInfo.logout(event.getPlayer().getGameProfile().getId());
		}
	}

	public static void registerStaffCommands() {
		CommandDispatcher<CommandSource> dispatcher = ServerLifecycleHooks.getCurrentServer().getCommands().getDispatcher();
		ExtendedSpawnCommand.register(dispatcher);
		ExtendedModControlCommand.register(dispatcher);
		ExtendedFirstLastSeenCommand.register(dispatcher);
	}
}
