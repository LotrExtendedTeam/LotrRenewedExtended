package lotr.common.structure.StructureV4;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedConfiguredStructures {

	public static final Map<String, Supplier<StructureFeature<?, ?>>> CONFIGURED_STRUCTURES = new HashMap<>();
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_SMALL = ExtendedStructures.HOBBIT_TAVERN_SMALL.get().configured(IFeatureConfig.NONE);

	public static void genConfiguredStructures() {
		registerConfiguredStructures(ExtendedStructures.HOBBIT_TAVERN_SMALL.get(), CONFIGURED_HOBBIT_TAVERN_SMALL);
	}
	public static void registerConfiguredStructures(Structure<?> structure, StructureFeature<?, ?> feature) {
		ResourceLocation location = structure.getRegistryName();
		CONFIGURED_STRUCTURES.put(location.getPath(), ()-> feature);
		Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(location.getNamespace(), "configured_"+location.getPath()), feature);
    }
}
