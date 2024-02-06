package lotr.common.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lotr.common.structureV5.structures.HouseStructure;

import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedStructuresV5 {
    public static final DeferredRegister<Structure<?>> STRUCTURE_REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, LotrExtendedMod.MODID);
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_HOUSE = STRUCTURE_REGISTRY.register("hobbit_house", () -> new HouseStructure());
    
    public static final int SALT = 785555898;
   
	public static void setupStructures() {
		register(HOBBIT_HOUSE.get(), true, 40, 6);

	}
	
	public static void register(Structure<NoFeatureConfig> structure, boolean editLand, int spacing, int seperation) {
		if(spacing-seperation<= 0) {
			throw new IllegalArgumentException("Structure SeperationSettings setup detected an invalid configuration where seperation>=spacing: "+seperation+">="+spacing);
		}
		StructureSeparationSettings SSsettings = new StructureSeparationSettings(spacing, seperation, SALT);
		
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

		if (editLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
		}
		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, SSsettings).build();
		
		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(structureSettings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = structureSettings.getValue().structureSettings().structureConfig();
            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, SSsettings);
                structureSettings.getValue().structureSettings().structureConfig();

            } else {
                structureMap.put(structure, SSsettings);
            }
        });
	}
}
