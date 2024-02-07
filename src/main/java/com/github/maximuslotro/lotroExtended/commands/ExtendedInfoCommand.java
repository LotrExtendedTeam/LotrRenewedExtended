package com.github.maximuslotro.lotroExtended.commands;

import com.github.maximuslotro.lotroExtended.mixins.MIXINConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedInfoCommand
{
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedInfo")
				.executes(CommandContext -> execute(CommandContext.getSource())));
	}

	public static int execute(CommandSource source) {
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.listPatches"), false);
        for (String patch : MIXINConfig.getInjectedPatches())
        	source.sendSuccess(new StringTextComponent("- " + patch), false);
        return Command.SINGLE_SUCCESS;
    }

}
