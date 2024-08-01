package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.spawners.types.ExtendedStructureEntityTypeInterface;
import com.github.maximuslotro.lotrrextended.common.spawners.types.MultiSpawnType;
import com.github.maximuslotro.lotrrextended.common.spawners.types.MultiSpawnType.MultiSpawnEntry;
import com.github.maximuslotro.lotrrextended.common.spawners.types.SingleSpawnType;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.entity.EntityType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedStructureEntitySpawnerEntry extends WeightedRandom.Item {
	private final ExtendedStructureEntityTypeInterface entityProvider;
	private final int minSpawn;
	private final int maxSpawn;
	public ExtendedStructureEntitySpawnerEntry(ExtendedStructureEntityTypeInterface entityProvider, int weight, int minSpawn, int maxSpawn) {
		super(weight);
		this.entityProvider = entityProvider;
		this.minSpawn = minSpawn;
		this.maxSpawn = maxSpawn;
		
	}

	public List<String> getAllEntities(){
		return entityProvider.getAllPossableEntities().stream().map(EntityType::getRegistryName).map(ResourceLocation::toString).collect(Collectors.toList());
	}

	public static class Serializer implements JsonDeserializer<ExtendedStructureEntitySpawnerEntry>, JsonSerializer<ExtendedStructureEntitySpawnerEntry> {
		public ExtendedStructureEntitySpawnerEntry deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedStructureEntitySpawnerEntry");
			int weight = jsonobject.get("weight").getAsInt();
			int minSpawn = jsonobject.get("minSpawnCount").getAsInt();
			int maxSpawn = jsonobject.get("maxSpawnCount").getAsInt();
			ExtendedStructureEntityTypeInterface entityProvider = null;
			if (jsonobject.has("entity_type")) {
				EntityType<?> entityType = getEntityTypeFromString(jsonobject.get("entity_type").getAsString());
				if (entityType == null) {
					ExtendedLog.warn("Failed to load structure entity type: "+jsonobject.get("entity_type").getAsString());
					return null;
				}

				entityProvider = new SingleSpawnType(entityType);
			} else if (jsonobject.has("entity_types")) {
				List<MultiSpawnEntry> entities = new ArrayList<>();
				JsonArray types = jsonobject.get("entity_types").getAsJsonArray();
				for(JsonElement element : types) {
					JsonObject jsonSubObject = element.getAsJsonObject();
					EntityType<?> entityType = getEntityTypeFromString(jsonSubObject.get("entity_type").getAsString());
					if (entityType == null) {
						ExtendedLog.warn("Failed to load structure entity type: "+jsonSubObject.get("entity_type").getAsString());
						return null;
					}
					int weightSub = jsonSubObject.get("weight").getAsInt();
					entities.add(new MultiSpawnEntry(weightSub, entityType));
				}
				entityProvider = new MultiSpawnType(entities);
			} else {
				ExtendedLog.error("Loaded Entity Structure Spawn Json with invalid spawn configuration!");
				ExtendedLog.error(jsonobject.getAsString());
				return null;
			}
			return new ExtendedStructureEntitySpawnerEntry(entityProvider, weight, minSpawn, maxSpawn);
		}

		public JsonElement serialize(ExtendedStructureEntitySpawnerEntry entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("weight", entitySpawner.weight);
			jsonobject.addProperty("minSpawnCount", entitySpawner.minSpawn);
			jsonobject.addProperty("maxSpawnCount", entitySpawner.maxSpawn);
			if (entitySpawner.entityProvider instanceof SingleSpawnType) {
				jsonobject.addProperty("entity_type", ((SingleSpawnType)entitySpawner.entityProvider).getEntity().getRegistryName().toString());
			} else if (entitySpawner.entityProvider instanceof MultiSpawnType){
				List<MultiSpawnEntry> entities = ((MultiSpawnType)entitySpawner.entityProvider).getEntities();
				JsonArray elements = new JsonArray();
				for (MultiSpawnEntry entry : entities) {
					JsonObject subObject = new JsonObject();
					jsonobject.addProperty("weight", entry.weight);
					jsonobject.addProperty("entity_type", entry.getEntity().getRegistryName().toString());
					elements.add(subObject);
				}
				jsonobject.add("entity_types", elements);
			} else {
				ExtendedLog.error("Tried to serialize a structure entity spawn type with an unknown spawn type!");
				ExtendedLog.error(entitySpawner.entityProvider.getClass().getName());
				return null;
			}
			return jsonobject;
		}

		@Nullable
		public static EntityType<?> getEntityTypeFromString(String str) {
			ResourceLocation entityReasourceName = new ResourceLocation(str);
			return ForgeRegistries.ENTITIES.getValue(entityReasourceName);
		}
	}
}