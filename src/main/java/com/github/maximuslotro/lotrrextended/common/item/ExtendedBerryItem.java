package com.github.maximuslotro.lotrrextended.common.item;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;

public class ExtendedBerryItem extends BlockNamedItem{

	public ExtendedBerryItem(Block pBlock, Properties pProperties) {
		super(pBlock, pProperties);
		CompostingHelper.prepareCompostable(this, 0.3F);
	}
}