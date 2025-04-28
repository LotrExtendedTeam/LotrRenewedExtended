package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.BlueMountainsBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(BlueMountainsBiome.class)
public class MixinBlueMountainsBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((BlueMountainsBiome)(Object)this, builder, 0, 0.4F, TreeCluster.of(10, 12),
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakFancy(), 1000, 
			LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.spruceDead(), 50, 
			LOTRBiomeFeatures.birch(), 4000, LOTRBiomeFeatures.pine(), 5000, 
			LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.fir(), 5000);
		LOTRBiomeFeatures.addGrass((BlueMountainsBiome)(Object)this, builder, 6, GrassBlends.MUTED);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MUTED);
		LOTRBiomeFeatures.addMountainsFlowers(builder, 1, LOTRBlocks.DWARFWORT.get(), 1);
	}
}
