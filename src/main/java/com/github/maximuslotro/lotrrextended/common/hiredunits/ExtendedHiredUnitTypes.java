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

	public static final ResourceLocation BREE_GUARD = make("bree_guard");
	public static final ResourceLocation GUNDABAD_ORC = make("gundabad_orc");
	public static final ResourceLocation DUNEDAIN_RANGER = make("dunedain_ranger");

	private static ResourceLocation make(String pId) {
		return validate(new ResourceLocation(LotrExtendedMod.MODID, pId));
	}

	private static ResourceLocation validate(ResourceLocation pId) {
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
