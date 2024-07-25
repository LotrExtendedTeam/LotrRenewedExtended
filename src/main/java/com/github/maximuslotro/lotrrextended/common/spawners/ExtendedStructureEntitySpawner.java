package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;

public class ExtendedStructureEntitySpawner {
	public static final ExtendedStructureEntitySpawner EMPTY = new ExtendedStructureEntitySpawner(new ExtendedStructureEntitySpawnerEntity[0], true);
	private final List<ExtendedStructureEntitySpawnerEntity> entries;
	private final boolean isSingleUse;

	private ExtendedStructureEntitySpawner(ExtendedStructureEntitySpawnerEntity[] entries, boolean isSingleUse) {
		this.entries = Lists.newArrayList(entries);
		this.isSingleUse = isSingleUse;
	}

	public List<ExtendedStructureEntitySpawnerEntity> getEntries() {
		return new ArrayList<>(entries);
	}

	public static class Serializer implements JsonDeserializer<ExtendedStructureEntitySpawner>, JsonSerializer<ExtendedStructureEntitySpawner> {
		public ExtendedStructureEntitySpawner deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedStructureEntitySpawner");
			ExtendedStructureEntitySpawnerEntity[] alootpool = JSONUtils.getAsObject(jsonobject, "pools", new ExtendedStructureEntitySpawnerEntity[0], context, ExtendedStructureEntitySpawnerEntity[].class);
			boolean isSingleUse = jsonobject.get("isSingleUse").getAsBoolean();
			return new ExtendedStructureEntitySpawner(alootpool, isSingleUse);
		}

		public JsonElement serialize(ExtendedStructureEntitySpawner entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("isSingleUse", entitySpawner.isSingleUse);

			if (!entitySpawner.entries.isEmpty()) {
				jsonobject.add("pools", context.serialize(entitySpawner.entries));
			}

			return jsonobject;
		}
	}
}
