package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedWeightedStructureConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.StructureFeature;

/**
 * Holds and registers configured structure features for the mod.
 * <p>
 * Configured structures are specific instances of structures combined with their generation
 * configurations, such as template pools or weighted pools. These configured structures
 * are registered here so they can easily be referenced and added to biome generation.
 * </p>
 * <p>
 * Registration is done manually to the {@link WorldGenRegistries#CONFIGURED_STRUCTURE_FEATURE}
 * registry and also added to {@link FlatGenerationSettings#STRUCTURE_FEATURES} to avoid conflicts
 * with flat world generation and other mods' custom chunk generators.
 * </p>
 * <p>
 * It is recommended to call registration during mod setup (e.g., in {@code FMLCommonSetupEvent}).
 * </p>
 */
public class ExtendedConfiguredStructuresV5 {
	//Static instances of our structures so we can reference them and add it to biomes easily.
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_BARN = register(ExtendedStructuresV5.HOBBIT_BARN.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_barn_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_SMALL = register(ExtendedStructuresV5.HOBBIT_HOLE_SMALL.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_hole_small_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOLE_LARGE = register(ExtendedStructuresV5.HOBBIT_HOLE_LARGE.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_hole_large_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_HOUSE = register(ExtendedStructuresV5.HOBBIT_HOUSE.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_house_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_BLANKET = register(ExtendedStructuresV5.HOBBIT_PICNIC_BLANKET.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_picnic_blanket_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_PICNIC_TABLE = register(ExtendedStructuresV5.HOBBIT_PICNIC_TABLE.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_picnic_table_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_SMALL = register(ExtendedStructuresV5.HOBBIT_TAVERN_SMALL.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_tavern_small_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_TAVERN_LARGE = register(ExtendedStructuresV5.HOBBIT_TAVERN_LARGE.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_tavern_large_pool")));
	public static StructureFeature<?, ?> CONFIGURED_HOBBIT_WINDMILL = register(ExtendedStructuresV5.HOBBIT_WINDMILL.get().configured(new ExtendedSinglePoolConfig("hobbit/hobbit_windmill_pool")));

	public static StructureFeature<?, ?> CONFIGURED_RANGER_CAMP = register(ExtendedStructuresV5.RANGER_CAMP.get().configured(new ExtendedSinglePoolConfig("ranger/camp_parts/firepit_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_ENCAMPMENT = register(ExtendedStructuresV5.RANGER_ENCAMPMENT.get().configured(new ExtendedSinglePoolConfig("ranger/encampment_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_FORT = register(ExtendedStructuresV5.RANGER_FORT.get().configured(new ExtendedSinglePoolConfig("ranger/fort_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HIDEOUT = register(ExtendedStructuresV5.RANGER_HIDEOUT.get().configured(new ExtendedSinglePoolConfig("ranger/hideout_pool")));
	public static StructureFeature<?, ?> CONFIGURED_RANGER_WATCHTOWER = register(ExtendedStructuresV5.RANGER_WATCHTOWER.get().configured(new ExtendedSinglePoolConfig("ranger/watchtower_pool")));
	public static ExtendedWeightedStructureConfig rangerHouseHideoutConfig = new ExtendedWeightedStructureConfig()
			.addStructureToPool("ranger/hideout_house_pool", 50).addStructureToPool("ranger/ruins_pool", 70).addStructureToPool("ranger/hideout_house_pool2", 50);
	public static StructureFeature<?, ?> CONFIGURED_RANGER_HOUSE_HIDEOUT = register(ExtendedStructuresV5.RANGER_HOUSE_HIDEOUT.get().configured(rangerHouseHideoutConfig));
	//public static StructureFeature<?, ?> CONFIGURED_RANGER_HOUSE_RUIN = register(ExtendedStructuresV5.RANGER_HOUSE_RUIN.get().configured(new SinglePoolConfig("ranger/ruins_pool")));
	public static ExtendedWeightedStructureConfig arnorRuinsConfig = new ExtendedWeightedStructureConfig()
			.addStructureToPool("ruins/arnor_ruins_pool", 50).addStructureToPool("ruins/general_ruins_pool", 50);
	public static StructureFeature<?, ?> CONFIGURED_ARNOR_RUINS = register(ExtendedStructuresV5.ARNOR_RUINS.get().configured(arnorRuinsConfig));
	public static StructureFeature<?, ?> CONFIGURED_RUINED_HOUSE = register(ExtendedStructuresV5.RANGER_HOUSE_RUIN.get().configured(new ExtendedSinglePoolConfig("ranger/ruins_pool")));

	public static StructureFeature<?, ?> CONFIGURED_GUNDABAD_CAMP = register(ExtendedStructuresV5.GUNDABAD_CAMP.get().configured(new ExtendedSinglePoolConfig("gundabad/camp_parts/firepit_pool")));
	public static StructureFeature<?, ?> CONFIGURED_GUNDABAD_ENCAMPMENT = register(ExtendedStructuresV5.GUNDABAD_ENCAMPMENT.get().configured(new ExtendedSinglePoolConfig("gundabad/gundabad_encampment_pool")));
	public static ExtendedWeightedStructureConfig barrowDownsRuinsConfig = new ExtendedWeightedStructureConfig().addStructureToPool("ruins/arnor_other_ruins_pool", 15)
		.addStructureToPool("ruins/arnor_tower_ruins_pool", 5).addStructureToPool("ruins/general_ruins_pool", 80);

	public static StructureFeature<?, ?> CONFIGURED_BARROW_DOWNS_RUINS = register(ExtendedStructuresV5.BARROW_DOWN_RUINS.get().configured(barrowDownsRuinsConfig));
	public static StructureFeature<?, ?> CONFIGURED_BARROW_DOWN_BARROWS = register(ExtendedStructuresV5.BARROW_DOWN_BARROWS.get().configured(new ExtendedWeightedStructureConfig().addStructureToPool("barrow_downs/barrow_pool", 1)));

	public static StructureFeature<?, ?> CONFIGURED_BREE_HAMLET = register(ExtendedStructuresV5.BREE_HAMLET.get().configured(new ExtendedSinglePoolConfig("bree/hamlet/well_pool")));
	public static StructureFeature<?, ?> CONFIGURED_BREE_VILLAGE = register(ExtendedStructuresV5.BREE_VILLAGE.get().configured(new ExtendedSinglePoolConfig("bree/village/start_pool")));

	public static StructureFeature<?, ?> CONFIGURED_BLUE_MOUNTAINS_SMITHY = register(ExtendedStructuresV5.BLUE_MOUNTAIN_SMITHY.get().configured(new ExtendedSinglePoolConfig("blue_mountains/smithy_pool")));
	public static StructureFeature<?, ?> CONFIGURED_BLUE_MOUNTAINS_STRONGHOLD = register(ExtendedStructuresV5.BLUE_MOUNTAIN_STRONGHOLD.get().configured(new ExtendedSinglePoolConfig("blue_mountains/stronghold_pool")));
	public static StructureFeature<?, ?> CONFIGURED_BLUE_MOUNTAINS_MINE = register(ExtendedStructuresV5.BLUE_MOUNTAIN_MINE.get().configured(new ExtendedSinglePoolConfig("blue_mountains/mine_entrance_pool")));
	public static StructureFeature<?, ?> CONFIGURED_BLUE_MOUNTAINS_SETTLEMENT = register(ExtendedStructuresV5.BLUE_MOUNTAIN_SETTLEMENT.get().configured(new ExtendedSinglePoolConfig("blue_mountains/settlement_pool")));

	public static StructureFeature<?, ?> CONFIGURED_STONE_OF_HAPLESS = register(ExtendedStructuresV5.STONE_OF_HAPLESS.get().configured(new ExtendedSinglePoolConfig("lore/stone_of_hapless")));
	public static StructureFeature<?, ?> CONFIGURED_THE_LAST_HOMELY_HOUSE = register(ExtendedStructuresV5.THE_LAST_HOMELY_HOUSE.get().configured(new ExtendedSinglePoolConfig("lore/last_homely_house_start")));
	public static StructureFeature<?, ?> CONFIGURED_TOWER_HILLS_SOUTH_TOWER = register(ExtendedStructuresV5.TOWER_HILLS_SOUTH_TOWER.get().configured(new ExtendedSinglePoolConfig("lore/tower_hills_tower_south")));
	public static StructureFeature<?, ?> CONFIGURED_TOWER_HILLS_MIDDLE_TOWER = register(ExtendedStructuresV5.TOWER_HILLS_MIDDLE_TOWER.get().configured(new ExtendedSinglePoolConfig("lore/tower_hills_tower_middle")));
	public static StructureFeature<?, ?> CONFIGURED_TOWER_HILLS_NORTH_TOWER = register(ExtendedStructuresV5.TOWER_HILLS_NORTH_TOWER.get().configured(new ExtendedSinglePoolConfig("lore/tower_hills_tower_north")));

	public static StructureFeature<?, ?> CONFIGURED_RIVENDELL_STRUCTURES = register(ExtendedStructuresV5.RIVENDELL_STRUCTURE.get().configured(new ExtendedSinglePoolConfig("rivendell/rivendell_structures")));

	public static StructureFeature<?, ?> CONFIGURED_EREGION_RUINS_STRUCTURES = register(ExtendedStructuresV5.EREGION_RUINS_STRUCTURE.get().configured(new ExtendedSinglePoolConfig("ruins/eregion_ruins_pool")));

	public static StructureFeature<?, ?> CONFIGURED_TOWER_HILLS_TOWER = register(ExtendedStructuresV5.TOWER_HILLS_TOWER.get().configured(new ExtendedSinglePoolConfig("lindon/tower_hills_tower_start")));

	public static StructureFeature<?, ?> CONFIGURED_LINDON_VILLAGE = register(ExtendedStructuresV5.LINDON_VILLAGE.get().configured(new ExtendedSinglePoolConfig("lindon/lindon_village_start")));
	public static StructureFeature<?, ?> CONFIGURED_LINDON_TOWER = register(ExtendedStructuresV5.LINDON_TOWER.get().configured(new ExtendedSinglePoolConfig("lindon/lindon_tower")));
	public static StructureFeature<?, ?> CONFIGURED_LINDON_HALL = register(ExtendedStructuresV5.LINDON_HALL.get().configured(new ExtendedSinglePoolConfig("lindon/lindon_hall")));

	public static StructureFeature<?, ?> CONFIGURED_ISENGARD_GATEHOUSE = register(ExtendedStructuresV5.ISENGARD_GATEHOUSE.get().configured(new ExtendedSinglePoolConfig("lore/isengard/isengard_gatehouse")));
	public static StructureFeature<?, ?> CONFIGURED_ORTHANC = register(ExtendedStructuresV5.ORTHANC.get().configured(new ExtendedSinglePoolConfig("lore/isengard/isengard_orthanc")));
	public static StructureFeature<?, ?> CONFIGURED_ISENGARD_CAMP = register(ExtendedStructuresV5.ISENGARD_CAMP.get().configured(new ExtendedSinglePoolConfig("isengard/camp_starts")));

	public static StructureFeature<?, ?> CONFIGURED_DUNLAND_HILLFORT = register(ExtendedStructuresV5.DUNLAND_HILLFORT.get().configured(new ExtendedSinglePoolConfig("dunland/dunland_hillfort")));

	public static StructureFeature<?, ?> CONFIGURED_IRON_HILLS_MINE = register(ExtendedStructuresV5.IRON_HILLS_MINE.get().configured(new ExtendedSinglePoolConfig("iron_hills/mine_entrance_pool")));

	/**
     * Empty method intended to be called to ensure static initialization of this class
     * and the registration of configured structures.
     */
	public static void registerConfiguredStructures(){}

	/**
     * Registers a configured structure feature in the game's registry.
     * <p>
     * This method registers the configured structure under the mod's namespace with a
     * name prefixed by "configured_". It also adds the structure to
     * {@link FlatGenerationSettings#STRUCTURE_FEATURES} to prevent crashes in worlds
     * using flat chunk generators or other custom chunk generators that rely on this map.
     * </p>
     * <p>
     * This registration must happen before world generation begins, typically in the
     * mod's common setup phase.
     * </p>
     * Noticed this is not using a forge registry because there is none for configured structures.
     * We can register configured structures at any time before a world is clicked on and made.
     * But the best time to register configured features by code is honestly to do it in FMLCommonSetupEvent.
     * 
     * @param structure the configured structure feature to register
     * @return the same configured structure feature instance for chaining or reference
     */

    public static StructureFeature<?, ?> register(StructureFeature<?, ?> structure) {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(LotrExtendedMod.MODID, "configured_"+structure.feature.getRegistryName().getPath()), structure);
		 // Add to flat generation features to avoid crashes with custom ChunkGenerators or Superflat worlds
        FlatGenerationSettings.STRUCTURE_FEATURES.put(structure.feature, structure);
        return structure;
    }
}
