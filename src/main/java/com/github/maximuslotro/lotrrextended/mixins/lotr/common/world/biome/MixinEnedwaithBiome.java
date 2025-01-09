package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.EnedwaithBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(EnedwaithBiome.class)
public class MixinEnedwaithBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakTall(), 3000,
				LOTRBiomeFeatures.oakFancy(), 2000, LOTRBiomeFeatures.oakBees(), 5,
				LOTRBiomeFeatures.oakTallBees(), 3, LOTRBiomeFeatures.oakFancyBees(), 2,
				LOTRBiomeFeatures.spruce(), 10000, ExtendedBiomeFeatures.chestnut(), 10000,
				ExtendedBiomeFeatures.chestnutBees(), 50};
		LOTRBiomeFeatures.addTreesWithClusters((EnedwaithBiome)(Object)this, builder, 0, 0.04F, TreeCluster.of(8, 40), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((EnedwaithBiome)(Object)this, builder, 2, 0.1F, 75, weightedTrees);
		LOTRBiomeFeatures.addGrass((EnedwaithBiome)(Object)this, builder, 8, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
	}
}
