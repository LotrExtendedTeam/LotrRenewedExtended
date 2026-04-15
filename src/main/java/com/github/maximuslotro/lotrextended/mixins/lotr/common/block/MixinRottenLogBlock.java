package com.github.maximuslotro.lotrextended.mixins.lotr.common.block;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.block.LOTRLogBlock;
import lotr.common.block.RottenLogBlock;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@Mixin(RottenLogBlock.class)
public abstract class MixinRottenLogBlock extends LOTRLogBlock {

	public MixinRottenLogBlock(Properties p_i48440_1_) {
		super(null, null);
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return false;
	}
}
