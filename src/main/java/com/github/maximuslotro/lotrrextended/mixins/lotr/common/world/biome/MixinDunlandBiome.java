package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.DunlandBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(DunlandBiome.class)
public class MixinDunlandBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.oak(), 1000,
				LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakFancy(), 200,
				LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakTallBees(), 10,
				LOTRBiomeFeatures.oakFancyBees(), 2, ExtendedBiomeFeatures.chestnut(), 500,
				ExtendedBiomeFeatures.chestnutFancy(), 100, ExtendedBiomeFeatures.chestnutBees(), 5,
				ExtendedBiomeFeatures.chestnutFancyBees(), 1, LOTRBiomeFeatures.pine(), 5000,
				LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.fir(), 5000 };
		LOTRBiomeFeatures.addTreesWithClusters((DunlandBiome)(Object)this, builder, 0, 0.4F, TreeCluster.of(12, 10), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((DunlandBiome)(Object)this, builder, 6, 0.1F, 85, weightedTrees);
		LOTRBiomeFeatures.addGrass((DunlandBiome)(Object)this, builder, 6, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addForestFlowers(builder, 2);
		LOTRBiomeFeatures.addFoxBerryBushes(builder);
	}
}
