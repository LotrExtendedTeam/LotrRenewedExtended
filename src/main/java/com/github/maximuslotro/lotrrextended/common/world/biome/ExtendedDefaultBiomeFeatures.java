package com.github.maximuslotro.lotrrextended.common.world.biome;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeatures;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ExtendedDefaultBiomeFeatures {
	public static void addFangornVegetation(BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ExtendedFeatures.FLOWER_FANGORN);
		pBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ExtendedFeatures.PATCH_WATERLILLY_FANGORN);
		pBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ExtendedFeatures.PATCH_RIVERWEED);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.FLOWER_FANGORN_STONEROOT);
	}

	public static void addFlowers(BiomeGenerationSettings.Builder pBuilder, ConfiguredFeature<?, ?> flowers) {
		pBuilder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, flowers);
	}

	public static void addDefaultOres(BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_AMBER);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_AMETHYST);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_DIAMOND);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_EMERALD);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_OPAL);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_RUBY);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_SAPPHIRE);
		pBuilder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ExtendedFeatures.ORE_TOPAZ);
	}
}
