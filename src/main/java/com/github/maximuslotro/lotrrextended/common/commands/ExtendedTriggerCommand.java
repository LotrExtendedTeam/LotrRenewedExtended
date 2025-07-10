package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lotr.common.entity.npc.ExtendedTolkienEntity;
import lotr.common.world.spawning.WanderingTradersSpawner;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ExtendedTriggerCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedTrigger")
				.requires(context -> context.hasPermission(2))
				.then(Commands.literal("spawnWanderingTrader")
					.executes(context -> triggerTrader(context.getSource()))
					)
				.then(Commands.literal("spawnTolkien")
					.executes(context -> triggerTolkien(context.getSource()))
					)
				.then(Commands.literal("wightJumpscare")
					.executes(context -> triggerJumpscare(context.getSource()))
					)
				);
	}
	
	public static int triggerTrader(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player =source.getPlayerOrException();
		WanderingTradersSpawner.spawn(player.getLevel(), player);
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerTolkien(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player =source.getPlayerOrException();
		ExtendedTolkienEntity.spawn(player.level, player);
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerJumpscare(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player =source.getPlayerOrException();
		player.getLevel().sendParticles((ServerPlayerEntity) player, ExtendedParticles.BARROW_WIGHT_JUMPSCARE.get(), false, player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 1);
		return Command.SINGLE_SUCCESS;
	}
}
