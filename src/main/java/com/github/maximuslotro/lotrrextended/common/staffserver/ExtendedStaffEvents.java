package com.github.maximuslotro.lotrrextended.common.staffserver;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedStaffEvents {

	@SubscribeEvent
	public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!(event.getPlayer() instanceof ServerPlayerEntity)) return;
		if (!ExtendedServerConfig.enableStaffServer.get()) return;
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		ExtendedTravelTracker.checkDimensionRestrictions(player, event.getTo().location());
	}

	private static int tickCounter = 0;

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.END && !ExtendedServerConfig.enableStaffServer.get()) {
			return;
		}
		tickCounter++;
		if (tickCounter < 20) {
			return;
		}
		tickCounter = 0;
		ExtendedTravelTracker.handleServerTravelSecond();
		ExtendedCombatTracker.handleServerTick();
	}

	@SubscribeEvent
	public static void playerDeathEvent(LivingDeathEvent event) {
		if(event.getEntityLiving() instanceof PlayerEntity) {
			ExtendedCombatTracker.playerDeathEvent((PlayerEntity) event.getEntityLiving());
		}
	}

	@SubscribeEvent
	public static void playerDamagedEvent(LivingAttackEvent event) {
		if(event.getEntityLiving() instanceof ServerPlayerEntity && event.getSource().getEntity() instanceof ServerPlayerEntity) {
			ExtendedCombatTracker.playerDamagedEvent((ServerPlayerEntity)event.getEntityLiving(), (ServerPlayerEntity)event.getSource().getEntity());
		}
	}

	public static void registerStaffCommands() {
		CommandDispatcher<CommandSource> dispatcher = ServerLifecycleHooks.getCurrentServer().getCommands().getDispatcher();
		ExtendedSpawnCommand.register(dispatcher);
		ExtendedModControlCommand.register(dispatcher);
	}
}
