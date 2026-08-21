package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedWorldRegenEngineWatcher;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;

public class ExtendedPruneWorldCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedPrune")
			.requires(cs -> cs.hasPermission(4))
			.then((Commands.argument("dryRun", BoolArgumentType.bool())
				.executes(ctx -> pruneWorld(ctx.getSource(), BoolArgumentType.getBool(ctx, "dryRun")))))
			.then((Commands.literal("cancel")
				.executes(ctx -> cancelPrune(ctx.getSource()))))
			);
	}

	private static int pruneWorld(CommandSource source, boolean dryRun) {
		MinecraftServer server = source.getServer();
		ServerPlayerEntity player;

		try {
			player = source.getPlayerOrException();
		} catch (Exception e) {
			source.sendFailure(new StringTextComponent("Must be a player"));
			return 0;
		}

		if (ExtendedWorldRegenEngineWatcher.startWorldPrune(server, player, true, dryRun)) {
			source.sendFailure(new StringTextComponent("Prune already running"));
			return 0;
		}
		source.sendSuccess(new StringTextComponent("Started world pruning..."), true);
		return Command.SINGLE_SUCCESS;
	}

	private static int cancelPrune(CommandSource source) {
		ExtendedWorldRegenEngineWatcher.stopWorldPrune();
		source.sendSuccess(new StringTextComponent("Canceled world prune"), true);
		return Command.SINGLE_SUCCESS;
	}
}