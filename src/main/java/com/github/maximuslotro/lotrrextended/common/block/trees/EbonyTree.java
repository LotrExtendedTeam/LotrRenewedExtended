package com.github.maximuslotro.lotrrextended.common.block.trees;

import java.util.Random;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class EbonyTree extends Tree {
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean bees) {
		if (rand.nextInt(8) == 0) {
			return bees ? ExtendedBiomeFeatures.ebonyFancyBees() : ExtendedBiomeFeatures.ebonyFancy();
		}
		return bees ? ExtendedBiomeFeatures.ebonyBees() : ExtendedBiomeFeatures.ebony();
	}
}