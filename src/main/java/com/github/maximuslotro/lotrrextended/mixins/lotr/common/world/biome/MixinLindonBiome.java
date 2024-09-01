package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LindonBiome;

@Mixin(LindonBiome.class)
public class MixinLindonBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected final Object[] lindonTrees() {
		return new Object[] { LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakFancy(), 250, 
				LOTRBiomeFeatures.oakBees(), 1, LOTRBiomeFeatures.oakFancyBees(), 1, 
				LOTRBiomeFeatures.birch(), 10000, LOTRBiomeFeatures.birchFancy(), 2000, 
				LOTRBiomeFeatures.birchBees(), 10, LOTRBiomeFeatures.birchFancyBees(), 2, 
				LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 250, 
				LOTRBiomeFeatures.beechBees(), 1, LOTRBiomeFeatures.beechFancyBees(), 1, 
				ExtendedBiomeFeatures.chestnut(), 400, ExtendedBiomeFeatures.chestnutFancy(), 100, 
				ExtendedBiomeFeatures.chestnutBees(), 4, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.aspen(), 2000, LOTRBiomeFeatures.aspenLarge(), 1000, 
				LOTRBiomeFeatures.apple(), 20, LOTRBiomeFeatures.pear(), 20, 
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1 };
	}
}
