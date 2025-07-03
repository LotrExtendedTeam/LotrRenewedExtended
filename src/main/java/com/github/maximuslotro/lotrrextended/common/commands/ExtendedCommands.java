package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.Commands.EnvironmentType;

public class ExtendedCommands {
	public static void registerCommands(CommandDispatcher<CommandSource> dispatcher, EnvironmentType environment) {
		ExtendedInfoCommand.register(dispatcher);
		FellowshipMessageCommand.register(dispatcher);
		ExtendedFellowshipCommand.register(dispatcher);
		ExtendedStructureHelperComamnd.register(dispatcher);
		ExtendedLotrEnchantCommand.register(dispatcher);
		//ExtendedStructureScanningCommand.register(dispatcher);
		//ExtendedStructureSummonv2.register(dispatcher);
		ExtendedConfigCommand.register(dispatcher);
		if(ExtendedServerConfig.enableStaffServer.get()) {
			dispatcher.register(Commands.literal("config").requires(context -> context.hasPermission(4)));
			dispatcher.register(Commands.literal("forge").requires(context -> context.hasPermission(4)));
			dispatcher.register(Commands.literal("trigger").requires(context -> context.hasPermission(4)));
			dispatcher.register(Commands.literal("teammsg").requires(context -> context.hasPermission(4)));
			dispatcher.register(Commands.literal("tm").requires(context -> context.hasPermission(4)));
		}
	}
}
