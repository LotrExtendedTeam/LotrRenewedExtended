package com.github.maximuslotro.lotrrextended.common.traders;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;

public class ExtendedTraderPoolItem extends WeightedRandom.Item {
	private final ItemStack stack;
	private final int averageCost;
	private final int maxSellQuantity;

	public ExtendedTraderPoolItem(ItemStack stack, int weight, int averageCost, int maxSellQuantity) {
		super(weight);
		this.stack = stack;
		this.averageCost = averageCost;
		this.maxSellQuantity = maxSellQuantity;
	}

	/**
	 * Gets the contained item
	 * @return The contained item
	 * */
	public ItemStack getStack(){
		return stack;
	}

	/**
	 * Gets the average cost of the item
	 * @return The average cost of the item
	 * */
	public int getAverageCost(){
		return averageCost;
	}

	/**
	 * Gets the maximum amount of this item that can be traded, -1 represents infinite selling/buying if this item
	 * @return The maximum amount of this item that can be sold
	 * */
	public int getMaxSellQuantity(){
		return maxSellQuantity;
	}

	public static class Serializer implements JsonDeserializer<ExtendedTraderPoolItem>, JsonSerializer<ExtendedTraderPoolItem> {
		@Override
		public ExtendedTraderPoolItem deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedTraderPoolItem");
			ItemStack stack = getStack(JSONUtils.getAsJsonObject(jsonobject, "equipmentItem"));
			int weight = jsonobject.get("weight").getAsInt();
			int averageCost = jsonobject.get("averageCost").getAsInt();
			int maxSellQuantity = jsonobject.get("maxSellQuantity").getAsInt();
			return new ExtendedTraderPoolItem(stack, weight, averageCost, maxSellQuantity);
		}

		@Override
		public JsonElement serialize(ExtendedTraderPoolItem entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("weight", entitySpawner.weight);
			jsonobject.addProperty("averageCost", entitySpawner.averageCost);
			jsonobject.add("maxSellQuantity", entitySpawner.serializeItem());
			jsonobject.add("equipmentItem", entitySpawner.serializeItem());
			return jsonobject;
		}
	}

	/**
	 * Copied from {@see DisplayInfo#serializeItem()}
	 * */
	@SuppressWarnings("deprecation")
	private JsonObject serializeItem() {
		JsonObject itemJson = new JsonObject();
		itemJson.addProperty("item", Registry.ITEM.getKey(this.stack.getItem()).toString());
		if (this.stack.hasTag()) {
			itemJson.addProperty("nbt", this.stack.getTag().toString());
		}

		return itemJson;
	}

	/**
	 * Copied from {@see DisplayInfo#getStack(JsonObject jsonObject)}
	 * @param jsonObject The JsonObject to read from
	 * @return An ItemStack that was contained in the JsonObject
	 * */
	private static ItemStack getStack(JsonObject jsonObject) {
		if (!jsonObject.has("item")) {
			throw new JsonSyntaxException("Missing Equipment item type (add 'item' key)");
		} else {
			Item basicItem = JSONUtils.getAsItem(jsonObject, "item");
			if (jsonObject.has("data")) {
				throw new JsonParseException("Disallowed data tag found");
			} else {
				ItemStack generatedItemStack = new ItemStack(basicItem);
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