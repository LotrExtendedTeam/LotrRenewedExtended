package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtendedRecipeTypes {

	public static IRecipeType<ExtendeStoneGrinderRecipe> STONE_GRINDER = register("stone_grinding");
	public static IRecipeType<ExtendedISmithingRecipe> EXTENDED_SMITHING = register("smithing");
	public static IRecipeType<ExtendedCrucibleRecipe> CRUCIBLE = register("crucible");

	public static void register() {}

	private static <T extends IRecipe<?>> IRecipeType<T> register(String key) {
		final ResourceLocation location = new ResourceLocation(LotrExtendedMod.MODID, key);
		return Registry.register(Registry.RECIPE_TYPE, location, new IRecipeType<T>() {
			@Override
			public String toString() {
				return location.toString();
			}
		});
	}
}
