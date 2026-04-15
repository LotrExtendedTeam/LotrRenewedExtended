package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.SouthronCoastsBiome;

@Mixin(SouthronCoastsBiome.class)
public class MixinSouthronCoastsBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @return An array containing tree features followed by their spawn weights.
	 * */
	@Overwrite(remap = false)
	protected final Object[] southronCoastsTrees() {
		return new Object[] { LOTRBiomeFeatures.cedar(), 8000, 
				LOTRBiomeFeatures.oakDesert(), 5000, LOTRBiomeFeatures.oakDesertBees(), 50, 
				LOTRBiomeFeatures.cypress(), 4000,
				// Extended Start
				ExtendedBiomeFeatures.ebonyFancy(), 100, ExtendedBiomeFeatures.ebony(), 1};
	}
}
