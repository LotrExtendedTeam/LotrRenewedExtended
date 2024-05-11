package com.github.maximuslotro.lotrrextended.world.gen.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class BiomeStructureAdder {
	public static void addDimensionalSpacing(ServerChunkProvider provider) {
		ExtendedLog.info("Adding structures to valid dimension instanceof MiddleEarthChunkGenerator");
		Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(provider.generator.getSettings().structureConfig());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_BARN.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_HOLE_SMALL.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_HOLE_LARGE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_HOUSE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_PICNIC_BLANKET.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_PICNIC_TABLE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_TAVERN_SMALL.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_TAVERN_LARGE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.HOBBIT_WINDMILL.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.RANGER_CAMP.get());

		provider.generator.getSettings().structureConfig = tempMap;
	}
	private static void addStructureToMap(Map<Structure<?>, StructureSeparationSettings> tempMap, Structure<NoFeatureConfig> structure) {
		tempMap.putIfAbsent(structure, DimensionStructuresSettings.DEFAULTS.get(structure));
	}

	public static void addStructuresToBiomes(BiomeLoadingEvent event) {
		if(event.getName()!=null) {
			if(event.getName().toString().equals("lotr:shire")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_BARN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_LARGE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_PICNIC_BLANKET);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_PICNIC_TABLE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_LARGE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_WINDMILL);
			}
			if(event.getName().toString().equals("lotr:shire_woodlands")
					||event.getName().toString().equals("lotr:shire_moors")||event.getName().toString().equals("lotr:white_downs")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_BARN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_WINDMILL);
			}
			if(event.getName().toString().equals("lotr:lone_lands")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAPTAIN_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
			}
			if(event.getName().toString().equals("lotr:lone_lands_hills")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
			}
			if(event.getName().toString().equals("lotr:eriador")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAPTAIN_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
			}
			if(event.getName().toString().equals("lotr:eriador_downs")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAPTAIN_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
			}
			if(event.getName().toString().equals("lotr:minhiriath")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAPTAIN_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
			}
			if(event.getName().toString().equals("lotr:eryn_vorn")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAPTAIN_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
			}
			if(event.getName().toString().equals("lotr:tower_hills")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
			}
			if(event.getName().toString().equals("lotr:swanfleet")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
			}
			if(event.getName().toString().equals("lotr:hills_of_evendim")) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_RUIN);
			}
		}
	}
}
