package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;

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
									setBooleanValue(context.getSource(), "enableFellowshipCreation", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableFellowshipCreation", ExtendedServerConfig.enableFellowshipCreation.get()))
						)
				.then(Commands.literal("enableRenewedWaypointCreation")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableRenewedWaypointCreation.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableRenewedWaypointCreation", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableRenewedWaypointCreation", ExtendedServerConfig.enableRenewedWaypointCreation.get()))
						)
				.then(Commands.literal("enableLegacyWaypointCreation")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableLegacyWaypointCreation.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableLegacyWaypointCreation", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableLegacyWaypointCreation", ExtendedServerConfig.enableLegacyWaypointCreation.get()))
						)
				.then(Commands.literal("makeMetalDoorsRequirePower")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									cantSetValue(context.getSource(), "makeMetalDoorsRequirePower");
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "makeMetalDoorsRequirePower", ExtendedServerConfig.makeMetalDoorsRequirePower.get()))
						)
				.then(Commands.literal("enableBannerProtection")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableBannerProtection.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableBannerProtection", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableBannerProtection", ExtendedServerConfig.enableBannerProtection.get()))
						)
				.then(Commands.literal("barrowWightChestFirstSpawnChance")
						.then(Commands.argument("set", IntegerArgumentType.integer(0, 100))
								.executes(context -> {
									ExtendedServerConfig.barrowWightChestFirstSpawnChance.set(IntegerArgumentType.getInteger(context, "set"));
									setIntValue(context.getSource(), "barrowWightChestFirstSpawnChance", IntegerArgumentType.getInteger(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveIntValue(context.getSource(), "barrowWightChestFirstSpawnChance", ExtendedServerConfig.barrowWightChestFirstSpawnChance.get()))
						)
				.then(Commands.literal("npcGlobalSpawnDampingCoefficient")
					.then(Commands.argument("set", DoubleArgumentType.doubleArg(0.0D, 1.0D))
							.executes(context -> {
								ExtendedServerConfig.npcGlobalSpawnDampingCoefficient.set(DoubleArgumentType.getDouble(context, "set"));
								setDoubleValue(context.getSource(), "npcGlobalSpawnDampingCoefficient", DoubleArgumentType.getDouble(context, "set"));
								return Command.SINGLE_SUCCESS;})
							)
					.executes(context -> retrieveDoubleValue(context.getSource(), "npcGlobalSpawnDampingCoefficient", ExtendedServerConfig.npcGlobalSpawnDampingCoefficient.get()))
					)
				//Chair
				.then(Commands.literal("enableChairSlabSitting")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableChairSlabSitting.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableChairSlabSitting", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableChairSlabSitting", ExtendedServerConfig.enableChairSlabSitting.get()))
						)
				.then(Commands.literal("enableChairSwapping")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableChairSwapping.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableChairSwapping", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableChairSwapping", ExtendedServerConfig.enableChairSwapping.get()))
						).then(Commands.literal("enableChairProtectionOverride")
								.then(Commands.argument("set", BoolArgumentType.bool())
										.executes(context -> {
											ExtendedServerConfig.enableChairProtectionOverride.set(BoolArgumentType.getBool(context, "set"));
											setBooleanValue(context.getSource(), "enableChairProtectionOverride", BoolArgumentType.getBool(context, "set"));
											return Command.SINGLE_SUCCESS;})
										)
								.executes(context -> retrieveBooleanValue(context.getSource(), "enableChairProtectionOverride", ExtendedServerConfig.enableChairProtectionOverride.get()))
								)
				//Vanilla patches
				.then(Commands.literal("enableFishingEnchantedItems")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableFishingEnchantedItems.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableFishingEnchantedItems", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableFishingEnchantedItems", ExtendedServerConfig.enableFishingEnchantedItems.get()))
						)
				.then(Commands.literal("enableEnchantedLootDrops")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableEnchantedLootDrops.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableEnchantedLootDrops", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableEnchantedLootDrops", ExtendedServerConfig.enableEnchantedLootDrops.get()))
						)
				//Special
				.then(Commands.literal("enableDevMode")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									cantSetValue(context.getSource(), "enableDevMode");
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableDevMode", ExtendedServerConfig.enableDevMode.get()))
						)
				.then(Commands.literal("enableExtendedLogging")
						.then(Commands.argument("set", BoolArgumentType.bool())
								.executes(context -> {
									ExtendedServerConfig.enableExtendedLogging.set(BoolArgumentType.getBool(context, "set"));
									setBooleanValue(context.getSource(), "enableExtendedLogging", BoolArgumentType.getBool(context, "set"));
									return Command.SINGLE_SUCCESS;})
								)
						.executes(context -> retrieveBooleanValue(context.getSource(), "enableExtendedLogging", ExtendedServerConfig.enableExtendedLogging.get()))
						));
	}

	public static int retrieveBooleanValue(CommandSource source, String name, boolean value) {
		source.sendSuccess(new StringTextComponent("Current ").withStyle(TextFormatting.GREEN)
				.append(new StringTextComponent(name).withStyle(TextFormatting.AQUA))
				.append(new StringTextComponent(" value: ").withStyle(TextFormatting.GOLD))
				.append(new StringTextComponent(Boolean.toString(value)).withStyle(TextFormatting.GOLD)), true);
		return Command.SINGLE_SUCCESS;
	}
	public static int retrieveIntValue(CommandSource source, String name, int value) {
		source.sendSuccess(new StringTextComponent("Current ").withStyle(TextFormatting.GREEN)
				.append(new StringTextComponent(name).withStyle(TextFormatting.AQUA))
				.append(new StringTextComponent(" value: ").withStyle(TextFormatting.GOLD))
				.append(new StringTextComponent(Integer.toString(value)).withStyle(TextFormatting.GOLD)), true);
		return Command.SINGLE_SUCCESS;
	}
	public static int retrieveDoubleValue(CommandSource source, String name, double value) {
		source.sendSuccess(new StringTextComponent("Current ").withStyle(TextFormatting.GREEN)
				.append(new StringTextComponent(name).withStyle(TextFormatting.AQUA))
				.append(new StringTextComponent(" value: ").withStyle(TextFormatting.GOLD))
				.append(new StringTextComponent(Double.toString(value)).withStyle(TextFormatting.GOLD)), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int cantSetValue(CommandSource source, String name) {
		source.sendSuccess(new StringTextComponent(name).withStyle(TextFormatting.AQUA)
				.append(new StringTextComponent(" can only be edited in server config while server is off!").withStyle(TextFormatting.RED)), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int setBooleanValue(CommandSource source, String name, boolean value) {
		TextComponent text = value ? new TranslationTextComponent("gui.lotrextended.base.enabled") : new TranslationTextComponent("gui.lotrextended.base.disabled");
		if(value) {
			text.withStyle(TextFormatting.GREEN);
		}else {
			text.withStyle(TextFormatting.RED);
		}
		source.sendSuccess(text.append(" "+name).withStyle(TextFormatting.AQUA), false);
		return Command.SINGLE_SUCCESS;
	}

	public static int setIntValue(CommandSource source, String name, int value) {
		TextComponent text = new TranslationTextComponent("gui.lotrextended.base.set");
		text.withStyle(TextFormatting.GREEN);
		text.append(" "+name+":").withStyle(TextFormatting.AQUA);
		text.append(" "+ value).withStyle(TextFormatting.GOLD);
		source.sendSuccess(text, false);
		return Command.SINGLE_SUCCESS;
	}

	public static int setDoubleValue(CommandSource source, String name, double value) {
		TextComponent text = new TranslationTextComponent("gui.lotrextended.base.set");
		text.withStyle(TextFormatting.GREEN);
		text.append(" "+name+":").withStyle(TextFormatting.AQUA);
		text.append(" "+ value).withStyle(TextFormatting.GOLD);
		source.sendSuccess(text, false);
		return Command.SINGLE_SUCCESS;
	}
}
