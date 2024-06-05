package lotr.common.block.trees;

import java.util.Random;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class BananaTree extends Tree {
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean bees) {
		return ExtendedBiomeFeatures.banana();
	}
}