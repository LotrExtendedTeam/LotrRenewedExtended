package com.github.maximuslotro.lotrrextended.common.commands;

import com.github.maximuslotro.lotrrextended.mixins.MIXINConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import lotr.common.data.ExtendedLevelData;
import lotr.common.util.LOTREnchantUtils;
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
				.executes(CommandContext -> execute(CommandContext.getSource())));
	}

	public static int execute(CommandSource source) {
        source.sendSuccess(new TranslationTextComponent("command.lotrextended.info.listPatches"), false);
    	TextComponent prefex = new StringTextComponent("-");
    	prefex.withStyle(TextFormatting.GREEN);
        for (String patch : MIXINConfig.getInjectedPatches()) {
            TextComponent patchName = new StringTextComponent(patch);
            patchName.withStyle(TextFormatting.GOLD);
            TextComponent message = new TranslationTextComponent("%s %s", prefex, patchName);
            source.sendSuccess(message, false);
        }

        // display ExtendedLevelData
        // TODO: Make these strings translatable
        if (ExtendedLevelData.serverInstance() != null) {
            source.sendSuccess(new StringTextComponent("Server timeUntilTrader: " + ExtendedLevelData.serverInstance().getTimeUntilTrader()), false);
            if (source.getEntity() != null) {
                source.sendSuccess(new StringTextComponent("Your trader cooldown (seconds): " + ExtendedLevelData.serverInstance().getPlayersTraderCooldown(source.getEntity().getUUID())), false);
            }
        }

        // display bane data
        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) source.getEntity();
            ItemStack item = entity.getItemInHand(Hand.MAIN_HAND);

            String baneData = LOTREnchantUtils.getBaneData(item);
            // TODO: Make these strings translatable
            source.sendSuccess(new StringTextComponent("Bane Data:"), false);
            source.sendSuccess(new StringTextComponent(baneData), false);
        }

        return Command.SINGLE_SUCCESS;
    }

}
