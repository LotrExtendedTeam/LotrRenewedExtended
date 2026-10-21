package com.github.maximuslotro.lotrrextended.common.commands;


import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.map.conquest.ExtendedConquestRegion;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lotr.common.command.LOTRBaseCommand;
import lotr.common.command.arguments.FactionArgument;
import lotr.common.fac.Faction;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedConquestCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> clear = Commands.literal("clear").executes(context -> clearConquest(context.getSource()));
		LiteralArgumentBuilder<CommandSource> getScale = Commands.literal("getScale").executes(context -> getConquestScale(context.getSource()));
		LiteralArgumentBuilder<CommandSource> setScale = Commands.literal("setScale").then(Commands.argument("scale", IntegerArgumentType.integer(1, 16))
			.executes(context -> setConquestScale(context.getSource(), IntegerArgumentType.getInteger(context, "scale"))));
		LiteralArgumentBuilder<CommandSource> setConquest = Commands.literal("set").then(Commands.argument("pos", Vec3Argument.vec3())
			.then(Commands.argument("faction", FactionArgument.faction()).then(Commands.argument("amount", FloatArgumentType.floatArg(1))
			.executes(context -> modifyConquest(context.getSource(), Vec3Argument.getVec3(context, "pos"), FactionArgument.getFaction(context, "faction"), 
				FloatArgumentType.getFloat(context, "amount"), true)))));
		LiteralArgumentBuilder<CommandSource> addConquest = Commands.literal("add").then(Commands.argument("pos", Vec3Argument.vec3())
			.then(Commands.argument("faction", FactionArgument.faction()).then(Commands.argument("amount", FloatArgumentType.floatArg(1))
			.executes(context -> modifyConquest(context.getSource(), Vec3Argument.getVec3(context, "pos"), FactionArgument.getFaction(context, "faction"), 
				FloatArgumentType.getFloat(context, "amount"), false)))));
		LiteralArgumentBuilder<CommandSource> getConquest = Commands.literal("get").then(Commands.argument("pos", Vec3Argument.vec3())
			.then(Commands.argument("faction", FactionArgument.faction())
			.executes(context -> getConquest(context.getSource(), Vec3Argument.getVec3(context, "pos"), FactionArgument.getFaction(context, "faction")))));
		LiteralArgumentBuilder<CommandSource> getRate = Commands.literal("getRate").executes(context -> getConquestRate(context.getSource()));
		LiteralArgumentBuilder<CommandSource> setRate = Commands.literal("setRate").then(Commands.argument("rate", FloatArgumentType.floatArg(0.1F))
			.executes(context -> setConquestRate(context.getSource(), FloatArgumentType.getFloat(context, "rate"))));
		LiteralArgumentBuilder<CommandSource> enable = Commands.literal("enable").executes(context -> enableDisableConquest(context.getSource(), true));
		LiteralArgumentBuilder<CommandSource> disable = Commands.literal("disable").executes(context -> enableDisableConquest(context.getSource(), false));
		dispatcher.register(Commands.literal("conquest").requires(context -> context.hasPermission(4))
			.then(clear).then(getScale).then(setScale).then(setConquest).then(addConquest).then(getConquest).then(getRate).then(setRate)
			.then(enable).then(disable));
	}

	public static int getConquestRate(CommandSource source) {
		float rate = ExtendedLevelData.serverInstance().getConquestData().getConquestRate();
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.rate.get", rate), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int setConquestRate(CommandSource source, float rate) {
		ExtendedLevelData.serverInstance().getConquestData().setConquestRate(rate);
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.rate.set", rate), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int getConquest(CommandSource source, Vector3d pos, Faction faction) {
		ExtendedConquestRegion region = ExtendedLevelData.serverInstance().getConquestData().getConquestSquare(pos);
		float conquest = region.getConquest(faction.getName());
		if(conquest>0) {
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.get", faction.getName().toString(), conquest, pos.toString()), true);
		}else {
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.get.failure", faction.getName().toString(), pos.toString()), true);
		}
		return Command.SINGLE_SUCCESS;
	}

	public static int modifyConquest(CommandSource source, Vector3d pos, Faction faction, float amount, boolean shouldSet) {
		ExtendedConquestRegion region = ExtendedLevelData.serverInstance().getConquestData().getConquestSquare(pos);
		if(shouldSet) {
			region.setConquestRaw(faction.getName(), amount);
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.modify.set", faction.getName().toString(), amount, pos.toString()), true);
		}else {
			region.addConquestRaw(faction.getName(), amount);
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.modify.add", amount, faction.getName().toString(), pos.toString()), true);

		}
		return Command.SINGLE_SUCCESS;
	}

	public static int clearConquest(CommandSource source) {
		ExtendedLevelData.serverInstance().getConquestData().clearConquestMap();
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.clear"), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int getConquestScale(CommandSource source) {
		int scale = ExtendedLevelData.serverInstance().getConquestData().getGridSquareScale();
		int mapScale = ExtendedLevelData.serverInstance().getConquestData().getConquestSizeInMapPixels();
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.scale.get", scale, mapScale), true);
		return Command.SINGLE_SUCCESS;
	}

	public static int setConquestScale(CommandSource source, int scale) {
		ExtendedLevelData.serverInstance().getConquestData().setGridSquareScale(scale);
		int mapScale = ExtendedLevelData.serverInstance().getConquestData().getConquestSizeInMapPixels();
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.scale.set", scale, mapScale), true);
		clearConquest(source);
		return Command.SINGLE_SUCCESS;
	}

	public static int enableDisableConquest(CommandSource source, boolean enable) {
		ExtendedServerConfig.enableConquest.set(enable);
		TranslationTextComponent result;
		if(enable) {
			result = new TranslationTextComponent("gui.lotrextended.base.enabled");
		}else {
			result = new TranslationTextComponent("gui.lotrextended.base.disabled");
		}
		ExtendedPacketHandler.syncServerConfigWithAllClients();
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.conquest.toggle", result), true);
		return Command.SINGLE_SUCCESS;
	}
}
