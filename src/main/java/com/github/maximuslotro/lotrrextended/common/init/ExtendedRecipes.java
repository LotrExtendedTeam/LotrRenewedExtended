package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendeStoneGrinderRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedSmithingRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedRecipes {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LotrExtendedMod.MODID);
	public static final RegistryObject<IRecipeSerializer<ExtendeStoneGrinderRecipe>> STONE_GRINDING_SERIALIZER = RECIPE_SERIALIZERS.register("stone_grinding", ExtendeStoneGrinderRecipe.ExtendedStoneGrinderRecipeSerializer::new);
	public static final RegistryObject<IRecipeSerializer<ExtendedSmithingRecipe>> EXTENDED_SMITHING = RECIPE_SERIALIZERS.register("smithing", ExtendedSmithingRecipe.Serializer::new);

	public static void register(IEventBus modBus) {
		RECIPE_SERIALIZERS.register(modBus);
	}
}
