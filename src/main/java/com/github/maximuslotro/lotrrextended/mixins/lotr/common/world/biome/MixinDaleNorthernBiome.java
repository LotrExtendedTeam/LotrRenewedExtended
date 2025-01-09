package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.DaleBiome.Northern;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(Northern.class)
public class MixinDaleNorthernBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakDead(), 500, LOTRBiomeFeatures.oakBees(), 50,
				LOTRBiomeFeatures.oakFancyBees(), 10, LOTRBiomeFeatures.spruce(), 2000,
				LOTRBiomeFeatures.spruceDead(), 500, ExtendedBiomeFeatures.chestnut(), 1000,
				ExtendedBiomeFeatures.chestnutFancy(), 200, ExtendedBiomeFeatures.chestnutBees(), 10,
				ExtendedBiomeFeatures.chestnutFancyBees(), 2, LOTRBiomeFeatures.maple(), 500,
				LOTRBiomeFeatures.mapleBees(), 5, LOTRBiomeFeatures.pine(), 2000,
				LOTRBiomeFeatures.pineDead(), 200, LOTRBiomeFeatures.fir(), 2000,
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50,
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1 };
		LOTRBiomeFeatures.addTreesWithClusters((Northern)(Object)this, builder, 0, 0.05F, TreeCluster.of(8, 40), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreelineIncrease((Northern)(Object)this, builder, 2, 0.3F, 2, 82, weightedTrees);
		LOTRBiomeFeatures.addGrass((Northern)(Object)this, builder, 6, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addBorealFlowers(builder, 2);
	}
}
