package com.github.maximuslotro.lotrrextended.common.commands;

import java.util.Collection;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedParticles;
import com.github.maximuslotro.lotrrextended.common.world.spawning.BanditsSpawner;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import lotr.common.entity.npc.ExtendedTolkienEntity;
import lotr.common.world.spawning.InvasionSpawner;
import lotr.common.world.spawning.WanderingTradersSpawner;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedTriggerCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedTrigger")
			.requires(context -> context.hasPermission(2))
			.then(Commands.literal("attemptWanderingTraderSpawn")
				.then(Commands.argument("targets", EntityArgument.players())
					.then(Commands.argument("disableBanner", BoolArgumentType.bool())
						.then(Commands.argument("disableAlignment", BoolArgumentType.bool())
							.executes(context -> triggerTraderSpawn(context.getSource(), BoolArgumentType.getBool(context, "disableBanner"), 
								BoolArgumentType.getBool(context, "disableAlignment"), EntityArgument.getPlayers(context, "targets")))))))
			.then(Commands.literal("wanderingTraderSpawn")
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(context -> triggerGuaranteedTraderSpawn(context.getSource(), EntityArgument.getPlayers(context, "targets")))))
			.then(Commands.literal("attemptBanditSpawn")
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(context -> triggerBanditSpawn(context.getSource(), EntityArgument.getPlayers(context, "targets")))))
			.then(Commands.literal("banditSpawn")
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(context -> triggerGuaranteedBanditSpawn(context.getSource(), EntityArgument.getPlayers(context, "targets")))))
			.then(Commands.literal("spawnTolkien")
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(context -> triggerTolkien(context.getSource(), EntityArgument.getPlayers(context, "targets")))))
			.then(Commands.literal("wightJumpscare")
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(context -> triggerJumpscare(context.getSource(), EntityArgument.getPlayers(context, "targets")))))
			.then(Commands.literal("invasion")
				.then(Commands.argument("targets", EntityArgument.players())
					.executes(context -> triggerInvasionTest(context.getSource(), EntityArgument.getPlayers(context, "targets"))))));
	}

	public static int triggerGuaranteedTraderSpawn(CommandSource source, Collection<? extends ServerPlayerEntity> pTargets) throws CommandSyntaxException {
		for(ServerPlayerEntity serverplayerentity : pTargets) {
			WanderingTradersSpawner.attemptSpawn(serverplayerentity.getLevel(), serverplayerentity, false, false);
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerTraderSpawn(CommandSource source, boolean disableBanner, boolean disableAlignment, Collection<? extends ServerPlayerEntity> pTargets) throws CommandSyntaxException {
		for(ServerPlayerEntity serverplayerentity : pTargets) {
			boolean result = WanderingTradersSpawner.attemptSpawn(serverplayerentity.getLevel(), serverplayerentity, disableBanner, disableAlignment);
			if(result) {
				source.sendSuccess(new TranslationTextComponent("command.lotrextended.trigger.trader.success", serverplayerentity.getDisplayName()), true);
			} else {
				source.sendSuccess(new TranslationTextComponent("command.lotrextended.trigger.trader.failure", serverplayerentity.getDisplayName()), true);
			}
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerGuaranteedBanditSpawn(CommandSource source, Collection<? extends ServerPlayerEntity> pTargets) throws CommandSyntaxException {
		for(ServerPlayerEntity player : pTargets) {
			BanditsSpawner.attemptSpawn(player.getLevel(), player, true);
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerBanditSpawn(CommandSource source, Collection<? extends ServerPlayerEntity> pTargets) throws CommandSyntaxException {
		for (ServerPlayerEntity player : pTargets) {
			boolean result = BanditsSpawner.attemptSpawn(player.getLevel(), player, false);
			if (result) {
				source.sendSuccess(new TranslationTextComponent("command.lotrextended.trigger.bandit.success", player.getDisplayName()), true);
			} else {
				source.sendSuccess(new TranslationTextComponent("command.lotrextended.trigger.bandit.failure", player.getDisplayName()), true);
			}
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerInvasionTest(CommandSource source, Collection<? extends ServerPlayerEntity> pTargets) throws CommandSyntaxException {
		for (ServerPlayerEntity player : pTargets) {
			boolean result = InvasionSpawner.requireInvasionSpawn(player.getLevel(), player);
			if (result) {
				source.sendSuccess(new TranslationTextComponent("command.lotrextended.trigger.invasion.success", player.getDisplayName()), true);
			} else {
				source.sendFailure(new TranslationTextComponent("command.lotrextended.trigger.invasion.failure", player.getDisplayName()));
			}
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerTolkien(CommandSource source, Collection<? extends ServerPlayerEntity> pTargets) throws CommandSyntaxException {
		for (ServerPlayerEntity player : pTargets) {
			ExtendedTolkienEntity.spawn(player.getLevel(), player);
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int triggerJumpscare(CommandSource source, Collection<? extends ServerPlayerEntity> pTargets) throws CommandSyntaxException {
		for (ServerPlayerEntity player : pTargets) {
			player.getLevel().sendParticles(player, ExtendedParticles.BARROW_WIGHT_JUMPSCARE.get(), false, player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 1);
		}
		return Command.SINGLE_SUCCESS;
	}
}