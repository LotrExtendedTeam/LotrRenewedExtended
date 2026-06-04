package com.github.maximuslotro.lotrrextended.common.item;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedModifierTomeItem extends Item {
	private ExtendedModifierType[] bannedTypes;
	private String langId;

	public ExtendedModifierTomeItem(Properties pProperties, String pLangId, ExtendedModifierType... pBannedTypes) {
		super(pProperties);
		langId = pLangId;
		bannedTypes = pBannedTypes;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pItemStack, @Nullable World world, List<ITextComponent> textLines, ITooltipFlag p_77624_4_) {
		textLines.add(1, (new TranslationTextComponent("gui.lotrextended.reforge_books.description", 
			new TranslationTextComponent("gui.lotrextended.reforge_books."+langId).withStyle(TextFormatting.AQUA))).withStyle(TextFormatting.GRAY));
	}

	public boolean isBannedType(ExtendedModifierType type) {
		return Arrays.asList(bannedTypes).contains(type);
	}
}
