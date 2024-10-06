package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;


public class ExtendedSimpleBlockFeature extends Feature<ExtendedSimpleBlockFeatureConfig> {
	public ExtendedSimpleBlockFeature(Codec<ExtendedSimpleBlockFeatureConfig> p_66808_) {
		super(p_66808_);
	}

	@Override
	public boolean place(ISeedReader worldgenlevel, ChunkGenerator chunkGenerator, Random random, BlockPos blockpos, ExtendedSimpleBlockFeatureConfig config) {
		BlockState blockstate = config.state.getState(random, blockpos);
		if (blockstate.canSurvive(worldgenlevel, blockpos)) {
			if (blockstate.getBlock() instanceof DoublePlantBlock) {
				if (!worldgenlevel.isEmptyBlock(blockpos.above())) {
					return false;
				}
				((DoublePlantBlock)blockstate.getBlock()).placeAt(worldgenlevel, blockpos, 2);
			} else {
				worldgenlevel.setBlock(blockpos, blockstate, 2);
			}
			return true;
		} else {
			return false;
		}
	}
}