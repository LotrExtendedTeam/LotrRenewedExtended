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
	public static final int defaultEntitySpawnTickRate = 3600;
	public static final int defaultPlayerMinSpawnDistance = 10;
	public static final int defaultPlayerMaxSpawnDistance = 50;
	public static final int defultHomeRadius = 16;
	public static final int defaultLocalSpawnCap = 4;
	public static final ExtendedSpawnerBoundsEntry defaultBoundary = new ExtendedSpawnerBoundsEntry(0, 0, 0);
	public static final ExtendedStructureEntitySpawner EMPTY = new ExtendedStructureEntitySpawner(ExtendedSpawners.EMPTY, new ExtendedStructureEntitySpawnerEntry[0], SpawnerType.UNKNOWN, defaultBoundary, defaultBoundary);
	private final List<ExtendedStructureEntitySpawnerEntry> entries;
	private final ResourceLocation id;
	private final SpawnerType spawnType;
	private boolean setHomePositionOnSpawn = false;
	private boolean setHomeRandom = false;
	private int homeRadius = defultHomeRadius;
	private int entitySpawnTickRate = defaultEntitySpawnTickRate;
	private int playerMinSpawnDistance = defaultPlayerMinSpawnDistance;
	private int playerMaxSpawnDistance = defaultPlayerMaxSpawnDistance;
	private ExtendedSpawnerBoundsEntry checkBoundaries = defaultBoundary;
	private ExtendedSpawnerBoundsEntry spawnRegion = defaultBoundary;
	private int poolRolls = 1;
	private int localSpawnCap = defaultLocalSpawnCap;

	public ExtendedStructureEntitySpawner(ResourceLocation id, ExtendedStructureEntitySpawnerEntry[] entries, SpawnerType spawnType,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
		this(id, Lists.newArrayList(entries), spawnType, checkBoundaries, spawnRegion);
	}

	public ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
		this.id = id;
		this.entries = entries;
		this.spawnType = spawnType;
		this.checkBoundaries = checkBoundaries;
		this.spawnRegion = spawnRegion;
	}

	public ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn,
			boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion, int poolRolls, int localSpawnCap) {
		this(id, entries, spawnType, checkBoundaries, spawnRegion);
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
		this.setHomeRandom = setHomeRandom;
		this.homeRadius = homeRadius;
		this.entitySpawnTickRate = entitySpawnTickRate;
		this.playerMinSpawnDistance = playerMinSpawnDistance;
		this.playerMaxSpawnDistance = playerMaxSpawnDistance;
		this.poolRolls = poolRolls;
		this.localSpawnCap = localSpawnCap;
	}

	public ResourceLocation getId() {
		return id;
	}

	public boolean shouldTick() {
		return !id.equals(ExtendedSpawners.EMPTY);
	}

	public List<ExtendedStructureEntitySpawnerEntry> getEntries() {
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

	public boolean setHomeRadius(int homeRadius) {
		if(homeRadius<1) {
			this.homeRadius = defultHomeRadius;
			return false;
		}else {
			this.homeRadius = homeRadius;
			return true;
		}
	}

	public int getEntitySpawnTickRate() {
		return entitySpawnTickRate;
	}

	public boolean setEntitySpawnTickRate(int entitySpawnTickRate) {
		if(entitySpawnTickRate<1) {
			this.entitySpawnTickRate = defaultEntitySpawnTickRate;
			return false;
		}else {
			this.entitySpawnTickRate = entitySpawnTickRate;
			return true;
		}
	}

	public int getPoolRolls() {
		return poolRolls;
	}

	public boolean setPoolRolls(int poolRolls) {
		if(poolRolls<1) {
			this.poolRolls = 1;
			return false;
		}else {
			this.poolRolls = poolRolls;
			return true;
		}
	}

	public int getLocalSpawnCap() {
		return localSpawnCap;
	}

	public boolean setLocalSpawnCap(int localSpawnCap) {
		if(localSpawnCap<0) {
			this.localSpawnCap = defaultLocalSpawnCap;
			return false;
		}else {
			this.localSpawnCap = localSpawnCap;
			return true;
		}
	}

	// ##################################### Player Distance Methods ###########################
	public int getPlayerMinSpawnDistance() {
		return playerMinSpawnDistance;
	}

	public boolean setPlayerMinSpawnDistance(int playerMinSpawnDistance) {
		if(playerMinSpawnDistance<0) {
			this.playerMinSpawnDistance = defaultPlayerMinSpawnDistance;
			return false;
		}else {
			this.playerMinSpawnDistance = playerMinSpawnDistance;
			return true;
		}
	}

	public int getPlayerMaxSpawnDistance() {
		return playerMaxSpawnDistance;
	}

	public boolean setPlayerMaxSpawnDistance(int playerMaxSpawnDistance) {
		if(playerMaxSpawnDistance<0) {
			this.playerMaxSpawnDistance = defaultPlayerMaxSpawnDistance;
			return false;
		}else {
			this.playerMaxSpawnDistance = playerMaxSpawnDistance;
			return true;
		}
	}

	public boolean setPlayerSpawnDistance(int playerMinSpawnDistance, int playerMaxSpawnDistance) {
		if(!setPlayerMinSpawnDistance(playerMinSpawnDistance) || !setPlayerMaxSpawnDistance(playerMaxSpawnDistance)) {
			return false;
		}
		return true;
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
			ExtendedStructureEntitySpawnerEntry[] alootpool = JSONUtils.getAsObject(jsonobject, "pools", new ExtendedStructureEntitySpawnerEntry[0], context, ExtendedStructureEntitySpawnerEntry[].class);
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
			ExtendedSpawnerBoundsEntry check = JSONUtils.getAsObject(jsonobject, "check_boundaries", defaultBoundary, context, ExtendedSpawnerBoundsEntry.class);
			ExtendedSpawnerBoundsEntry spawn  = JSONUtils.getAsObject(jsonobject, "spawn_area", defaultBoundary, context, ExtendedSpawnerBoundsEntry.class);
			ExtendedStructureEntitySpawner spawner = new ExtendedStructureEntitySpawner(new ResourceLocation(id), alootpool, type, check, spawn);
			if(jsonobject.has("setHomeOnSpawn") && jsonobject.get("setHomeOnSpawn").getAsBoolean()) {
				spawner.setSetHomePositionOnSpawn(true);
				if(!jsonobject.has("homeRadius")) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid settings! homeRadius must be set if setHomeOnSpawn is enabled. Ignoring spawner - %s", id);
					return null;
				} else if(!spawner.setHomeRadius(jsonobject.get("homeRadius").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid homeRadius set! Resetting to default! - %s", id);
				}
			}
			if(jsonobject.has("setHomeRandomly")) {
				spawner.setSetHomeRandom(jsonobject.get("setHomeRandomly").getAsBoolean());
				if(!spawner.isSetHomePositionOnSpawn()) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid settings! Can't set setHomeRandomly if setHomeOnSpawn is not set! - %s", id);
				}
			}
			if(jsonobject.has("entitySpawnTickRate")) {
				if(!spawner.setEntitySpawnTickRate(jsonobject.get("entitySpawnTickRate").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid entitySpawnTickRate set! Resetting to default! - %s", id);
				}
			}
			if(jsonobject.has("playerMinSpawnDistance") || jsonobject.has("playerMaxSpawnDistance")) {
				if(!jsonobject.has("playerMinSpawnDistance") || !jsonobject.has("playerMaxSpawnDistance")) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid playerSpawnDistances set! Both must be set if either are set! Ignoring spawner - %s", id);
					return null;
				}else {
					if(!spawner.setPlayerMinSpawnDistance(jsonobject.get("playerMinSpawnDistance").getAsInt())) {
						ExtendedLog.warn("Loaded Structure Spawner with invalid playerMinSpawnDistance set! Resetting to default! - %s", id);
					}
					if(!spawner.setPlayerMaxSpawnDistance(jsonobject.get("playerMaxSpawnDistance").getAsInt())) {
						ExtendedLog.warn("Loaded Structure Spawner with invalid playerMaxSpawnDistance set! Resetting to default! - %s", id);
					}
				}
			}
			if(jsonobject.has("numRolls")) {
				if(!spawner.setPoolRolls(jsonobject.get("numRolls").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid numRolls set! Resetting to default! - %s", id);
				}
			}
			if(jsonobject.has("spawnCap")) {
				if(type.isSingleSpawnType()) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid settings! spawnCap should be set to 0 if spawner is of the Single types - %s", id);
				}else if(!spawner.setLocalSpawnCap(jsonobject.get("spawnCap").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid spawnCap set! Resetting to default! - %s", id);
				}
			}
			return spawner;
		}

		public JsonElement serialize(ExtendedStructureEntitySpawner entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", entitySpawner.getId().toString());
			jsonobject.addProperty("spawner_type", entitySpawner.spawnType.getString());
			if(entitySpawner.getCheckBoundaries() != defaultBoundary) {
				jsonobject.add("check_boundaries", context.serialize(entitySpawner.checkBoundaries));
			}
			if(entitySpawner.getSpawnBoundaries() != defaultBoundary) {
				jsonobject.add("spawn_area", context.serialize(entitySpawner.spawnRegion));
			}
			if(entitySpawner.isSetHomePositionOnSpawn()) {
				jsonobject.addProperty("setHomeOnSpawn", entitySpawner.isSetHomePositionOnSpawn());
				if(entitySpawner.isSetHomeRandom()) {
					jsonobject.addProperty("setHomeRandomly", entitySpawner.isSetHomeRandom());
				}
				jsonobject.addProperty("homeRadius", entitySpawner.getHomeRadius());
			}
			if(entitySpawner.getEntitySpawnTickRate() != defaultEntitySpawnTickRate) {
				jsonobject.addProperty("entitySpawnTickRate", entitySpawner.getEntitySpawnTickRate());
			}
			if(entitySpawner.getPlayerMinSpawnDistance() != defaultPlayerMinSpawnDistance || entitySpawner.getPlayerMaxSpawnDistance() != defaultPlayerMaxSpawnDistance) {
				jsonobject.addProperty("playerMinSpawnDistance", entitySpawner.getPlayerMinSpawnDistance());
				jsonobject.addProperty("playerMaxSpawnDistance", entitySpawner.getPlayerMaxSpawnDistance());
			}
			// Don't save spawnCap if not default or if single spawn type and cap is not equal to 0
			if(!entitySpawner.spawnType.isSingleSpawnType()) {
				jsonobject.addProperty("spawnCap", entitySpawner.getLocalSpawnCap());
			}
			// Don't save spawnCap if single spawn type
			if(entitySpawner.getPoolRolls() != 1) {
				jsonobject.addProperty("numRolls", entitySpawner.getPoolRolls());
			}
			if (!entitySpawner.entries.isEmpty()) {
				jsonobject.add("pools", context.serialize(entitySpawner.entries));
			}
			return jsonobject;
		}
	}

	public static class Builder {
		private List<ExtendedStructureEntitySpawnerEntry> entries = new ArrayList<>();
		private final SpawnerType spawnType;
		private boolean setHomePositionOnSpawn = false;
		private boolean setHomeRandom = false;
		private int homeRadius = defultHomeRadius;
		private int entitySpawnTickRate = defaultEntitySpawnTickRate;
		private int playerMinSpawnDistance = defaultPlayerMinSpawnDistance;
		private int playerMaxSpawnDistance = defaultPlayerMaxSpawnDistance;
		private ExtendedSpawnerBoundsEntry checkBoundaries = defaultBoundary;
		private ExtendedSpawnerBoundsEntry spawnRegion = defaultBoundary;
		private int poolRolls = 1;
		private int localSpawnCap = defaultLocalSpawnCap;

		public Builder(List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn, 
				boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
				ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion, int poolRolls, int localSpawnCap) {
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
			this.poolRolls = poolRolls;
			this.localSpawnCap = localSpawnCap;
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
	
		public ExtendedStructureEntitySpawner.Builder setPoolRolls(int rolls) {
			this.poolRolls = rolls;
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder setlocalSpawnCap(int cap) {
			this.localSpawnCap = cap;
			return this;
		}

		public ExtendedStructureEntitySpawner build(ResourceLocation id) {
			return new ExtendedStructureEntitySpawner(id, entries, spawnType, setHomePositionOnSpawn, setHomeRandom, homeRadius, 
					entitySpawnTickRate, playerMinSpawnDistance, playerMaxSpawnDistance, checkBoundaries, spawnRegion, poolRolls, localSpawnCap);
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
			entries.add(new ExtendedStructureEntitySpawnerEntry(new SingleSpawnType(type), weight, minSpawnCount, maxSpawnCount));
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder addMultiPool(MultiSpawnType multiPool, int weight, int minSpawnCount, int maxSpawnCount) {
			entries.add(new ExtendedStructureEntitySpawnerEntry(multiPool, weight, minSpawnCount, maxSpawnCount));
			return this;
		}
	}
}
