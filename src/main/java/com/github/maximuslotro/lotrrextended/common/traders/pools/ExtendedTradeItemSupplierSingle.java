package com.github.maximuslotro.lotrrextended.common.traders.pools;

import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedTradeItemSupplierSingle implements ExtendedTradeItemSupplier {
	private final ItemStackAndPrice stackAndPrice;

	public ExtendedTradeItemSupplierSingle(ItemStackAndPrice stack) {
		this.stackAndPrice = stack;
	}

	@Override
	public ItemStack chooseItem(Random rand) {
		return stackAndPrice.getItemStack();
	}

	@Override
	public JsonElement serializeJson() {
		JsonObject itemJson = new JsonObject();
		itemJson.addProperty("item", ForgeRegistries.ITEMS.getKey(stackAndPrice.getItemStack().getItem()).toString());
		if(stackAndPrice.getItemStack().getCount()>1) {
			itemJson.addProperty("count", stackAndPrice.getItemStack().getCount());
		}
		if (stackAndPrice.getItemStack().hasTag()) {
			itemJson.addProperty("nbt", stackAndPrice.getItemStack().getTag().toString());
		}

		return itemJson;
	}

	public static ExtendedTradeItemSupplier deserializeJson(JsonObject element) {
		ItemStackAndPrice stack = ExtendedTradeItemSupplier.getStack(JSONUtils.getAsJsonObject(element, "stack"));
		return new ExtendedTradeItemSupplierSingle(stack);
	}

	@Override
	public List<ItemStack> returnAllPossableItems() {
		return new ArrayList<>(Collections.singletonList(stackAndPrice.getItemStack()));
	}
}