package com.github.maximuslotro.lotrrextended.common.datagen.map;

import net.minecraft.util.ResourceLocation;

public class ExtendedTravelRegions {
	public static ResourceLocation ROHAN = makeLotr("rohan");

	public static ResourceLocation makeLotr(String location) {
		return new ResourceLocation("lotr", location);
	}

	public static void init() {}
}
