package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

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
			}if (event.getName().equals(LOTRBiomes.NAN_CURUNIR.getRegistryName())) {
				ExtendedBiomeFeatures.addOrthancWalls(event.getGeneration());
				ExtendedBiomeFeatures.addScorchedLand(event.getGeneration(), ExtendedConfiguredFeatures.ISENGARD_SCORCHED_LAND, 80);
				ExtendedBiomeFeatures.addBoulders(event.getGeneration(), ExtendedBlocks.WASTE_BLOCK.get().defaultBlockState(), 1, 3, 32, 3, 3);
			}
			if(!event.getName().equals(LOTRBiomes.SEA.getRegistryName()) && !event.getName().getPath().contains("river")) {
				ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 10000);
				if(event.getName().equals(LOTRBiomes.FORODWAITH.getRegistryName()) || event.getName().equals(LOTRBiomes.FORODWAITH_MOUNTAINS.getRegistryName())) {
					ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 3000);
				}
				if(event.getName().equals(LOTRBiomes.LINDON.getRegistryName())) {
					ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 4000);
				}
				if(event.getName().equals(LOTRBiomes.LINDON_WOODLANDS.getRegistryName())) {
					ExtendedBiomeFeatures.addMeteorites(event.getGeneration(), 3000);
				}
			}
		}
	}
}