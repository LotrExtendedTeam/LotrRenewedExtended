package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedTradeItemSupplierMulti implements ExtendedTradeItemSupplier {
	private final List<ItemStack> stacks;

	public ExtendedTradeItemSupplierMulti(List<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public ItemStack chooseItem(Random rand) {
		return stacks.get(rand.nextInt(stacks.size()));
	}

	@Override
	public JsonElement serializeJson() {
		JsonArray itemArray = new JsonArray();
		for(ItemStack stack : stacks) {
			JsonObject itemJson = new JsonObject();
			itemJson.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
			if (stack.hasTag()) {
				itemJson.addProperty("nbt", stack.getTag().toString());
			}
			itemArray.add(itemJson);
		}
		return itemArray;
	}

	public static ExtendedTradeItemSupplier deserializeJson(JsonObject element) {
		List<ItemStack> stacks = new ArrayList<>();
		JsonArray stackArray = element.getAsJsonArray("stacks");
		for(JsonElement ele : stackArray) {
			ItemStack stack = ExtendedTradeItemSupplier.getStack(JSONUtils.convertToJsonObject(ele, "stack"));
			stacks.add(stack);
		}
		return new ExtendedTradeItemSupplierMulti(stacks);
	}
}