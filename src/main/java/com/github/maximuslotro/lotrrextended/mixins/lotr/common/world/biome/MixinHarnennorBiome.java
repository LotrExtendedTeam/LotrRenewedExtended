package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.HarnennorBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(HarnennorBiome.class)
public class MixinHarnennorBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((HarnennorBiome)(Object)this, builder, 0, 0.1F, TreeCluster.of(3, 30), 
				LOTRBiomeFeatures.oakDesert(), 10000, LOTRBiomeFeatures.oakDesertBees(), 100, 
				LOTRBiomeFeatures.cedar(), 2500, 
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1);
		LOTRBiomeFeatures.addGrass((HarnennorBiome)(Object)this, builder, 8, GrassBlends.WITH_ARID);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_ARID);
		LOTRBiomeFeatures.addDeadBushes(builder, 1);
		LOTRBiomeFeatures.addHaradFlowers(builder, 3);
		LOTRBiomeFeatures.addCactiFreq(builder, 1);
	}
}
