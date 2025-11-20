package com.github.maximuslotro.lotrextended.mixins.lotr.common.item;

import org.spongepowered.asm.mixin.Mixin;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedGemstoneColoringItem;

import lotr.common.item.DurnorItem;
import net.minecraft.util.text.TextFormatting;

@Mixin(DurnorItem.class)
public class MixinDurnorItem implements ExtendedGemstoneColoringItem {

	@Override
	public TextFormatting getExtendedGemstoneColor() {
		return TextFormatting.DARK_RED;
	}
}