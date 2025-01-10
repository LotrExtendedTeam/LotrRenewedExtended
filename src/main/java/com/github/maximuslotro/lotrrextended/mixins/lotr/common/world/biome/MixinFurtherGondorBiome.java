package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.FurtherGondorBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(FurtherGondorBiome.class)
public class MixinFurtherGondorBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((FurtherGondorBiome)(Object)this, builder, 0, 0.45F, TreeCluster.of(16, 15),
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10,
				LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 100,
				LOTRBiomeFeatures.birchBees(), 5, LOTRBiomeFeatures.birchFancyBees(), 1,
				LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.larch(), 2000,
				LOTRBiomeFeatures.oakShrub(), 10000);
		LOTRBiomeFeatures.addGrass((FurtherGondorBiome)(Object)this, builder, 12, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 5, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
	}
}
