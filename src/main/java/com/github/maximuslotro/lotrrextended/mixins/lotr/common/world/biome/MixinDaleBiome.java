package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.DaleBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(DaleBiome.class)
public class MixinDaleBiome {

	/***
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
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1 };
		LOTRBiomeFeatures.addTreesWithClusters((DaleBiome)(Object)this, builder, 0, 0.05F, TreeCluster.of(8, 16), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreelineIncrease((DaleBiome)(Object)this, builder, 2, 0.3F, 3, 82, weightedTrees);
		LOTRBiomeFeatures.addGrass((DaleBiome)(Object)this, builder, 8, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
	}
}
