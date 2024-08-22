package com.github.maximuslotro.lotrrextended.common.item;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;

public class ExtendedBerries extends BlockNamedItem{

	public ExtendedBerries(Block p_i50041_1_, Properties p_i50041_2_) {
		super(p_i50041_1_, p_i50041_2_);
		CompostingHelper.prepareCompostable(this, 0.3F);
	}
}