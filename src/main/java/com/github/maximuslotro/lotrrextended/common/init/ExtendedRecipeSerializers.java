package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendeStoneGrinderRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedCoinSmithingHundredRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedCoinSmithingOneRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedCoinSmithingTenRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedGemSmithingRecipe;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedSmithingRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedRecipeSerializers {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LotrExtendedMod.MODID);
	public static final RegistryObject<IRecipeSerializer<ExtendeStoneGrinderRecipe>> STONE_GRINDING_SERIALIZER = RECIPE_SERIALIZERS.register("stone_grinding", ExtendeStoneGrinderRecipe.ExtendedStoneGrinderRecipeSerializer::new);
	public static final RegistryObject<IRecipeSerializer<ExtendedSmithingRecipe>> EXTENDED_SMITHING = RECIPE_SERIALIZERS.register("smithing", ExtendedSmithingRecipe.Serializer::new);
	public static final RegistryObject<SpecialRecipeSerializer<ExtendedGemSmithingRecipe>> EXTENDED_SMITHING_GEM = RECIPE_SERIALIZERS.register("crafting_special_smithing_gem", () -> new SpecialRecipeSerializer<>(ExtendedGemSmithingRecipe::new));
	public static final RegistryObject<SpecialRecipeSerializer<ExtendedCoinSmithingOneRecipe>> EXTENDED_SMITHING_COIN_ONE = RECIPE_SERIALIZERS.register("crafting_special_smithing_coin_one", () -> new SpecialRecipeSerializer<>(ExtendedCoinSmithingOneRecipe::new));
	public static final RegistryObject<SpecialRecipeSerializer<ExtendedCoinSmithingTenRecipe>> EXTENDED_SMITHING_COIN_TEN = RECIPE_SERIALIZERS.register("crafting_special_smithing_coin_ten", () -> new SpecialRecipeSerializer<>(ExtendedCoinSmithingTenRecipe::new));
	public static final RegistryObject<SpecialRecipeSerializer<ExtendedCoinSmithingHundredRecipe>> EXTENDED_SMITHING_COIN_HUNDRED = RECIPE_SERIALIZERS.register("crafting_special_smithing_coin_hundred", () -> new SpecialRecipeSerializer<>(ExtendedCoinSmithingHundredRecipe::new));

	public static void register(IEventBus modBus) {
		RECIPE_SERIALIZERS.register(modBus);
	}
}