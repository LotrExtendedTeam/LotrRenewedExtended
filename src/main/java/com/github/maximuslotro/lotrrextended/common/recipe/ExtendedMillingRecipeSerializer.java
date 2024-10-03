package com.github.maximuslotro.lotrrextended.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ExtendedMillingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendeGrinderRecipe> {

	@Override
	public ExtendeGrinderRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
		String group = JSONUtils.getAsString(json, "group", "");
		Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
		if (ingredient.isEmpty()) {
			throw new JsonParseException("No ingredients for stone grinding recipe");
		}
		if (!json.has("result")) {
			throw new JsonSyntaxException("Missing result, expected to find a string or object");
		}
		ItemStack result = getItemStack(json);
		float experience = JSONUtils.getAsFloat(json, "experience", 0.0F);
		int grindingtime = JSONUtils.getAsInt(json, "grindingtime", 200);
		float breakChance = JSONUtils.getAsFloat(json, "breakchance", 0.0F);
		return new ExtendeGrinderRecipe(recipeId, group, ingredient, result, experience, grindingtime, breakChance);
	}

	public static ItemStack getItemStack(JsonObject json) {
		String itemName = JSONUtils.getAsString(json, "result");
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
		if (item == null) {
			throw new JsonSyntaxException("Unknown item '" + itemName + "'");
		}
		return new ItemStack(item, JSONUtils.getAsInt(json, "count", 1));
	}

	@Override
	public ExtendeGrinderRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
		String group = buffer.readUtf(32767);
		Ingredient ingredient = Ingredient.fromNetwork(buffer);
		ItemStack result = buffer.readItem();
		float experience = buffer.readFloat();
		int grindingtime = buffer.readVarInt();
		float breakChance = buffer.readFloat();
		return new ExtendeGrinderRecipe(recipeId, group, ingredient, result, experience, grindingtime, breakChance);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, ExtendeGrinderRecipe recipe) {
		buffer.writeUtf(recipe.group);
		recipe.ingredient.toNetwork(buffer);
		buffer.writeItem(recipe.result);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.grindingTime);
		buffer.writeFloat(recipe.breakChance);
	}
}