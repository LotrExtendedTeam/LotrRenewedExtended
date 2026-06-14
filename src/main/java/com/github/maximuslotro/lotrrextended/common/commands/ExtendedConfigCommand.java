package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
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
		command.then(buildBoolean("enableBannerProtection", ExtendedServerConfig.enableBannerProtection));
		command.then(buildDouble("npcGlobalSpawnDampingCoefficient", ExtendedServerConfig.npcGlobalSpawnDampingCoefficient, 0.0D, 1.0D));
		command.then(buildBoolean("enableDefaultWaypointTeleportation", ExtendedServerConfig.enableDefaultWaypointTeleportation));
		command.then(buildBoolean("enableCustomWaypointTeleportation", ExtendedServerConfig.enableCustomWaypointTeleportation));
		command.then(buildBoolean("enableLegacyFeastMode", ExtendedServerConfig.enableLegacyFeastMode));
		command.then(buildBoolean("enableHungerDecrease", ExtendedServerConfig.enableHungerDecrease));
		// Entity
		command.then(buildInteger("barrowWightChestFirstSpawnChance", ExtendedServerConfig.barrowWightChestFirstSpawnChance, 0, 100));
		command.then(buildBoolean("enableLegacyCaptainAggressionBehavior", ExtendedServerConfig.enableLegacyCaptainPassiveBehavior));
		command.then(buildBoolean("enableLegacyTraderPassiveBehavior", ExtendedServerConfig.enableLegacyTraderPassiveBehavior));
		command.then(buildBoolean("enableWanderingTraderSpawns", ExtendedServerConfig.enableWanderingTraderSpawns));
		command.then(buildBoolean("enableBanditEncounters", ExtendedServerConfig.enableBanditEncounters));
		command.then(buildBoolean("enableTolkienSpawning", ExtendedServerConfig.enableTolkienSpawning));
		// Blocks
		command.then(buildBoolean("makeMetalDoorsRequirePower", ExtendedServerConfig.makeMetalDoorsRequirePower));
		command.then(buildBoolean("barsConnectToBranches", ExtendedServerConfig.barsConnectToBranches));
		command.then(buildBoolean("barsConnectToSlabSides", ExtendedServerConfig.barsConnectToSlabSides));
		command.then(buildDouble("dripstoneMinimumDamageHeight", ExtendedServerConfig.dripstoneMinimumDamageHeight, 0.0D, 5.0D));
		command.then(buildInteger("bellRingChainMaxLength", ExtendedServerConfig.bellRingChainMaxLength, 1, 50));
		command.then(buildInteger("gateToggleLength", ExtendedServerConfig.gateToggleLength, 4, 32));
		// Chair
		command.then(buildBoolean("enableChairSlabSitting", ExtendedServerConfig.enableChairSlabSitting));
		command.then(buildBoolean("enableChairSwapping", ExtendedServerConfig.enableChairSwapping));
		command.then(buildBoolean("enableChairProtectionOverride", ExtendedServerConfig.enableChairProtectionOverride));
		// Modifiers
		command.then(buildBoolean("enableVanillaEnchanting", ExtendedServerConfig.enableVanillaEnchanting));
		command.then(buildBoolean("enableVanillaXp", ExtendedServerConfig.enableVanillaXp));
		command.then(buildBoolean("enableExtendedModifiers", ExtendedServerConfig.enableExtendedModifiers));
		// Special
		command.then(buildBoolean("enableExtendedLogging", ExtendedServerConfig.enableExtendedLogging));
		command.then(buildBoolean("enableSpecialLogging", ExtendedServerConfig.enableSpecialLogging));
		command.then(buildBoolean("enableStaffServer", ExtendedServerConfig.enableStaffServer, true));
		command.then(buildBoolean("enableModWhiteList", ExtendedServerConfig.enableModWhiteList));
		// Server Enhancements
		command.then(buildBoolean("enableExtendedSpawnProtection", ExtendedServerConfig.enableExtendedSpawnProtection));
		command.then(buildInteger("overworldSpawnProtectionRadius", ExtendedServerConfig.overworldSpawnProtectionRadius, 0, 500));
		command.then(buildInteger("middleEarthSpawnProtectionRadius", ExtendedServerConfig.middleEarthSpawnProtectionRadius, 0, 500));
		// World Generation
		command.then(buildBoolean("enableFixedStructures", ExtendedServerConfig.enableFixedStructures));
		command.then(buildBoolean("enableFixedStructureImladris", ExtendedServerConfig.enableFixedStructureImladris));
		command.then(buildBoolean("enableFixedStructureTowerHills", ExtendedServerConfig.enableFixedStructureTowerHills));
		command.then(buildBoolean("enableFixedStructureOrthanc", ExtendedServerConfig.enableFixedStructureOrthanc));
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
						boolean oldValue = configType.get();
						configType.set(BoolArgumentType.getBool(context, "set"));
						if(oldValue!=configType.get()) {
							ExtendedPacketHandler.syncServerConfigWithAllClients();
						}
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
					double oldValue = configType.get();
					configType.set(DoubleArgumentType.getDouble(context, "set"));
					if(oldValue!=configType.get()) {
						ExtendedPacketHandler.syncServerConfigWithAllClients();
					}
					setDoubleValue(context.getSource(), name, DoubleArgumentType.getDouble(context, "set"));
					ExtendedPacketHandler.syncServerConfigWithAllClients();
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveDoubleValue(context.getSource(), name, configType.get()));
	}

	public static LiteralArgumentBuilder<CommandSource> buildInteger(String name, ForgeConfigSpec.IntValue configType, int min, int max){
		return Commands.literal(name)
			.then(Commands.argument("set", IntegerArgumentType.integer(min, max))
				.executes(context -> {
					int oldValue = configType.get();
					configType.set(IntegerArgumentType.getInteger(context, "set"));
					if(oldValue!=configType.get()) {
						ExtendedPacketHandler.syncServerConfigWithAllClients();
					}
					setIntValue(context.getSource(), name, IntegerArgumentType.getInteger(context, "set"));
					ExtendedPacketHandler.syncServerConfigWithAllClients();
					return Command.SINGLE_SUCCESS;})
				)
			.executes(context -> retrieveIntValue(context.getSource(), name, configType.get()));
	}

	public static int retrieveBooleanValue(CommandSource source, String name, boolean value) {
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.config.current").withStyle(TextFormatting.GREEN)
			.append(new StringTextComponent(" "+name+" ").withStyle(TextFormatting.AQUA))
			.append(new TranslationTextComponent("command.lotrextended.config.value").withStyle(TextFormatting.GOLD))
			.append(new StringTextComponent(": "+Boolean.toString(value)).withStyle(TextFormatting.GOLD)), true);
		return Command.SINGLE_SUCCESS;
	}
	public static int retrieveIntValue(CommandSource source, String name, int value) {
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.config.current").withStyle(TextFormatting.GREEN)
			.append(new StringTextComponent(" "+name+" ").withStyle(TextFormatting.AQUA))
			.append(new TranslationTextComponent("command.lotrextended.config.value").withStyle(TextFormatting.GOLD))
			.append(new StringTextComponent(": "+Integer.toString(value)).withStyle(TextFormatting.GOLD)), true);
		return Command.SINGLE_SUCCESS;
	}
	public static int retrieveDoubleValue(CommandSource source, String name, double value) {
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.config.current").withStyle(TextFormatting.GREEN)
			.append(new StringTextComponent(" "+name+" ").withStyle(TextFormatting.AQUA))
			.append(new TranslationTextComponent("command.lotrextended.config.value").withStyle(TextFormatting.GOLD))
			.append(new StringTextComponent(": "+Double.toString(value)).withStyle(TextFormatting.GOLD)), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int cantSetValue(CommandSource source, String name) {
		source.sendSuccess(new StringTextComponent(name+" ").withStyle(TextFormatting.AQUA)
			.append(new TranslationTextComponent("command.lotrextended.config.cant").withStyle(TextFormatting.RED)), true);
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
