package lotr.common.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lotr.common.structureV5.structures.hobbits.*;
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
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_BARN = STRUCTURE_REGISTRY.register("hobbit_barn", () -> new HobbitBarnStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_HOLE_SMALL = STRUCTURE_REGISTRY.register("hobbit_hole_small", () -> new HobbitHoleSmallStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_HOUSE = STRUCTURE_REGISTRY.register("hobbit_house", () -> new HobbitHouseStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_PICNIC_BLANKET = STRUCTURE_REGISTRY.register("hobbit_picnic_blanket", () -> new HobbitPicnicBlanketStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_PICNIC_TABLE = STRUCTURE_REGISTRY.register("hobbit_picnic_table", () -> new HobbitPicnicTableStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_TAVERN_SMALL = STRUCTURE_REGISTRY.register("hobbit_tavern_small", () -> new HobbitTavernSmallStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_WINDMILL = STRUCTURE_REGISTRY.register("hobbit_windmill", () -> new HobbitWindmillStructure());
    
    public static final int SALT = 785555898;
   
	public static void setupStructures() {
		// For StructureSeparationSettings, the first value is the average distance in chunks between spawn attempts (spacing)
		// the second value it minimum distance in chunks between spawn attempts (separation) (this must always be less than the first!
		// Setting spacing/separation to be 1/0, you can then have the structure spawn only in certain chunks in Structure.isFeatureChunk()
		// Setting spacing/separation to be 2/1, you can then have the structure spawn in every chunk
		register(HOBBIT_BARN.get(), true, 12, 3);
		register(HOBBIT_HOLE_SMALL.get(), true, 6, 3);
		register(HOBBIT_HOUSE.get(), true, 6, 3);
		register(HOBBIT_PICNIC_BLANKET.get(), false, 7, 4);
		register(HOBBIT_PICNIC_TABLE.get(), true, 7, 4);
		register(HOBBIT_TAVERN_SMALL.get(), true, 13, 9);
		register(HOBBIT_WINDMILL.get(), true, 18, 10);

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
