package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.IthilienBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;

@Mixin(IthilienBiome.class)
public class MixinIthilienBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @return An array containing tree features followed by their spawn weights.
	 * */
	@Overwrite(remap = false)
	protected Object[] getIthilienTrees() {
		return new Object[] { ExtendedBiomeFeatures.willowTree(), 5000, LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakTall(), 1000,
				LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakBees(), 50,
				LOTRBiomeFeatures.oakTallBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 20,
				LOTRBiomeFeatures.oakParty(), 50, LOTRBiomeFeatures.lebethron(), 1000,
				LOTRBiomeFeatures.lebethronFancy(), 500, LOTRBiomeFeatures.lebethronBees(), 10,
				LOTRBiomeFeatures.lebethronFancy(), 5, LOTRBiomeFeatures.lebethronParty(), 20,
				LOTRBiomeFeatures.birch(), 1500, LOTRBiomeFeatures.birchFancy(), 500,
				LOTRBiomeFeatures.birchBees(), 15, LOTRBiomeFeatures.birchFancyBees(), 5,
				LOTRBiomeFeatures.cedar(), 1500, LOTRBiomeFeatures.cypress(), 1800,
				ExtendedBiomeFeatures.chestnut(), 1000, ExtendedBiomeFeatures.chestnutFancy(), 500,
				ExtendedBiomeFeatures.chestnutBees(), 10, ExtendedBiomeFeatures.chestnutFancyBees(), 5,
				LOTRBiomeFeatures.pine(), 500, LOTRBiomeFeatures.apple(), 50,
				LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 5,
				LOTRBiomeFeatures.pearBees(), 5, LOTRBiomeFeatures.culumalda(), 40,
				LOTRBiomeFeatures.culumaldaBees(), 1 };
	}
}
