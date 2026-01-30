package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class BiomeFeatureAdder {
	public static void addFeatureToBiomes(BiomeLoadingEvent event) {
		if(event.getName()!=null && event.getName().getNamespace().equals("lotr")) {
			if(!(event.getName().equals(LOTRBiomes.MORDOR.getRegistryName())
				||event.getName().equals(LOTRBiomes.MORDOR_MOUNTAINS.getRegistryName())
				||event.getName().equals(LOTRBiomes.MORGUL_VALE.getRegistryName()))) {
				ExtendedBiomeFeatures.addBushes(event.getGeneration(), 4);
			}

			if (event.getName().equals(LOTRBiomes.DEAD_MARSHES.getRegistryName())) {
				ExtendedBiomeFeatures.addRemains(event.getGeneration());
			}
			ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 2);
		}
	}
}
