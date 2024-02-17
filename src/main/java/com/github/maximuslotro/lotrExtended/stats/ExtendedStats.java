package com.github.maximuslotro.lotrExtended.stats;

import com.github.maximuslotro.lotrExtended.common.datagen.DataGenValueHolder;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.DatagenModLoader;

public class ExtendedStats {
	public static ResourceLocation EAT_CHEESE_SLICE;

	public static void init() {
		EAT_CHEESE_SLICE = registerStat("eat_cheese_slice", "Cheese Slices Eaten", IStatFormatter.DEFAULT);
	}

	private static ResourceLocation registerStat(String key, String defaultName, IStatFormatter formatter) {
		if(DatagenModLoader.isRunningDataGen()) {DataGenValueHolder.statsHolders.put("stats.lotrextended."+key, defaultName);}
		ResourceLocation res = new ResourceLocation("lotrextended", key);
		Registry.register(Registry.CUSTOM_STAT, res.toString(), res);
		Stats.CUSTOM.get(res, formatter);
		return res;
	}
}