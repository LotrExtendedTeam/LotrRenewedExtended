package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeature;

/**
 * Based upon {@link RandomPatchFeature}
 * */
public class ExtendedDirectionalRandomPatchFeature extends Feature<BlockClusterFeatureConfig> {
	public ExtendedDirectionalRandomPatchFeature(Codec<BlockClusterFeatureConfig> p_i231979_1_) {
		super(p_i231979_1_);
	}

	@Override
	public boolean place(ISeedReader pWorldReader, ChunkGenerator pGenerator, Random pRandom, BlockPos targetPos, BlockClusterFeatureConfig pConfig) {
		int placedCount = 0;
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		List<Direction> directions = Arrays.asList(Direction.values());
		for(int j = 0; j < pConfig.tries; ++j) {
			mutablePos.setWithOffset(targetPos, 
				pRandom.nextInt(pConfig.xspread) - pRandom.nextInt(pConfig.xspread), 
				pRandom.nextInt(pConfig.yspread) - pRandom.nextInt(pConfig.yspread), 
				pRandom.nextInt(pConfig.zspread) - pRandom.nextInt(pConfig.zspread));
			boolean isSpaceValid = pWorldReader.isEmptyBlock(mutablePos) ;//|| (pConfig.canReplace && pWorldReader.getBlockState(mutablePos).getMaterial().isReplaceable());
			if (isSpaceValid) {
				Collections.shuffle(directions, pRandom);
				for(Direction dir : directions) {
					//BlockPos supportPos = mutablePos.relative(dir.getOpposite()); 
					//BlockState supportState = pWorldReader.getBlockState(supportPos);
					//boolean whitelistValid = pConfig.whitelist.isEmpty() || pConfig.whitelist.contains(supportState.getBlock());
					//boolean blacklistValid = !pConfig.blacklist.contains(supportState);
					//boolean isSupportSolid = supportState.isSolidRender(pWorldReader, supportPos);
					//if(whitelistValid && blacklistValid ) {//&& isSupportSolid) {
						BlockState stateToPlace = pConfig.stateProvider.getState(pRandom, mutablePos);
						if (stateToPlace.hasProperty(DirectionalBlock.FACING)) {
							stateToPlace = stateToPlace.setValue(DirectionalBlock.FACING, dir);
						}
						/*
						boolean waterValid = !pConfig.needWater || 
							pWorldReader.getFluidState(supportPos.west()).is(FluidTags.WATER) || 
							pWorldReader.getFluidState(supportPos.east()).is(FluidTags.WATER) || 
							pWorldReader.getFluidState(supportPos.north()).is(FluidTags.WATER) || 
							pWorldReader.getFluidState(supportPos.south()).is(FluidTags.WATER) || 
							pWorldReader.getFluidState(supportPos.above()).is(FluidTags.WATER) || 
							pWorldReader.getFluidState(supportPos.below()).is(FluidTags.WATER);
							*/
						if(/*waterValid && */stateToPlace.canSurvive(pWorldReader, mutablePos)) {
							pConfig.blockPlacer.place(pWorldReader, mutablePos, stateToPlace, pRandom);
							++placedCount;
							break;
						}
					//}
				}
			}
		}
		return placedCount > 0;
	}
}