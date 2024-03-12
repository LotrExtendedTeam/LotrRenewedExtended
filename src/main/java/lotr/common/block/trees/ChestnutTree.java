package lotr.common.block.trees;

import java.util.Random;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.gen.feature.WeightedRandomFeatureConfig;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ChestnutTree extends Tree {
	@SuppressWarnings("unchecked")
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean bees) {
		return bees ? (ConfiguredFeature<BaseTreeFeatureConfig, ?>) ((WeightedRandomFeatureConfig<?>) ExtendedBiomeFeatures.chestnutBees().config).getRandomFeature(rand) : 
			(ConfiguredFeature<BaseTreeFeatureConfig, ?>) ((WeightedRandomFeatureConfig<?>) ExtendedBiomeFeatures.chestnut().config).getRandomFeature(rand);
	}
}