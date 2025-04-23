package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import lotr.common.world.gen.feature.WillowTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedFeature {

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "lotrextended");
	public static final ExtendedSimpleBlockFeature SIMPLE_BLOCK_PRE = new ExtendedSimpleBlockFeature(ExtendedSimpleBlockFeatureConfig.CODEC);
	public static final RegistryObject<ExtendedSimpleBlockFeature> SIMPLE_BLOCK = regFeature("simple_block", SIMPLE_BLOCK_PRE);
	public static final ExtendedVegetationPatchFeature VEGETATION_PATCH_PRE = new ExtendedVegetationPatchFeature(ExtendedVegetationPatchConfig.CODEC);
	public static final RegistryObject<ExtendedVegetationPatchFeature> VEGETATION_PATCH = regFeature("vegetation_patch", VEGETATION_PATCH_PRE);
	public static final ExtendedHolyUnderwaterSphereFeature HOLEY_UNDERWATER_SPHERE_PRE = new ExtendedHolyUnderwaterSphereFeature(ExtendedHoleySphereConfig.CODEC);
	public static final RegistryObject<ExtendedHolyUnderwaterSphereFeature> HOLEY_UNDERWATER_SPHERE = regFeature("holey_underwater_sphere", HOLEY_UNDERWATER_SPHERE_PRE);

	public static final WillowTreeFeature WILLOW_TREE_WATER_FEATURE_PRE = new WillowTreeFeature(NoFeatureConfig.CODEC, true);
	public static final RegistryObject<WillowTreeFeature> WILLOW_TREE_WATER_FEATURE = regFeature("willow_tree_water_feature", WILLOW_TREE_WATER_FEATURE_PRE);

	public static final WillowTreeFeature WILLOW_TREE_FEATURE_PRE = new WillowTreeFeature(NoFeatureConfig.CODEC, false);
	public static final RegistryObject<WillowTreeFeature> WILLOW_TREE_FEATURE = regFeature("willow_tree_feature", WILLOW_TREE_FEATURE_PRE);

	private static <FC extends IFeatureConfig, F extends Feature<FC>> RegistryObject<F> regFeature(String name, F feature) {
		return FEATURES.register(name, () -> feature);
	}

	public static void register(IEventBus modBus) {
		FEATURES.register(modBus);
	}

	public static void register(RegistryEvent.Register<Feature<?>> event) {
		if (event.getRegistry() == ForgeRegistries.FEATURES) {
			ExtendedFeatures.register();
		}
	}
}
