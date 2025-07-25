package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.util.List;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;

public interface ExtendedTradeItemSupplier {

	ItemStack chooseItem(Random rand);

	List<ItemStack> returnAllPossableItems();

	JsonElement serializeJson();

	/**
	 * Copied from {@link net.minecraft.advancements.DisplayInfo}
	 * @param jsonObject The JsonObject to read from
	 * @return An ItemStack that was contained in the JsonObject
	 * */
	public static ItemStack getStack(JsonObject jsonObject) {
		if (!jsonObject.has("item")) {
			throw new JsonSyntaxException("Missing Equipment item type (add 'item' key)");
		} else {
			Item basicItem = JSONUtils.getAsItem(jsonObject, "item");
			if (jsonObject.has("data")) {
				throw new JsonParseException("Disallowed data tag found");
			} else {
				ItemStack generatedItemStack = new ItemStack(basicItem);
				if (jsonObject.has("count")) {
					generatedItemStack.setCount(jsonObject.get("count").getAsInt());
				}
				if (jsonObject.has("nbt")) {
					try {
						CompoundNBT nbt = JsonToNBT.parseTag(JSONUtils.convertToString(jsonObject.get("nbt"), "nbt"));
						generatedItemStack.setTag(nbt);
					} catch (CommandSyntaxException exception) {
						throw new JsonSyntaxException("Invalid nbt tag: " + exception.getMessage());
					}
				}

				return generatedItemStack;
			}
		}
	}
}
