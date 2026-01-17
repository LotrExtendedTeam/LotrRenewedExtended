package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;

public class ExtendedTradeItemSupplierSingle implements ExtendedTradeItemSupplier {
	private final ItemStackAndPrice stackAndPrice;

	public ExtendedTradeItemSupplierSingle(ItemStackAndPrice pStackAndPrice) {
		this.stackAndPrice = pStackAndPrice;
	}

	public ExtendedTradeItemSupplierSingle(ItemStack pStack, int pPrice) {
		this.stackAndPrice = new ItemStackAndPrice(pStack, pPrice);
	}

	@Override
	public ItemStackAndPrice chooseItemStackAndPrice(Random pRand) {
		return stackAndPrice;
	}

	@Override
	public JsonElement serializeJson() {
		return ExtendedTradeItemSupplier.serializeStack(stackAndPrice);
	}

	public static ExtendedTradeItemSupplier deserializeJson(JsonObject element) {
		ItemStackAndPrice stack = ExtendedTradeItemSupplier.deserializeStack(JSONUtils.getAsJsonObject(element, "stack"));
		return new ExtendedTradeItemSupplierSingle(stack);
	}

	@Override
	public List<ItemStackAndPrice> returnAllPossableItemsAndPrices() {
		return new ArrayList<>(Collections.singletonList(stackAndPrice));
	}
}