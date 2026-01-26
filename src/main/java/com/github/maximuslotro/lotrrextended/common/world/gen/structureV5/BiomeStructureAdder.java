package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import java.util.HashMap;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.event.world.BiomeLoadingEvent;

/**
 * Utility class for adding custom structures to biome generation and
 * injecting structure spacing settings into the world generation system.
 * <p>
 * This class handles:
 * <ul>
 *   <li>Adding mod-specific structures to the structure spacing map of the
 *       chunk generator to ensure proper spawning.</li>
 *   <li>Registering structures with biomes during biome loading events, 
 *       controlling which biomes spawn which structures.</li>
 * </ul>
 * <p>
 * Important notes:
 * <ul>
 *   <li>When blacklisting a structure from certain dimensions, 
 *       both removal from the structure spacing map and biome generation
 *       lists should be considered.</li>
 *   <li>Structures are selectively added to biomes primarily within the
 *       "lotr" namespace, with detailed handling based on biome registry names.</li>
 * </ul>
 */
public class BiomeStructureAdder {
	/**
	 * Injects structure spacing configuration into the given {@link ServerChunkProvider}'s
	 * chunk generator settings to ensure custom structures can spawn.
	 * <p>
	 * This method clones the current structure spacing map, adds required structures
	 * with their default separation settings if not already present, and replaces
	 * the chunk generator's structure configuration with the updated map.
	 * <p>
	 * Use this method to guarantee that structures spawn in valid dimensions, or to
	 * blacklist structures from certain dimensions by controlling this map.
	 * <p>
	 * <b>Note:</b> When blacklisting, also attempt to remove the structures from
	 * the map if they might have already been added.
	 *
	 * @param provider the {@link ServerChunkProvider} whose chunk generator's
	 *                 structure configuration will be modified.
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
		addStructureToMap(tempMap, ExtendedStructuresV5.BARROW_DOWN_RUINS.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.BARROW_DOWN_BARROWS.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.BREE_HAMLET.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.BREE_VILLAGE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.BLUE_MOUNTAIN_SMITHY.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.BLUE_MOUNTAIN_STRONGHOLD.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.BLUE_MOUNTAIN_MINE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.STONE_OF_HAPLESS.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.RIVENDELL_STRUCTURE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.EREGION_RUINS_STRUCTURE.get());
		addStructureToMap(tempMap, ExtendedStructuresV5.THE_LAST_HOMELY_HOUSE.get());
		provider.generator.getSettings().structureConfig = tempMap;
	}

	/**
	 * Adds a structure and its default separation settings to the provided structure spacing map,
	 * only if it is not already present.
	 *
	 * @param tempMap   the map of structures to their separation settings to update.
	 * @param structure the {@link Structure} to add.
	 */
	private static void addStructureToMap(Map<Structure<?>, StructureSeparationSettings> tempMap, Structure<? extends IFeatureConfig> structure) {
		tempMap.putIfAbsent(structure, DimensionStructuresSettings.DEFAULTS.get(structure));
	}

	/**
	 * Adds configured structures to biomes during the {@link BiomeLoadingEvent}.
	 * <p>
	 * This method selectively adds LOTR mod structures to specific biomes based on their registry names.
	 * Structures are added to the biome's generation list if the biome's namespace is "lotr".
	 * <p>
	 * The method handles many specific biome names, adding appropriate configured structures 
	 * to each biome's generation structures list.
	 * <p>
	 * This allows for fine control of structure spawning per biome, including adding structures
	 * only to suitable biomes and skipping others.
	 *
	 * @param event the {@link BiomeLoadingEvent} during which structures should be added to the biome.
	 */
	public static void addStructuresToBiomes(BiomeLoadingEvent event) {
		if(event.getName()!=null && event.getName().getNamespace().equals("lotr")) {
			ResourceLocation biomeName = event.getName();
			if(biomeName.equals(LOTRBiomes.SHIRE.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_BARN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_SMALL);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_LARGE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_PICNIC_BLANKET);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_PICNIC_TABLE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_LARGE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_WINDMILL);
			}else if(biomeName.equals(LOTRBiomes.SHIRE_WOODLANDS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_BARN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_SMALL);
			}else if(biomeName.equals(LOTRBiomes.SHIRE_MOORS.getRegistryName())||biomeName.equals(LOTRBiomes.WHITE_DOWNS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_BARN);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOLE_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_TAVERN_SMALL);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_HOBBIT_WINDMILL);
			}else if(biomeName.equals(LOTRBiomes.LONE_LANDS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(LOTRBiomes.LONE_LANDS_HILLS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}else if(biomeName.equals(LOTRBiomes.ERIADOR.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}else if(biomeName.equals(LOTRBiomes.ERIADOR_DOWNS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}else if(biomeName.equals(LOTRBiomes.MINHIRIATH.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(LOTRBiomes.ERYN_VORN.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}else if(biomeName.equals(LOTRBiomes.TOWER_HILLS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_TOWER_HILLS_SOUTH_TOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_TOWER_HILLS_MIDDLE_TOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_TOWER_HILLS_NORTH_TOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_TOWER_HILLS_TOWER);
			}else if(biomeName.equals(LOTRBiomes.SWANFLEET.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}else if(biomeName.equals(LOTRBiomes.HILLS_OF_EVENDIM.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HOUSE_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}else if(biomeName.equals(LOTRBiomes.ENEDWAITH.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
			}else if(biomeName.equals(LOTRBiomes.TROLLSHAWS.getRegistryName())) {
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_CAMP);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_ENCAMPMENT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_FORT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_HIDEOUT);
				//event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RANGER_WATCHTOWER);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RUINED_HOUSE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_ARNOR_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(LOTRBiomes.NORTHERN_WILDERLAND.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(LOTRBiomes.LONG_MARSHES.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(LOTRBiomes.ANDUIN_HILLS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(LOTRBiomes.VALES_OF_ANDUIN.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(LOTRBiomes.GLADDEN_FIELDS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_CAMP);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_GUNDABAD_ENCAMPMENT);
			}else if(biomeName.equals(ExtendedBiomes.BARROW_DOWNS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BARROW_DOWNS_RUINS);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BARROW_DOWN_BARROWS);
			}else if(biomeName.equals(LOTRBiomes.BREELAND.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BREE_HAMLET);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BREE_VILLAGE);
			}else if(biomeName.equals(LOTRBiomes.BLUE_MOUNTAINS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_SMITHY);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_STRONGHOLD);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_MINE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_SETTLEMENT);
			}else if(biomeName.equals(LOTRBiomes.BLUE_MOUNTAINS_FOOTHILLS.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_SMITHY);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_STRONGHOLD);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_MINE);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_BLUE_MOUNTAINS_SETTLEMENT);
			}else if(biomeName.equals(LOTRBiomes.WESTERN_ISLES.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_STONE_OF_HAPLESS);
			}else if(biomeName.equals(LOTRBiomes.RIVENDELL.getRegistryName()) || biomeName.equals(ExtendedBiomes.RIVENDELL_VALLEY.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_RIVENDELL_STRUCTURES);
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_THE_LAST_HOMELY_HOUSE);
			}else if(biomeName.equals(LOTRBiomes.EREGION.getRegistryName())) {
				event.getGeneration().getStructures().add(() -> ExtendedConfiguredStructuresV5.CONFIGURED_EREGION_RUINS_STRUCTURES);
			}
		}
	}
}
