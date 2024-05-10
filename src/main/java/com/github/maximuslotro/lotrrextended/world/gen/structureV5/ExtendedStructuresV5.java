package com.github.maximuslotro.lotrrextended.world.gen.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.world.gen.structureV5.structures.hobbits.*;
import com.github.maximuslotro.lotrrextended.world.gen.structureV5.structures.rangers.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

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
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_HOLE_LARGE = STRUCTURE_REGISTRY.register("hobbit_hole_large", () -> new HobbitHoleLargeStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_HOUSE = STRUCTURE_REGISTRY.register("hobbit_house", () -> new HobbitHouseStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_PICNIC_BLANKET = STRUCTURE_REGISTRY.register("hobbit_picnic_blanket", () -> new HobbitPicnicBlanketStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_PICNIC_TABLE = STRUCTURE_REGISTRY.register("hobbit_picnic_table", () -> new HobbitPicnicTableStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_TAVERN_SMALL = STRUCTURE_REGISTRY.register("hobbit_tavern_small", () -> new HobbitTavernSmallStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_TAVERN_LARGE = STRUCTURE_REGISTRY.register("hobbit_tavern_large", () -> new HobbitTavernLargeStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> HOBBIT_WINDMILL = STRUCTURE_REGISTRY.register("hobbit_windmill", () -> new HobbitWindmillStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> RANGER_CAMP = STRUCTURE_REGISTRY.register("ranger_camp", () -> new RangerCampStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> RANGER_CAPTAIN_ENCAMPMENT = STRUCTURE_REGISTRY.register("ranger_captain_encampment", () -> new RangerCaptainEncampmentStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> RANGER_ENCAMPMENT = STRUCTURE_REGISTRY.register("ranger_encampment", () -> new RangerEncampmentStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> RANGER_FORT = STRUCTURE_REGISTRY.register("ranger_fort", () -> new RangerFortStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> RANGER_HIDEOUT = STRUCTURE_REGISTRY.register("ranger_hideout", () -> new RangerHideoutStructure());
    public static final RegistryObject<Structure<NoFeatureConfig>> RANGER_WATCHTOWER = STRUCTURE_REGISTRY.register("ranger_watchtower", () -> new RangerWatchtowerStructure());

    
    public static final int SALT = 785555898;
   
	public static void setupStructures() {
		// For StructureSeparationSettings, the first value is the average distance in chunks between spawn attempts (spacing)
		// the second value it minimum distance in chunks between spawn attempts (separation) (this must always be less than the first!
		// Setting spacing/separation to be 1/0, you can then have the structure spawn only in certain chunks in Structure.isFeatureChunk()
		// Setting spacing/separation to be 2/1, you can then have the structure spawn in every chunk
		register(HOBBIT_BARN.get(), true,              32, 12, 445575475);
		register(HOBBIT_HOLE_SMALL.get(), true,        24, 5, 445436262);
		register(HOBBIT_HOLE_LARGE.get(), false,       30, 10, 445436264);
		register(HOBBIT_HOUSE.get(), true,             24, 5, 445546465);
		register(HOBBIT_PICNIC_BLANKET.get(), false,   14, 8, 745785884);
		register(HOBBIT_PICNIC_TABLE.get(), true,      14, 8, 747348);
		register(HOBBIT_TAVERN_SMALL.get(), true,      25, 15, 489984587);
		register(HOBBIT_TAVERN_LARGE.get(), false,     43, 30, 965956779);
		register(HOBBIT_WINDMILL.get(), true,          40, 20, 965983479);
		register(RANGER_CAMP.get(), true,              26, 21, 9659838);
		register(RANGER_CAPTAIN_ENCAMPMENT.get(), true,32, 28, 9654578);
		register(RANGER_ENCAMPMENT.get(), true,        30, 28, 96553578);
		register(RANGER_FORT.get(), true,              29, 26, 965745738);
		register(RANGER_HIDEOUT.get(), true,           30, 28, 9664438);
		register(RANGER_WATCHTOWER.get(), true,        26, 21, 9645758);

	}
	
	public static void register(Structure<NoFeatureConfig> structure, boolean editLand, int spacing, int seperation, int salt) {
		if(spacing-seperation<= 0) {
			throw new IllegalArgumentException("Structure SeperationSettings setup detected an invalid configuration where seperation>=spacing: "+seperation+">="+spacing);
		}
		StructureSeparationSettings SSsettings = new StructureSeparationSettings(spacing, seperation, salt);
		
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
