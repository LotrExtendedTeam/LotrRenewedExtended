package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
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
import net.minecraft.util.ResourceLocation;

public class ExtendedStructureEntitySpawner {
	public static final int defultEntitySpawnTickRate = 3600;
	public static final int defultPlayerMinSpawnDistance = 10;
	public static final int defultPlayerMaxSpawnDistance = 50;
	public static final int defultHomeRadius = 16;
	public static final ExtendedSpawnerBoundsEntry defultBoundary = new ExtendedSpawnerBoundsEntry(0, 0, 0);
	public static final ExtendedStructureEntitySpawner EMPTY = new ExtendedStructureEntitySpawner(new ResourceLocation("empty"), new ExtendedStructureEntitySpawnerEntity[0], SpawnerType.UNKNOWN, defultBoundary, defultBoundary);
	private final List<ExtendedStructureEntitySpawnerEntity> entries;
	private final ResourceLocation id;
	private final SpawnerType spawnType;
	private boolean setHomePositionOnSpawn = false;
	private boolean setHomeRandom = false;
	private int homeRadius = defultHomeRadius;
	private int entitySpawnTickRate = defultEntitySpawnTickRate;
	private int playerMinSpawnDistance = defultPlayerMinSpawnDistance;
	private int playerMaxSpawnDistance = defultPlayerMaxSpawnDistance;
	private ExtendedSpawnerBoundsEntry checkBoundaries = defultBoundary;
	private ExtendedSpawnerBoundsEntry spawnRegion = defultBoundary;

	public ExtendedStructureEntitySpawner(ResourceLocation id, ExtendedStructureEntitySpawnerEntity[] entries, SpawnerType spawnType,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
		this(id, Lists.newArrayList(entries), spawnType, checkBoundaries, spawnRegion);
	}

	public ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntity> entries, SpawnerType spawnType,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
		this.id = id;
		this.entries = entries;
		this.spawnType = spawnType;
		this.checkBoundaries = checkBoundaries;
		this.spawnRegion = spawnRegion;
	}

	public ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntity> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn,
			boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
		this(id, entries, spawnType, checkBoundaries, spawnRegion);
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
		this.setHomeRandom = setHomeRandom;
		this.homeRadius = homeRadius;
		this.entitySpawnTickRate = entitySpawnTickRate;
		this.playerMinSpawnDistance = playerMinSpawnDistance;
		this.playerMaxSpawnDistance = playerMaxSpawnDistance;
	}

	public ResourceLocation getId() {
		return id;
	}

	public List<ExtendedStructureEntitySpawnerEntity> getEntries() {
		return new ArrayList<>(entries);
	}

	public SpawnerType getSpawnType() {
		return spawnType;
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

	// ##################################### Player Distance Methods ###########################
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

	// ##################################### Boundaries ##########################################
	public ExtendedSpawnerBoundsEntry getCheckBoundaries() {
		return checkBoundaries;
	}

	public void setCBoundaries(int topY, int bottomY, int horizontal) {
		setCheckBoundaries(new ExtendedSpawnerBoundsEntry(topY, bottomY, horizontal));
	}

	public void setCheckBoundaries(ExtendedSpawnerBoundsEntry checkBoundaries) {
		this.checkBoundaries = checkBoundaries;
	}

	public ExtendedSpawnerBoundsEntry getSpawnBoundaries() {
		return spawnRegion;
	}

	public void setSpawnArea(int topY, int bottomY, int horizontal) {
		setSpawnArea(new ExtendedSpawnerBoundsEntry(topY, bottomY, horizontal));
	}

	public void setSpawnArea(ExtendedSpawnerBoundsEntry spawnBoundaries) {
		this.spawnRegion = spawnBoundaries;
	}

	public void setAreas(int topYC, int bottomYC, int horizontalC, int topYS, int bottomYS, int horizontalS) {
		setCheckBoundaries(new ExtendedSpawnerBoundsEntry(topYC, bottomYC, horizontalC));
		setSpawnArea(new ExtendedSpawnerBoundsEntry(topYS, bottomYS, horizontalS));
	}

	public static class Serializer implements JsonDeserializer<ExtendedStructureEntitySpawner>, JsonSerializer<ExtendedStructureEntitySpawner> {
		public ExtendedStructureEntitySpawner deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedStructureEntitySpawner");
			ExtendedStructureEntitySpawnerEntity[] alootpool = JSONUtils.getAsObject(jsonobject, "pools", new ExtendedStructureEntitySpawnerEntity[0], context, ExtendedStructureEntitySpawnerEntity[].class);
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load structure spawner due to invalid id: %s!", id);
				return null;
			}
			SpawnerType type = SpawnerType.bySpawnerName(jsonobject.get("spawner_type").getAsString());
			if(type==SpawnerType.UNKNOWN) {
				ExtendedLog.warn("Failed to load structure spawner %s due to invalid spawn type!", id);
				return null;
			}
			ExtendedSpawnerBoundsEntry check = JSONUtils.getAsObject(jsonobject, "check_boundaries", defultBoundary, context, ExtendedSpawnerBoundsEntry.class);
			ExtendedSpawnerBoundsEntry spawn  = JSONUtils.getAsObject(jsonobject, "spawn_area", defultBoundary, context, ExtendedSpawnerBoundsEntry.class);
			ExtendedStructureEntitySpawner spawner = new ExtendedStructureEntitySpawner(new ResourceLocation(id), alootpool, type, check, spawn);
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
			jsonobject.addProperty("id", entitySpawner.getId().toString());
			jsonobject.addProperty("spawner_type", entitySpawner.spawnType.getString());
			if(entitySpawner.getCheckBoundaries() != defultBoundary) {
				jsonobject.add("check_boundaries", context.serialize(entitySpawner.checkBoundaries));
			}
			if(entitySpawner.getSpawnBoundaries() != defultBoundary) {
				jsonobject.add("spawn_area", context.serialize(entitySpawner.spawnRegion));
			}
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
		private final SpawnerType spawnType;
		private boolean setHomePositionOnSpawn = false;
		private boolean setHomeRandom = false;
		private int homeRadius = 16;
		private int entitySpawnTickRate = defultEntitySpawnTickRate;
		private int playerMinSpawnDistance = defultPlayerMinSpawnDistance;
		private int playerMaxSpawnDistance = defultPlayerMaxSpawnDistance;
		private ExtendedSpawnerBoundsEntry checkBoundaries = defultBoundary;
		private ExtendedSpawnerBoundsEntry spawnRegion = defultBoundary;

		public Builder(List<ExtendedStructureEntitySpawnerEntity> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn, 
				boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
				ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
			this.entries = entries;
			this.spawnType = spawnType;
			this.setHomePositionOnSpawn = setHomePositionOnSpawn;
			this.setHomeRandom = setHomeRandom;
			this.homeRadius = homeRadius;
			this.entitySpawnTickRate = entitySpawnTickRate;
			this.playerMinSpawnDistance = playerMinSpawnDistance;
			this.playerMaxSpawnDistance = playerMaxSpawnDistance;
			this.checkBoundaries = checkBoundaries;
			this.spawnRegion = spawnRegion;
		}

		private Builder(SpawnerType spawnType) {
			this.spawnType = spawnType;
		}

		private Builder(SpawnerType spawnType, boolean setHomePositionOnSpawn, int homeRadius) {
			this.spawnType = spawnType;
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

		public ExtendedStructureEntitySpawner.Builder setBoundaries(int topYC, int bottomYC, int horizontalC, int topYS, int bottomYS, int horizontalS) {
			checkBoundaries = new ExtendedSpawnerBoundsEntry(topYC, bottomYC, horizontalC);
			spawnRegion = new ExtendedSpawnerBoundsEntry(topYS, bottomYS, horizontalS);
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

		public ExtendedStructureEntitySpawner build(ResourceLocation id) {
			return new ExtendedStructureEntitySpawner(id, entries, spawnType, setHomePositionOnSpawn, setHomeRandom, homeRadius, 
					entitySpawnTickRate, playerMinSpawnDistance, playerMaxSpawnDistance, checkBoundaries, spawnRegion);
		}

		public ExtendedStructureEntitySpawner save(Consumer<ExtendedStructureEntitySpawner> pConsumer, String pId) {
			ExtendedStructureEntitySpawner spawner = this.build(new ResourceLocation(pId));
			pConsumer.accept(spawner);
			return spawner;
		}

		public ExtendedStructureEntitySpawner save(Consumer<ExtendedStructureEntitySpawner> pConsumer, ResourceLocation pId) {
			ExtendedStructureEntitySpawner spawner = this.build(pId);
			pConsumer.accept(spawner);
			return spawner;
		}

		public static ExtendedStructureEntitySpawner.Builder spawner(SpawnerType spawnType) {
			return new ExtendedStructureEntitySpawner.Builder(spawnType);
		}

		public static ExtendedStructureEntitySpawner.Builder spawner(SpawnerType spawnType, boolean setHomePositionOnSpawn, int homeRadius) {
			return new ExtendedStructureEntitySpawner.Builder(spawnType);
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
