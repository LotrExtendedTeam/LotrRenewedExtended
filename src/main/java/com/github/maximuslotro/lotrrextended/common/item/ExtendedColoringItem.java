package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

public class ExtendedColoringItem extends Item implements ExtendedGemstoneColoringItem{
	private TextFormatting color;
	public ExtendedColoringItem(Properties pProperties, TextFormatting color) {
		super(pProperties);
		this.color = color;
	}

	@Override
	public TextFormatting getExtendedGemstoneColor() {
		return color;
	}
}