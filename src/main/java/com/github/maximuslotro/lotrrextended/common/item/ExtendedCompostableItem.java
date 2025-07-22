package com.github.maximuslotro.lotrrextended.common.item;

import lotr.common.event.CompostingHelper;
import net.minecraft.item.Item;

public class ExtendedCompostableItem extends Item{

	public ExtendedCompostableItem(Properties pProperties, float value) {
		super(pProperties);
		CompostingHelper.prepareCompostable(this, value);
	}
}