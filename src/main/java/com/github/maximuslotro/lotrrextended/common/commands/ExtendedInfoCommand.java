package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.mixins.MIXINConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedInfoCommand
{
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedInfo")
				.executes(CommandContext -> execute(CommandContext.getSource())));
	}

	public static int execute(CommandSource source) {
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.listPatches"), false);
    	TextComponent prefex = new StringTextComponent("-");
    	prefex.withStyle(TextFormatting.GREEN);
        for (String patch : MIXINConfig.getInjectedPatches()) {
            TextComponent patchName = new StringTextComponent(patch);
            patchName.withStyle(TextFormatting.GOLD);
            TextComponent message = new TranslationTextComponent("%s %s", prefex, patchName);
            source.sendSuccess(message, false);
        }
        return Command.SINGLE_SUCCESS;
    }

}
