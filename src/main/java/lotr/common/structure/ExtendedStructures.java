package lotr.common.structure;

import java.util.function.Supplier;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lotr.common.structure.singleInstanceStructures.HobbitTavernStructure;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedStructures {
	// Registry
	public static final DeferredRegister<Structure<?>> DEF_REG_STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, LotrExtendedMod.MODID);

	// Structures
	// #######################SHIRE STANDALONE STRUCTURES
	public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_TAVERN_SMALL = register("hobbit_tavern_small",
			() -> new HobbitTavernStructure(NoFeatureConfig.CODEC), true, new StructureSeparationSettings(1,2,785555898));

	public static <C extends IFeatureConfig> RegistryObject<Structure<C>> register(String name,
			Supplier<? extends Structure<C>> sup, boolean editLand, StructureSeparationSettings settings) {
		RegistryObject<Structure<C>> structure = DEF_REG_STRUCTURES.register(name, sup);
		Structure.STRUCTURES_REGISTRY.put(sup.get().getRegistryName().toString(), structure.get());
		if (editLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
					.addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure.get()).build();
		}
		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure.get(), settings).build();

		return structure;
	}

	public static void init() {}
}
