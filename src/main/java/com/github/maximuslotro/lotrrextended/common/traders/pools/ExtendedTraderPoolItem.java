package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.WeightedRandom;

public class ExtendedTraderPoolItem extends WeightedRandom.Item {
	private final int maxTransferQuantity;
	private final ExtendedTradeItemSupplier itemSupplier;
	private final boolean shouldTradePersist;

	public ExtendedTraderPoolItem(ExtendedTradeItemSupplier itemSupplier, int weight, int maxTransferQuantity, boolean shouldTradePersist) {
		super(weight);
		this.maxTransferQuantity = maxTransferQuantity;
		this.itemSupplier = itemSupplier;
		this.shouldTradePersist = shouldTradePersist;
	}

	/**
	 * Gets the maximum amount of this item that can be traded, 200 is the default in legacy
	 * @return The maximum amount of this item that can be sold
	 * */
	public int getMaxTransferQuantity(){
		return maxTransferQuantity;
	}

	public boolean shouldTradePersist() {
		return shouldTradePersist;
	}

	/**
	 * Gets a contained ItemStack
	 * @param rand A passed Random instance to pick from multi pools
	 * @return A chosen ItemStack instance
	 * */
	public ItemStackAndPrice getStackAndPrice(Random rand){
		return itemSupplier.chooseItemStackAndPrice(rand);
	}

	/**
	 * Gets all contained ItemStack and prices
	 * @return All available ItemStackAndPrice instances
	 * */
	public List<ItemStackAndPrice> getAllStacksAndPrices(){
		return itemSupplier.returnAllPossableItemsAndPrices();
	}

	public static class Serializer implements JsonDeserializer<ExtendedTraderPoolItem>, JsonSerializer<ExtendedTraderPoolItem> {
		@Override
		public ExtendedTraderPoolItem deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedBaseTraderPoolItem");
			int weight = JSONUtils.getAsInt(jsonobject, "weight", 1);
			int maxSellQuantity = JSONUtils.getAsInt(jsonobject, "maxTransferQuantity", 200);
			boolean shouldTradePersist = JSONUtils.getAsBoolean(jsonobject, "shouldTradePersist", true);
			if(jsonobject.has("stack")) {
				return new ExtendedTraderPoolItem(ExtendedTradeItemSupplierSingle.deserializeJson(jsonobject), weight, maxSellQuantity, shouldTradePersist);
			}
			if(jsonobject.has("stacks")) {
				return new ExtendedTraderPoolItem(ExtendedTradeItemSupplierMulti.deserializeJson(jsonobject), weight, maxSellQuantity, shouldTradePersist);
			}
			throw new JsonParseException("Failed finding valid ExtendedTraderPoolItem type: " + json.toString());
		}

		@Override
		public JsonElement serialize(ExtendedTraderPoolItem traderPool, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("weight", traderPool.weight);
			if(traderPool.getMaxTransferQuantity()!=200) {
				jsonobject.addProperty("maxTransferQuantity", traderPool.getMaxTransferQuantity());
			}
			if(!traderPool.shouldTradePersist()) {
				jsonobject.addProperty("shouldTradePersist", traderPool.shouldTradePersist());
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