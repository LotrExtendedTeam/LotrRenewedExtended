package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.lang.reflect.Type;
import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.WeightedRandom;

public class ExtendedTraderPoolItem extends WeightedRandom.Item {
	private final int averageCost;
	private final int maxTransferQuantity;
	private final ExtendedTradeItemSupplier itemSupplier;
	
	public ExtendedTraderPoolItem(ExtendedTradeItemSupplier itemSupplier, int weight, int averageCost, int maxTransferQuantity) {
		super(weight);
		this.averageCost = averageCost;
		this.maxTransferQuantity = maxTransferQuantity;
		this.itemSupplier = itemSupplier;
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
	public int getMaxTransferQuantity(){
		return maxTransferQuantity;
	}

	/**
	 * Gets a contained ItemStack
	 * @param rand A passed Random instance to pick from multi pools
	 * @return A chosen ItemStack instance
	 * */
	public ItemStack getStack(Random rand){
		return itemSupplier.chooseItem(rand);
	}

	public static class Serializer implements JsonDeserializer<ExtendedTraderPoolItem>, JsonSerializer<ExtendedTraderPoolItem> {
		@Override
		public ExtendedTraderPoolItem deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedBaseTraderPoolItem");
			int weight = JSONUtils.getAsInt(jsonobject, "weight", 1);
			int averageCost = jsonobject.get("averageCost").getAsInt();
			int maxSellQuantity = JSONUtils.getAsInt(jsonobject, "maxTransferQuantity", -1);
			if(jsonobject.has("stack")) {
				return new ExtendedTraderPoolItem(ExtendedTradeItemSupplierSingle.deserializeJson(jsonobject), weight, averageCost, maxSellQuantity);
			}
			if(jsonobject.has("stacks")) {
				return new ExtendedTraderPoolItem(ExtendedTradeItemSupplierMulti.deserializeJson(jsonobject), weight, averageCost, maxSellQuantity);
			}
			throw new JsonParseException("Failed finding valid ExtendedTraderPoolItem type: " + json.toString());
		}

		@Override
		public JsonElement serialize(ExtendedTraderPoolItem traderPool, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			if(traderPool.weight!=1) {
				jsonobject.addProperty("weight", traderPool.weight);
			}
			jsonobject.addProperty("averageCost", traderPool.averageCost);
			if(traderPool.getMaxTransferQuantity()!=-1) {
				jsonobject.addProperty("maxTransferQuantity", traderPool.getMaxTransferQuantity());
			}

			if(traderPool.itemSupplier instanceof ExtendedTradeItemSupplierSingle) {
				jsonobject.add("stack", (traderPool.itemSupplier.serializeJson()));
			}
			if(traderPool.itemSupplier instanceof ExtendedTradeItemSupplierMulti) {
				jsonobject.add("stacks", (traderPool.itemSupplier.serializeJson()));
			}
			return jsonobject;
		}
	}
}