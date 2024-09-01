package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.ShireBiome;

@Mixin(ShireBiome.class)
public class MixinShireBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected final Object[] shireTrees() {
		return new Object[] { LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 4000, 
				LOTRBiomeFeatures.oakBees(), 100, LOTRBiomeFeatures.oakFancyBees(), 40, 
				LOTRBiomeFeatures.oakParty(), 200, ExtendedBiomeFeatures.chestnut(), 2500, 
				ExtendedBiomeFeatures.chestnutFancy(), 1000, ExtendedBiomeFeatures.chestnutBees(), 25, 
				ExtendedBiomeFeatures.chestnutFancyBees(), 10, LOTRBiomeFeatures.birch(), 250, 
				LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.birchBees(), 2, 
				LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500, 
				LOTRBiomeFeatures.aspenLarge(), 100, LOTRBiomeFeatures.apple(), 50, 
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pear(), 50, 
				LOTRBiomeFeatures.pearBees(), 1, LOTRBiomeFeatures.cherry(), 20, 
				LOTRBiomeFeatures.cherryBees(), 1, 
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1 };
	}
}
