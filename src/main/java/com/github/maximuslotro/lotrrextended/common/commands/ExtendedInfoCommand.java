package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrextended.mixins.MIXINConfig;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import lotr.common.data.ExtendedLevelData;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedInfoCommand
{
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("lotrExtendedInfo")
			.requires(context -> context.hasPermission(2))
			.executes(CommandContext -> showPlayerData(CommandContext.getSource()))
			.then(Commands.literal("mixins")
				.executes(CommandContext -> listMixins(CommandContext.getSource())))
			);
	}

	public static int showPlayerData(CommandSource source) {
        // display ExtendedLevelData
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.server_timeUntilTrader").
                append(new StringTextComponent("" + ExtendedLevelData.serverInstance().getTimeUntilTrader())), false);
        if (source.getEntity() != null) {
            source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.your_trader_cooldown").
                    append(new StringTextComponent("" + ExtendedLevelData.serverInstance().getPlayersTraderCooldown(source.getEntity().getUUID()))), false);
        }
        if (source.getEntity() != null) {
            source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.your_bandit_cooldown").
                    append(new StringTextComponent("" + ExtendedLevelData.serverInstance().getPlayersBanditCooldown(source.getEntity().getUUID()))), false);
        }

        // display bane data
        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) source.getEntity();
            ItemStack item = entity.getItemInHand(Hand.MAIN_HAND);

            String baneData = ExtendedModifierUtils.getBaneData(item);
            if (!baneData.isEmpty()) {
                source.sendSuccess(StringTextComponent.EMPTY, false);
                source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.bane_progress"), false);
                source.sendSuccess(new StringTextComponent(baneData), false);
            }
        }

        return Command.SINGLE_SUCCESS;
    }
	
	public static int listMixins(CommandSource source) {
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.listPatches"), false);
    	TextComponent prefex = new StringTextComponent("-");
    	prefex.withStyle(TextFormatting.GREEN);
        for (String patch : MIXINConfig.getInjectedPatches()) {
            TextComponent patchName = new StringTextComponent(patch);
            patchName.withStyle(TextFormatting.GOLD);
            TextComponent message = new TranslationTextComponent("%s %s", prefex, patchName);
            source.sendSuccess(message, false);
        }
        return Command.SINGLE_SUCCESS;
    }
}
