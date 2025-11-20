package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

public class ExtendedFlintItem extends Item implements ExtendedGemstoneColoringItem {
	public ExtendedFlintItem(Properties properties) {
		super(properties);
	}

	@Override
	public TextFormatting getExtendedGemstoneColor() {
		return TextFormatting.BLACK;
	}
}