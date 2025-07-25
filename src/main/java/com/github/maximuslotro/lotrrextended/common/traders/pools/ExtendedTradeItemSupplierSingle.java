package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedTradeItemSupplierSingle implements ExtendedTradeItemSupplier {
	private final ItemStack stack;

	public ExtendedTradeItemSupplierSingle(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public ItemStack chooseItem(Random rand) {
		return stack;
	}

	@Override
	public JsonElement serializeJson() {
		JsonObject itemJson = new JsonObject();
		itemJson.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
		if(stack.getCount()>1) {
			itemJson.addProperty("count", stack.getCount());
		}
		if (stack.hasTag()) {
			itemJson.addProperty("nbt", stack.getTag().toString());
		}

		return itemJson;
	}

	public static ExtendedTradeItemSupplier deserializeJson(JsonObject element) {
		ItemStack stack = ExtendedTradeItemSupplier.getStack(JSONUtils.getAsJsonObject(element, "stack"));
		return new ExtendedTradeItemSupplierSingle(stack);
	}

	@Override
	public List<ItemStack> returnAllPossableItems() {
		return new ArrayList<>(Arrays.asList(stack));
	}
}