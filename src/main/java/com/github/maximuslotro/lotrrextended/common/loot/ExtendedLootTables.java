package com.github.maximuslotro.lotrrextended.common.loot;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class ExtendedLootTables {
	private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
	// CHESTS
	public static final ResourceLocation FISHING_GENERIC_CHEST = registerChest("fishing_generic");
	public static final ResourceLocation FISHING_TROPICS_CHEST = registerChest("fishing_tropics");

	public static final ResourceLocation HOBBIT_LARDER_CHEST = registerChest("hobbit_larder");
	public static final ResourceLocation HOBBIT_LIVING_CHEST = registerChest("hobbit_living");
	public static final ResourceLocation HOBBIT_TREASURE_CHEST = registerChest("hobbit_treasure");
	
	public static final ResourceLocation RANGER_CAMP_FARM_WHEAT_CHEST = registerChest("ranger_camp_farm_wheat");
	public static final ResourceLocation RANGER_CAMP_FARM_FLAX_CHEST = registerChest("ranger_camp_farm_flax");
	public static final ResourceLocation RANGER_CAMP_SUPPLIES_CHEST = registerChest("ranger_camp_general");
	public static final ResourceLocation RANGER_CAMP_SMITHY_CHEST = registerChest("ranger_camp_smithy");
	public static final ResourceLocation RANGER_WACHTOWER_CHEST = registerChest("ranger_watchtower");
	public static final ResourceLocation RANGER_HOUSE_CHEST = registerChest("ranger_house_chest");
	public static final ResourceLocation RANGER_HOUSE_BARREL = registerChest("ranger_house_barrel");

	public static final ResourceLocation GENERIC_RUIN_CHEST = registerChest("generic_ruin");
	public static final ResourceLocation ARNOR_TOWER_RUIN_CHEST = registerChest("arnor_tower_ruin");
	public static final ResourceLocation EREGION_RUIN_CHEST = registerChest("eregion_ruin");

	public static final ResourceLocation GUNDABAD_CAMP_SUPPLIES_CHEST = registerChest("gundabad_camp_general");
	public static final ResourceLocation GUNDABAD_CAMP_SMITHY_CHEST = registerChest("gundabad_camp_smithy");
	public static final ResourceLocation GUNDABAD_CAMP_WOOD_CHEST = registerChest("gundabad_camp_wood");
	public static final ResourceLocation GUNDABAD_CAMP_WOOL_CHEST = registerChest("gundabad_camp_wool");

	public static final ResourceLocation BARROW_DOWN_CHEST = registerChest("barrow_downs");

	public static final ResourceLocation BREE_HAMLET_GUARD_HOUSE_CHEST = registerChest("bree_hamlet_guard_house");
	public static final ResourceLocation BREE_HAMLET_HOUSE_CHEST = registerChest("bree_hamlet_house");
	public static final ResourceLocation BREE_HAMLET_FARM_CHEST = registerChest("bree_hamlet_farm");
	public static final ResourceLocation BREE_HAMLET_POTTER_CHEST = registerChest("bree_hamlet_potter");

	public static final ResourceLocation BREE_VILLAGE_GUARD_HOUSE_CHEST = registerChest("bree_village_guard_house");
	public static final ResourceLocation BREE_VILLAGE_HOUSE_CHEST = registerChest("bree_village_house");
	public static final ResourceLocation BREE_VILLAGE_FARM_CHEST = registerChest("bree_village_farm");
	public static final ResourceLocation BREE_VILLAGE_GARDENING_CHEST = registerChest("bree_village_gardening");
	public static final ResourceLocation BREE_VILLAGE_POTTER_CHEST = registerChest("bree_village_potter");
	public static final ResourceLocation BREE_VILLAGE_SMITHY_CHEST = registerChest("bree_village_smithy");
	public static final ResourceLocation BREE_VILLAGE_BUTCHER_CHEST = registerChest("bree_village_butcher");
	public static final ResourceLocation BREE_VILLAGE_CHEESEMONGER_CHEST = registerChest("bree_village_cheesemonger");
	public static final ResourceLocation BREE_VILLAGE_LEATHERWORKER_CHEST = registerChest("bree_village_leatherworker");
	public static final ResourceLocation BREE_VILLAGE_BAKERY_CHEST = registerChest("bree_village_bakery");
	public static final ResourceLocation BREE_VILLAGE_MANOR_CHEST = registerChest("bree_village_manor");
	public static final ResourceLocation BREE_VILLAGE_STALLS_CHEST = registerChest("bree_village_stalls");
	public static final ResourceLocation BREE_VILLAGE_INN_CHEST = registerChest("bree_village_inn");
	public static final ResourceLocation BREE_VILLAGE_INN_CUSTOMER_CHEST = registerChest("bree_village_inn_customer_chest");
	public static final ResourceLocation BREE_VILLAGE_STONEMASON_CHEST = registerChest("bree_village_stonemason");
	public static final ResourceLocation BREE_VILLAGE_LUMBERMAN_CHEST = registerChest("bree_village_lumberman");
	public static final ResourceLocation BREE_VILLAGE_TREASURE_CHEST = registerChest("bree_village_treasure");
	public static final ResourceLocation BREE_VILLAGE_STABLE_CHEST = registerChest("bree_village_stable");
	
	//public static final ResourceLocation BREE_TOWN_HOUSE_CHEST = registerChest("bree_town_house");
	//public static final ResourceLocation BREE_TOWN_FARM_CHEST = registerChest("bree_town_farm");
	//public static final ResourceLocation BREE_TOWN_SMITHY_CHEST = registerChest("bree_town_smithy");
	//public static final ResourceLocation BREE_TOWN_INN_CHEST = registerChest("bree_town_inn");
	//public static final ResourceLocation BREE_TOWN_INN_CUSTOMER_CHEST = registerChest("bree_town_inn_customer_chest");
	//public static final ResourceLocation BREE_TOWN_SHERIF_CHEST = registerChest("bree_town_sherif");
	//public static final ResourceLocation BREE_TOWN_SHERIF_CONFISCATED_GOODS_CHEST = registerChest("bree_town_sherif_confiscated_goods");

	public static final ResourceLocation BLUE_MOUNTAINS_MINE_CHEST = registerChest("blue_mountains_mine");
	public static final ResourceLocation BLUE_MOUNTAINS_MINE_SUPPLY_CHEST = registerChest("blue_mountains_mine_supply");
	public static final ResourceLocation BLUE_MOUNTAINS_HOUSE_CHEST = registerChest("blue_mountains_house");
	public static final ResourceLocation BLUE_MOUNTAINS_SMITH_CHEST = registerChest("blue_mountains_smith");
	public static final ResourceLocation BLUE_MOUNTAINS_STRONGHOLD_CHEST = registerChest("blue_mountains_stronghold");
	public static final ResourceLocation BLUE_MOUNTAINS_FOOD_CHEST = registerChest("blue_mountains_food");
	public static final ResourceLocation BLUE_MOUNTAINS_TREASURE_CHEST = registerChest("blue_mountains_treasure");
	public static final ResourceLocation BLUE_MOUNTAINS_ARCHIVES_CHEST = registerChest("blue_mountains_archives");
	public static final ResourceLocation BLUE_MOUNTAINS_STONEMASON_CHEST = registerChest("blue_mountains_stonemason");
	public static final ResourceLocation BLUE_MOUNTAINS_BUTCHER_CHEST = registerChest("blue_mountains_butcher");
	public static final ResourceLocation BLUE_MOUNTAINS_TRADER_HALL_CHEST = registerChest("blue_mountains_trader_hall");
	public static final ResourceLocation BLUE_MOUNTAINS_TRADER_HALL_MAN_CHEST = registerChest("blue_mountains_trader_hall_man");
	public static final ResourceLocation BLUE_MOUNTAINS_TRADER_HALL_ELF_CHEST = registerChest("blue_mountains_trader_hall_elf");
	public static final ResourceLocation BLUE_MOUNTAINS_MEME_COIN = registerChest("blue_mountains_meme_coin");

	public static final ResourceLocation RIVENDELL_SMITH_CHEST = registerChest("rivendell_smith");
	public static final ResourceLocation RIVENDELL_HOUSE_CHEST = registerChest("rivendell_house");
	public static final ResourceLocation RIVENDELL_LORD_CHEST = registerChest("rivendell_lord");
	public static final ResourceLocation RIVENDELL_WEAVER_CHEST = registerChest("rivendell_weaver");
	public static final ResourceLocation RIVENDELL_TREASURE_CHEST = registerChest("rivendell_treasure");
	public static final ResourceLocation RIVENDELL_WEAPONS_CHEST = registerChest("rivendell_weapons");

	public static final ResourceLocation LINDON_SMITH_CHEST = registerChest("lindon_smith");
	public static final ResourceLocation LINDON_HOUSE_CHEST = registerChest("lindon_house");
	public static final ResourceLocation LINDON_LORD_CHEST = registerChest("lindon_lord");
	public static final ResourceLocation LINDON_WEAVER_CHEST = registerChest("lindon_weaver");
	public static final ResourceLocation LINDON_TREASURE_CHEST = registerChest("lindon_treasure");
	public static final ResourceLocation LINDON_ARCHIVES_CHEST = registerChest("lindon_archives");
	public static final ResourceLocation LINDON_FOOD_CHEST = registerChest("lindon_food");
	public static final ResourceLocation LINDON_EQUIPMENT_CHEST = registerChest("lindon_equipment");
	public static final ResourceLocation LINDON_ARROWS_CHEST = registerChest("lindon_arrows");

	public static final ResourceLocation ISENGARD_CAMP_SMITHY_CHEST = registerChest("isengard_camp_smithy");
	public static final ResourceLocation ISENGARD_CAMP_SUPPLIES_CHEST = registerChest("isengard_camp_supplies");
	public static final ResourceLocation ISENGARD_CAMP_WOOD_CHEST = registerChest("isengard_camp_wood");
	public static final ResourceLocation ISENGARD_FOOD_CHEST = registerChest("isengard_food");
	public static final ResourceLocation ISENGARD_WARG_PIT_CHEST = registerChest("isengard_warg_pit");
	public static final ResourceLocation ISENGARD_MINE_CHEST = registerChest("isengard_mine");
	public static final ResourceLocation ISENGARD_ORTHANC_FOOD_CHEST = registerChest("isengard_orthanc_food");
	public static final ResourceLocation ISENGARD_ORTHANC_BIRD_CHEST = registerChest("isengard_orthanc_bird");

	// KEGS
	public static final ResourceLocation HOBBIT_KEGS = registerKeg("hobbit");
	public static final ResourceLocation RANGER_KEGS = registerKeg("ranger");
	public static final ResourceLocation GUNDABAD_KEGS = registerKeg("gundabad");
	public static final ResourceLocation BREE_KEGS = registerKeg("bree");
	public static final ResourceLocation BLUE_MOUNTAINS_KEGS = registerKeg("blue_mountains");
	public static final ResourceLocation RIVENDELL_KEGS = registerKeg("rivendell");
	public static final ResourceLocation LINDON_KEGS = registerKeg("lindon");
	public static final ResourceLocation ISENGARD_KEGS = registerKeg("isengard");
	public static final ResourceLocation ISENGARD_ORTHANC_KEGS = registerKeg("isengard_orthanc");
	// VESSELS (MUGS, CUPS, GOBBLETS, HORNS, BOTTLES)
	public static final ResourceLocation HOBBIT_VESSELS = registerVessels("hobbit");
	public static final ResourceLocation RANGER_VESSELS = registerVessels("ranger");
	public static final ResourceLocation GUNDABAD_VESSELS = registerVessels("gundabad");
	public static final ResourceLocation BREE_VESSELS = registerVessels("bree");
	public static final ResourceLocation BLUE_MOUNTAINS_VESSELS = registerVessels("blue_mountains");
	public static final ResourceLocation RIVENDELL_VESSELS = registerVessels("rivendell");
	public static final ResourceLocation LINDON_VESSELS = registerVessels("lindon");
	public static final ResourceLocation ISENGARD_VESSELS = registerVessels("isengard");
	public static final ResourceLocation ISENGARD_ORTHANC_VESSELS = registerVessels("isengard_orthanc");

	// PLATES
	public static final ResourceLocation HOBBIT_PLATES = registerPlate("hobbit");
	public static final ResourceLocation RANGER_PLATES = registerPlate("ranger");
	public static final ResourceLocation GUNDABAD_PLATES = registerPlate("gundabad");
	public static final ResourceLocation BREE_MAN_PLATES = registerPlate("bree_man");
	public static final ResourceLocation BREE_HOBBIT_PLATES = registerPlate("bree_hobbit");
	public static final ResourceLocation BREE_CHEESEMONGER_PLATES = registerPlate("bree_cheesemonger");
	public static final ResourceLocation BREE_BUTCHER_PLATES = registerPlate("bree_butcher");
	public static final ResourceLocation BREE_BAKERY_PLATES = registerPlate("bree_bakery");
	public static final ResourceLocation BLUE_MOUNTAINS_PLATE = registerPlate("blue_mountains");
	public static final ResourceLocation RIVENDELL_PLATE = registerPlate("rivendell");
	public static final ResourceLocation LINDON_PLATE = registerPlate("lindon");
	public static final ResourceLocation ISENGARD_PLATE = registerPlate("isengard");
	public static final ResourceLocation ISENGARD_ORTHANC_PLATE = registerPlate("isengard_orthanc");

	// WEAPONRACKS
	public static final ResourceLocation HOBBIT_WEAPONRACKS = registerWeaponRack("hobbit");
	public static final ResourceLocation RANGER_WEAPONRACKS = registerWeaponRack("ranger");
	public static final ResourceLocation GUNDABAD_WEAPONRACKS = registerWeaponRack("gundabad");
	public static final ResourceLocation BARROW_DOWN_WEAPONRACKS = registerWeaponRack("barrow_down");
	
	public static final ResourceLocation BREE_MAN_WEAPONRACKS = registerWeaponRack("bree_man");
	public static final ResourceLocation BREE_HOBBIT_WEAPONRACKS = registerWeaponRack("bree_hobbit");
	public static final ResourceLocation BREE_SMITHY_WEAPONRACKS = registerWeaponRack("bree_smithy");
	public static final ResourceLocation BREE_BUTCHER_WEAPONRACKS = registerWeaponRack("bree_butcher");
	public static final ResourceLocation BREE_FARMER_WEAPONRACKS = registerWeaponRack("bree_farmer");
	
	public static final ResourceLocation BLUE_MOUNTAIN_WEAPONRACKS = registerWeaponRack("blue_mountains");
	public static final ResourceLocation RIVENDELL_WEAPONRACKS = registerWeaponRack("rivendell");
	public static final ResourceLocation RIVENDELL_SMITHY_WEAPONRACKS = registerWeaponRack("rivendell_smithy");
	
	public static final ResourceLocation LINDON_WEAPONRACKS = registerWeaponRack("lindon");
	public static final ResourceLocation LINDON_EQUIPMENT_WEAPONRACKS = registerWeaponRack("lindon_equipment");
	public static final ResourceLocation LINDON_BOW_WEAPONRACKS = registerWeaponRack("lindon_bow");

	public static final ResourceLocation ISENGARD_WEAPONRACKS = registerWeaponRack("isengard");

	// POTS
	public static final ResourceLocation BLUE_MOUNTAINS_TRADER_HALL_POT = registerPot("blue_mountains_trader_hall");
	public static final ResourceLocation RIVENDELL_POT = registerPot("rivendell");
	public static final ResourceLocation LINDON_POT = registerPot("lindon");
	public static final ResourceLocation ISENGARD_POT = registerPot("isengard");
	public static final ResourceLocation ISENGARD_ORTHANC_POT = registerPot("isengard_orthanc");

	// BOOKSHELF
	public static final ResourceLocation ISENGARD_ORTHANC_BOOKSHELF = registerBookshelf("isengard_orthanc");

	// ARMOR STANDS
	public static final ResourceLocation ISENGARD_ARMOR_STAND = registerArmorStand("isengard");

	// FISHING
	public static final ResourceLocation MIDDLE_EARTH_FISHING = registerGameplay("fishing");
	public static final ResourceLocation MIDDLE_EARTH_FISHING_FISH = registerGameplay("fishing/fish");
	public static final ResourceLocation MIDDLE_EARTH_FISHING_JUNK = registerGameplay("fishing/junk");
	public static final ResourceLocation MIDDLE_EARTH_FISHING_TREASURE = registerGameplay("fishing/treasure");

	public static void init() {}

	private static ResourceLocation registerChest(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "chests/"+location));
	}

	private static ResourceLocation registerKeg(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "kegs/"+location));
	}

	private static ResourceLocation registerVessels(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "vessels/"+location));
	}

	private static ResourceLocation registerPlate(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "plates/"+location));
	}

	private static ResourceLocation registerWeaponRack(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "weaponracks/"+location));
	}

	private static ResourceLocation registerPot(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "pots/"+location));
	}

	private static ResourceLocation registerBookshelf(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "bookshelves/"+location));
	}

	private static ResourceLocation registerArmorStand(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "armorstand/"+location));
	}

	private static ResourceLocation registerGameplay(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, "gameplay/"+location));
	}

	private static ResourceLocation register(ResourceLocation location) {
		if (LOCATIONS.add(location)) {
			return location;
		} else {
			throw new IllegalArgumentException(location + " is already a registered built-in loot table");
		}
	}

	public static Set<ResourceLocation> getLootTables() {
		return LOCATIONS;
	}
}
