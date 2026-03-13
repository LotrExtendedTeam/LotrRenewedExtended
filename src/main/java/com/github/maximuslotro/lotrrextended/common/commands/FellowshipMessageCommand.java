package com.github.maximuslotro.lotrrextended.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import lotr.common.command.LOTRBaseCommand;
import lotr.common.data.ExtendedFellowshipDataModule;
import lotr.common.data.ExtendedLOTRPlayerData;
import lotr.common.data.LOTRLevelData;
import lotr.common.fellowship.ExtendedFellowship;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.*;

import java.util.UUID;


public class FellowshipMessageCommand extends LOTRBaseCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("fmsg").requires(context -> context.hasPermission(0))
				.then(Commands.argument("fellowship", StringArgumentType.word())
						.suggests(SUGGEST_FELLOWSHIP_USER)
						.then(Commands.argument("message", StringArgumentType.greedyString())
								.executes(context -> sendMessageToFellowship(context.getSource(), (PlayerEntity) context.getSource().getEntity(), StringArgumentType.getString(context, "fellowship"), StringArgumentType.getString(context, "message"))
										)
								)
						)
				);
	}

	public static final SuggestionProvider<CommandSource> SUGGEST_FELLOWSHIP_USER = (ctx, builder) -> {
		ExtendedFellowshipDataModule fellowshipData = getFellowshipData((PlayerEntity) ctx.getSource().getEntity());
		return ISuggestionProvider.suggest(fellowshipData.listAllFellowshipNames(), builder);
	};


	private static int sendMessageToFellowship(CommandSource source, PlayerEntity player, String fellowshipname, String message) {
		ITextComponent component;

		if (((ExtendedLOTRPlayerData) LOTRLevelData.serverInstance().getData(player)).getExtendedMiscData().doHighContrastMessages()) {
			component = new StringTextComponent("[")
					.append(new StringTextComponent(fellowshipname).withStyle(getFellowshipData(player).getFellowshipByName(fellowshipname).getFellowshipColor()))
					.append(new StringTextComponent("] "))
					.append(player.getName())
					.append(new StringTextComponent(": "))
					.append(new StringTextComponent(message));
		} else {
			component = new StringTextComponent("[")
					.append(new StringTextComponent(fellowshipname).withStyle(TextFormatting.YELLOW))
					.append(new StringTextComponent("] "))
					.append(player.getName())
					.append(new StringTextComponent(": "))
					.append(new StringTextComponent(message));
		}

		ExtendedFellowshipDataModule data = getFellowshipData(player);
		ExtendedFellowship fellowship = data.getFellowshipByName(fellowshipname);
		if (fellowship == null) {
			source.sendFailure(new TranslationTextComponent("command.lotrextended.fellowship.error.noexists"));
			return 0;
		}

		for (UUID memberId : fellowship.getAllFellowshipMembers()) {
			ServerPlayerEntity target = source.getServer().getPlayerList().getPlayer(memberId);
			if (target != null) {
				target.sendMessage(component, player.getUUID());
			}
		}

		return 1;
	}

	private static ExtendedFellowshipDataModule getFellowshipData(PlayerEntity player) {
		return ((ExtendedLOTRPlayerData)getLevelData().getData(player.level, player.getUUID())).getFellowshipData();
	}
}
