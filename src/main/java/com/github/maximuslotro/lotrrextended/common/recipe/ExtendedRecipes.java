package com.github.maximuslotro.lotrrextended.common.recipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedRecipes {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "lotrextended");
	public static IRecipeType<ExtendeStoneGrinderRecipe> MILLSTONE;
	public static final RegistryObject<IRecipeSerializer<ExtendeStoneGrinderRecipe>> STONE_GRINDING_SERIALIZER = RECIPE_SERIALIZERS.register("stone_grinding", ExtendedStoneGrinderRecipeSerializer::new);

	public static void register(IEventBus modBus) {
		MILLSTONE = register("stone_grinding");
		RECIPE_SERIALIZERS.register(modBus);
	}

	private static <T extends IRecipe<?>> IRecipeType<T> register(String key) {
		final ResourceLocation location = new ResourceLocation("lotrextended", key);
		return Registry.register(Registry.RECIPE_TYPE, location, new IRecipeType<T>() {
			@Override
			public String toString() {
				return location.toString();
			}
		});
	}
}
