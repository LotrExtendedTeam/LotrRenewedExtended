package com.github.maximuslotro.lotrExtended.stats;

import com.github.maximuslotro.lotrExtended.common.datagen.DataGenValueHolder;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.DatagenModLoader;

public class ExtendedStats {
	public static ResourceLocation FELLOWSHIP_CREATE;
	public static ResourceLocation FELLOWSHIP_DISBAND;
	public static ResourceLocation EAT_CHEESE_SLICE;
	public static ResourceLocation SLICE_CHEESE_SLICE;
	public static ResourceLocation ANIMALS_BRANDED;

	public static void init() {
		FELLOWSHIP_CREATE = registerStat("fellowship_create", "Fellowships Created", IStatFormatter.DEFAULT);
		FELLOWSHIP_DISBAND = registerStat("fellowship_delete", "Fellowships Disbanded", IStatFormatter.DEFAULT);
		EAT_CHEESE_SLICE = registerStat("eat_cheese_slice", "Cheese Slices Eaten", IStatFormatter.DEFAULT);
		SLICE_CHEESE_SLICE = registerStat("sliced_cheese", "Cheese Slices Cut", IStatFormatter.DEFAULT);
		ANIMALS_BRANDED = registerStat("animals_branded", "Animals Branded", IStatFormatter.DEFAULT);
	}

	private static ResourceLocation registerStat(String key, String defaultName, IStatFormatter formatter) {
		if(DatagenModLoader.isRunningDataGen()) {DataGenValueHolder.statsHolders.put("stats.lotrextended."+key, defaultName);}
		ResourceLocation res = new ResourceLocation("lotrextended", key);
		Registry.register(Registry.CUSTOM_STAT, res.toString(), res);
		Stats.CUSTOM.get(res, formatter);
		return res;
	}
}