package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.init.LOTRBiomes;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class BiomeStructureAdder {
	/**
     * Will go into the world's ChunkGenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklisting a structure.
     * (Don't forget to attempt to remove your structure too from the map if you are blacklisting a dimension!)
     * (It might have your structure in it already.)
     *
     * Basically use this to make absolutely sure the ChunkGenerator can or cannot spawn our structures.
     */
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
		addStructureToMap(tempMap, ExtendedStructuresV5.RANGER_ENCAMPMENT.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.RANGER_FORT.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.RANGER_HIDEOUT.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.RANGER_HOUSE_HIDEOUT.get());
		//addStructureToMap(tempMap, ExtendedStructuresV5.RANGER_HOUSE_RUIN.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.RANGER_WATCHTOWER.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.ARNOR_RUINS.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.GUNDABAD_CAMP.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.GUNDABAD_ENCAMPMENT.get());
		provider.generator.getSettings().structureConfig = tempMap;
	}

	private static void addStructureToMap(Map<Structure<?>, StructureSeparationSettings> tempMap, Structure<? extends IFeatureConfig> structure) {
		tempMap.putIfAbsent(structure, DimensionStructuresSettings.DEFAULTS.get(structure));
	}

	/*
     * Add our structure to all biomes including other modded biomes if need be.
     * We can skip or add only to certain biomes based on stuff like biome category,
     * temperature, scale, precipitation, Mod id, etc. All kinds of options!
     *
     * We can even use the BiomeDictionary as well! To use BiomeDictionary, do
     * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
     * RegistryKey. Then that can be fed into the dictionary to get the biome's types.
     */
	public static void addStructuresToBiomes(BiomeLoadingEvent event) {
		if(event.getName()!=null) {
			if(event.getName().equals(LOTRBiomes.SHIRE.getRegistryName())) {
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
			if(event.getName().equals(LOTRBiomes.SHIRE_WOODLANDS.getRegistryName())
					||event.getName().equals(LOTRBiomes.SHIRE_MOORS.getRegistryName())||event.getName().equals(LOTRBiomes.WHITE_DOWNS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_BARN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_WINDMILL);
			}
			if(event.getName().equals(LOTRBiomes.LONE_LANDS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
			if(event.getName().equals(LOTRBiomes.LONE_LANDS_HILLS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}
			if(event.getName().equals(LOTRBiomes.ERIADOR.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}
			if(event.getName().equals(LOTRBiomes.ERIADOR_DOWNS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}
			if(event.getName().equals(LOTRBiomes.MINHIRIATH.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
			if(event.getName().equals(LOTRBiomes.ERYN_VORN.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}
			if(event.getName().equals(LOTRBiomes.TOWER_HILLS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
			}
			if(event.getName().equals(LOTRBiomes.SWANFLEET.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}
			if(event.getName().equals(LOTRBiomes.HILLS_OF_EVENDIM.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}
			if(event.getName().equals(LOTRBiomes.ENEDWAITH.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}
			if(event.getName().equals(LOTRBiomes.TROLLSHAWS.getRegistryName())) {
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
			if(event.getName().equals(LOTRBiomes.NORTHERN_WILDERLAND.getRegistryName())) {;
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
			if(event.getName().equals(LOTRBiomes.LONG_MARSHES.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
			if(event.getName().equals(LOTRBiomes.ANDUIN_HILLS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
			if(event.getName().equals(LOTRBiomes.VALES_OF_ANDUIN.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
			if(event.getName().equals(LOTRBiomes.GLADDEN_FIELDS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}
		}
	}
}
