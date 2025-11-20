package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

public class ExtendedGemstoneItem extends Item implements ExtendedGemstoneColoringItem{
	private TextFormatting color;
	public ExtendedGemstoneItem(Properties pProperties, TextFormatting color) {
		super(pProperties);
		this.color = color;
	}

	@Override
	public TextFormatting getExtendedGemstoneColor() {
		return color;
	}
}