package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedMillingRecipeBuilder {
	private final String group = "";
	private final Ingredient ingredient;
	private final Item result;
	private final int count;
	/**
	 * 1.0 is 100% chance to break, 0.0 is a 0% chance to break
	 * */
	private final float breakChance;
	private final float experience;
	private final int grindingTime;
	private final IRecipeSerializer<?> serializer;

	private ExtendedMillingRecipeBuilder(IItemProvider result, int count, Ingredient ingredient, float breakChance, float xp, int time,  IRecipeSerializer<?> serializer) {
		this.result = result.asItem();
		this.experience = xp;
		this.grindingTime = time;
		this.serializer = serializer;
		this.ingredient = ingredient;
		this.count = count;
		this.breakChance = breakChance;
	}

	public static ExtendedMillingRecipeBuilder grinding(IItemProvider result, Ingredient ingredient, IRecipeSerializer<?> serializer) {
		return grinding(result, 1, ingredient, 0.0f, 1.0f, 200, serializer);
	}

	public static ExtendedMillingRecipeBuilder grinding(IItemProvider result, Ingredient ingredient, float breakChance, IRecipeSerializer<?> serializer) {
		return grinding(result, 1, ingredient, breakChance, 1.0f, 200, serializer);
	}
	
	public static ExtendedMillingRecipeBuilder grinding(IItemProvider result, int count, Ingredient ingredient, float breakChance, IRecipeSerializer<?> serializer) {
		return grinding(result, count, ingredient, breakChance, 1.0f, 200, serializer);
	}

	public static ExtendedMillingRecipeBuilder grinding(IItemProvider result, int count, Ingredient ingredient, float breakChance, float xp, IRecipeSerializer<?> serializer) {
		return grinding(result, count, ingredient, breakChance, xp, 200, serializer);
	}

	public static ExtendedMillingRecipeBuilder grinding(IItemProvider result, int count, Ingredient ingredient, float breakChance, float xp, int time, IRecipeSerializer<?> serializer) {
		return new ExtendedMillingRecipeBuilder(result, count, ingredient, breakChance, xp, time, serializer);
	}

	public void save(Consumer<IFinishedRecipe> p_218630_1_) {
		this.save(p_218630_1_, ForgeRegistries.ITEMS.getKey(this.result));
	}

	public void save(Consumer<IFinishedRecipe> p_218632_1_, String p_218632_2_) {
		ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
		ResourceLocation resourcelocation1 = new ResourceLocation(p_218632_2_);
		if (resourcelocation1.equals(resourcelocation)) {
			throw new IllegalStateException("Recipe " + resourcelocation1 + " should remove its 'save' argument");
		} else {
			this.save(p_218632_1_, resourcelocation1);
		}
	}

	public void save(Consumer<IFinishedRecipe> p_218635_1_, ResourceLocation outputLocation) {
		this.ensureValid(outputLocation);
		p_218635_1_.accept(new ExtendedMillingRecipeBuilder.Result(outputLocation, this.group == null ? "" : this.group, this.ingredient, this.result, this.count, this.breakChance, this.experience, this.grindingTime, this.serializer));
	}


	private void ensureValid(ResourceLocation outputLocation) {
		if (this.ingredient.isEmpty()) {
			throw new IllegalStateException("Invalid Milling recipe! " + outputLocation);
		}
	}

	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final Ingredient ingredient;
		private final Item result;
		private final float experience;
		private final int grindingTime;
		private final int count;
		private final float breakChance;
		private final IRecipeSerializer<?> serializer;

		public Result(ResourceLocation outputLocation, String group, Ingredient ingredient, Item result, int count, float breakChance, float experience, int grindingTime, IRecipeSerializer<?> serializer) {
			this.id = outputLocation;
			this.group = group;
			this.ingredient = ingredient;
			this.result = result;
			this.experience = experience;
			this.grindingTime = grindingTime;
			this.serializer = serializer;
			this.count = count;
			this.breakChance = breakChance;
		}

		public void serializeRecipeData(JsonObject p_218610_1_) {
			if (!this.group.isEmpty()) {
				p_218610_1_.addProperty("group", this.group);
			}

			p_218610_1_.add("ingredient", ingredient.toJson());

			p_218610_1_.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
			if(count>1) {
				p_218610_1_.addProperty("count", this.count);
			}
			if(grindingTime!=200) {
				p_218610_1_.addProperty("grindingtime", this.grindingTime);
			}
			if(breakChance!=0.0F) {
				p_218610_1_.addProperty("breakchance", this.breakChance);
			}
			p_218610_1_.addProperty("experience", this.experience);
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