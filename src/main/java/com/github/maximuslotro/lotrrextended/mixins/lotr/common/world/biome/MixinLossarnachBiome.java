package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LossarnachBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(LossarnachBiome.class)
public class MixinLossarnachBiome {

	/***
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((LossarnachBiome)(Object)this, builder, 0, 0.4F, TreeCluster.of(10, 30), 
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 500, LOTRBiomeFeatures.oakFancyBees(), 100, 
				LOTRBiomeFeatures.darkOak(), 4000, LOTRBiomeFeatures.birch(), 3000, 
				LOTRBiomeFeatures.birchFancy(), 1000, LOTRBiomeFeatures.birchBees(), 300, 
				LOTRBiomeFeatures.birchFancyBees(), 100, LOTRBiomeFeatures.beech(), 500, 
				LOTRBiomeFeatures.beechFancy(), 100, LOTRBiomeFeatures.beechBees(), 50, 
				LOTRBiomeFeatures.beechFancyBees(), 10, LOTRBiomeFeatures.maple(), 500, 
				LOTRBiomeFeatures.mapleFancy(), 100, LOTRBiomeFeatures.mapleBees(), 50, 
				LOTRBiomeFeatures.mapleFancyBees(), 10, ExtendedBiomeFeatures.chestnut(), 500, 
				ExtendedBiomeFeatures.chestnutFancy(), 100, ExtendedBiomeFeatures.chestnutBees(), 5,
				ExtendedBiomeFeatures.chestnutFancyBees(), 1, LOTRBiomeFeatures.apple(), 400, 
				LOTRBiomeFeatures.pear(), 400, LOTRBiomeFeatures.appleBees(), 40, 
				LOTRBiomeFeatures.pearBees(), 40, LOTRBiomeFeatures.oakShrub(), 6000, 
				ExtendedBiomeFeatures.plum(), 200, ExtendedBiomeFeatures.plumBees(), 40);
		LOTRBiomeFeatures.addGrass((LossarnachBiome)(Object)this, builder, 8, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 10);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 3);
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
	}
}
