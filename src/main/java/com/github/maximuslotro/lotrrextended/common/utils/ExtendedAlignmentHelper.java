package com.github.maximuslotro.lotrrextended.common.utils;

import lotr.common.fac.Faction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedAlignmentHelper {
	public static void sendAlignmentError(PlayerEntity pPlayer, String message, Faction pFaction) {
		pPlayer.sendMessage(new TranslationTextComponent("chat.lotrextended.requires_alignment", 
			new StringTextComponent(message).withStyle(TextFormatting.YELLOW), 
			pFaction.getDisplayName()), Util.NIL_UUID);
	}
}
