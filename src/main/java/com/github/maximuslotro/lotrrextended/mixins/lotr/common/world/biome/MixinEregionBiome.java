package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.EregionBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(EregionBiome.class)
public class MixinEregionBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((EregionBiome)(Object)this, builder, 0, 0.4F, TreeCluster.of(12, 5), 
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, 
				LOTRBiomeFeatures.oakBees(), 5, LOTRBiomeFeatures.oakFancyBees(), 1, 
				ExtendedBiomeFeatures.chestnut(), 1000, ExtendedBiomeFeatures.chestnutFancy(), 500, 
				ExtendedBiomeFeatures.chestnutBees(), 10, ExtendedBiomeFeatures.chestnutFancyBees(), 5,
				LOTRBiomeFeatures.holly(), 10000, LOTRBiomeFeatures.hollyBees(), 10, 
				LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 500, 
				LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1, 
				LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 250, 
				LOTRBiomeFeatures.beechBees(), 1, LOTRBiomeFeatures.beechFancyBees(), 1, 
				LOTRBiomeFeatures.larch(), 2000, LOTRBiomeFeatures.aspen(), 200, 
				LOTRBiomeFeatures.aspenLarge(), 50, LOTRBiomeFeatures.apple(), 50, 
				LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1, 
				LOTRBiomeFeatures.pearBees(), 1);
		LOTRBiomeFeatures.addGrass((EregionBiome)(Object)this, builder, 6, GrassBlends.WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 3);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
		LOTRBiomeFeatures.addFoxBerryBushes(builder);
	}
}
