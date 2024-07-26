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
	public static final int defultEntitySpawnTickRate = 3600;
	public static final int defultPlayerMinSpawnDistance = 10;
	public static final int defultPlayerMaxSpawnDistance = 50;
	public static final int defultHomeRadius = 16;
	private final boolean isSingleUse;
	private boolean setHomePositionOnSpawn = false;
	private boolean setHomeRandom = false;
	private int homeRadius = defultHomeRadius;
	private int entitySpawnTickRate = defultEntitySpawnTickRate;
	private int playerMinSpawnDistance = defultPlayerMinSpawnDistance;
	private int playerMaxSpawnDistance = defultPlayerMaxSpawnDistance;

	public ExtendedStructureEntitySpawner(ExtendedStructureEntitySpawnerEntity[] entries, boolean isSingleUse) {
		this(Lists.newArrayList(entries), isSingleUse);
	}

	public ExtendedStructureEntitySpawner(List<ExtendedStructureEntitySpawnerEntity> entries, boolean isSingleUse) {
		this.entries = entries;
		this.isSingleUse = isSingleUse;
	}

	public ExtendedStructureEntitySpawner(List<ExtendedStructureEntitySpawnerEntity> entries, boolean isSingleUse, boolean setHomePositionOnSpawn,
			boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance) {
		this.entries = entries;
		this.isSingleUse = isSingleUse;
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
		this.setHomeRandom = setHomeRandom;
		this.homeRadius = homeRadius;
		this.entitySpawnTickRate = entitySpawnTickRate;
		this.playerMinSpawnDistance = playerMinSpawnDistance;
		this.playerMaxSpawnDistance = playerMaxSpawnDistance;
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

	public int getEntitySpawnTickRate() {
		return entitySpawnTickRate;
	}

	public void setEntitySpawnTickRate(int entitySpawnTickRate) {
		this.entitySpawnTickRate = entitySpawnTickRate;
	}

	public int getPlayerMinSpawnDistance() {
		return playerMinSpawnDistance;
	}

	public void setPlayerMinSpawnDistance(int playerMinSpawnDistance) {
		this.playerMinSpawnDistance = playerMinSpawnDistance;
	}

	public int getPlayerMaxSpawnDistance() {
		return playerMaxSpawnDistance;
	}

	public void setPlayerMaxSpawnDistance(int playerManSpawnDistance) {
		this.playerMaxSpawnDistance = playerManSpawnDistance;
	}

	public void setPlayerSpawnDistance(int playerMinSpawnDistance, int playerMaxSpawnDistance) {
		this.playerMinSpawnDistance = playerMinSpawnDistance;
		this.playerMaxSpawnDistance = playerMaxSpawnDistance;
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
			if(jsonobject.has("entitySpawnTickRate")) {
				spawner.setEntitySpawnTickRate(jsonobject.get("entitySpawnTickRate").getAsInt());
			}
			if(jsonobject.has("playerMinSpawnDistance")) {
				spawner.setPlayerMinSpawnDistance(jsonobject.get("playerMinSpawnDistance").getAsInt());
			}
			if(jsonobject.has("playerMaxSpawnDistance")) {
				spawner.setPlayerMaxSpawnDistance(jsonobject.get("playerMaxSpawnDistance").getAsInt());
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
				if(entitySpawner.getHomeRadius() != defultHomeRadius) {
					jsonobject.addProperty("defaultHomeRadius", entitySpawner.getHomeRadius());
				}
			}
			if(entitySpawner.getEntitySpawnTickRate() != defultEntitySpawnTickRate) {
				jsonobject.addProperty("entitySpawnTickRate", entitySpawner.getEntitySpawnTickRate());
			}
			if(entitySpawner.getPlayerMinSpawnDistance() != defultPlayerMinSpawnDistance) {
				jsonobject.addProperty("playerMinSpawnDistance", entitySpawner.getPlayerMinSpawnDistance());
			}
			if(entitySpawner.getPlayerMaxSpawnDistance() != defultPlayerMaxSpawnDistance) {
				jsonobject.addProperty("playerMaxSpawnDistance", entitySpawner.getPlayerMaxSpawnDistance());
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
		private int entitySpawnTickRate = defultEntitySpawnTickRate;
		private int playerMinSpawnDistance = defultPlayerMinSpawnDistance;
		private int playerMaxSpawnDistance = defultPlayerMaxSpawnDistance;

		public Builder(ExtendedStructureEntitySpawnerEntity[] entries, boolean isSingleUse, boolean setHomePositionOnSpawn, boolean setHomeRandom, 
				int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance) {
			this(Lists.newArrayList(entries), isSingleUse, setHomePositionOnSpawn, setHomeRandom, homeRadius, 
					entitySpawnTickRate, playerMinSpawnDistance, playerMaxSpawnDistance);
		}

		public Builder(List<ExtendedStructureEntitySpawnerEntity> entries, boolean isSingleUse, boolean setHomePositionOnSpawn, 
				boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance) {
			this.entries = entries;
			this.isSingleUse = isSingleUse;
			this.setHomePositionOnSpawn = setHomePositionOnSpawn;
			this.setHomeRandom = setHomeRandom;
			this.homeRadius = homeRadius;
			this.entitySpawnTickRate = entitySpawnTickRate;
			this.playerMinSpawnDistance = playerMinSpawnDistance;
			this.playerMaxSpawnDistance = playerMaxSpawnDistance;
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

		public ExtendedStructureEntitySpawner.Builder setPlayerDistance(int min, int max) {
			this.playerMinSpawnDistance = min;
			this.playerMaxSpawnDistance = max;
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder setEntitySpawnTickRate(int ticks) {
			this.entitySpawnTickRate = ticks;
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder setEntitySpawnSeconds(int seconds) {
			this.entitySpawnTickRate = seconds * 20;
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder setEntitySpawnMinutes(int minutes) {
			this.entitySpawnTickRate = minutes * 60 * 20;
			return this;
		}

		public ExtendedStructureEntitySpawner build() {
			return new ExtendedStructureEntitySpawner(entries, isSingleUse, setHomePositionOnSpawn, setHomeRandom, homeRadius, 
					entitySpawnTickRate, playerMinSpawnDistance, playerMaxSpawnDistance);
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
	}
}
