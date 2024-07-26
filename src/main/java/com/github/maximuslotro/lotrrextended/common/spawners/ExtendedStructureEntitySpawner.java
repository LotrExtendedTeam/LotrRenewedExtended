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
	private boolean setHomePositionOnSpawn = false;
	private boolean setHomeRandom = false;
	private int homeRadius = 16;

	private ExtendedStructureEntitySpawner(ExtendedStructureEntitySpawnerEntity[] entries, boolean isSingleUse) {
		this.entries = Lists.newArrayList(entries);
		this.isSingleUse = isSingleUse;
	}

	public List<ExtendedStructureEntitySpawnerEntity> getEntries() {
		return new ArrayList<>(entries);
	}

	public boolean doesRemoveAfterUse() {
		return isSingleUse;
	}

	public boolean isSetHomePositionOnSpawn() {
		return setHomePositionOnSpawn;
	}

	public void setSetHomePositionOnSpawn(boolean setHomePositionOnSpawn) {
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
	}

	public boolean isSetHomeRandom() {
		return setHomeRandom;
	}

	public void setSetHomeRandom(boolean setHomeRandom) {
		this.setHomeRandom = setHomeRandom;
	}

	public int getHomeRadius() {
		return homeRadius;
	}

	public void setHomeRadius(int homeRadius) {
		this.homeRadius = homeRadius;
	}

	public static class Serializer implements JsonDeserializer<ExtendedStructureEntitySpawner>, JsonSerializer<ExtendedStructureEntitySpawner> {
		public ExtendedStructureEntitySpawner deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedStructureEntitySpawner");
			ExtendedStructureEntitySpawnerEntity[] alootpool = JSONUtils.getAsObject(jsonobject, "pools", new ExtendedStructureEntitySpawnerEntity[0], context, ExtendedStructureEntitySpawnerEntity[].class);
			boolean isSingleUse = jsonobject.get("isSingleUse").getAsBoolean();
			ExtendedStructureEntitySpawner spawner = new ExtendedStructureEntitySpawner(alootpool, isSingleUse);
			if(jsonobject.has("setHomeOnSpawn")) {
				spawner.setSetHomePositionOnSpawn(jsonobject.get("setHomeOnSpawn").getAsBoolean());
				spawner.setHomeRadius(jsonobject.get("defaultHomeRadius").getAsInt());
			}
			if(jsonobject.has("setHomeRandomly")) {
				spawner.setSetHomeRandom(jsonobject.get("setHomeRandomly").getAsBoolean());
			}
			return spawner;
		}

		public JsonElement serialize(ExtendedStructureEntitySpawner entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("isSingleUse", entitySpawner.doesRemoveAfterUse());

			if (!entitySpawner.entries.isEmpty()) {
				jsonobject.add("pools", context.serialize(entitySpawner.entries));
			}
			if(entitySpawner.isSetHomePositionOnSpawn()) {
				jsonobject.addProperty("setHomeOnSpawn", entitySpawner.isSetHomePositionOnSpawn());
				if(entitySpawner.isSetHomeRandom()) {
					jsonobject.addProperty("setHomeRandomly", entitySpawner.isSetHomeRandom());
				}
				jsonobject.addProperty("defaultHomeRadius", entitySpawner.getHomeRadius());
			}
			return jsonobject;
		}
	}
}
