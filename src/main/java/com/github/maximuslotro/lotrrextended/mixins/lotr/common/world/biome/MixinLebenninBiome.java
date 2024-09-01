package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LebenninBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(LebenninBiome.class)
public class MixinLebenninBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((LebenninBiome)(Object)this, builder, 0, 0.5F, TreeCluster.of(10, 30), new Object[]{
				LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 3000, LOTRBiomeFeatures.oakBees(), 100,
				LOTRBiomeFeatures.oakFancyBees(), 30, LOTRBiomeFeatures.birch(), 3000, LOTRBiomeFeatures.birchFancy(),
				1000, LOTRBiomeFeatures.birchBees(), 30, LOTRBiomeFeatures.birchFancyBees(), 10,
				LOTRBiomeFeatures.beech(), 1500, LOTRBiomeFeatures.beechFancy(), 500, LOTRBiomeFeatures.beechBees(), 15,
				LOTRBiomeFeatures.beechFancyBees(), 5, LOTRBiomeFeatures.maple(), 500, LOTRBiomeFeatures.mapleBees(), 5,
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pearBees(), 1, 
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1});
		LOTRBiomeFeatures.addGrass((LebenninBiome)(Object)this, builder, 12, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 3, new Object[]{LOTRBlocks.MALLOS.get(), 50});
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
	}
}
