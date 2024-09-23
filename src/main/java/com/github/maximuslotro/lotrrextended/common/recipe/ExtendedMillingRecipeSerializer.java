package com.github.maximuslotro.lotrrextended.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ExtendedMillingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ExtendedMillstoneRecipe> {

	@Override
	public ExtendedMillstoneRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
		String group = JSONUtils.getAsString(json, "group", "");
		Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
		if (ingredient.isEmpty()) {
			throw new JsonParseException("No ingredients for milling recipe");
		}
		if (!json.has("result")) {
			throw new JsonSyntaxException("Missing result, expected to find a string or object");
		}
		ItemStack result;
		if (json.get("result").isJsonObject()) {
			result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
		} else {
			String resultString = JSONUtils.getAsString(json, "result");
			ResourceLocation resultId = new ResourceLocation(resultString);
			Item resultant = ForgeRegistries.ITEMS.getValue(recipeId);
	        if (resultant == null) {
	            throw new JsonSyntaxException("Unknown item '" + resultString + "'");
	        }
			result = new ItemStack(resultant);
		}
		if (json.has("count")) {
			int count = JSONUtils.getAsInt(json, "count", 1);
			result.setCount(count);
		}
		float experience = JSONUtils.getAsFloat(json, "experience", 0.0F);
		int millingtime = JSONUtils.getAsInt(json, "millingtime", 200);
		float breakChance = JSONUtils.getAsFloat(json, "breakchance", 1.0F);
		return new ExtendedMillstoneRecipe(recipeId, group, ingredient, result, experience, millingtime, breakChance);
	}

	@Override
	public ExtendedMillstoneRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
		String group = buffer.readUtf(32767);
		Ingredient ingredient = Ingredient.fromNetwork(buffer);
		ItemStack result = buffer.readItem();
		float experience = buffer.readFloat();
		int millingTime = buffer.readVarInt();
		float breakChance = buffer.readFloat();
		return new ExtendedMillstoneRecipe(recipeId, group, ingredient, result, experience, millingTime, breakChance);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, ExtendedMillstoneRecipe recipe) {
		buffer.writeUtf(recipe.group);
		recipe.ingredient.toNetwork(buffer);
		buffer.writeItem(recipe.result);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.millingTime);
		buffer.writeFloat(recipe.breakChance);
	}
}