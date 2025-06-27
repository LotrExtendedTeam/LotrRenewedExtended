package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.AnorienBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(AnorienBiome.class)
public class MixinAnorienBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((AnorienBiome)(Object)this, builder, 0, 0.02F, TreeCluster.of(10, 30),
			ExtendedBiomeFeatures.willowTree(), 2500,
				LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 3000,
				LOTRBiomeFeatures.oakBees(), 100, LOTRBiomeFeatures.oakFancyBees(), 30,
				LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 200,
				LOTRBiomeFeatures.birchBees(), 5, LOTRBiomeFeatures.birchFancyBees(), 2,
				LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200,
				LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2,
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50,
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1);
		LOTRBiomeFeatures.addGrass((AnorienBiome)(Object)this, builder, 10, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
	}
}
