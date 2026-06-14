package com.github.maximuslotro.lotrrextended.common.datagen.crafting.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.serialization.ExtendedJsonSerializers;
import com.google.gson.JsonObject;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ExtendedCrucibleRecipeBuilder {
	/**
	 * output1 -> valid output2
	 */
	private static final Map<Item, Item> VALID_SUB_OUTPUTS = new HashMap<>();

	static {
		VALID_SUB_OUTPUTS.put(Items.IRON_INGOT, Items.IRON_NUGGET);
		VALID_SUB_OUTPUTS.put(Items.GOLD_INGOT, Items.GOLD_NUGGET);
		VALID_SUB_OUTPUTS.put(LOTRItems.BRONZE_INGOT.get(), LOTRItems.BRONZE_NUGGET.get());
		VALID_SUB_OUTPUTS.put(LOTRItems.SILVER_INGOT.get(), LOTRItems.SILVER_NUGGET.get());
		VALID_SUB_OUTPUTS.put(LOTRItems.MITHRIL_INGOT.get(), LOTRItems.MITHRIL_NUGGET.get());
		VALID_SUB_OUTPUTS.put(LOTRItems.ORC_STEEL_INGOT.get(), ExtendedItems.ORC_STEEL_NUGGET.get());
	}

	private final String group = "";
	private final Ingredient ingredient;
	private final ItemStack result1;
	private final ItemStack result2;
	private final float experience;
	private final int meltingTime;
	private final IRecipeSerializer<?> serializer;

	private ExtendedCrucibleRecipeBuilder(Ingredient ingredient, ItemStack stack1, ItemStack stack2, float xp, int time,  IRecipeSerializer<?> serializer) {
		this.ingredient = ingredient;
		this.result1 = stack1;
		this.result2 = stack2;
		this.experience = xp;
		this.meltingTime = time;
		this.serializer = serializer;
	}

	public static ExtendedCrucibleRecipeBuilder specific(Ingredient ingredient, ItemStack stack1, IRecipeSerializer<?> serializer) {
		return crucible(ingredient, stack1, ItemStack.EMPTY, 1.0f, 400, serializer);
	}

	public static ExtendedCrucibleRecipeBuilder specific(Ingredient ingredient, ItemStack stack1, ItemStack stack2, IRecipeSerializer<?> serializer) {
		return crucible(ingredient, stack1, stack2, 1.0f, 400, serializer);
	}

	public static ExtendedCrucibleRecipeBuilder crucible(Ingredient ingredient, ItemStack stack1, ItemStack stack2, float xp, int time,  IRecipeSerializer<?> serializer) {
		return new ExtendedCrucibleRecipeBuilder(ingredient, stack1, stack2, xp, time, serializer);
	}

	public void save(Consumer<IFinishedRecipe> consumer) {
		if(result2.isEmpty()) {
			this.save(consumer, new ResourceLocation(LotrExtendedMod.MODID,  
					this.result1.getItem().getRegistryName().getPath()
					+"_from_"+this.ingredient.getItems()[0].getItem().getRegistryName().getPath()));
		}else {
			this.save(consumer, new ResourceLocation(LotrExtendedMod.MODID,  
					this.result1.getItem().getRegistryName().getPath()
					+"_and_"+this.result2.getItem().getRegistryName().getPath()
					+"_from_"+this.ingredient.getItems()[0].getItem().getRegistryName().getPath()));
		}
	}

	public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation outputLocation) {
		this.ensureValid(outputLocation);
		consumer.accept(new ExtendedCrucibleRecipeBuilder.Result(outputLocation, this.group == null ? "" : this.group, this.ingredient, this.result1, this.result2, this.experience, this.meltingTime, this.serializer));
	}


	private void ensureValid(ResourceLocation outputLocation) {
		if (this.ingredient.isEmpty() || (this.result1.isEmpty() && this.result2.isEmpty())) {
			throw new IllegalStateException("Invalid Crucible recipe! " + outputLocation);
		}
		// Validate output pair
	    if (ingredient.getItems()[0].isDamageableItem() && !this.result2.isEmpty()) {
	        Item output1 = this.result1.getItem();
	        Item output2 = this.result2.getItem();
	        if (VALID_SUB_OUTPUTS.containsKey(output1) && VALID_SUB_OUTPUTS.get(output1) != output2) {
	        	ExtendedLog.info("Recipe "+outputLocation+" contains a second result that can be overwritten depending on durability");
	        }
	    }
	}

	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final Ingredient ingredient;
		private final ItemStack result1;
		private final ItemStack result2;
		private final float experience;
		private final int meltingTime;
		private final IRecipeSerializer<?> serializer;

		public Result(ResourceLocation outputLocation, String group, Ingredient ingredient, ItemStack result1, ItemStack result2, float experience, int meltingTime, IRecipeSerializer<?> serializer) {
			this.id = outputLocation;
			this.group = group;
			this.ingredient = ingredient;
			this.result1= result1;
			this.result2= result2;
			this.experience = experience;
			this.meltingTime = meltingTime;
			this.serializer = serializer;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			if (!this.group.isEmpty()) {
				json.addProperty("group", this.group);
			}
			json.add("ingredient", ingredient.toJson());

			json.add("result1", ExtendedJsonSerializers.serializeStack(result1));
			if (!this.result2.isEmpty()) {
				json.add("result2", ExtendedJsonSerializers.serializeStack(result2));
			}

			json.addProperty("melt_time", this.meltingTime);
			json.addProperty("experience", this.experience);
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