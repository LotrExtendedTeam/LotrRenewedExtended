package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ExtendedFlowerBlock extends FlowerBlock {

	public ExtendedFlowerBlock(Effect pSuspiciousStewEffect, int pEffectDuration, Properties pProperties) {
		super(pSuspiciousStewEffect, pEffectDuration, pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, 0.65F);
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 30;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}
}
