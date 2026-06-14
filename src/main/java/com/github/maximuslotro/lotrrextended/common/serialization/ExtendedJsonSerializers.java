package com.github.maximuslotro.lotrrextended.common.serialization;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedJsonSerializers {
	public static JsonObject serializeStack(ItemStack stack) {
	    JsonObject json = new JsonObject();
	    json.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
	    if (stack.getCount() > 1) {
	        json.addProperty("count", stack.getCount());
	    }
	    return json;
	}
	public static JsonObject serializeStackWithTag(ItemStack stack) {
	    JsonObject json = new JsonObject();
	    json.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
	    if (stack.getCount() > 1) {
	        json.addProperty("count", stack.getCount());
	    }
	    if (stack.hasTag()) {
	    	json.addProperty("nbt", stack.getTag().toString());
		}
	    return json;
	}
	public static ItemStack deserializeStack(JsonObject json) {
	    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getAsString(json, "item")));
	    int count = JSONUtils.getAsInt(json, "count", 1);
	    ItemStack returning = new ItemStack(item, count);
	    if (json.has("nbt")) {
			try {
				CompoundNBT nbt = JsonToNBT.parseTag(JSONUtils.convertToString(json.get("nbt"), "nbt"));
				returning.setTag(nbt);
			} catch (CommandSyntaxException exception) {
				throw new JsonSyntaxException("Invalid nbt tag: " + exception.getMessage());
			}
		}
	    return returning;
	}
}
