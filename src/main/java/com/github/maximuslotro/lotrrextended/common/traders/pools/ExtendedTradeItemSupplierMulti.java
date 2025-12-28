package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.JSONUtils;

public class ExtendedTradeItemSupplierMulti implements ExtendedTradeItemSupplier {
	private final List<ItemStackAndPrice> stacks;

	public ExtendedTradeItemSupplierMulti(List<ItemStackAndPrice> pStacks) {
		this.stacks = pStacks;
	}

	@Override
	public ItemStackAndPrice chooseItemStackAndPrice(Random pRand) {
		return stacks.get(pRand.nextInt(stacks.size()));
	}

	@Override
	public JsonElement serializeJson() {
		JsonArray itemArray = new JsonArray();
		for(ItemStackAndPrice stack : stacks) {
			itemArray.add(ExtendedTradeItemSupplier.serializeStack(stack));
		}
		return itemArray;
	}

	public static ExtendedTradeItemSupplier deserializeJson(JsonObject element) {
		List<ItemStackAndPrice> stacks = new ArrayList<>();
		JsonArray stackArray = element.getAsJsonArray("stacks");
		for(JsonElement ele : stackArray) {
			stacks.add(ExtendedTradeItemSupplier.deserializeStack(JSONUtils.convertToJsonObject(ele, "stack")));
		}
		return new ExtendedTradeItemSupplierMulti(stacks);
	}

	@Override
	public List<ItemStackAndPrice> returnAllPossableItemsAndPrices() {
		return stacks;
	}
}