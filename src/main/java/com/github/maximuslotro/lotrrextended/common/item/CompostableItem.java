package com.github.maximuslotro.lotrrextended.common.item;

import lotr.common.event.CompostingHelper;
import net.minecraft.item.Item;

public class CompostableItem extends Item{

	public CompostableItem(Properties pProperties, float value) {
		super(pProperties);
		CompostingHelper.prepareCompostable(this, value);
	}
}