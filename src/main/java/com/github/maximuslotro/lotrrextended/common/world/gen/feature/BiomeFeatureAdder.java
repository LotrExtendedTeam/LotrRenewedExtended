package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBiomePointers;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class BiomeFeatureAdder {
	public static void addFeatureToBiomes(BiomeLoadingEvent event) {
		if(event.getName()!=null && event.getName().getNamespace().equals("lotr")) {
			if(!(event.getName().equals(ExtendedBiomePointers.MORDOR.location())
				||event.getName().equals(ExtendedBiomePointers.MORDOR_MOUNTAINS.location())
				||event.getName().equals(ExtendedBiomePointers.MORGUL_VALE.location()))) {
				ExtendedBiomeFeatures.addBushes(event.getGeneration(), 4);
			}
			if (event.getName().equals(ExtendedBiomePointers.DEAD_MARSHES.location())) {
				ExtendedBiomeFeatures.addRemains(event.getGeneration());
			}if (event.getName().equals(ExtendedBiomePointers.NAN_CURUNIR.location())) {
				ExtendedBiomeFeatures.addOrthancWalls(event.getGeneration());
				ExtendedBiomeFeatures.addScorchedLand(event.getGeneration(), ExtendedConfiguredFeatures.ISENGARD_SCORCHED_LAND, 80);
				ExtendedBiomeFeatures.addBoulders(event.getGeneration(), ExtendedBlocks.WASTE_BLOCK.get().defaultBlockState(), 1, 3, 32, 3, 3);
			}
			if(!event.getName().equals(ExtendedBiomePointers.SEA.location()) && !event.getName().getPath().contains("river")) {
				ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 10000);
				if(event.getName().equals(ExtendedBiomePointers.FORODWAITH.location()) || event.getName().equals(ExtendedBiomePointers.FORODWAITH_MOUNTAINS.location())) {
					ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 3000);
				}
				if(event.getName().equals(ExtendedBiomePointers.LINDON.location())) {
					ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 4000);
				}
				if(event.getName().equals(ExtendedBiomePointers.LINDON_WOODLANDS.location())) {
					ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 3000);
				}
			}
		}
	}
}