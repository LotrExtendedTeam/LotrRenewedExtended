package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.EriadorBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;

@Mixin(EriadorBiome.class)
public class MixinEriadorBiome {

	/***
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected final Object[] eriadorTrees() {
		return new Object[] { LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000, 
				LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 1, 
				LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 100, 
				LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1, 
				LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.beech(), 200, 
				LOTRBiomeFeatures.beechFancy(), 20, LOTRBiomeFeatures.beechBees(), 1, 
				LOTRBiomeFeatures.beechFancyBees(), 1, ExtendedBiomeFeatures.chestnut(), 1000, 
				ExtendedBiomeFeatures.chestnutFancy(), 100, ExtendedBiomeFeatures.chestnutBees(), 10, 
				ExtendedBiomeFeatures.chestnutFancyBees(), 1, LOTRBiomeFeatures.maple(), 50, 
				LOTRBiomeFeatures.mapleFancy(), 5, LOTRBiomeFeatures.mapleBees(), 1, 
				LOTRBiomeFeatures.mapleFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500, 
				LOTRBiomeFeatures.aspenLarge(), 50, LOTRBiomeFeatures.apple(), 20, 
				LOTRBiomeFeatures.pear(), 20, LOTRBiomeFeatures.appleBees(), 1, 
				LOTRBiomeFeatures.pearBees(), 1 };
	}
}
