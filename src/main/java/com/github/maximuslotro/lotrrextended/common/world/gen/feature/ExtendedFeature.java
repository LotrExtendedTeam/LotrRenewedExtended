package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedFeature {

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "lotrextended");
	public static final ExtendedSimpleBlockFeature SIMPLE_BLOCK_PRE = new ExtendedSimpleBlockFeature(ExtendedSimpleBlockFeatureConfig.CODEC);
	public static final RegistryObject<ExtendedSimpleBlockFeature> SIMPLE_BLOCK = redFeature("simple_block", SIMPLE_BLOCK_PRE);
	public static final ExtendedVegetationPatchFeature VEGETATION_PATCH_PRE = new ExtendedVegetationPatchFeature(ExtendedVegetationPatchConfig.CODEC);
	public static final RegistryObject<ExtendedVegetationPatchFeature> VEGETATION_PATCH = redFeature("vegetation_patch", VEGETATION_PATCH_PRE);
	public static final ExtendedHolyUnderwaterSphereFeature HOLEY_UNDERWATER_SPHERE_PRE = new ExtendedHolyUnderwaterSphereFeature(ExtendedHoleySphereConfig.CODEC);
	public static final RegistryObject<ExtendedHolyUnderwaterSphereFeature> HOLEY_UNDERWATER_SPHERE = redFeature("holey_underwater_sphere", HOLEY_UNDERWATER_SPHERE_PRE);

	private static <FC extends IFeatureConfig, F extends Feature<FC>> RegistryObject<F> redFeature(String name, F feature) {
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
