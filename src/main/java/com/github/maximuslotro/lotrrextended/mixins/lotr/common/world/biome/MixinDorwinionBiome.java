package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.DorwinionBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;

@Mixin(DorwinionBiome.class)
public class MixinDorwinionBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected final Object[] dorwinionTrees() {
		return new Object[] {
				ExtendedBiomeFeatures.willowTree(), 1000,
				LOTRBiomeFeatures.oak(), 2000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 200, LOTRBiomeFeatures.oakFancyBees(), 100,
				LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 500,
				LOTRBiomeFeatures.birchBees(), 10, LOTRBiomeFeatures.birchFancyBees(), 5,
				LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechFancy(), 200,
				LOTRBiomeFeatures.beechBees(), 2, LOTRBiomeFeatures.beechFancyBees(), 2,
				LOTRBiomeFeatures.cypress(), 5000, LOTRBiomeFeatures.oakShrub(), 8000,
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50,
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1,
				ExtendedBiomeFeatures.plum(), 100, ExtendedBiomeFeatures.plumBees(), 1 };
	}
}
