package com.github.maximuslotro.lotrrextended.common.advancements.predicates;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedSimpleBlockPredicate {
	public static final ExtendedSimpleBlockPredicate ANY = new ExtendedSimpleBlockPredicate((Block)null);
	private final Block block;

	public ExtendedSimpleBlockPredicate(Block pBlock) {
		this.block = pBlock;
	}

	public boolean matches(Block block) {
		if (this == ANY) {
			return true;
		}else if (block != this.block) {
			return false;
		} 
		return true;
	}

	public static ExtendedSimpleBlockPredicate fromJson(@Nullable JsonElement pJson) {
		if (pJson != null && !pJson.isJsonNull()) {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(pJson, "block");
			Block block = null;
			if (jsonobject.has("block")) {
				ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(jsonobject, "block"));
				block = ForgeRegistries.BLOCKS.getValue(resourcelocation);
				if (block == null) {
					throw new JsonSyntaxException("Unknown block '" + resourcelocation + "'");
				}
			}
			return new ExtendedSimpleBlockPredicate(block);
		} else {
			return ANY;
		}
	}

	public JsonElement serializeToJson() {
		if (this == ANY) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonobject = new JsonObject();
			if (this.block != null) {
				jsonobject.addProperty("block", ForgeRegistries.BLOCKS.getKey(this.block).toString());
			}
			return jsonobject;
		}
	}
}