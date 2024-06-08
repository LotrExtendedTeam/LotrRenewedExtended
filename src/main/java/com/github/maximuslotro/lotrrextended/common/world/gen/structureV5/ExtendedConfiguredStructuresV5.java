package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ExtendedConfiguredStructuresV5 {
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_BARN = register(ExtendedStructuresV5.HOBBIT_BARN.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_SMALL = register(ExtendedStructuresV5.HOBBIT_HOLE_SMALL.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_LARGE = register(ExtendedStructuresV5.HOBBIT_HOLE_LARGE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOUSE = register(ExtendedStructuresV5.HOBBIT_HOUSE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_BLANKET = register(ExtendedStructuresV5.HOBBIT_PICNIC_BLANKET.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_TABLE = register(ExtendedStructuresV5.HOBBIT_PICNIC_TABLE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_SMALL = register(ExtendedStructuresV5.HOBBIT_TAVERN_SMALL.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_LARGE = register(ExtendedStructuresV5.HOBBIT_TAVERN_LARGE.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_WINDMILL = register(ExtendedStructuresV5.HOBBIT_WINDMILL.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_CAMP = register(ExtendedStructuresV5.RANGER_CAMP.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_ENCAMPMENT = register(ExtendedStructuresV5.RANGER_ENCAMPMENT.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_FORT = register(ExtendedStructuresV5.RANGER_FORT.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HIDEOUT = register(ExtendedStructuresV5.RANGER_HIDEOUT.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_WATCHTOWER = register(ExtendedStructuresV5.RANGER_WATCHTOWER.get().configured(IFeatureConfig.NONE));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HOUSE_RUIN = register(ExtendedStructuresV5.RANGER_HOUSE_RUIN.get().configured(IFeatureConfig.NONE));

	public static void registerConfiguredStructures(){}

    public static StructureFeature<?, ?> register(StructureFeature<?, ?> structure) {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(LotrExtendedMod.MODID, "configured_"+structure.feature.getRegistryName().getPath()), structure);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(structure.feature, structure);
        return structure;
    }
}
