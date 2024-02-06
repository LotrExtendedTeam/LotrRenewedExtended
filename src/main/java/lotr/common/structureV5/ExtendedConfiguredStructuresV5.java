package lotr.common.structureV5;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ExtendedConfiguredStructuresV5 {
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOUSE = ExtendedStructuresV5.HOBBIT_HOUSE.get().configured(IFeatureConfig.NONE);

	public static void registerConfiguredStructures(){
		registerConfiguredStructures(CONFIGURED_HOBBIT_HOUSE);
	}
    public static void registerConfiguredStructures(StructureFeature<?, ?> structure) {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(LotrExtendedMod.MODID, "configured_"+structure.feature.getRegistryName().getPath()), structure);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(structure.feature, structure);
    }
}
