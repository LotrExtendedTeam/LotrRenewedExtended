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
	public static final BannerPattern dunedain = registerNewPattern("dunedain", "Dúnedain", true);
	public static final BannerPattern blue_mountains = registerNewPattern("blue_mountains", "Blue Mountains", true);
	public static final BannerPattern gundabad_orc = registerNewPattern("gundabad_orc", "Gundabad Orc", true);
	public static final BannerPattern bree = registerNewPattern("bree", "Bree", true);
	public static final BannerPattern hobbit = registerNewPattern("hobbit", "Shire Hobbit", true);
	public static final BannerPattern angmar = registerNewPattern("angmar", "Angmar", true);
	public static final BannerPattern dale = registerNewPattern("dale", "Dale", true);
	public static final BannerPattern dol_amroth = registerNewPattern("dol_amroth", "Dol Amroth", true);
	public static final BannerPattern dol_guldur = registerNewPattern("dol_guldur", "Dol Guldur", true);
	public static final BannerPattern dorwinion = registerNewPattern("dorwinion", "Dorwinion", true);
	public static final BannerPattern dunland = registerNewPattern("dunland", "Dunland", true);
	public static final BannerPattern gondor = registerNewPattern("gondor", "Gondor", true);
	public static final BannerPattern isengard = registerNewPattern("isengard", "Isengard", true);
	public static final BannerPattern lindon = registerNewPattern("lindon", "Lindon", true);
	public static final BannerPattern lothlorien = registerNewPattern("lothlorien", "Lothlorien", true);
	public static final BannerPattern mordor = registerNewPattern("mordor", "Mordor", true);
	public static final BannerPattern rivendell = registerNewPattern("rivendell", "Rivendell", true);
	public static final BannerPattern rohan = registerNewPattern("rohan", "Rohan", true);
	public static final BannerPattern woodland_realm = registerNewPattern("woodland_realm", "Woodland Realm", true);

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
