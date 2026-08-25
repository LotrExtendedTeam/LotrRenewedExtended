package com.github.maximuslotro.lotrrextended.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class ExtendedWallBlock extends WallBlock {

	public ExtendedWallBlock(Supplier<Block> block){
		this(block.get());
	}

	public ExtendedWallBlock(Block block){
		super(Properties.copy(block));
	}
}
