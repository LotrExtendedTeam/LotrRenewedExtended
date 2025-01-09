package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.AnduinValeBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;

@Mixin(AnduinValeBiome.class)
public class MixinAnduinValeBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected final Object[] anduinTrees() {
		return new Object[] { LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 100, LOTRBiomeFeatures.oakFancyBees(), 10,
				LOTRBiomeFeatures.birch(), 1500, LOTRBiomeFeatures.birchFancy(), 150,
				LOTRBiomeFeatures.birchBees(), 15, LOTRBiomeFeatures.birchFancyBees(), 2,
				LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.larch(), 1500,
				ExtendedBiomeFeatures.chestnut(), 1000, ExtendedBiomeFeatures.chestnutFancy(), 100,
				ExtendedBiomeFeatures.chestnutBees(), 10, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.pine(), 1000, LOTRBiomeFeatures.apple(), 10,
				LOTRBiomeFeatures.appleBees(), 10, LOTRBiomeFeatures.pear(), 10,
				LOTRBiomeFeatures.pearBees(), 10 };
	}
}
