package com.github.maximuslotro.lotrExtended.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import lotr.common.command.LOTRBaseCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;


public class FellowshipMessageCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("fmsg").requires(context -> context.hasPermission(0))
				.then(Commands.argument("fellowship", StringArgumentType.word())
						.then(Commands.argument("message", StringArgumentType.greedyString())
								.executes(context -> messageFellowship(context.getSource(), StringArgumentType.getString(context, "fellowship"), StringArgumentType.getString(context, "message"))))));
	}
	private static int messageFellowship(CommandSource source, String fellowship, String message) {
		source.sendSuccess(new StringTextComponent("Not implimented") , true);
		return 0;
	}
}
