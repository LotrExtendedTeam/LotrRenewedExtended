package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedConfigCommand
{
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedConfig").requires(context -> context.hasPermission(4))
				.then(Commands.literal("enableFellowshipCreation")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								ExtendedServerConfig.enableFellowshipCreation.set(BoolArgumentType.getBool(context, "set"));
								setValue(context.getSource(), "enableFellowshipCreation", BoolArgumentType.getBool(context, "set"));
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "enableFellowshipCreation", ExtendedServerConfig.enableFellowshipCreation.get()))
				)
				.then(Commands.literal("enableRenewedWaypointCreation")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								ExtendedServerConfig.enableRenewedWaypointCreation.set(BoolArgumentType.getBool(context, "set"));
								setValue(context.getSource(), "enableRenewedWaypointCreation", BoolArgumentType.getBool(context, "set"));
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "enableRenewedWaypointCreation", ExtendedServerConfig.enableRenewedWaypointCreation.get()))
				)
				.then(Commands.literal("enableLegacyWaypointCreation")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								ExtendedServerConfig.enableLegacyWaypointCreation.set(BoolArgumentType.getBool(context, "set"));
								setValue(context.getSource(), "enableLegacyWaypointCreation", BoolArgumentType.getBool(context, "set"));
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "enableLegacyWaypointCreation", ExtendedServerConfig.enableLegacyWaypointCreation.get()))
				)
				.then(Commands.literal("makeMetalDoorsRequirePower")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								cantSetValue(context.getSource(), "makeMetalDoorsRequirePower");
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "makeMetalDoorsRequirePower", ExtendedServerConfig.makeMetalDoorsRequirePower.get()))
				)
				.then(Commands.literal("enableFishingEnchantedItems")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								ExtendedServerConfig.enableFishingEnchantedItems.set(BoolArgumentType.getBool(context, "set"));
								setValue(context.getSource(), "enableFishingEnchantedItems", BoolArgumentType.getBool(context, "set"));
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "enableFishingEnchantedItems", ExtendedServerConfig.enableFishingEnchantedItems.get()))
				)
				.then(Commands.literal("enableEnchantedLootDrops")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								ExtendedServerConfig.enableEnchantedLootDrops.set(BoolArgumentType.getBool(context, "set"));
								setValue(context.getSource(), "enableEnchantedLootDrops", BoolArgumentType.getBool(context, "set"));
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "enableEnchantedLootDrops", ExtendedServerConfig.enableEnchantedLootDrops.get()))
				)
				.then(Commands.literal("enableDevMode")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								cantSetValue(context.getSource(), "enableDevMode");
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "enableDevMode", ExtendedServerConfig.enableDevMode.get()))
				)
				.then(Commands.literal("enableExtendedLogging")
						.then(Commands.argument("set", BoolArgumentType.bool())
							.executes(context -> {
								ExtendedServerConfig.enableExtendedLogging.set(BoolArgumentType.getBool(context, "set"));
								setValue(context.getSource(), "enableExtendedLogging", BoolArgumentType.getBool(context, "set"));
								return Command.SINGLE_SUCCESS;})
							)
						.executes(context -> retrieveValue(context.getSource(), "enableExtendedLogging", ExtendedServerConfig.enableExtendedLogging.get()))
				));
	}

	public static int retrieveValue(CommandSource source, String name, boolean value) {
		source.sendSuccess(new StringTextComponent("Current ").withStyle(TextFormatting.GREEN)
										.append(new StringTextComponent(name).withStyle(TextFormatting.AQUA))
										.append(new StringTextComponent(" value: ").withStyle(TextFormatting.GOLD))
										.append(new StringTextComponent(Boolean.toString(value)).withStyle(TextFormatting.GOLD)), true);
        return Command.SINGLE_SUCCESS;
    }

	public static int cantSetValue(CommandSource source, String name) {
		source.sendSuccess(new StringTextComponent(name).withStyle(TextFormatting.AQUA)
										.append(new StringTextComponent(" can only be edited in server config while server is off!").withStyle(TextFormatting.RED)), true);
        return Command.SINGLE_SUCCESS;
    }

	public static int setValue(CommandSource source, String name, boolean value) {
    	TextComponent text = value ? new TranslationTextComponent("gui.lotrextended.base.enabled") : new TranslationTextComponent("gui.lotrextended.base.disabled");
    	if(value) {
    		text.withStyle(TextFormatting.GREEN);
    	}else {
    		text.withStyle(TextFormatting.RED);
    	}
    	source.sendSuccess(text.append(" "+name).withStyle(TextFormatting.AQUA), false);
        return Command.SINGLE_SUCCESS;
    }
}
