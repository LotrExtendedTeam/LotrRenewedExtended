package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.PinnathGelinBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(PinnathGelinBiome.class)
public class MixinPinnathGelinBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((PinnathGelinBiome)(Object)this, builder, 0, 0.01F, TreeCluster.of(10, 80),
				ExtendedBiomeFeatures.willowTree(), 1000,
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10,
				LOTRBiomeFeatures.aspen(), 5000, LOTRBiomeFeatures.birch(), 2000,
				LOTRBiomeFeatures.birchFancy(), 500, LOTRBiomeFeatures.birchBees(), 20,
				LOTRBiomeFeatures.birchFancyBees(), 5, ExtendedBiomeFeatures.chestnut(), 1000,
				ExtendedBiomeFeatures.chestnutFancy(), 500, ExtendedBiomeFeatures.chestnutBees(), 10,
				ExtendedBiomeFeatures.chestnutFancyBees(), 5, LOTRBiomeFeatures.apple(), 50,
				LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pearBees(), 1,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1);
		LOTRBiomeFeatures.addGrass((PinnathGelinBiome)(Object)this, builder, 10, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
	}
}
