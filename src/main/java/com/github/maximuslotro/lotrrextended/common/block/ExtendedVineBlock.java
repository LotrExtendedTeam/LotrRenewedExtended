package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.VineBlock;

public class ExtendedVineBlock extends VineBlock{

	public ExtendedVineBlock(Properties p_i48303_1_) {
		super(p_i48303_1_);
		((FireBlock) Blocks.FIRE).setFlammable(this, 15, 100);
	}
}
