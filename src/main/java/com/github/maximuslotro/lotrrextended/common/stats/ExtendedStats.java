package com.github.maximuslotro.lotrrextended.common.stats;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.DatagenModLoader;

public class ExtendedStats {
	public static ResourceLocation FELLOWSHIP_CREATE = registerStat("fellowship_create", "Fellowships Created", IStatFormatter.DEFAULT);
	public static ResourceLocation FELLOWSHIP_DISBAND = registerStat("fellowship_delete", "Fellowships Disbanded", IStatFormatter.DEFAULT);
	public static ResourceLocation EAT_CHEESE_SLICE = registerStat("eat_cheese_slice", "Cheese Slices Eaten", IStatFormatter.DEFAULT);
	public static ResourceLocation SLICE_CHEESE_SLICE = registerStat("sliced_cheese", "Cheese Slices Cut", IStatFormatter.DEFAULT);
	public static ResourceLocation ANIMALS_BRANDED = registerStat("animals_branded", "Animals Branded", IStatFormatter.DEFAULT);
	public static ResourceLocation TRADERS_TRADED = registerStat("traders_traded", "Times Traded With Traders", IStatFormatter.DEFAULT);
	public static ResourceLocation INTERACT_WITH_STONE_CHEST = registerStat("interact_with_stone_chest", "Stone Chests Opened", IStatFormatter.DEFAULT);
	public static ResourceLocation INTERACT_WITH_BOOKSHELF = registerStat("interact_with_bookshelf", "Bookshelves Opened", IStatFormatter.DEFAULT);

	public static void init() {}

	private static ResourceLocation registerStat(String key, String defaultName, IStatFormatter formatter) {
		if(DatagenModLoader.isRunningDataGen()) {DataGenValueHolder.statsHolders.put("stat.lotrextended."+key, defaultName);}
		ResourceLocation res = new ResourceLocation("lotrextended", key);
		Registry.register(Registry.CUSTOM_STAT, res.toString(), res);
		Stats.CUSTOM.get(res, formatter);
		return res;
	}
}