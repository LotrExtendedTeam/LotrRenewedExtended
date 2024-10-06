package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.world.gen.placement.ExtendedCaveSurface;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ExtendedFeatures {
	public static final ConfiguredFeature<ExtendedSimpleBlockFeatureConfig, ?> MOSS_VEGETATION = register("moss_vegetation", ExtendedFeature.SIMPLE_BLOCK.get().configured(new ExtendedSimpleBlockFeatureConfig(new WeightedBlockStateProvider().add(ExtendedBlocks.MOSS_CARPET.get().defaultBlockState(), 25).add(Blocks.GRASS.defaultBlockState(), 50).add(Blocks.TALL_GRASS.defaultBlockState(), 10))));
	public static final ConfiguredFeature<ExtendedVegetationPatchConfig, ?> MOSS_PATCH = register("moss_patch", ExtendedFeature.VEGETATION_PATCH.get().configured(new ExtendedVegetationPatchConfig(ExtendedTags.Blocks.MOSS_REPLACEABLE, new SimpleBlockStateProvider(ExtendedBlocks.MOSS_BLOCK.get().defaultBlockState()), ()-> MOSS_VEGETATION, ExtendedCaveSurface.FLOOR, 1, 0.0F, 5, 0.8F, 4, 7, 0.3F)));
	public static final ConfiguredFeature<ExtendedVegetationPatchConfig, ?> MOSS_PATCH_BONEMEAL = register("moss_patch_bonemeal", ExtendedFeature.VEGETATION_PATCH.get().configured(new ExtendedVegetationPatchConfig(ExtendedTags.Blocks.MOSS_REPLACEABLE, new SimpleBlockStateProvider(ExtendedBlocks.MOSS_BLOCK.get().defaultBlockState()), ()-> MOSS_VEGETATION, ExtendedCaveSurface.FLOOR, 1, 0.0F, 5, 0.6F, 1, 2, 0.75F)));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String configuredFeatureName, ConfiguredFeature<FC, ?> pConfiguredFeature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(LotrExtendedMod.MODID, configuredFeatureName), pConfiguredFeature);
	}

	public static void register() {}
}
