package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.tileentity.BannerPattern;

public class ExtendedBannerPatterns {
	public static final BannerPattern durin = registerNewPattern("durin", true);
	public static final BannerPattern dunedain = registerNewPattern("dunedain", true);
	public static final BannerPattern blue_mountains = registerNewPattern("blue_mountains", true);

	public static BannerPattern registerNewPattern(String id, boolean isItem) {
		return BannerPattern.create(id.toUpperCase(), id, id, isItem);
	}
}
