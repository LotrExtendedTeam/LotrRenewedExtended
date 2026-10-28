package com.github.maximuslotro.lotrrextended.common.world.biome;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ExtendedDefaultBiomeFeatures {
	public static void addFangornFlowers(BiomeGenerationSettings.Builder pBuilder, ConfiguredFeature<?, ?> flowerFeature) {
		pBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, flowerFeature);
	}
}
