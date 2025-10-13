package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeSerializers;
import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtendedDyeableRecipeBuilder {
	private final IRecipeType<?> recipeType;
	private final Ingredient ingredient;
	
	private ExtendedDyeableRecipeBuilder(IRecipeType<?> pRecipeType, Ingredient pIngredient) {
		this.recipeType = pRecipeType;
		this.ingredient = pIngredient;
	}

	public static ExtendedDyeableRecipeBuilder dyeing(IRecipeType<?> pRecipeType, Item pIngredient) {
		return dyeing(pRecipeType, new ItemStack(pIngredient));
	}

	public static ExtendedDyeableRecipeBuilder dyeing(IRecipeType<?> pRecipeType, ItemStack pIngredient) {
		if (!(pIngredient.getItem() instanceof IDyeableArmorItem) ) {
			throw new IllegalArgumentException("Dyable Recipe item must be instanceof IDyeableArmorItem: "+pIngredient.toString());
		}
		return new ExtendedDyeableRecipeBuilder(pRecipeType, Ingredient.of(pIngredient));
	}

	public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
		consumer.accept(new ExtendedDyeableRecipeBuilder.Result(location, this.recipeType, ingredient));
	}

	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final IRecipeType<?> recipeType;
		private final Ingredient ingredient;

		public Result(ResourceLocation pId, IRecipeType<?> pRecipeType, Ingredient pIngredient) {
			this.id = pId;
			this.recipeType = pRecipeType;
			this.ingredient = pIngredient;
		}

		@Override
		public void serializeRecipeData(JsonObject jsonObject) {
			jsonObject.addProperty("recipe_type", Registry.RECIPE_TYPE.getKey(recipeType).toString());
			jsonObject.add("ingredient", ingredient.toJson());
		}

		@Override
		public IRecipeSerializer<?> getType() {
			return ExtendedRecipeSerializers.EXTENDED_TABLE_ARMOR_DYE.get();
		}

		@Override
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		@Nullable
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Override
		@Nullable
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}