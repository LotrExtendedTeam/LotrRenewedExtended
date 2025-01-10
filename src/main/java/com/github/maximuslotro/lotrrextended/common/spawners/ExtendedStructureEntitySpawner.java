package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
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
import net.minecraft.util.WeightedRandom;

public class ExtendedStructureEntitySpawner {
	public static final ExtendedStructureEntitySpawner EMPTY = new ExtendedStructureEntitySpawner(ExtendedSpawners.EMPTY, new ArrayList<>(), SpawnerType.UNKNOWN, ExtendedSpawnerBoundsEntry.DEFAULT, ExtendedSpawnerBoundsEntry.DEFAULT);
	private static final int defaultEntitySpawnTickRate = 6000;
	private static final int defaultPlayerMinSpawnDistance = 15;
	private static final int defaultPlayerMaxSpawnDistance = 64;
	private static final int defultHomeRadius = 16;
	private static final int defaultLocalSpawnCap = 4;
	private final List<ExtendedStructureEntitySpawnerEntry> entries;
	private final ResourceLocation id;
	private final SpawnerType spawnType;
	private boolean setHomePositionOnSpawn = false;
	private boolean setHomeRandom = false;
	private int homeRadius = defultHomeRadius;
	private int entitySpawnTickRate = defaultEntitySpawnTickRate;
	private int playerMinSpawnDistance = defaultPlayerMinSpawnDistance;
	private int playerMaxSpawnDistance = defaultPlayerMaxSpawnDistance;
	private ExtendedSpawnerBoundsEntry checkBoundaries = ExtendedSpawnerBoundsEntry.DEFAULT;
	private ExtendedSpawnerBoundsEntry spawnRegion = ExtendedSpawnerBoundsEntry.DEFAULT;
	private int poolRolls = 1;
	private int localSpawnCap = defaultLocalSpawnCap;
	protected boolean isCustom = false;
	private boolean shouldDisableFutureSpawn = false;
	private int randomDiscardChance = 0;
	private boolean enableInsideSpawning = false;
	private int enableInsideSpawningChance = 100;

	private ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
		this.id = id;
		this.entries = entries;
		this.spawnType = spawnType;
		this.checkBoundaries = checkBoundaries;
		this.spawnRegion = spawnRegion;
	}

	public ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn,
			boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion, int poolRolls, int localSpawnCap, boolean shouldDisableFutureSpawn,
			int randomDiscardChance, boolean enableInsideSpawning, int enableInsideSpawningChance) {
		this(id, entries, spawnType, checkBoundaries, spawnRegion);
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
		this.setHomeRandom = setHomeRandom;
		this.homeRadius = homeRadius;
		this.entitySpawnTickRate = entitySpawnTickRate;
		this.playerMinSpawnDistance = playerMinSpawnDistance;
		this.playerMaxSpawnDistance = playerMaxSpawnDistance;
		this.poolRolls = poolRolls;
		this.localSpawnCap = localSpawnCap;
		this.shouldDisableFutureSpawn = shouldDisableFutureSpawn;
		this.randomDiscardChance = randomDiscardChance;
		this.enableInsideSpawning = enableInsideSpawning;
		this.enableInsideSpawningChance = enableInsideSpawningChance;
	}

	public ResourceLocation getId() {
		return id;
	}

	public boolean isEmptySpawner() {
		return this.equals(ExtendedStructureEntitySpawner.EMPTY) || id.equals(ExtendedSpawners.EMPTY);
	}

	public boolean shouldTick() {
		return !ExtendedServerConfig.enableDevMode.get() && !shouldDisableFutureSpawn;
	}

	public boolean isCustom() {
		return isCustom;
	}

	public List<ExtendedStructureEntitySpawnerEntry> getEntries() {
		return new ArrayList<>(entries);
	}

	public List<EntityType<?>> getSpawns(Random random) {
		return WeightedRandom.getRandomItem(random, entries).getSpawns(random);
	}

	public SpawnerType getSpawnType() {
		return spawnType;
	}

	public boolean isSetHomePositionOnSpawn() {
		return setHomePositionOnSpawn;
	}

	public void setSetHomePositionOnSpawn(boolean setHomePositionOnSpawn) {
		isCustom = true;
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
	}

	public boolean isSetHomeRandom() {
		return setHomeRandom;
	}

	public void setSetHomeRandom(boolean setHomeRandom) {
		isCustom = true;
		this.setHomeRandom = setHomeRandom;
	}

	public int getHomeRadius() {
		return homeRadius;
	}

	public boolean setHomeRadius(int homeRadius) {
		isCustom = true;
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
		isCustom = true;
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
		isCustom = true;
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
		isCustom = true;
		if(localSpawnCap<0) {
			this.localSpawnCap = defaultLocalSpawnCap;
			return false;
		}else {
			this.localSpawnCap = localSpawnCap;
			return true;
		}
	}

	public boolean isShouldDisableFutureSpawn() {
		return shouldDisableFutureSpawn;
	}

	public void setShouldDisableFutureSpawn(boolean shouldDisableFutureSpawn) {
		isCustom = true;
		this.shouldDisableFutureSpawn = shouldDisableFutureSpawn;
	}

	public boolean setRandomDiscardChance(int chance) {
		isCustom = true;
		if(chance<0) {
			this.randomDiscardChance = 0;
			return false;
		}else if(chance>100) {
			this.randomDiscardChance = 100;
			return false;
		}else {
			this.randomDiscardChance = chance;
			return true;
		}
	}

	public int getRandomDiscardChance() {
		return randomDiscardChance;
	}

	public boolean shouldCheckRandomDiscardChance() {
		return randomDiscardChance != 0;
	}

	// INSIDE SPAWNING CHANCES
	public boolean insideSpawning() {
		return enableInsideSpawning;
	}

	public void setInsideSpawn(boolean enableInsideSpawning) {
		isCustom = true;
		this.enableInsideSpawning = enableInsideSpawning;
	}

	public void setInsideSpawn(boolean enableInsideSpawning, int chance) {
		isCustom = true;
		this.enableInsideSpawning = enableInsideSpawning;
		this.enableInsideSpawningChance = chance;
	}

	public int getinsideSpawningChance() {
		return enableInsideSpawningChance;
	}

	public void setInsideSpawnChance(int chance) {
		isCustom = true;
		this.enableInsideSpawningChance = chance;
	}

	// ##################################### Player Distance Methods ###########################
	public int getPlayerMinSpawnDistance() {
		return playerMinSpawnDistance;
	}

	public boolean setPlayerMinSpawnDistance(int playerMinSpawnDistance) {
		isCustom = true;
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
		isCustom = true;
		if(playerMaxSpawnDistance<0) {
			this.playerMaxSpawnDistance = defaultPlayerMaxSpawnDistance;
			return false;
		}else {
			this.playerMaxSpawnDistance = playerMaxSpawnDistance;
			return true;
		}
	}

	public boolean setPlayerSpawnDistance(int playerMinSpawnDistance, int playerMaxSpawnDistance) {
		isCustom = true;
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
		isCustom = true;
		setCheckBoundaries(new ExtendedSpawnerBoundsEntry(topY, bottomY, horizontal));
	}

	public void setCheckBoundaries(ExtendedSpawnerBoundsEntry checkBoundaries) {
		isCustom = true;
		this.checkBoundaries = checkBoundaries;
	}

	public ExtendedSpawnerBoundsEntry getSpawnBoundaries() {
		return spawnRegion;
	}

	public void setSpawnArea(int topY, int bottomY, int horizontal) {
		isCustom = true;
		setSpawnArea(new ExtendedSpawnerBoundsEntry(topY, bottomY, horizontal));
	}

	public void setSpawnArea(ExtendedSpawnerBoundsEntry spawnBoundaries) {
		isCustom = true;
		this.spawnRegion = spawnBoundaries;
	}

	public void setAreas(int topYC, int bottomYC, int horizontalC, int topYS, int bottomYS, int horizontalS) {
		isCustom = true;
		setCheckBoundaries(new ExtendedSpawnerBoundsEntry(topYC, bottomYC, horizontalC));
		setSpawnArea(new ExtendedSpawnerBoundsEntry(topYS, bottomYS, horizontalS));
	}

	public static class Serializer implements JsonDeserializer<ExtendedStructureEntitySpawner>, JsonSerializer<ExtendedStructureEntitySpawner> {
		@Override
		public ExtendedStructureEntitySpawner deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedStructureEntitySpawner");
			List<ExtendedStructureEntitySpawnerEntry> alootpool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "pools", new ExtendedStructureEntitySpawnerEntry[0], context, ExtendedStructureEntitySpawnerEntry[].class));
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load structure spawner due to invalid id: %s!", id);
				return null;
			}
			if(alootpool.size()<1) {
				ExtendedLog.warn("Failed to load structure spawner due to spawner pool size less than 1: %s!", id);
				return null;
			}
			SpawnerType type = SpawnerType.bySpawnerName(jsonobject.get("spawner_type").getAsString());
			if(type==SpawnerType.UNKNOWN) {
				ExtendedLog.warn("Failed to load structure spawner %s due to invalid spawn type!", id);
				return null;
			}
			if(type==SpawnerType.SINGLE_FAMILY && alootpool.size()>1) {
				ExtendedLog.warn("Failed to load structure spawner %s due to single_family_spawner type spawners not allowing more than one entity pool!", id);
				return null;
			}
			if(type==SpawnerType.SINGLE_FAMILY && alootpool.size()==1 && alootpool.get(0).getSpawnerForTypeArguments() instanceof MultiSpawnType) {
				ExtendedLog.warn("Failed to load structure spawner %s due to single_family_spawner type not supporting multiSpawnTypes (entity_types), should only contain an (entity_type)!", id);
				return null;
			}
			ExtendedSpawnerBoundsEntry check = JSONUtils.getAsObject(jsonobject, "check_boundaries", ExtendedSpawnerBoundsEntry.DEFAULT, context, ExtendedSpawnerBoundsEntry.class);
			ExtendedSpawnerBoundsEntry spawn  = JSONUtils.getAsObject(jsonobject, "spawn_area", ExtendedSpawnerBoundsEntry.DEFAULT, context, ExtendedSpawnerBoundsEntry.class);
			ExtendedStructureEntitySpawner spawner = new ExtendedStructureEntitySpawner(new ResourceLocation(id), alootpool, type, check, spawn);
			if(jsonobject.has("setHomeOnSpawn") && jsonobject.get("setHomeOnSpawn").getAsBoolean()) {
				spawner.setSetHomePositionOnSpawn(true);
				if(!jsonobject.has("homeRadius")) {
					ExtendedLog.error("Loaded Structure Spawner with invalid settings! homeRadius must be set if setHomeOnSpawn is enabled. Ignoring spawner - %s", id);
					return null;
				} else if(!spawner.setHomeRadius(jsonobject.get("homeRadius").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid homeRadius set! Resetting to default! - %s", id);
				}
			}
			if(jsonobject.has("setHomeRandomly")) {
				if(!spawner.isSetHomePositionOnSpawn() || type==SpawnerType.SINGLE_FAMILY) {
					if(type==SpawnerType.SINGLE_FAMILY) {
						ExtendedLog.warn("Loaded Structure Spawner with invalid settings! Can't set setHomeRandomly if single_family_spawner type! - %s", id);
					}
					if(!spawner.isSetHomePositionOnSpawn()) {
						ExtendedLog.warn("Loaded Structure Spawner with invalid settings! Can't set setHomeRandomly if setHomeOnSpawn is not set! - %s", id);
					}
				}else {
					spawner.setSetHomeRandom(jsonobject.get("setHomeRandomly").getAsBoolean());
				}
			}
			if(jsonobject.has("entitySpawnTickRate")) {
				if(!spawner.setEntitySpawnTickRate(jsonobject.get("entitySpawnTickRate").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid entitySpawnTickRate set! Resetting to default! - %s", id);
				}
			}
			if(jsonobject.has("playerMinSpawnDistance") || jsonobject.has("playerMaxSpawnDistance")) {
				if(!jsonobject.has("playerMinSpawnDistance") || !jsonobject.has("playerMaxSpawnDistance")) {
					ExtendedLog.error("Loaded Structure Spawner with invalid playerSpawnDistances set! Both must be set if either are set! Ignoring spawner - %s", id);
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
			if(jsonobject.has("disable_for_use_in_future")) {
				spawner.setShouldDisableFutureSpawn(jsonobject.get("disable_for_use_in_future").getAsBoolean());
			}
			if(jsonobject.has("inside_spawning")) {
				spawner.setInsideSpawn(jsonobject.get("inside_spawning").getAsBoolean());
				if(!spawner.insideSpawning()) {
					ExtendedLog.info("Loaded Structure Spawner with weird inside_spawning configuration, value is false by default, no need to force this! - %s", id);
				}
				if(jsonobject.has("inside_spawning_chance")) {
					spawner.setInsideSpawnChance(jsonobject.get("inside_spawning_chance").getAsInt());
					if(spawner.getinsideSpawningChance()==100) {
						ExtendedLog.info("Loaded Structure Spawner with weird inside_spawning_chance configuration, value is 100 by default, no need to force this! - %s", id);
					}
				}
			}
			if(jsonobject.has("randomDiscardChance")) {
				if(!spawner.setRandomDiscardChance(jsonobject.get("randomDiscardChance").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid randomDiscardChance set! Resetting to default! - %s", id);
				}
			}
			// Set to false since above operations set it to true and only the below check can properly set it!
			spawner.isCustom = false;
			// If this flag is being triggered, the Spawners are loading custom spawner data, else this is the manager loading spawners
			if(jsonobject.has("customLoad")) {
				// Use json getter instead of setting directly in the case customLoad is not set to true (This should not happen unless people are doing weird things!)
				spawner.isCustom = jsonobject.get("customLoad").getAsBoolean();
			}
			return spawner;
		}

		@Override
		public JsonElement serialize(ExtendedStructureEntitySpawner entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", entitySpawner.getId().toString());
			jsonobject.addProperty("spawner_type", entitySpawner.spawnType.getString());
			if(entitySpawner.isShouldDisableFutureSpawn()) {
				jsonobject.addProperty("disable_for_use_in_future", true);
			}
			if(entitySpawner.getCheckBoundaries() != ExtendedSpawnerBoundsEntry.DEFAULT) {
				jsonobject.add("check_boundaries", context.serialize(entitySpawner.checkBoundaries));
			}
			if(entitySpawner.getSpawnBoundaries() != ExtendedSpawnerBoundsEntry.DEFAULT) {
				jsonobject.add("spawn_area", context.serialize(entitySpawner.spawnRegion));
			}
			if(entitySpawner.insideSpawning()) {
				jsonobject.addProperty("inside_spawning", entitySpawner.insideSpawning());
				if(entitySpawner.getinsideSpawningChance()!=100) {
					jsonobject.addProperty("inside_spawning_chance", entitySpawner.getinsideSpawningChance());
				}
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
			if(!entitySpawner.getSpawnType().isSingleSpawnType()) {
				jsonobject.addProperty("spawnCap", entitySpawner.getLocalSpawnCap());
			}
			// Add flag for Spawners saving custom settings to be treated as custom when loading again, should never be set in Json!
			if(entitySpawner.isCustom()) {
				jsonobject.addProperty("customLoad", true);
			}
			if(entitySpawner.shouldCheckRandomDiscardChance()) {
				jsonobject.addProperty("randomDiscardChance", entitySpawner.getRandomDiscardChance());
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
		private ExtendedSpawnerBoundsEntry checkBoundaries = ExtendedSpawnerBoundsEntry.DEFAULT;
		private ExtendedSpawnerBoundsEntry spawnRegion = ExtendedSpawnerBoundsEntry.DEFAULT;
		private int poolRolls = 1;
		private int localSpawnCap = defaultLocalSpawnCap;
		private boolean shouldDisableFutureSpawn = false;
		private int randomDiscardChance = 0;
		private boolean enableInsideSpawning = false;
		private int enableInsideSpawningChance = 100;

		public Builder(List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn,
				boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
				ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion, int poolRolls, int localSpawnCap, boolean shouldDisableFutureSpawn,
				int randomDiscardChance, boolean enableInsideSpawning, int enableInsideSpawningChance) {
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
			this.shouldDisableFutureSpawn = shouldDisableFutureSpawn;
			this.randomDiscardChance = randomDiscardChance;
			this.enableInsideSpawning = enableInsideSpawning;
			this.enableInsideSpawningChance = enableInsideSpawningChance;
		}

		private Builder(SpawnerType spawnType) {
			this.spawnType = spawnType;
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

		public ExtendedStructureEntitySpawner.Builder setRandomDiscardChance(int chance) {
			this.randomDiscardChance = chance;
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder disableForFutureSpawning() {
			this.shouldDisableFutureSpawn = true;
			return this;
		}

		public ExtendedStructureEntitySpawner.Builder enableNonRandomSpawning() {
			this.enableInsideSpawning = true;
			this.enableInsideSpawningChance = 100;
			return this;
		}
		public ExtendedStructureEntitySpawner.Builder enableNonRandomSpawning(int chance) {
			this.enableInsideSpawning = true;
			this.enableInsideSpawningChance = chance;
			return this;
		}

		public ExtendedStructureEntitySpawner build(ResourceLocation id, boolean isCustom) {
			ExtendedStructureEntitySpawner spawner = new ExtendedStructureEntitySpawner(id, entries, spawnType, setHomePositionOnSpawn, setHomeRandom, homeRadius,
					entitySpawnTickRate, playerMinSpawnDistance, playerMaxSpawnDistance, checkBoundaries, spawnRegion, poolRolls, localSpawnCap, shouldDisableFutureSpawn,
					randomDiscardChance, enableInsideSpawning, enableInsideSpawningChance);
			spawner.isCustom = isCustom;
			return spawner;
		}

		public ExtendedStructureEntitySpawner save(Consumer<ExtendedStructureEntitySpawner> pConsumer, String pId) {
			return save(pConsumer, new ResourceLocation(pId));
		}

		public ExtendedStructureEntitySpawner save(Consumer<ExtendedStructureEntitySpawner> pConsumer, ResourceLocation pId) {
			ExtendedStructureEntitySpawner spawner = this.build(pId, false);
			pConsumer.accept(spawner);
			return spawner;
		}

		public static ExtendedStructureEntitySpawner.Builder spawner(SpawnerType spawnType) {
			return new ExtendedStructureEntitySpawner.Builder(spawnType);
		}

		public static ExtendedStructureEntitySpawner.Builder spawner(SpawnerType spawnType, boolean setHomePositionOnSpawn, int homeRadius) {
			return new ExtendedStructureEntitySpawner.Builder(spawnType).setHomeInfo(setHomePositionOnSpawn, homeRadius);
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
