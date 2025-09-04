package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendeStoneGrinderRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedISmeltingRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtendedRecipesTypes {

	public static IRecipeType<ExtendeStoneGrinderRecipe> MILLSTONE = register("stone_grinding");
	public static IRecipeType<ExtendedISmeltingRecipe> EXTENDED_SMELTING = register("smelting");

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
