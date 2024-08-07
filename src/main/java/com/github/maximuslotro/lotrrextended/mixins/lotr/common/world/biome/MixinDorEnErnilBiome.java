package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.DorEnErnilBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;

@Mixin(DorEnErnilBiome.class)
public class MixinDorEnErnilBiome {
	/***
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected final Object[] dorEnErnilTrees() {
		return new Object[] { LOTRBiomeFeatures.birch(), 2000, LOTRBiomeFeatures.birchFancy(), 2000, 
				LOTRBiomeFeatures.birchBees(), 20, LOTRBiomeFeatures.birchFancyBees(), 20, 
				LOTRBiomeFeatures.oak(), 800, LOTRBiomeFeatures.oakTall(), 800, 
				LOTRBiomeFeatures.oakFancy(), 800, LOTRBiomeFeatures.oakBees(), 8, 
				LOTRBiomeFeatures.oakTallBees(), 8, LOTRBiomeFeatures.oakFancyBees(), 8, 
				LOTRBiomeFeatures.cedar(), 1000, LOTRBiomeFeatures.cypress(), 500, 
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, 
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1, 
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1 };
	}
}
