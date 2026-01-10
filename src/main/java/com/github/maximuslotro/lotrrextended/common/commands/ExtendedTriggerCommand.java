package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.entity.special.ExtendedInvasionSpawnerEntity;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lotr.common.entity.npc.ExtendedTolkienEntity;
import lotr.common.world.spawning.BanditsSpawner;
import lotr.common.world.spawning.InvasionBiomeList;
import lotr.common.world.spawning.InvasionSpawner;
import lotr.common.world.spawning.WanderingTradersSpawner;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedTriggerCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedTrigger")
			.requires(context -> context.hasPermission(2))
			.then(Commands.literal("spawnWanderingTrader")
				.executes(context -> triggerTrader(context.getSource())))
			.then(Commands.literal("spawnTolkien")
				.executes(context -> triggerTolkien(context.getSource())))
			.then(Commands.literal("wightJumpscare")
				.executes(context -> triggerJumpscare(context.getSource())))
			.then(Commands.literal("attemptBanditSpawn")
				.executes(context -> triggerBanditSpawn(context.getSource())))
			.then(Commands.literal("banditSpawn")
				.executes(context -> triggerGuaranteedBanditSpawn(context.getSource())))
			.then(Commands.literal("invasionTest")
				.executes(context -> triggerInvasionTest(context.getSource()))));
	}

	public static int triggerTrader(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.getPlayerOrException();
		WanderingTradersSpawner.spawn(player.getLevel(), player);
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerGuaranteedBanditSpawn(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.getPlayerOrException();
		BanditsSpawner.spawn(player.getLevel(), player, true, true);
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerBanditSpawn(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.getPlayerOrException();
		boolean result = BanditsSpawner.spawn(player.getLevel(), player, false, false);
		if (result) {
			source.sendSuccess(new StringTextComponent("Attempt to spawn bandit was successful."), true);
		} else {
			source.sendSuccess(new StringTextComponent("Attempt to spawn bandit was unsuccessful."), true);
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerInvasionTest(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.getPlayerOrException();
		boolean result = InvasionSpawner.requireInvasionSpawn(player.getLevel(), player);
		if (result) {
			source.sendSuccess(new StringTextComponent("An invasion was successfully spawned."), true);
		} else {
			source.sendFailure(new StringTextComponent("Could not find an invasion to spawn here."));
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerTolkien(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.getPlayerOrException();
		ExtendedTolkienEntity.spawn(player.level, player);
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerJumpscare(CommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.getPlayerOrException();
		player.getLevel().sendParticles((ServerPlayerEntity) player, ExtendedParticles.BARROW_WIGHT_JUMPSCARE.get(), false, player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 1);
		return Command.SINGLE_SUCCESS;
	}
}