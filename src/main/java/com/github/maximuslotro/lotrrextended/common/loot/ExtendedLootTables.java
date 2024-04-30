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

	public static final ResourceLocation HOBBIT_KEGS = register("kegs/hobbit");
	public static final ResourceLocation RANGER_KEGS = register("kegs/ranger");

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
