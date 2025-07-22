package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.RohanBiome;

@Mixin(RohanBiome.class)
public class MixinRohanBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @return An array containing tree features followed by their spawn weights.
	 * */
	@Overwrite(remap = false)
	protected final Object[] rohanTrees() {
		return new Object[] { 
				ExtendedBiomeFeatures.willowTree(), 2500,
				LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakFancy(), 8000, 
				LOTRBiomeFeatures.oakBees(), 40, LOTRBiomeFeatures.oakFancyBees(), 80, 
				LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchFancy(), 100, 
				LOTRBiomeFeatures.birchBees(), 2, LOTRBiomeFeatures.birchFancyBees(), 1, 
				LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechFancy(), 100, 
				LOTRBiomeFeatures.beechBees(), 2, LOTRBiomeFeatures.beechFancyBees(), 1, 
				LOTRBiomeFeatures.pine(), 200, LOTRBiomeFeatures.apple(), 20, 
				LOTRBiomeFeatures.pear(), 20, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pearBees(), 1 };
	}
}
