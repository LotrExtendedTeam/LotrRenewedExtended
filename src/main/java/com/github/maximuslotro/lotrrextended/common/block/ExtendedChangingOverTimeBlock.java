package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Optional;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;


public interface ExtendedChangingOverTimeBlock<T extends Enum<T>> {
	int SCAN_DISTANCE = 4;

	public Optional<BlockState> getNext(BlockState pState);

	public float getChanceModifier();

	public default void onRandomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		if (pRandom.nextFloat() < 0.05688889F) {
			this.applyChangeOverTime(pState, pLevel, pPos, pRandom);
		}
	}

	public T getAge();

	@SuppressWarnings("rawtypes")
	public default void applyChangeOverTime(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		int i = this.getAge().ordinal();
		int j = 0;
		int k = 0;

		for(BlockPos blockpos : BlockPos.withinManhattan(pPos, 4, 4, 4)) {
			int l = blockpos.distManhattan(pPos);
			if (l > 4) {
				break;
			}

			if (!blockpos.equals(pPos)) {
				BlockState blockstate = pLevel.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				if (block instanceof ExtendedChangingOverTimeBlock) {
					Enum<?> oenum = ((ExtendedChangingOverTimeBlock)block).getAge();
					if (this.getAge().getClass() == oenum.getClass()) {
						int i1 = oenum.ordinal();
						if (i1 < i) {
							return;
						}

						if (i1 > i) {
							++k;
						} else {
							++j;
						}
					}
				}
			}
		}

		float f = (float)(k + 1) / (float)(k + j + 1);
		float f1 = f * f * this.getChanceModifier();
		if (pRandom.nextFloat() < f1) {
			this.getNext(pState).ifPresent((p_153039_) -> pLevel.setBlockAndUpdate(pPos, p_153039_));
		}

	}
}
