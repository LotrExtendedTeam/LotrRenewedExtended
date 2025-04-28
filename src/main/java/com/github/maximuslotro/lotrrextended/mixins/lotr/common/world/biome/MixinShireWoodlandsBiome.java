package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.ShireWoodlandsBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(ShireWoodlandsBiome.class)
public class MixinShireWoodlandsBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((ShireWoodlandsBiome)(Object)this, builder, 9, 0.5F,
				ExtendedBiomeFeatures.willowTree(), 10000,
				LOTRBiomeFeatures.shirePine(), 25000, LOTRBiomeFeatures.oak(), 10000,
				LOTRBiomeFeatures.oakFancy(), 4000, LOTRBiomeFeatures.oakBees(), 20,
				LOTRBiomeFeatures.oakFancyBees(), 8, LOTRBiomeFeatures.birch(), 2500,
				LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.birchBees(), 1,
				LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.aspen(), 3000,
				LOTRBiomeFeatures.aspenLarge(), 1000, LOTRBiomeFeatures.apple(), 50,
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pear(), 50,
				LOTRBiomeFeatures.pearBees(), 1, LOTRBiomeFeatures.cherry(), 20,
				LOTRBiomeFeatures.cherryBees(), 1,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1);
		LOTRBiomeFeatures.addGrass((ShireWoodlandsBiome)(Object)this, builder, 10, GrassBlends.SHIRE_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_WITH_FERNS);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 6, LOTRBlocks.SHIRE_HEATHER.get(), 20);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 2);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 6);
		LOTRBiomeFeatures.addFoxBerryBushes(builder);
	}
}
