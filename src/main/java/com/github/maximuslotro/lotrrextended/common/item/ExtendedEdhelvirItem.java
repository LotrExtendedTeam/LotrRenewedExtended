package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

public class ExtendedEdhelvirItem extends Item implements ExtendedGemstoneColoringItem {
	public ExtendedEdhelvirItem(Properties properties) {
		super(properties);
	}

	@Override
	public TextFormatting getExtendedGemstoneColor() {
		return TextFormatting.DARK_AQUA;
	}
}