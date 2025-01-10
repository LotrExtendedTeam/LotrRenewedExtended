package com.github.maximuslotro.lotrrextended.common.world.gen.tree;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;

public abstract class ExtendedFoliagePlacer extends FoliagePlacer{

	public ExtendedFoliagePlacer(FeatureSpread p_i241999_1_, FeatureSpread p_i241999_2_) {
		super(p_i241999_1_, p_i241999_2_);
	}

	public void doPlaceLeafBlock(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, Mutable pos, Set<BlockPos> leaves, MutableBoundingBox box) {
		if (TreeFeature.validTreePos(world, pos)) {
			world.setBlock(pos, config.leavesProvider.getState(rand, pos), 19);
			box.expand(new MutableBoundingBox(pos, pos));
			leaves.add(pos.immutable());
		}
	}
	public void doPlaceAdditionBlock(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, Mutable pos, Set<BlockPos> leaves, MutableBoundingBox box, BlockState addition) {
		if (TreeFeature.validTreePos(world, pos)) {
			world.setBlock(pos, addition, 3);
			box.expand(new MutableBoundingBox(pos, pos));
			leaves.add(pos.immutable());
		}
	}
}
