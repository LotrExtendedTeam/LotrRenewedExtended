package com.github.maximuslotro.lotrrextended.common.hiredunits;

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

public class ExtendedHiredUnitEquipmentEntry extends WeightedRandom.Item {
	private final ItemStack stack;
	public ExtendedHiredUnitEquipmentEntry(ItemStack stack, int weight) {
		super(weight);
		this.stack = stack;

	}

	public ItemStack getStack(){
		return stack;
	}

	public static class Serializer implements JsonDeserializer<ExtendedHiredUnitEquipmentEntry>, JsonSerializer<ExtendedHiredUnitEquipmentEntry> {
		@Override
		public ExtendedHiredUnitEquipmentEntry deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedHiredUnitEquipmentEntry");
			int weight = jsonobject.get("weight").getAsInt();
			ItemStack stack = getStack(JSONUtils.getAsJsonObject(jsonobject, "equipmentItem"));
			return new ExtendedHiredUnitEquipmentEntry(stack, weight);
		}

		@Override
		public JsonElement serialize(ExtendedHiredUnitEquipmentEntry entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("weight", entitySpawner.weight);
			jsonobject.add("equipmentItem", entitySpawner.serializeItem());
			return jsonobject;
		}
	}

	@SuppressWarnings("deprecation")
	private JsonObject serializeItem() {
		JsonObject lvt_1_1_ = new JsonObject();
		lvt_1_1_.addProperty("item", Registry.ITEM.getKey(this.stack.getItem()).toString());
		if (this.stack.hasTag()) {
			lvt_1_1_.addProperty("nbt", this.stack.getTag().toString());
		}

		return lvt_1_1_;
	}

	private static ItemStack getStack(JsonObject p_193221_0_) {
		if (!p_193221_0_.has("item")) {
			throw new JsonSyntaxException("Missing Equipment item type (add 'item' key)");
		} else {
			Item basicItem = JSONUtils.getAsItem(p_193221_0_, "item");
			if (p_193221_0_.has("data")) {
				throw new JsonParseException("Disallowed data tag found");
			} else {
				ItemStack generatedItemStack = new ItemStack(basicItem);
				if (p_193221_0_.has("nbt")) {
					try {
						CompoundNBT nbt = JsonToNBT.parseTag(JSONUtils.convertToString(p_193221_0_.get("nbt"), "nbt"));
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