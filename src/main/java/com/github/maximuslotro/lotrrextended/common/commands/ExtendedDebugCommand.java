package com.github.maximuslotro.lotrrextended.common.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerDebugToggles;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ExtendedDebugCommand extends ExtendedValueSetCommand{
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> command = Commands.literal("lotrExtendedDebug").requires(context -> context.hasPermission(4));
		command.then(buildBoolean("sendPathNav", ()->ExtendedServerDebugToggles.renderNavPaths, val -> ExtendedServerDebugToggles.renderNavPaths = val));
		dispatcher.register(command);
	}

	public static LiteralArgumentBuilder<CommandSource> buildBoolean(String name, BooleanSupplier configType, Consumer<Boolean> setter){
		return Commands.literal(name)
			.then(Commands.argument("set", BoolArgumentType.bool())
				.executes(context -> {
					boolean newValue = BoolArgumentType.getBool(context, "set");
					setter.accept(newValue);
					setBooleanValue(context.getSource(), name, newValue);
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveBooleanValue(context.getSource(), name, configType.getAsBoolean()));
	}

	public static LiteralArgumentBuilder<CommandSource> buildDouble(String name, DoubleSupplier configType, double min, double max, Consumer<Double> setter){
		return Commands.literal(name)
			.then(Commands.argument("set", DoubleArgumentType.doubleArg(min, max))
				.executes(context -> {
					double newValue = DoubleArgumentType.getDouble(context, "set");
					setter.accept(newValue);
					setDoubleValue(context.getSource(), name, newValue);
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveDoubleValue(context.getSource(), name, configType.getAsDouble()));
	}

	public static LiteralArgumentBuilder<CommandSource> buildInteger(String name, IntSupplier configType, int min, int max, Consumer<Integer> setter){
		return Commands.literal(name)
			.then(Commands.argument("set", IntegerArgumentType.integer(min, max))
				.executes(context -> {
					int newValue = IntegerArgumentType.getInteger(context, "set");
					setter.accept(newValue);
					setIntValue(context.getSource(), name, newValue);
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveIntValue(context.getSource(), name, configType.getAsInt()));
	}
}
