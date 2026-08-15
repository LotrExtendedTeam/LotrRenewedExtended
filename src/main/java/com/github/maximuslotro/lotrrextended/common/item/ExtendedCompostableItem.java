package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedDeferredComposterInjector;

import net.minecraft.item.Item;

public class ExtendedCompostableItem extends Item {
	public ExtendedCompostableItem(Properties pProperties, float value) {
		super(pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, value);
	}
}