package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.HarondorBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(HarondorBiome.class)
public class MixinHarondorBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { LOTRBiomeFeatures.oakDesert(), 10000, LOTRBiomeFeatures.oakDesertBees(), 10,
				LOTRBiomeFeatures.cedar(), 2500,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1  };
		LOTRBiomeFeatures.addTrees((HarondorBiome)(Object)this, builder, 0, 0.1F, weightedTrees);
		LOTRBiomeFeatures.addTreesIncrease((HarondorBiome)(Object)this, builder, 0, 0.0625F, 3, weightedTrees);
		LOTRBiomeFeatures.addTreesIncrease((HarondorBiome)(Object)this, builder, 0, 0.0625F, 7, weightedTrees);
		LOTRBiomeFeatures.addGrass((HarondorBiome)(Object)this, builder, 8, GrassBlends.WITH_ARID);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_ARID);
		LOTRBiomeFeatures.addDeadBushes(builder, 1);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 4);
		LOTRBiomeFeatures.addCactiFreq(builder, 1);
	}
}
