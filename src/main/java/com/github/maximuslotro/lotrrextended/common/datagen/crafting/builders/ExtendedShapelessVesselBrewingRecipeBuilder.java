package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lotr.common.recipe.LOTRRecipes;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedShapelessVesselBrewingRecipeBuilder {
	private final String group = "";
	private final List<Ingredient> ingredients = Lists.newArrayList();
	private final Item result;
	private final IRecipeSerializer<?> serializer;

	private ExtendedShapelessVesselBrewingRecipeBuilder(IItemProvider result, IRecipeSerializer<?> serializer) {
		this.result = result.asItem();
		this.serializer = serializer;
	}

	public static ExtendedShapelessVesselBrewingRecipeBuilder crafting(IItemProvider result) {
		return brewing(result, LOTRRecipes.VESSEL_DRINK_SHAPELESS.get());
	}

	public static ExtendedShapelessVesselBrewingRecipeBuilder brewing(IItemProvider result, IRecipeSerializer<?> serializer) {
		return new ExtendedShapelessVesselBrewingRecipeBuilder(result, serializer);
	}

	public ExtendedShapelessVesselBrewingRecipeBuilder requires(ITag<Item> pTagIngredient) {
		return this.requires(Ingredient.of(pTagIngredient));
	}

	public ExtendedShapelessVesselBrewingRecipeBuilder requires(Ingredient pIngredient) {
		this.ingredients.add(pIngredient);
		return this;
	}

	public void save(Consumer<IFinishedRecipe> p_218630_1_) {
		this.save(p_218630_1_, ForgeRegistries.ITEMS.getKey(this.result));
	}

	public void save(Consumer<IFinishedRecipe> p_218635_1_, ResourceLocation outputLocation) {
		p_218635_1_.accept(new ExtendedShapelessVesselBrewingRecipeBuilder.Result(outputLocation, this.group == null ? "" : this.group, this.ingredients, this.result, this.serializer));
	}

	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final List<Ingredient> ingredients;
		private final Item result;
		private final IRecipeSerializer<?> serializer;

		public Result(ResourceLocation outputLocation, String group, List<Ingredient> ingredients, Item result, IRecipeSerializer<?> serializer) {
			this.id = outputLocation;
			this.group = group;
			this.ingredients = ingredients;
			this.result = result;
			this.serializer = serializer;
		}

		@Override
		public void serializeRecipeData(JsonObject jsonObject) {
			if (!this.group.isEmpty()) {
				jsonObject.addProperty("group", this.group);
			}
			JsonArray ingredientsJSON = new JsonArray();
			for(Ingredient ingredient : this.ingredients) {
				ingredientsJSON.add(ingredient.toJson());
			}
			jsonObject.add("ingredients", ingredientsJSON);
			JsonObject resultJSON = new JsonObject();
			resultJSON.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
			jsonObject.add("result", resultJSON);
		}

		@Override
		public IRecipeSerializer<?> getType() {
			return this.serializer;
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