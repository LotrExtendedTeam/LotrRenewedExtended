package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.RivendellBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(RivendellBiome.class)
public class MixinRivendellBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 2000,
				LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 20,
				LOTRBiomeFeatures.birch(), 5000, LOTRBiomeFeatures.birchFancy(), 1000,
				LOTRBiomeFeatures.birchBees(), 50, LOTRBiomeFeatures.birchFancyBees(), 10,
				LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200,
				LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2,
				ExtendedBiomeFeatures.chestnut(), 500, ExtendedBiomeFeatures.chestnutFancy(), 100,
				ExtendedBiomeFeatures.chestnutBees(), 5, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.larch(), 1500, LOTRBiomeFeatures.aspen(), 1000,
				LOTRBiomeFeatures.aspenLarge(), 500, LOTRBiomeFeatures.apple(), 50,
				LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pearBees(), 1 };
		LOTRBiomeFeatures.addTreesWithClusters((RivendellBiome)(Object)this, builder, 0, 0.25F, TreeCluster.of(12, 16), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreelineIncrease((RivendellBiome)(Object)this, builder, 2, 0.2F, 2, 85, weightedTrees);
		LOTRBiomeFeatures.addGrass((RivendellBiome)(Object)this, builder, 6, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 5);
	}
}
