package com.github.maximuslotro.lotrrextended.common.staffserver;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSDataRequestPacket;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedStaffEvents {

	@SubscribeEvent
	public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getPlayer() instanceof ServerPlayerEntity && ExtendedServerConfig.enableStaffServer.get()) {
			ExtendedPlayerInfo.login(event.getPlayer().getUUID());
			ExtendedPacketHandler.sendToClient(ExtendedSDataRequestPacket.requestModListPacket(), (ServerPlayerEntity) event.getPlayer());
		}
	}

	@SubscribeEvent
	public static void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
		if (event.getPlayer() instanceof ServerPlayerEntity && ExtendedServerConfig.enableStaffServer.get()) {
			ExtendedPlayerInfo.logout(event.getPlayer().getUUID());
			ExtendedSpecialConfig.onPlayerLoggout(event.getPlayer().getUUID());
		}
	}

	public static void registerStaffCommands() {
		CommandDispatcher<CommandSource> dispatcher = ServerLifecycleHooks.getCurrentServer().getCommands().getDispatcher();
		ExtendedSpawnCommand.register(dispatcher);
		ExtendedModControlCommand.register(dispatcher);
		ExtendedFirstLastSeenCommand.register(dispatcher);
	}
}
