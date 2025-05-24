package com.github.maximuslotro.lotrrextended.common.traders;

import java.util.Set;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DatagenModLoader;

/**
 * Stores IDs for built-in trader profiles.
 */
public class ExtendedTraderTypes {
	private static final Set<ResourceLocation> UNIT_TYPES = Sets.newHashSet();

	public static final ResourceLocation HOBBIT_BARTENDER = make("hobbit_bartender");
	public static final ResourceLocation HOBBIT_FARMER = make("hobbit_farmer");
	
	private static ResourceLocation make(String pId) {
		return validate(new ResourceLocation(LotrExtendedMod.MODID, pId));
	}

	private static ResourceLocation validate(ResourceLocation pId) {
		if(DatagenModLoader.isRunningDataGen()) {
			if (UNIT_TYPES.add(pId)) {
				return pId;
			} else {
				throw new IllegalArgumentException(pId + " is already a registered built-in extended trader type");
			}
		}
		return pId;
	}
}
