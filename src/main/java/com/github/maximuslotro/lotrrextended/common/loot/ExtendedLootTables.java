package com.github.maximuslotro.lotrrextended.common.loot;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class ExtendedLootTables {
	private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
	public static final ResourceLocation HOBBIT_LARDER = register("chests/hobbit_larder");
	public static final ResourceLocation HOBBIT_LIVING = register("chests/hobbit_living");
	public static final ResourceLocation HOBBIT_TREASURE = register("chests/hobbit_treasure");
	public static final ResourceLocation RANGER_CAMP_FARM_WHEAT = register("chests/ranger_camp_farm_wheat");
	public static final ResourceLocation RANGER_CAMP_FARM_FLAX = register("chests/ranger_camp_farm_flax");
	public static final ResourceLocation RANGER_CAMP_SUPPLIES = register("chests/ranger_camp_general");
	public static final ResourceLocation RANGER_CAMP_SMITHY = register("chests/ranger_camp_smithy");
	public static final ResourceLocation RANGER_WACHTOWER = register("chests/ranger_watchtower");
	public static final ResourceLocation RANGER_HOUSE_CHEST = register("chests/ranger_house_chest");
	public static final ResourceLocation RANGER_HOUSE_BARREL = register("chests/ranger_house_barrel");
	public static final ResourceLocation GENERIC_RUIN = register("chests/generic_ruin");
	public static final ResourceLocation ARNOR_TOWER_RUIN = register("chests/arnor_tower_ruin");

	public static final ResourceLocation GUNDABAD_CAMP_SUPPLIES = register("chests/gundabad_camp_general");
	public static final ResourceLocation GUNDABAD_CAMP_SMITHY = register("chests/gundabad_camp_smithy");
	public static final ResourceLocation GUNDABAD_CAMP_WOOD = register("chests/gundabad_camp_wood");
	public static final ResourceLocation GUNDABAD_CAMP_WOOL = register("chests/gundabad_camp_wool");

	public static final ResourceLocation BARROW_DOWN_CHEST = register("chests/barrow_downs");

	public static final ResourceLocation HOBBIT_KEGS = register("kegs/hobbit");
	public static final ResourceLocation RANGER_KEGS = register("kegs/ranger");
	public static final ResourceLocation GUNDABAD_KEGS = register("kegs/gundabad");
	public static final ResourceLocation BREE_KEGS = register("kegs/bree");

	public static final ResourceLocation HOBBIT_PLATES = register("plates/hobbit");
	public static final ResourceLocation RANGER_PLATES = register("plates/ranger");
	public static final ResourceLocation GUNDABAD_PLATES = register("plates/gundabad");
	public static final ResourceLocation BREE_MAN_PLATES = register("plates/bree_man");
	public static final ResourceLocation BREE_HOBBIT_PLATES = register("plates/bree_hobbit");
	public static void init() {}

	private static ResourceLocation register(String location) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, location));
	}

	private static ResourceLocation register(ResourceLocation location) {
		if (LOCATIONS.add(location)) {
			return location;
		} else {
			throw new IllegalArgumentException(location + " is already a registered built-in loot table");
		}
	}

	public static Set<ResourceLocation> getLocations() {
		return LOCATIONS;
	}
}
