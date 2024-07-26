package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.spawners.types.MultiSpawnType;
import com.github.maximuslotro.lotrrextended.common.spawners.types.SingleSpawnType;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.entity.EntityType;
import net.minecraft.util.JSONUtils;

public class ExtendedStructureEntitySpawner {
	public static final ExtendedStructureEntitySpawner EMPTY = new ExtendedStructureEntitySpawner(new ExtendedStructureEntitySpawnerEntity[0], true);
	private final List<ExtendedStructureEntitySpawnerEntity> entries;
	private final boolean isSingleUse;
	private boolean setHomePositionOnSpawn = false;
	private boolean setHomeRandom = false;
	private int homeRadius = 16;

	public ExtendedStructureEntitySpawner(ExtendedStructureEntitySpawnerEntity[] entries, boolean isSingleUse) {
		this(Lists.newArrayList(entries), isSingleUse);
	}

	public ExtendedStructureEntitySpawner(List<ExtendedStructureEntitySpawnerEntity> entries, boolean isSingleUse) {
		this.entries = entries;
		this.isSingleUse = isSingleUse;
	}

	public ExtendedStructureEntitySpawner(List<ExtendedStructureEntitySpawnerEntity> entries, boolean isSingleUse, boolean setHomePositionOnSpawn,
			boolean setHomeRandom, int homeRadius) {
		this.entries = entries;
		this.isSingleUse = isSingleUse;
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
		this.setHomeRandom = setHomeRandom;
		this.homeRadius = homeRadius;
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

			if(entitySpawner.isSetHomePositionOnSpawn()) {
				jsonobject.addProperty("setHomeOnSpawn", entitySpawner.isSetHomePositionOnSpawn());
				if(entitySpawner.isSetHomeRandom()) {
					jsonobject.addProperty("setHomeRandomly", entitySpawner.isSetHomeRandom());
				}
				jsonobject.addProperty("defaultHomeRadius", entitySpawner.getHomeRadius());
			}
			if (!entitySpawner.entries.isEmpty()) {
				jsonobject.add("pools", context.serialize(entitySpawner.entries));
			}
			return jsonobject;
		}
	}

	public static class Builder {
		private List<ExtendedStructureEntitySpawnerEntity> entries = new ArrayList<>();
		private boolean isSingleUse;
		private boolean setHomePositionOnSpawn = false;
		private boolean setHomeRandom = false;
		private int homeRadius = 16;

		public Builder(ExtendedStructureEntitySpawnerEntity[] entries, boolean isSingleUse, boolean setHomePositionOnSpawn, boolean setHomeRandom, int homeRadius) {
			this(Lists.newArrayList(entries), isSingleUse, setHomePositionOnSpawn, setHomeRandom,homeRadius);
		}

		public Builder(List<ExtendedStructureEntitySpawnerEntity> entries, boolean isSingleUse, boolean setHomePositionOnSpawn, boolean setHomeRandom, int homeRadius) {
			this.entries = entries;
			this.isSingleUse = isSingleUse;
			this.setHomePositionOnSpawn = setHomePositionOnSpawn;
			this.setHomeRandom = setHomeRandom;
			this.homeRadius = homeRadius;
		}

		private Builder(boolean isSingleUse) {
			this.isSingleUse = isSingleUse;
		}

		private Builder(boolean isSingleUse, boolean setHomePositionOnSpawn, int homeRadius) {
			this.isSingleUse = isSingleUse;
			this.setHomePositionOnSpawn = setHomePositionOnSpawn;
			this.homeRadius = homeRadius;
		}

		public ExtendedStructureEntitySpawner.Builder setHomeInfo(boolean setHomeRandom, int homeRadius) {
			this.setHomePositionOnSpawn = true;
			this.setHomeRandom = setHomeRandom;
			this.homeRadius = homeRadius;
			return this;
		}

		public static ExtendedStructureEntitySpawner.Builder spawner(boolean isSingleUse) {
			return new ExtendedStructureEntitySpawner.Builder(isSingleUse);
		}

		public static ExtendedStructureEntitySpawner.Builder spawner(boolean isSingleUse, boolean setHomePositionOnSpawn, int homeRadius) {
			return new ExtendedStructureEntitySpawner.Builder(isSingleUse);
		}
		
		public ExtendedStructureEntitySpawner.Builder addSinglePool(EntityType<?> type, int weight, int minSpawnCount, int maxSpawnCount) {
			entries.add(new ExtendedStructureEntitySpawnerEntity(new SingleSpawnType(type), weight, minSpawnCount, maxSpawnCount));
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder addMultiPool(MultiSpawnType multiPool, int weight, int minSpawnCount, int maxSpawnCount) {
			entries.add(new ExtendedStructureEntitySpawnerEntity(multiPool, weight, minSpawnCount, maxSpawnCount));
			return this;
		}

		public ExtendedStructureEntitySpawner build() {
			return new ExtendedStructureEntitySpawner(entries, isSingleUse, setHomePositionOnSpawn, setHomeRandom, homeRadius);
		}

		public boolean isSetHomePositionOnSpawn() {
			return setHomePositionOnSpawn;
		}

		public boolean isSetHomeRandom() {
			return setHomeRandom;
		}

		public int getHomeRadius() {
			return homeRadius;
		}

		public void setSingleUse(boolean isSingleUse) {
			this.isSingleUse = isSingleUse;
		}
	}
}
