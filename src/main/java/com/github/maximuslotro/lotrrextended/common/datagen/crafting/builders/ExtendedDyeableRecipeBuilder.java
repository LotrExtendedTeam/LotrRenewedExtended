package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtendedDyeableRecipeBuilder {
	private final IRecipeSerializer<?> serializer;
	private final IRecipeType<?> recipeType;
	
	private ExtendedDyeableRecipeBuilder(IRecipeSerializer<?> pSerializer, IRecipeType<?> pRecipeType) {
		this.serializer = pSerializer;
		this.recipeType = pRecipeType;
	}

	public static ExtendedDyeableRecipeBuilder dyeing(IRecipeSerializer<?> pSerializer, IRecipeType<?> pRecipeType) {
		return new ExtendedDyeableRecipeBuilder(pSerializer, pRecipeType);
	}

	public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation location) {
		consumer.accept(new ExtendedDyeableRecipeBuilder.Result(location, this.serializer, this.recipeType));
	}

	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final IRecipeSerializer<?> serializer;
		private final IRecipeType<?> recipeType;

		public Result(ResourceLocation pId, IRecipeSerializer<?> pSerializer, IRecipeType<?> pRecipeType) {
			this.serializer = pSerializer;
			this.recipeType = pRecipeType;
			id = pId;
		}

		@Override
		public void serializeRecipeData(JsonObject jsonObject) {
			jsonObject.addProperty("recipe_type", Registry.RECIPE_TYPE.getKey(recipeType).toString());
			
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