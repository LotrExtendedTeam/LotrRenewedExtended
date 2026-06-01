package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.serialization.ExtendedJsonSerializers;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ExtendedCrucibleSpecificRecipe extends ExtendedCrucibleRecipe {
	protected final Ingredient ingredient;

	public ExtendedCrucibleSpecificRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result1, ItemStack result2, float experience, int meltingTime) {
		super(id, group, result1, result2, experience, meltingTime);
		this.ingredient = ingredient;
	}

	@Override
	public boolean matches(IInventory inventory, World world) {
		return ingredient.test(inventory.getItem(0));
	}

	@Override
	public ItemStack assemble1(IInventory inventory) {
		return this.result1.copy();
	}

	@Override
	public ItemStack assemble2(IInventory inventory) {
		return this.result2.copy();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.ingredient);
		return nonnulllist;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.CRUCIBLE_SPECIFIC_SERIALIZER.get();
	}

	public static class ExtendedCrucibleSpecificRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendedCrucibleSpecificRecipe> {

		@Override
		public ExtendedCrucibleSpecificRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			String group = JSONUtils.getAsString(json, "group", "");
			Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
			if (ingredient.isEmpty()) {
				throw new JsonParseException("No ingredients for stone grinding recipe");
			}
			if (!json.has("result1")) {
				throw new JsonSyntaxException("Missing result, expected to find a string or object");
			}
			ItemStack result1 = ExtendedJsonSerializers.deserializeStack(JSONUtils.getAsJsonObject(json, "result1"));
			ItemStack result2 = ItemStack.EMPTY;
			if (json.has("result2")) {
			    result2 = ExtendedJsonSerializers.deserializeStack(JSONUtils.getAsJsonObject(json, "result2"));
			}
			float experience = JSONUtils.getAsFloat(json, "experience", 0.0F);
			int meltingTime = JSONUtils.getAsInt(json, "melt_time", 400);
			return new ExtendedCrucibleSpecificRecipe(recipeId, group, ingredient, result1, result2, experience, meltingTime);
		}

		@Override
		public ExtendedCrucibleSpecificRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			String group = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			ItemStack result1 = buffer.readItem();
			ItemStack result2 = buffer.readItem();
			float experience = buffer.readFloat();
			int grindingtime = buffer.readVarInt();
			return new ExtendedCrucibleSpecificRecipe(recipeId, group, ingredient, result1, result2, experience, grindingtime);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, ExtendedCrucibleSpecificRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.ingredient.toNetwork(buffer);
			buffer.writeItem(recipe.result1);
			buffer.writeItem(recipe.result2);
			buffer.writeFloat(recipe.experience);
			buffer.writeVarInt(recipe.meltingTime);
		}
	}
}