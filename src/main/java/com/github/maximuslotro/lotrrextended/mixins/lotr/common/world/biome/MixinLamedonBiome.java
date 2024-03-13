package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LamedonBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(LamedonBiome.class)
public class MixinLamedonBiome {

	@Shadow(remap = false)
	protected int getLamedonBaseTreeRate() {return 0;}

	/***
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((LamedonBiome)(Object)this, builder, getLamedonBaseTreeRate(), 0.1F, TreeCluster.of(10, 20), 
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, 
				LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10, 
				LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 100, 
				LOTRBiomeFeatures.birchBees(), 5, LOTRBiomeFeatures.birchFancyBees(), 1, 
				LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 100, 
				LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 1, 
				ExtendedBiomeFeatures.chestnut(), 2000, ExtendedBiomeFeatures.chestnutFancy(), 500, 
				ExtendedBiomeFeatures.chestnutBees(), 20, ExtendedBiomeFeatures.chestnutFancyBees(), 5,
				LOTRBiomeFeatures.larch(), 3000, LOTRBiomeFeatures.aspen(), 3000, 
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, 
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1);
		LOTRBiomeFeatures.addGrass((LamedonBiome)(Object)this, builder, 8, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 3);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
	}
}
