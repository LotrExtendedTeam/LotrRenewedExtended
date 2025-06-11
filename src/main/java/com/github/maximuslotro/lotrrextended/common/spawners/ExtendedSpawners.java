package com.github.maximuslotro.lotrrextended.common.spawners;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

/**
 * Stores IDs for built-in entity spawners.
 */
public class ExtendedSpawners {
	private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
	// ANIMALS
	public static final ResourceLocation DUCK_SINGLE  = register("duck_single_spawner");
	public static final ResourceLocation HORSE_SINGLE  = register("generic_horse_single_spawner");

	// HOBBITS
	public static final ResourceLocation HOBBIT_PICNIC_TABLE = register("hobbit_picnic_table_spawner");
	public static final ResourceLocation HOBBIT_PICNIC_BLANKET = register("hobbit_picnic_blanket_spawner");
	public static final ResourceLocation HOBBIT_BARTENDER_SMALL = register("hobbit_bartender_small_spawner");
	public static final ResourceLocation HOBBIT_BARTENDER_LARGE = register("hobbit_bartender_large_spawner");
	public static final ResourceLocation HOBBIT_TAVERN_SMALL = register("hobbit_tavern_small_spawner");
	public static final ResourceLocation HOBBIT_TAVERN_LARGE = register("hobbit_tavern_large_spawner");
	public static final ResourceLocation HOBBIT_TAVERN_LARGE_SHERIFF = register("hobbit_tavern_large_sheriff_spawner");
	public static final ResourceLocation HOBBIT_BARN_FARMER = register("hobbit_barn_farmer_spawner");
	public static final ResourceLocation HOBBIT_BARN_FARMHAND = register("hobbit_barn_farmhand_spawner");
	public static final ResourceLocation HOBBIT_BARN_ANIMALS = register("hobbit_barn_animals_spawner");
	public static final ResourceLocation HOBBIT_WINDMILL = register("hobbit_windmill_spawner");
	public static final ResourceLocation HOBBIT_FAMILY_HOUSE = register("hobbit_family_house_spawner");
	public static final ResourceLocation HOBBIT_FAMILY_HOLE_SMALL = register("hobbit_family_hole_small_spawner");
	public static final ResourceLocation HOBBIT_FAMILY_HOLE_LARGE = register("hobbit_family_hole_large_spawner");
	// RANGERS
	public static final ResourceLocation DUNEDAIN_RANGER_SINGLE_CHANCE = register("dunedain_ranger_single_chance_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_WATCHTOWER = register("dunedain_watchtower_ranger_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_HOUSE_CAMP = register("dunedain_house_ranger_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_ENCAMPMENT = register("dunedain_encampment_ranger_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_ENCAMPMENT_CAPTAIN = register("dunedain_encampment_captain_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_CAMP = register("dunedain_camp_ranger_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_CAMP_CAPTAIN = register("dunedain_camp_captain_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_CAMP_CAPTAIN_PERMANENT = register("dunedain_camp_captain_permanent_spawner");
	public static final ResourceLocation DUNEDAIN_RANGER_CAMP_ANIMALS = register("dunedain_camp_animal_spawner");
	// GUNDABAD ORCS
	public static final ResourceLocation GUNDABAD_CAMP_ORC = register("gundabad_camp_orc_spawner");
	public static final ResourceLocation GUNDABAD_CAMP_CAPTAIN = register("gundabad_camp_captain_spawner");
	public static final ResourceLocation GUNDABAD_CAMP_CAPTAIN_PERMANENT = register("gundabad_camp_captain_permanent_spawner");
	public static final ResourceLocation GUNDABAD_CAMP_PRISONERS = register("gundabad_camp_prisoners_spawner");
	public static final ResourceLocation GUNDABAD_ENCAMPMENT_ORC = register("gundabad_encampment_orc_spawner");
	public static final ResourceLocation GUNDABAD_ENCAMPMENT_TRADER = register("gundabad_encampment_trader_spawner");
	public static final ResourceLocation GUNDABAD_ENCAMPMENT_CAPTAIN = register("gundabad_encampment_captain_spawner");
	public static final ResourceLocation GUNDABAD_ENCAMPMENT_PRISONERS = register("gundabad_encampment_prisoners_spawner");
	public static final ResourceLocation GUNDABAD_ENCAMPMENT_ANIMAL = register("gundabad_encampment_animal_spawner");
	public static final ResourceLocation GUNDABAD_ENCAMPMENT_WARG = register("gundabad_encampment_warg_spawner");
	// RUINS ORC
	public static final ResourceLocation GUNDABAD_ORC_RUIN = register("gundabad_orc_ruin_spawner");
	// BREE HAMLETS
	public static final ResourceLocation BREE_HAMLET_AREA_SPAWNER = register("bree_hamlet_area_spawner");
	public static final ResourceLocation BREE_HAMLET_FAMILY_SPAWNER = register("bree_hamlet_family_spawner");
	public static final ResourceLocation BREE_HAMLET_ANIMALS = register("bree_hamlet_animals_spawner");
	public static final ResourceLocation BREE_HAMLET_FARMER = register("bree_hamlet_farmer_spawner");
	public static final ResourceLocation BREE_HAMLET_GUARD_HOUSE = register("bree_hamlet_guard_house_spawner");
	public static final ResourceLocation BREE_HAMLET_GUARD_HOUSE_SHERIFF = register("bree_hamlet_guard_house_sheriff_spawner");
	public static final ResourceLocation BREE_HAMLET_POTTER = register("bree_hamlet_potter_spawner");
	public static final ResourceLocation BREE_HAMLET_MILLER = register("bree_hamlet_miller_spawner");
	// BREE VILLAGE
	public static final ResourceLocation BREE_VILLAGE_AREA_SPAWNER = register("bree_village_area_spawner");
	public static final ResourceLocation BREE_VILLAGE_FAMILY_SPAWNER = register("bree_village_family_spawner");
	public static final ResourceLocation BREE_VILLAGE_ANIMALS = register("bree_village_animals_spawner");
	public static final ResourceLocation BREE_VILLAGE_FARMER = register("bree_village_farmer_spawner");
	public static final ResourceLocation BREE_VILLAGE_GUARD_HOUSE = register("bree_village_guard_house_spawner");
	public static final ResourceLocation BREE_VILLAGE_GUARD_HOUSE_SHERIFF = register("bree_village_guard_house_sheriff_spawner");
	public static final ResourceLocation BREE_VILLAGE_POTTER = register("bree_village_potter_spawner");
	public static final ResourceLocation BREE_VILLAGE_SMITH = register("bree_village_smith_spawner");
	public static final ResourceLocation BREE_VILLAGE_BAKER = register("bree_village_baker_spawner");
	public static final ResourceLocation BREE_VILLAGE_BUTCHER = register("bree_village_butcher_spawner");
	public static final ResourceLocation BREE_VILLAGE_LUMBERMAN = register("bree_village_lumberman_spawner");
	public static final ResourceLocation BREE_VILLAGE_STONEMASON = register("bree_village_stonemason_spawner");
	public static final ResourceLocation BREE_VILLAGE_INNKEEPER = register("bree_village_innkeeper_spawner");
	public static final ResourceLocation BREE_VILLAGE_FLORIST = register("bree_village_florist_spawner");
	public static final ResourceLocation BREE_VILLAGE_CHEESEMONGER = register("bree_village_cheesemonger_spawner");
	public static final ResourceLocation BREE_VILLAGE_LEATHERWORKER = register("bree_village_leatherworker_spawner");
	public static final ResourceLocation BREE_VILLAGE_STALL = register("bree_village_stall_spawner");
	public static final ResourceLocation BREE_VILLAGE_INN = register("bree_village_inn_spawner");
	public static final ResourceLocation BREE_VILLAGE_STABLEMASTER = register("bree_village_stablemaster_spawner");
	public static final ResourceLocation BREE_VILLAGE_MANOR_LORD = register("bree_village_manor_lord_spawner");

	private static ResourceLocation register(String pId) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, pId));
	}

	private static ResourceLocation register(ResourceLocation pId) {
		if (LOCATIONS.add(pId)) {
			return pId;
		} else {
			throw new IllegalArgumentException(pId + " is already a registered built-in extended structure entity spawner");
		}
	}

	public static Set<ResourceLocation> getSpawners() {
		return LOCATIONS;
	}
}
