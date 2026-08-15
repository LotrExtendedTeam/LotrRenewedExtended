package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedDeferredComposterInjector;

import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;

public class ExtendedBerryItem extends BlockNamedItem{
	public ExtendedBerryItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, 0.3F);
	}
}