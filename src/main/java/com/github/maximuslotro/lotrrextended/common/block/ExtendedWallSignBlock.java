package com.github.maximuslotro.lotrrextended.common.block;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;

public class ExtendedWallSignBlock extends WallSignBlock {

	public ExtendedWallSignBlock(Supplier<Block> pSignBlock, WoodType pWoodType) {
		super(Properties.copy(pSignBlock.get()).dropsLike(pSignBlock.get()), pWoodType);
		ExtendedSignTileEntitySetup.add(this);
	}
}