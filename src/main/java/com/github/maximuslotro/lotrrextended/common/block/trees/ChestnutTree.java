package com.github.maximuslotro.lotrrextended.common.block.trees;

import java.util.Random;

import lotr.common.block.trees.PartyableTree;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ChestnutTree extends PartyableTree {
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean bees) {
		if (rand.nextInt(10) == 0)
			return bees ? ExtendedBiomeFeatures.chestnutFancyBees() : ExtendedBiomeFeatures.chestnutFancy();
		return bees ? ExtendedBiomeFeatures.chestnutBees() : ExtendedBiomeFeatures.chestnut();
	}

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getPartyTreeFeature(Random var1) {
		return ExtendedBiomeFeatures.chestnutParty();
	}
}