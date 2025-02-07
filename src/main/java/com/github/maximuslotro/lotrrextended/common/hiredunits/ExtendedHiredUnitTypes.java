package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

/**
 * Stores IDs for built in entity spawners.
 */
public class ExtendedHiredUnitTypes {
	private static final Set<ResourceLocation> UNIT_TYPES = Sets.newHashSet();

	public static final ResourceLocation BREE_GUARD = register("bree_guard");

	private static ResourceLocation register(String pId) {
		return register(new ResourceLocation(LotrExtendedMod.MODID, pId));
	}

	private static ResourceLocation register(ResourceLocation pId) {
		if (UNIT_TYPES.add(pId)) {
			return pId;
		} else {
			throw new IllegalArgumentException(pId + " is already a registered built-in extended hired unit type");
		}
	}

	public static Set<ResourceLocation> all() {
		return UNIT_TYPES;
	}
}
