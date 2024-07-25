package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedStructureEntitySpawnerEntity {

	public ExtendedStructureEntitySpawnerEntity() {
		
	}

	public static ExtendedStructureEntitySpawnerEntity read(JsonObject json, ResourceLocation LocationName) {

		return null;
	}

	public static class Serializer implements JsonDeserializer<ExtendedStructureEntitySpawnerEntity>, JsonSerializer<ExtendedStructureEntitySpawnerEntity> {
		public ExtendedStructureEntitySpawnerEntity deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedStructureEntitySpawnerEntry");
			return new ExtendedStructureEntitySpawnerEntity();
		}

		public JsonElement serialize(ExtendedStructureEntitySpawnerEntity entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			return jsonobject;
		}
	}
}
