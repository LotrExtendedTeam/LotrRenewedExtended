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
import net.minecraftforge.registries.ForgeRegistries;

public interface ExtendedTradeItemSupplier {

	ItemStackAndPrice chooseItemStackAndPrice(Random rand);

	List<ItemStackAndPrice> returnAllPossableItemsAndPrices();

	JsonElement serializeJson();

	/**
	 * Copied and modified from {@link net.minecraft.advancements.DisplayInfo}
	 * @param jsonObject The JsonObject to read from
	 * @return An ItemStackAndPrice that was contained in the JsonObject
	 * */
	public static ItemStackAndPrice deserializeStack(JsonObject jsonObject) {
		if (!jsonObject.has("item")) {
			throw new JsonSyntaxException("Missing Equipment item type (add 'item' key)");
		} else {
			Item basicItem = JSONUtils.getAsItem(jsonObject, "item");
			int price = JSONUtils.getAsInt(jsonObject, "averageCost", 1000);

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
				return new ItemStackAndPrice(generatedItemStack, price);
			}
		}
	}

	public static JsonObject serializeStack(ItemStackAndPrice pStackAndPrice) {
		JsonObject itemJson = new JsonObject();
		itemJson.addProperty("item", ForgeRegistries.ITEMS.getKey(pStackAndPrice.getItemStack().getItem()).toString());
		itemJson.addProperty("averageCost", pStackAndPrice.getAverageCost());
		if(pStackAndPrice.getItemStack().getCount()>1) {
			itemJson.addProperty("count", pStackAndPrice.getItemStack().getCount());
		}
		if (pStackAndPrice.getItemStack().hasTag()) {
			itemJson.addProperty("nbt", pStackAndPrice.getItemStack().getTag().toString());
		}
		return itemJson;
	}
}
