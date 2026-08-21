package com.github.maximuslotro.lotrrextended.common.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands.EnvironmentType;

public class ExtendedCommands {
	public static void registerCommands(CommandDispatcher<CommandSource> dispatcher, EnvironmentType environment) {
		ExtendedInfoCommand.register(dispatcher);
		FellowshipMessageCommand.register(dispatcher);
		ExtendedFellowshipCommand.register(dispatcher);
		ExtendedStructureHelperComamnd.register(dispatcher);
		ExtendedModifierCommand.register(dispatcher);
		//ExtendedStructureScanningCommand.register(dispatcher);
		//ExtendedStructureSummonv2.register(dispatcher);
		ExtendedConfigCommand.register(dispatcher);
		ExtendedTriggerCommand.register(dispatcher);
		ExtendedSummonCommand.register(dispatcher);
		ExtendedDebugCommand.register(dispatcher);
		ExtendedFirstLastSeenCommand.register(dispatcher);
		ExtendedPruneWorldCommand.register(dispatcher);
	}
}
