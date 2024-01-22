package com.github.maximuslotro.lotroExtended.stats;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtendedStats {
	public static ResourceLocation EAT_CHEESE_SLICE;

	public static void init() {
		EAT_CHEESE_SLICE = registerStat("eat_cheese_slice", IStatFormatter.DEFAULT);
	}

	private static ResourceLocation registerStat(String key, IStatFormatter formatter) {
		ResourceLocation res = new ResourceLocation("lotrextended", key);
		Registry.register(Registry.CUSTOM_STAT, res.toString(), res);
		Stats.CUSTOM.get(res, formatter);
		return res;
	}
}