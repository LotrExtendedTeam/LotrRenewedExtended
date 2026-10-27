package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;

import net.minecraft.block.BlockState;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ExtendedFangornStoneRootBlock extends ExtendedFangornFlowerBlock {

	public ExtendedFangornStoneRootBlock(Effect pSuspiciousStewEffect, int pEffectDuration, Properties pProperties) {
		super(pSuspiciousStewEffect, pEffectDuration, pProperties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		return pState.is(ExtendedTags.Blocks.BASE_STONE_MIDDLE_EARTH);
	}
}
