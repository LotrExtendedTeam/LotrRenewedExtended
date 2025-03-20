package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

public class ExtendedConfigCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> command = Commands.literal("lotrExtendedConfig").requires(context -> context.hasPermission(4));
		// Gameplay
		command.then(buildBoolean("enableFellowshipCreation", ExtendedServerConfig.enableFellowshipCreation));
		command.then(buildBoolean("enableRenewedWaypointCreation", ExtendedServerConfig.enableRenewedWaypointCreation));
		command.then(buildBoolean("enableLegacyWaypointCreation", ExtendedServerConfig.enableLegacyWaypointCreation));
		command.then(buildBoolean("makeMetalDoorsRequirePower", ExtendedServerConfig.makeMetalDoorsRequirePower, true));
		command.then(buildBoolean("enableBannerProtection", ExtendedServerConfig.enableBannerProtection));
		command.then(buildInt("barrowWightChestFirstSpawnChance", ExtendedServerConfig.barrowWightChestFirstSpawnChance, 0, 100));
		command.then(buildDouble("npcGlobalSpawnDampingCoefficient", ExtendedServerConfig.npcGlobalSpawnDampingCoefficient, 0.0D, 1.0D));
		command.then(buildDouble("dripstoneMinimumDamageHeight", ExtendedServerConfig.dripstoneMinimumDamageHeight, 0.0D, 5.0D));
		// Chair
		command.then(buildBoolean("enableChairSlabSitting", ExtendedServerConfig.enableChairSlabSitting));
		command.then(buildBoolean("enableChairSwapping", ExtendedServerConfig.enableChairSwapping));
		command.then(buildBoolean("enableChairProtectionOverride", ExtendedServerConfig.enableChairProtectionOverride));
		// Vanilla Patches
		command.then(buildBoolean("enableFishingEnchantedItems", ExtendedServerConfig.enableFishingEnchantedItems));
		command.then(buildBoolean("enableEnchantedLootDrops", ExtendedServerConfig.enableEnchantedLootDrops));
		// Special
		command.then(buildBoolean("enableDevMode", ExtendedServerConfig.enableDevMode, true));
		command.then(buildBoolean("enableExtendedLogging", ExtendedServerConfig.enableExtendedLogging));
		// Register
		dispatcher.register(command);
	}

	public static LiteralArgumentBuilder<CommandSource> buildBoolean(String name, ForgeConfigSpec.BooleanValue configType){
		return buildBoolean(name, configType, false);
	}
	public static LiteralArgumentBuilder<CommandSource> buildBoolean(String name, ForgeConfigSpec.BooleanValue configType, boolean cantSet){
		return Commands.literal(name)
			.then(Commands.argument("set", BoolArgumentType.bool())
				.executes(context -> {
					if(cantSet) {
						cantSetValue(context.getSource(), name);
					}else {
						configType.set(BoolArgumentType.getBool(context, "set"));
						setBooleanValue(context.getSource(), name, BoolArgumentType.getBool(context, "set"));
					}
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveBooleanValue(context.getSource(), name, configType.get()));
	}

	public static LiteralArgumentBuilder<CommandSource> buildDouble(String name, ForgeConfigSpec.DoubleValue configType, double min, double max){
		return Commands.literal(name)
			.then(Commands.argument("set", DoubleArgumentType.doubleArg(min, max))
				.executes(context -> {
					configType.set(DoubleArgumentType.getDouble(context, "set"));
					setDoubleValue(context.getSource(), name, DoubleArgumentType.getDouble(context, "set"));
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveDoubleValue(context.getSource(), name, configType.get()));
	}

	public static LiteralArgumentBuilder<CommandSource> buildInt(String name, ForgeConfigSpec.IntValue configType, int min, int max){
		return Commands.literal(name)
			.then(Commands.argument("set", IntegerArgumentType.integer(min, max))
				.executes(context -> {
					configType.set(IntegerArgumentType.getInteger(context, "set"));
					setDoubleValue(context.getSource(), name, IntegerArgumentType.getInteger(context, "set"));
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveIntValue(context.getSource(), name, configType.get()));
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
		text.append(" "+Integer.toString(value)).withStyle(TextFormatting.GOLD);
		source.sendSuccess(text, false);
		return Command.SINGLE_SUCCESS;
	}

	public static int setDoubleValue(CommandSource source, String name, double value) {
		TextComponent text = new TranslationTextComponent("gui.lotrextended.base.set");
		text.withStyle(TextFormatting.GREEN);
		text.append(" "+name+":").withStyle(TextFormatting.AQUA);
		text.append(" "+Double.toString(value)).withStyle(TextFormatting.GOLD);
		source.sendSuccess(text, false);
		return Command.SINGLE_SUCCESS;
	}
}
