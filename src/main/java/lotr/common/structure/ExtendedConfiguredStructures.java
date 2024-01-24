package lotr.common.structure;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ExtendedConfiguredStructures {
	public static StructureFeature<?, ?> HOBBIT_TAVERN_SMALL = ExtendedStructures.HOBBIT_TAVERN_SMALL.get().configured(IFeatureConfig.NONE);

	public static void register() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(LotrExtendedMod.MODID, "configured_run_down_house"), HOBBIT_TAVERN_SMALL);
	}
}
