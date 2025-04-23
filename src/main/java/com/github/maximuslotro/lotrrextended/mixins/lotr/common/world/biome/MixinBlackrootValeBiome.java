package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.BlackrootValeBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(BlackrootValeBiome.class)
public class MixinBlackrootValeBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((BlackrootValeBiome)(Object)this, builder, 0, 0.2F, TreeCluster.of(8, 20),
				ExtendedBiomeFeatures.willowTree(), 2500,
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 10,
				LOTRBiomeFeatures.darkOak(), 5000, LOTRBiomeFeatures.fir(), 3000,
				LOTRBiomeFeatures.larch(), 3000, LOTRBiomeFeatures.aspen(), 1000,
				LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 100,
				LOTRBiomeFeatures.birchBees(), 5, LOTRBiomeFeatures.birchFancyBees(), 1,
				ExtendedBiomeFeatures.chestnut(), 2000, ExtendedBiomeFeatures.chestnutFancy(), 500,
				ExtendedBiomeFeatures.chestnutBees(), 20, ExtendedBiomeFeatures.chestnutFancyBees(), 5,
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50,
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1);
		LOTRBiomeFeatures.addGrass((BlackrootValeBiome)(Object)this, builder, 12, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 8, LOTRBlocks.BLACKROOT.get(), 60);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 2);
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
	}
}
