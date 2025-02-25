package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.WilderlandBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WilderlandBiome.class)
public class MixinWilderlandBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((WilderlandBiome)(Object)this, builder, 0, 0.05F, TreeCluster.of(8, 20),
				LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 1,
				LOTRBiomeFeatures.oakDead(), 5000, LOTRBiomeFeatures.spruce(), 2000,
				LOTRBiomeFeatures.spruceDead(), 1000, ExtendedBiomeFeatures.chestnut(), 1000,
				ExtendedBiomeFeatures.chestnutBees(), 1, ExtendedBiomeFeatures.chestnutFancy(), 100,
				ExtendedBiomeFeatures.chestnutFancyBees(), 1);
		LOTRBiomeFeatures.addGrass((WilderlandBiome)(Object)this, builder, 14, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 8, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addRhunPlainsFlowers(builder, 3);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
	}
}
