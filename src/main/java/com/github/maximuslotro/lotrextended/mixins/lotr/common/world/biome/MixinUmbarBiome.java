package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.UmbarBiome;

@Mixin(UmbarBiome.class)
public class MixinUmbarBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @return An array containing tree features followed by their spawn weights.
	 * */
	@Overwrite(remap = false)
	protected final Object[] umbarTrees() {
		return new Object[] { LOTRBiomeFeatures.oakDesert(), 10000, LOTRBiomeFeatures.oakDesertBees(), 10,
				LOTRBiomeFeatures.cypress(), 5000,
				LOTRBiomeFeatures.cedar(), 3000, 
				// Extended Start
				ExtendedBiomeFeatures.ebonyFancy(), 100, ExtendedBiomeFeatures.ebony(), 2,
				ExtendedBiomeFeatures.ebonyFancyBees(), 2, ExtendedBiomeFeatures.ebonyBees(), 1,
				ExtendedBiomeFeatures.plum(), 20, ExtendedBiomeFeatures.plumBees(), 1};
	}
}
