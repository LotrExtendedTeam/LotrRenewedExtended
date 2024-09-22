package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("deprecation")
public class ExtendedVesselBrewingRecipeBuilder {
	private final String group = "";
	private final List<Ingredient> ingredients = Lists.newArrayList();
	private final Item result;
	private final float experience;
	private final int brewTime;
	private final IRecipeSerializer<?> serializer;

	private ExtendedVesselBrewingRecipeBuilder(IItemProvider result, float xp, int time,  IRecipeSerializer<?> serializer) {
		this.result = result.asItem();
		this.experience = xp;
		this.brewTime = time;
		this.serializer = serializer;
	}

	public static ExtendedVesselBrewingRecipeBuilder brewing(IItemProvider result, IRecipeSerializer<?> serializer) {
		return brewing(result, 1.0f, serializer);
	}

	public static ExtendedVesselBrewingRecipeBuilder brewing(IItemProvider result, float xp, IRecipeSerializer<?> serializer) {
		return brewing(result, xp, 12000, serializer);
	}

	public static ExtendedVesselBrewingRecipeBuilder brewing(IItemProvider result, float xp, int time, IRecipeSerializer<?> serializer) {
		return new ExtendedVesselBrewingRecipeBuilder(result, xp, time, serializer);
	}

	public ExtendedVesselBrewingRecipeBuilder requires(ITag<Item> p_203221_1_) {
		return this.requires(Ingredient.of(p_203221_1_));
	}

	public ExtendedVesselBrewingRecipeBuilder requires(IItemProvider p_200491_1_, int p_200491_2_) {
		for(int i = 0; i < p_200491_2_; ++i) {
			this.requires(Ingredient.of(p_200491_1_));
		}
		return this;
	}

	public ExtendedVesselBrewingRecipeBuilder requires(Ingredient p_200489_1_) {
		return this.requires(p_200489_1_, 1);
	}

	public ExtendedVesselBrewingRecipeBuilder requires(Ingredient p_200492_1_, int p_200492_2_) {
		for(int i = 0; i < p_200492_2_; ++i) {
			this.ingredients.add(p_200492_1_);
		}
		return this;
	}

	public void save(Consumer<IFinishedRecipe> p_218630_1_) {
		this.save(p_218630_1_, Registry.ITEM.getKey(this.result));
	}

	public void save(Consumer<IFinishedRecipe> p_218632_1_, String p_218632_2_) {
		ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
		ResourceLocation resourcelocation1 = new ResourceLocation(p_218632_2_);
		if (resourcelocation1.equals(resourcelocation)) {
			throw new IllegalStateException("Recipe " + resourcelocation1 + " should remove its 'save' argument");
		} else {
			this.save(p_218632_1_, resourcelocation1);
		}
	}

	public void save(Consumer<IFinishedRecipe> p_218635_1_, ResourceLocation outputLocation) {
		this.ensureValid(outputLocation);
		p_218635_1_.accept(new ExtendedVesselBrewingRecipeBuilder.Result(outputLocation, this.group == null ? "" : this.group, this.ingredients, this.result, this.experience, this.brewTime, this.serializer));
	}


	private void ensureValid(ResourceLocation outputLocation) {
		if (this.ingredients.size()!=6) {
			throw new IllegalStateException("Invalid Drink Recipe! " + outputLocation);
		}
	}

	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final List<Ingredient> ingredients;
		private final Item result;
		private final float experience;
		private final int brewingTime;
		private final IRecipeSerializer<?> serializer;

		public Result(ResourceLocation outputLocation, String group, List<Ingredient> ingredients, Item result, float experience, int brewingTime, IRecipeSerializer<?> serializer) {
			this.id = outputLocation;
			this.group = group;
			this.ingredients = ingredients;
			this.result = result;
			this.experience = experience;
			this.brewingTime = brewingTime;
			this.serializer = serializer;
		}

		public void serializeRecipeData(JsonObject p_218610_1_) {
			if (!this.group.isEmpty()) {
				p_218610_1_.addProperty("group", this.group);
			}

			JsonArray jsonarray = new JsonArray();
			for(Ingredient ingredient : this.ingredients) {
				jsonarray.add(ingredient.toJson());
			}
			p_218610_1_.add("ingredients", jsonarray);

			p_218610_1_.addProperty("result", Registry.ITEM.getKey(this.result).toString());
			p_218610_1_.addProperty("experience", this.experience);
			p_218610_1_.addProperty("brewtime", this.brewingTime);
		}

		public IRecipeSerializer<?> getType() {
			return this.serializer;
		}

		public ResourceLocation getId() {
			return this.id;
		}

		@Nullable
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}