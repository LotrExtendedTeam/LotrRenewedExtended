package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.AnfalasBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(AnfalasBiome.class)
public class MixinAnfalasBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((AnfalasBiome)(Object)this, builder, 0, 0.05F, TreeCluster.of(8, 25), 
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, 
			LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10, 
			LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 500, 
			LOTRBiomeFeatures.birchBees(), 10, LOTRBiomeFeatures.birchFancyBees(), 5, 
			LOTRBiomeFeatures.cypress(), 4000, LOTRBiomeFeatures.apple(), 50,
			LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, 
			LOTRBiomeFeatures.pearBees(), 1,
			ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1);
		LOTRBiomeFeatures.addGrass((AnfalasBiome)(Object)this, builder, 6, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
	}
}
