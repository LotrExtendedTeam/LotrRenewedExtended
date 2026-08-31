package com.github.maximuslotro.lotrrextended.common.commands;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.data.ExtendedLevelData;
import com.github.maximuslotro.lotrrextended.common.data.ExtendedPlayerInfo;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedEntityUtils;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ExtendedFirstLastSeenCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("firstjoined")
			.then((Commands.argument("playername", StringArgumentType.word())
				.executes(ctx -> firstJoinedPlayer(ctx.getSource(), StringArgumentType.getString(ctx, "playername")))))
			);
		dispatcher.register(Commands.literal("seen")
			.then((Commands.argument("playername", StringArgumentType.word())
				.executes(ctx -> seenPlayer(ctx.getSource(), StringArgumentType.getString(ctx, "playername")))))
			);
	}

	private static int firstJoinedPlayer(CommandSource source, String playername) {
		@Nullable UUID player = ExtendedEntityUtils.getPlayerUUIDFromUsername(playername);
		if(player==null || !ExtendedLevelData.serverInstance().getPlayerData().doesPlayerInfoExist(player)) {
			source.sendFailure(new TranslationTextComponent("gui.lotrextended.base.player_not_found").withStyle(TextFormatting.RED));
			return Command.SINGLE_SUCCESS;
		}
		ExtendedPlayerInfo profile = ExtendedLevelData.serverInstance().getPlayerData().getPlayerInfo(player);

		Instant firstJoin = profile.getFirstLogin(); // your Instant
		ZonedDateTime zdt = firstJoin.atZone(ZoneId.of("America/Chicago"));
		int day = zdt.getDayOfMonth();
		String suffix = getDaySuffix(day);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d'" + suffix + "' yyyy, 'at' HH:mm");
		String formattedDate = zdt.format(formatter);
		IFormattableTextComponent playerName = new StringTextComponent(playername).withStyle(TextFormatting.GREEN);
		IFormattableTextComponent joinDate = new StringTextComponent(formattedDate).withStyle(TextFormatting.YELLOW);
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.seen.joined", playerName, joinDate).withStyle(TextFormatting.GREEN), true);
		ExtendedLevelData.serverInstance().getPlayerData().unload(player);
		return Command.SINGLE_SUCCESS;
	}

	private static int seenPlayer(CommandSource source, String playername) {
		if(Lists.newArrayList(ServerLifecycleHooks.getCurrentServer().getPlayerNames()).contains(playername)) {
			source.sendSuccess(new TranslationTextComponent("command.lotrextended.seen.online", playername).withStyle(TextFormatting.GREEN), true);
			return Command.SINGLE_SUCCESS;
		}
		@Nullable UUID player = ExtendedEntityUtils.getPlayerUUIDFromUsername(playername);
		if(player==null || !ExtendedLevelData.serverInstance().getPlayerData().doesPlayerInfoExist(player)) {
			source.sendFailure(new TranslationTextComponent("gui.lotrextended.base.player_not_found").withStyle(TextFormatting.RED));
			return Command.SINGLE_SUCCESS;
		}
		ExtendedPlayerInfo profile = ExtendedLevelData.serverInstance().getPlayerData().getPlayerInfo(player);
		Instant lastLogout = profile.getLastLogout();
		if(lastLogout==null) {
			source.sendFailure(new TranslationTextComponent("command.lotrextended.seen.never").withStyle(TextFormatting.RED));
			ExtendedLevelData.serverInstance().getPlayerData().unload(player);
			return Command.SINGLE_SUCCESS;
		}
		String timeAgo = formatTimeAgo(lastLogout);
		IFormattableTextComponent playerName = new StringTextComponent(playername).withStyle(TextFormatting.GREEN);
		IFormattableTextComponent joinDate = new StringTextComponent(timeAgo).withStyle(TextFormatting.YELLOW);
		source.sendSuccess(new TranslationTextComponent("command.lotrextended.seen.last", playerName, joinDate).withStyle(TextFormatting.GREEN), true);
		ExtendedLevelData.serverInstance().getPlayerData().unload(player);
		return Command.SINGLE_SUCCESS;
	}

	private static String formatTimeAgo(Instant past) {
		Duration duration = Duration.between(past, Instant.now());

		long minutes = duration.toMinutes();
		long hours = minutes / 60;
		long days = hours / 24;
		long months = days / 30;
		long years = months / 12;

		minutes %= 60;
		hours %= 24;
		days %= 30;
		months %= 12;

		StringBuilder sb = new StringBuilder();

		if (years > 0)
			sb.append(years).append(" year").append(years != 1 ? "s, " : ", ");
		if (months > 0)
			sb.append(months).append(" month").append(months != 1 ? "s, " : ", ");
		if (days > 0)
			sb.append(days).append(" day").append(days != 1 ? "s, " : ", ");
		if (hours > 0)
			sb.append(hours).append(" hour").append(hours != 1 ? "s, " : ", ");

		if (minutes > 0 || sb.length() == 0)
			sb.append(minutes).append(" minute").append(minutes != 1 ? "s" : "");

		return sb.append(" ago").toString();
	}

	private static String getDaySuffix(int day) {
		if (day >= 11 && day <= 13) return "th";
		int lastDigit = day % 10;
		switch (lastDigit) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}
}