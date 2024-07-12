package com.github.maximuslotro.lotrrextended.common.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.fml.DatagenModLoader;

public class ExtendedBannerPatterns {
	private static final List<BannerPattern> patterns = new ArrayList<>();
	private static final Map<String, String> hashToName = new HashMap<>();
	public static final BannerPattern durin = registerNewPattern("durin", "Durin", true);
	public static final BannerPattern dunedain = registerNewPattern("dunedain", "Dunedain", true);
	public static final BannerPattern blue_mountains = registerNewPattern("blue_mountains", "Blue Mountains", true);
	public static final BannerPattern gundabad_orc = registerNewPattern("gundabad_orc", "Gundabad Orc", true);

	public static BannerPattern registerNewPattern(String id, String desc, boolean isItem) {
		String name = "lotrextended_"+id;
		if(DatagenModLoader.isRunningDataGen()) {
			hashToName.put(name, desc);
		}
		BannerPattern pattern = BannerPattern.create(name.toUpperCase(), name, name, isItem);
		patterns.add(pattern);
		return pattern;
	}

	public static List<BannerPattern> getPatterns() {
		return patterns;
	}

	public static Map<String, String> getHashtoname() {
		return hashToName;
	}
}
