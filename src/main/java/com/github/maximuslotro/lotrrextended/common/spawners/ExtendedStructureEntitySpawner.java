package com.github.maximuslotro.lotrrextended.common.spawners;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.serialization.ExtendedSerialization;
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
import net.minecraft.util.math.vector.Vector3f;

/**
 * Represents an extended entity spawner configuration within a structure,
 * supporting detailed control over spawning behavior, boundaries, timing,
 * player distance constraints, and persistence.
 * <p>
 * Each spawner has a unique identifier, a list of spawn entries (entity pools),
 * and customizable settings for home position, spawn tick rate, spawn boundaries,
 * local caps, and random discard chances.
 * <p>
 * This class supports distinguishing default (empty) spawners from customized
 * ones, and provides extensive setters that mark the spawner as custom when
 * changes are made.
 */
public class ExtendedStructureEntitySpawner {
	/**
	 * A constant representing an empty/default spawner with no entries and unknown type.
	 */
	public static final ExtendedStructureEntitySpawner EMPTY = new ExtendedStructureEntitySpawner(LotrExtendedMod.EMPTY, new ArrayList<>(), SpawnerType.UNKNOWN, ExtendedSpawnerBoundsEntry.DEFAULT, ExtendedSpawnerBoundsEntry.DEFAULT);
	/** Default tick rate (in game ticks) for spawning entities (6000 ticks). */
	private static final int defaultEntitySpawnTickRate = 6000;
	/** Default minimum player distance to trigger spawns (15 blocks). */
	private static final int defaultPlayerMinSpawnDistance = 15;
	/** Default maximum player distance to allow spawns (64 blocks). */
	private static final int defaultPlayerMaxSpawnDistance = 64;
	/** Default home radius around spawn home position (16 blocks). */
	private static final int defultHomeRadius = 16;
	/** Default local spawn cap for entities spawned by this spawner (4). */
	private static final int defaultLocalSpawnCap = 4;
	/** List of entity spawn entries (pools) associated with this spawner. */
	private final List<ExtendedStructureEntitySpawnerEntry> entries;
	/** Unique identifier for this spawner. */
	private final ResourceLocation id;
	/** Defines the type of spawner (e.g., monster, ambient). */
	private final SpawnerType spawnType;
	/** Whether to set a home position when an entity spawns. */
	private boolean setHomePositionOnSpawn = false;
	/** Whether to randomize the home position on spawn. */
	private boolean setHomeRandom = false;
	/** Radius around home position entities consider their "home". */
	private int homeRadius = defultHomeRadius;
	/** Tick rate between entity spawn attempts. */
	private int entitySpawnTickRate = defaultEntitySpawnTickRate;
	/** Minimum player distance required for spawning to occur. */
	private int playerMinSpawnDistance = defaultPlayerMinSpawnDistance;
	/** Maximum player distance allowed for spawning to occur. */
	private int playerMaxSpawnDistance = defaultPlayerMaxSpawnDistance;
	/** Boundaries checked before spawning (vertical and horizontal limits). */
	private ExtendedSpawnerBoundsEntry checkBoundaries = ExtendedSpawnerBoundsEntry.DEFAULT;
	/** Region boundaries within which spawning is allowed. */
	private ExtendedSpawnerBoundsEntry spawnRegion = ExtendedSpawnerBoundsEntry.DEFAULT;
	/** Number of attempts (rolls) to select spawn pools per spawn event. */
	private int poolRolls = 1;
	/** Cap on the number of entities spawned locally by this spawner. */
	private int localSpawnCap = defaultLocalSpawnCap;
	/** Whether this spawner configuration has been customized from defaults. */
	protected boolean isCustom = false;
	/** Flag indicating if future spawns should be disabled. */
	private boolean shouldDisableFutureSpawn = false;
	/** Chance (percentage) to randomly discard a spawn attempt. */
	private int randomDiscardChance = 0;
	/** Enables spawning inside structures or defined inside areas. */
	private boolean enableInsideSpawning = false;
	/** Chance (percentage) that inside spawning occurs when enabled. */
	private int enableInsideSpawningChance = 100;
	/** Whether persistence is required for spawned entities. */
	private boolean isPersistenceRequired = true;
	/** Whether entities should respawn upon death. */
	private boolean shouldRespawnOnDeath = false;
	/** How much to offset spawning bounding box, use for larger entities like horses. */
	private Vector3f bbInflateFactor = null;
	/**
	 * Constructs a new spawner with given core parameters.
	 * 
	 * @param id Unique identifier for this spawner.
	 * @param entries List of spawn entries (entity pools).
	 * @param spawnType Type of spawner.
	 * @param checkBoundaries Boundaries checked before spawning.
	 * @param spawnRegion Region within which spawning is allowed.
	 */
	private ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType,
		ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion) {
		this.id = id;
		this.entries = entries;
		this.spawnType = spawnType;
		this.checkBoundaries = checkBoundaries;
		this.spawnRegion = spawnRegion;
	}

	/**
	 * Constructs a fully configured spawner with detailed parameters.
	 *
	 * @param id Unique identifier.
	 * @param entries List of spawn entries.
	 * @param spawnType Spawner type.
	 * @param setHomePositionOnSpawn Whether to set home position on spawn.
	 * @param setHomeRandom Whether home position is randomized.
	 * @param homeRadius Radius around home position.
	 * @param entitySpawnTickRate Tick rate between spawns.
	 * @param playerMinSpawnDistance Minimum player distance for spawning.
	 * @param playerMaxSpawnDistance Maximum player distance for spawning.
	 * @param checkBoundaries Boundaries to check for spawning.
	 * @param spawnRegion Spawn region boundaries.
	 * @param poolRolls Number of pool rolls per spawn.
	 * @param localSpawnCap Local spawn cap.
	 * @param shouldDisableFutureSpawn Flag to disable future spawning.
	 * @param randomDiscardChance Chance to randomly discard spawns.
	 * @param enableInsideSpawning Enables inside spawning.
	 * @param enableInsideSpawningChance Chance of inside spawning.
	 * @param isPersistenceRequired Whether persistence is required.
	 * @param shouldRespawnOnDeath Whether to respawn on death.
	 * @param bbInflateFactor The bounding box offset for larger entities
	 */
	public ExtendedStructureEntitySpawner(ResourceLocation id, List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn,
		boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
		ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion, int poolRolls, int localSpawnCap, boolean shouldDisableFutureSpawn,
		int randomDiscardChance, boolean enableInsideSpawning, int enableInsideSpawningChance, boolean isPersistenceRequired,boolean shouldRespawnOnDeath,
		@Nullable Vector3f bbInflateFactor) {
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
		this.isPersistenceRequired = isPersistenceRequired;
		this.shouldRespawnOnDeath = shouldRespawnOnDeath;
		this.bbInflateFactor = bbInflateFactor;
	}
	/**
	 * Returns the unique identifier of this spawner.
	 * 
	 * @return ResourceLocation identifier.
	 */
	public ResourceLocation getId() {
		return id;
	}

	/**
	 * Checks if this spawner is the empty/default spawner.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmptySpawner() {
		return this.equals(ExtendedStructureEntitySpawner.EMPTY) || id.equals(LotrExtendedMod.EMPTY);
	}

	/**
	 * Determines if this spawner should perform ticking (spawn attempts).
	 * Returns false if developer mode is enabled or spawning is disabled.
	 * 
	 * @return true if ticking is enabled, false otherwise.
	 */
	public boolean shouldTick() {
		return !ExtendedServerConfig.enableDevMode.get() && !shouldDisableFutureSpawn;
	}
	/**
	 * Returns whether this spawner has been customized.
	 *
	 * @return true if customized, false otherwise.
	 */
	public boolean isCustom() {
		return isCustom;
	}
	/**
	 * Returns a defensive copy of the entity spawn entries associated with this spawner.
	 * 
	 * @return List of ExtendedStructureEntitySpawnerEntry.
	 */
	public List<ExtendedStructureEntitySpawnerEntry> getEntries() {
		return new ArrayList<>(entries);
	}
	/**
	 * Selects and returns a list of entity types to spawn based on weighted random selection
	 * from the spawner entries.
	 * 
	 * @param random Random instance for selection.
	 * @return List of EntityType to spawn.
	 */
	public List<EntityType<?>> getSpawns(Random random) {
		return WeightedRandom.getRandomItem(random, entries).getSpawns(random);
	}
	/**
	 * Returns the spawner type.
	 *
	 * @return SpawnerType enum.
	 */
	public SpawnerType getSpawnType() {
		return spawnType;
	}
	/**
	 * Returns whether the home position is set on spawn.
	 *
	 * @return true if setHomePositionOnSpawn is enabled.
	 */
	public boolean isSetHomePositionOnSpawn() {
		return setHomePositionOnSpawn;
	}
	/**
	 * Sets whether to set home position on spawn.
	 * Marks the spawner as custom.
	 *
	 * @param setHomePositionOnSpawn boolean flag.
	 */
	public void setSetHomePositionOnSpawn(boolean setHomePositionOnSpawn) {
		isCustom = true;
		this.setHomePositionOnSpawn = setHomePositionOnSpawn;
	}
	/**
	 * Returns whether home position is randomized on spawn.
	 *
	 * @return true if setHomeRandom is enabled.
	 */
	public boolean isSetHomeRandom() {
		return setHomeRandom;
	}
	/**
	 * Sets whether to randomize home position on spawn.
	 * Marks the spawner as custom.
	 *
	 * @param setHomeRandom boolean flag.
	 */
	public void setSetHomeRandom(boolean setHomeRandom) {
		isCustom = true;
		this.setHomeRandom = setHomeRandom;
	}
	/**
	 * Returns the home radius around the home position.
	 *
	 * @return home radius in blocks.
	 */
	public int getHomeRadius() {
		return homeRadius;
	}
	/**
	 * Sets the home radius, must be &gt;= 1; if invalid, resets to default.
	 * Marks the spawner as custom.
	 *
	 * @param homeRadius radius value.
	 * @return true if valid and set; false if reset to default.
	 */
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
	/**
	 * Returns the tick rate between entity spawns.
	 *
	 * @return spawn tick rate in game ticks.
	 */
	public int getEntitySpawnTickRate() {
		return entitySpawnTickRate;
	}
	/**
	 * Sets the tick rate for spawning entities, must be &gt;= 1; resets to default if invalid.
	 * Marks the spawner as custom.
	 *
	 * @param entitySpawnTickRate tick rate value.
	 * @return true if valid and set; false if reset.
	 */
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
	/**
	 * Returns the number of pool rolls per spawn event.
	 *
	 * @return pool rolls count.
	 */
	public int getPoolRolls() {
		return poolRolls;
	}
	/**
	 * Sets the number of pool rolls, must be &gt;= 1; resets to 1 if invalid.
	 * Marks the spawner as custom.
	 *
	 * @param poolRolls number of rolls.
	 * @return true if valid and set; false if reset.
	 */
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
	/**
	 * Returns the local spawn cap for this spawner.
	 *
	 * @return local spawn cap.
	 */
	public int getLocalSpawnCap() {
		return localSpawnCap;
	}
	/**
	 * Sets the local spawn cap, must be &gt;= 0; resets to default if invalid.
	 * Marks the spawner as custom.
	 *
	 * @param localSpawnCap cap value.
	 * @return true if valid and set; false if reset.
	 */
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
	/**
	 * Returns whether future spawning is disabled.
	 *
	 * @return true if disabled.
	 */
	public boolean isShouldDisableFutureSpawn() {
		return shouldDisableFutureSpawn;
	}
	/**
	 * Sets whether to disable future spawning.
	 * Marks the spawner as custom.
	 *
	 * @param shouldDisableFutureSpawn true to disable future spawning.
	 */
	public void setShouldDisableFutureSpawn(boolean shouldDisableFutureSpawn) {
		isCustom = true;
		this.shouldDisableFutureSpawn = shouldDisableFutureSpawn;
	}
	/**
	 * Sets the chance to randomly discard spawn attempts, specified as a percentage (0–100).
	 * <p>
	 * If the provided value is less than 0, it is clamped to 0.  
	 * If the value is greater than 100, it is clamped to 100.  
	 * In both clamping cases, the method returns {@code false}.
	 * </p>
	 * <p>
	 * This method also flags the spawner as custom.
	 * </p>
	 *
	 * @param chance The discard chance percentage (expected range: 0–100).
	 * @return {@code true} if the input was within the valid range (0–100); {@code false} if it was clamped.
	 */
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
	/**
	 * Returns the chance (percentage 0-100) to randomly discard a spawn attempt.
	 *
	 * @return random discard chance as an integer percentage.
	 */
	public int getRandomDiscardChance() {
		return randomDiscardChance;
	}

	/**
	 * Checks whether the random discard chance is active (non-zero).
	 *
	 * @return true if random discard chance is not zero; false otherwise.
	 */
	public boolean shouldCheckRandomDiscardChance() {
		return randomDiscardChance != 0;
	}

	/**
	 * Returns whether inside spawning (spawning inside certain bounds) is enabled.
	 *
	 * @return true if inside spawning is enabled.
	 */
	public boolean insideSpawning() {
		return enableInsideSpawning;
	}
	/**
	 * Enables or disables inside spawning.
	 * Marks the spawner as custom.
	 *
	 * @param enableInsideSpawning true to enable inside spawning.
	 */
	public void setInsideSpawn(boolean enableInsideSpawning) {
		isCustom = true;
		this.enableInsideSpawning = enableInsideSpawning;
	}
	/**
	 * Enables or disables inside spawning and sets its chance.
	 * Marks the spawner as custom.
	 *
	 * @param enableInsideSpawning whether to enable inside spawning.
	 * @param chance the chance percentage (0-100) for inside spawning attempts.
	 */
	public void setInsideSpawn(boolean enableInsideSpawning, int chance) {
		isCustom = true;
		this.enableInsideSpawning = enableInsideSpawning;
		this.enableInsideSpawningChance = chance;
	}
	/**
	 * Returns the chance (0-100) for inside spawning attempts.
	 *
	 * @return chance as integer percentage.
	 */
	public int getinsideSpawningChance() {
		return enableInsideSpawningChance;
	}
	/**
	 * Sets the chance (0-100) that inside spawning attempts will occur.
	 * Marks the spawner as custom.
	 *
	 * @param chance the chance percentage for inside spawning.
	 */
	public void setInsideSpawnChance(int chance) {
		isCustom = true;
		this.enableInsideSpawningChance = chance;
	}
	/**
	 * Returns whether persistence is required for spawned entities.
	 *
	 * @return true if persistence is required.
	 */
	public boolean isPersistenceRequired() {
		return isPersistenceRequired;
	}
	/**
	 * Sets whether persistence is required for spawned entities.
	 * Marks the spawner as custom.
	 *
	 * @param isPersistenceRequired true to require persistence.
	 */
	public void setPersistenceRequired(boolean isPersistenceRequired) {
		isCustom = true;
		this.isPersistenceRequired = isPersistenceRequired;
	}
	/**
	 * Returns whether the spawner should respawn entities upon their death.
	 *
	 * @return true if respawn on death is enabled.
	 */
	public boolean shouldRespawnOnDeath() {
		return shouldRespawnOnDeath;
	}
	/**
	 * Sets whether the spawner should respawn entities on their death.
	 * Marks the spawner as custom.
	 *
	 * @param shouldRespawnOnDeath true to enable respawning on death.
	 */
	public void setRespawnOnDeath(boolean shouldRespawnOnDeath) {
		isCustom = true;
		this.shouldRespawnOnDeath = shouldRespawnOnDeath;
	}

	// ##################################### Player Distance Methods ###########################
	/**
	 * Returns the minimum player distance required for spawning.
	 *
	 * @return minimum player spawn distance in blocks.
	 */
	public int getPlayerMinSpawnDistance() {
		return playerMinSpawnDistance;
	}
	/**
	 * Sets the minimum player distance required for spawning.
	 * Marks the spawner as custom.
	 *
	 * @param playerMinSpawnDistance minimum distance in blocks.
	 * @return true if valid and set; false if reset to default.
	 */
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
	/**
	 * Returns the maximum player distance allowed for spawning.
	 *
	 * @return maximum player spawn distance in blocks.
	 */
	public int getPlayerMaxSpawnDistance() {
		return playerMaxSpawnDistance;
	}

	/**
	 * Sets the maximum player distance allowed for spawning.
	 * Marks the spawner as custom.
	 *
	 * @param playerMaxSpawnDistance maximum distance in blocks.
	 * @return true if valid and set; false if reset to default.
	 */
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
	/**
	 * Sets the player minimum and maximum spawn distances simultaneously.
	 * Marks the spawner as custom.
	 *
	 * @param playerMinSpawnDistance minimum player distance required for spawning.
	 * @param playerMaxSpawnDistance maximum player distance allowed for spawning.
	 * @return true if both distances are valid and set; false if either resets to default.
	 */
	public boolean setPlayerSpawnDistance(int playerMinSpawnDistance, int playerMaxSpawnDistance) {
		isCustom = true;
		if(!setPlayerMinSpawnDistance(playerMinSpawnDistance) || !setPlayerMaxSpawnDistance(playerMaxSpawnDistance)) {
			return false;
		}
		return true;
	}

	// ##################################### Boundaries ##########################################
	/**
	 * Returns the boundaries that should be checked before spawning entities.
	 *
	 * @return the check boundaries configuration.
	 */
	public ExtendedSpawnerBoundsEntry getCheckBoundaries() {
		return checkBoundaries;
	}
	/**
	 * Sets the check boundaries used before spawning entities.
	 * Marks the spawner as custom.
	 *
	 * @param topY Top Y coordinate of check boundaries.
	 * @param bottomY Bottom Y coordinate of check boundaries.
	 * @param horizontal Horizontal radius for check boundaries.
	 */
	public void setCBoundaries(int topY, int bottomY, int horizontal) {
		isCustom = true;
		setCheckBoundaries(new ExtendedSpawnerBoundsEntry(topY, bottomY, horizontal));
	}
	/**
	 * Sets the boundaries to check before spawning entities.
	 * Marks the spawner as custom.
	 *
	 * @param checkBoundaries boundaries to check.
	 */
	public void setCheckBoundaries(ExtendedSpawnerBoundsEntry checkBoundaries) {
		isCustom = true;
		this.checkBoundaries = checkBoundaries;
	}
	/**
	 * Returns the spawn region boundaries within which entities can spawn.
	 *
	 * @return spawn region boundaries.
	 */
	public ExtendedSpawnerBoundsEntry getSpawnBoundaries() {
		return spawnRegion;
	}
	/**
	 * Sets the spawn boundaries used before spawning entities.
	 * Marks the spawner as custom.
	 *
	 * @param topY Top Y coordinate of spawn boundaries.
	 * @param bottomY Bottom Y coordinate of spawn boundaries.
	 * @param horizontal Horizontal radius for spawn boundaries.
	 */
	public void setSpawnArea(int topY, int bottomY, int horizontal) {
		isCustom = true;
		setSpawnArea(new ExtendedSpawnerBoundsEntry(topY, bottomY, horizontal));
	}
	/**
	 * Sets the spawn region boundaries.
	 * Marks the spawner as custom.
	 *
	 * @param spawnBoundaries spawn region boundaries.
	 */
	public void setSpawnArea(ExtendedSpawnerBoundsEntry spawnBoundaries) {
		isCustom = true;
		this.spawnRegion = spawnBoundaries;
	}
	/**
	 * Sets both the check boundaries and spawn region boundaries at once.
	 * Marks the spawner as custom.
	 *
	 * @param topYC Top Y coordinate for check boundaries.
	 * @param bottomYC Bottom Y coordinate for check boundaries.
	 * @param horizontalC Horizontal boundary for check boundaries.
	 * @param topYS Top Y coordinate for spawn region.
	 * @param bottomYS Bottom Y coordinate for spawn region.
	 * @param horizontalS Horizontal boundary for spawn region.
	 */
	public void setAreas(int topYC, int bottomYC, int horizontalC, int topYS, int bottomYS, int horizontalS) {
		isCustom = true;
		setCheckBoundaries(new ExtendedSpawnerBoundsEntry(topYC, bottomYC, horizontalC));
		setSpawnArea(new ExtendedSpawnerBoundsEntry(topYS, bottomYS, horizontalS));
	}
	/** 
	 * Gets the bounding box offset an entity can be spawned with
	 * Marks the spawner as custom.
	 * 
	 * @return A float based vector offset
	 */
	@Nullable
	public Vector3f getBBInflateFactor() {
		return bbInflateFactor;
	}
	/** 
	 * Gets the bounding box offset an entity can be spawned with
	 * Marks the spawner as custom.
	 * 
	 * @param bbInflateFactor The bounding box offset for the spawner
	 */
	public void setSpawnBBOffset(@Nullable Vector3f bbInflateFactor) {
		isCustom = true;
		this.bbInflateFactor = bbInflateFactor;
	}
	/**
	 * Handles JSON serialization and deserialization for {@link ExtendedStructureEntitySpawner} objects.
	 * 
	 * <p>This class implements both {@link JsonDeserializer} and {@link JsonSerializer} interfaces to
	 * convert between JSON representations and {@link ExtendedStructureEntitySpawner} instances.
	 * 
	 * <p>The deserialization process performs extensive validation on the JSON structure, including:
	 * <ul>
	 *   <li>Verifying the presence and format of required fields such as "id" and "spawner_type".</li>
	 *   <li>Checking constraints specific to the {@link SpawnerType}, such as limits on entity pools and multi-spawn types.</li>
	 *   <li>Validating configuration flags like home position settings, spawn distances, persistence, and spawn rates.</li>
	 *   <li>Logging warnings or errors using {@link ExtendedLog} when invalid or inconsistent data is encountered.</li>
	 * </ul>
	 * 
	 * <p>The serialization method converts an {@link ExtendedStructureEntitySpawner} instance back into JSON,
	 * including only relevant properties and respecting default values to minimize JSON size.
	 */
	public static class Serializer implements JsonDeserializer<ExtendedStructureEntitySpawner>, JsonSerializer<ExtendedStructureEntitySpawner> {
		/**
		 * Deserializes JSON into an {@link ExtendedStructureEntitySpawner} instance.
		 * 
		 * <p>This method parses the JSON structure, extracts required fields, validates constraints,
		 * and configures the resulting spawner accordingly.
		 * 
		 * @param json the JSON element to deserialize
		 * @param typeOfT the target type (expected to be {@code ExtendedStructureEntitySpawner.class})
		 * @param context the deserialization context used for nested objects
		 * @return the deserialized {@link ExtendedStructureEntitySpawner} or {@code null} if validation fails
		 * @throws JsonParseException if JSON is malformed or unexpected types are encountered
		 */
		@Override
		public ExtendedStructureEntitySpawner deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
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
			if(jsonobject.has("persistenceRequired")) {
				spawner.isPersistenceRequired = jsonobject.get("persistenceRequired").getAsBoolean();
			}
			if(jsonobject.has("shouldRespawnOnDeath")) {
				spawner.shouldRespawnOnDeath = jsonobject.get("shouldRespawnOnDeath").getAsBoolean();
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
					ExtendedLog.warn("Loaded Structure Spawner with weird inside_spawning configuration, value is false by default, no need to force this! - %s", id);
				}
				if(jsonobject.has("inside_spawning_chance")) {
					spawner.setInsideSpawnChance(jsonobject.get("inside_spawning_chance").getAsInt());
					if(spawner.getinsideSpawningChance()==100) {
						ExtendedLog.warn("Loaded Structure Spawner with weird inside_spawning_chance configuration, value is 100 by default, no need to force this! - %s", id);
					}
				}
			}
			if(jsonobject.has("randomDiscardChance")) {
				if(!spawner.setRandomDiscardChance(jsonobject.get("randomDiscardChance").getAsInt())) {
					ExtendedLog.warn("Loaded Structure Spawner with invalid randomDiscardChance set! Resetting to default! - %s", id);
				}
			}
			if(jsonobject.has("bbInflateFactor")) {
				spawner.setSpawnBBOffset(ExtendedSerialization.Vector3fSerializer.fromJson(jsonobject.get("bbInflateFactor")));
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

		/**
		 * Serializes an {@link ExtendedStructureEntitySpawner} instance into JSON.
		 * 
		 * <p>This method converts the spawner's state into a JSON representation, including
		 * optional fields only when they differ from defaults or when explicitly set.
		 * 
		 * @param entitySpawner the {@link ExtendedStructureEntitySpawner} to serialize
		 * @param typeOfSrc the runtime type of the source object
		 * @param context the serialization context used for nested objects
		 * @return a {@link JsonElement} representing the serialized spawner
		 */
		@Override
		public JsonElement serialize(ExtendedStructureEntitySpawner entitySpawner, Type typeOfSrc, JsonSerializationContext context) {
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
			if(entitySpawner.getBBInflateFactor()!=null) {
				jsonobject.add("bbInflateFactor", ExtendedSerialization.Vector3fSerializer.toJson(entitySpawner.getBBInflateFactor()));
			}
			jsonobject.addProperty("persistenceRequired", entitySpawner.isPersistenceRequired());
			if(entitySpawner.shouldRespawnOnDeath()) {
				jsonobject.addProperty("shouldRespawnOnDeath", entitySpawner.shouldRespawnOnDeath());
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

	/**
	 * A builder class for constructing {@link ExtendedStructureEntitySpawner} instances with customized configuration.
	 * <p>
	 * This builder supports configuring spawn behavior such as timing, spawn region, entity pool, home settings,
	 * player proximity rules, and more. Use the fluent methods provided to chain configuration settings and
	 * call {@code build()} or {@code save()} to create and optionally store the final spawner.
	 */
	public static class Builder {
		/**
		 * A list of spawn entries defining the entity types and their spawn characteristics.
		 */
		private List<ExtendedStructureEntitySpawnerEntry> entries = new ArrayList<>();
		/**
		 * Type of the spawner being built (e.g., STRUCTURE, NATURAL, etc.).
		 */
		private final SpawnerType spawnType;
		/**
		 * If true, entities spawned will have a home position set.
		 */
		private boolean setHomePositionOnSpawn = false;
		/**
		 * If true, home positions are randomized within the specified home radius.
		 */
		private boolean setHomeRandom = false;
		/**
		 * Radius used when setting the home position.
		 */
		private int homeRadius = defultHomeRadius;
		/**
		 * The number of ticks between each spawn attempt.
		 */
		private int entitySpawnTickRate = defaultEntitySpawnTickRate;
		/**
		 * Minimum distance from player for the spawner to activate.
		 */
		private int playerMinSpawnDistance = defaultPlayerMinSpawnDistance;
		/**
		 * Maximum distance from player for the spawner to activate.
		 */
		private int playerMaxSpawnDistance = defaultPlayerMaxSpawnDistance;
		/**
		 * Defines the outer boundaries where the spawner checks for valid spawning.
		 */
		private ExtendedSpawnerBoundsEntry checkBoundaries = ExtendedSpawnerBoundsEntry.DEFAULT;
		/**
		 * Defines the actual region within which entities can spawn.
		 */
		private ExtendedSpawnerBoundsEntry spawnRegion = ExtendedSpawnerBoundsEntry.DEFAULT;
		/**
		 * Number of spawn pool rolls the spawner will attempt per activation.
		 */
		private int poolRolls = 1;
		/**
		 * The maximum number of active entities this spawner can maintain at one time.
		 */
		private int localSpawnCap = defaultLocalSpawnCap;
		/**
		 * If true, disables spawning for future uses (e.g., when entities not yet added).
		 */
		private boolean shouldDisableFutureSpawn = false;
		/**
		 * Chance (0–100%) that a spawn attempt is skipped.
		 */
		private int randomDiscardChance = 0;
		/**
		 * If true, forces spawning directly at the spawner's location.
		 */
		private boolean enableInsideSpawning = false;
		/**
		 * Chance (0–100%) for a spawn to occur at the exact spawner position.
		 */
		private int enableInsideSpawningChance = 100;
		/**
		 * If true, entities spawned are marked as persistent and won't despawn.
		 */
		private boolean isPersistenceRequired = true;
		/**
		 * If true, the entity will respawn after death. Typically used for specific roles like Traders or Captains.
		 */
		private boolean shouldRespawnOnDeath = false;
		/** 
		 * How much to offset spawning bounding box, use for larger entities like horses. 
		 */
		private Vector3f bbInflateFactor = null;
		/**
		 * Full constructor for manual instantiation of the Builder with all configuration options.
		 *
		 * @param entries List of spawn entries defining entities and their spawn conditions.
		 * @param spawnType Type of spawner controlling spawn behavior.
		 * @param setHomePositionOnSpawn Whether to set a home position for the spawned entity on spawn.
		 * @param setHomeRandom Whether the home position should be randomized within the home radius.
		 * @param homeRadius Radius around the home position in which the entity will remain.
		 * @param entitySpawnTickRate Number of ticks between spawn attempts.
		 * @param playerMinSpawnDistance Minimum distance from player required for spawning.
		 * @param playerMaxSpawnDistance Maximum distance from player allowed for spawning.
		 * @param checkBoundaries Boundaries to check for spawn validity before spawning.
		 * @param spawnRegion Region boundaries where spawning is allowed.
		 * @param poolRolls Number of spawn attempts per tick or cycle.
		 * @param localSpawnCap Maximum number of entities allowed locally before spawning stops.
		 * @param shouldDisableFutureSpawn Whether spawning should be disabled after this spawn cycle.
		 * @param randomDiscardChance Chance (0-100) to randomly discard spawn attempts.
		 * @param enableInsideSpawning Whether spawning inside the spawn region is enabled.
		 * @param enableInsideSpawningChance Chance (0-100) that inside spawning is allowed.
		 * @param isPersistenceRequired Whether spawned entities should persist (not despawn).
		 * @param shouldRespawnOnDeath Whether entities should respawn after death.
		 * @param spawnOffset The offset that can be applied to entities on spawn
		 * @param bbInflateFactor The bounding box offset that should be applied
		 */
		public Builder(List<ExtendedStructureEntitySpawnerEntry> entries, SpawnerType spawnType, boolean setHomePositionOnSpawn,
			boolean setHomeRandom, int homeRadius, int entitySpawnTickRate, int playerMinSpawnDistance, int playerMaxSpawnDistance,
			ExtendedSpawnerBoundsEntry checkBoundaries, ExtendedSpawnerBoundsEntry spawnRegion, int poolRolls, int localSpawnCap, 
			boolean shouldDisableFutureSpawn, int randomDiscardChance, boolean enableInsideSpawning, int enableInsideSpawningChance, 
			boolean isPersistenceRequired, boolean shouldRespawnOnDeath, @Nullable Vector3f spawnOffset, @Nullable Vector3f bbInflateFactor) {
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
			this.isPersistenceRequired = isPersistenceRequired;
			this.shouldRespawnOnDeath = shouldRespawnOnDeath;
			this.bbInflateFactor = bbInflateFactor;
		}

		/**
		 * Constructor used internally to initialize with a given {@link SpawnerType}.
		 * @param spawnType Type of the spawner.
		 */
		private Builder(SpawnerType spawnType) {
			this.spawnType = spawnType;
		}

		/**
		 * Enables home position and sets its configuration.
		 *
		 * @param setHomeRandom Whether the home position is random.
		 * @param homeRadius    The radius for setting the home position.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setHomeInfo(boolean setHomeRandom, int homeRadius) {
			this.setHomePositionOnSpawn = true;
			this.setHomeRandom = setHomeRandom;
			this.homeRadius = homeRadius;
			return this;
		}

		/**
		 * Sets the minimum and maximum distances from the player to allow spawning.
		 *
		 * @param min Minimum player distance.
		 * @param max Maximum player distance.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setPlayerDistance(int min, int max) {
			this.playerMinSpawnDistance = min;
			this.playerMaxSpawnDistance = max;
			return this;
		}

		/**
		 * Sets the check and spawn region boundaries.
		 *
		 * @param topYC       Check boundary top Y.
		 * @param bottomYC    Check boundary bottom Y.
		 * @param horizontalC Check boundary horizontal range.
		 * @param topYS       Spawn region top Y.
		 * @param bottomYS    Spawn region bottom Y.
		 * @param horizontalS Spawn region horizontal range.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setBoundaries(int topYC, int bottomYC, int horizontalC, int topYS, int bottomYS, int horizontalS) {
			checkBoundaries = new ExtendedSpawnerBoundsEntry(topYC, bottomYC, horizontalC);
			spawnRegion = new ExtendedSpawnerBoundsEntry(topYS, bottomYS, horizontalS);
			return this;
		}

		/**
		 * Sets spawn rate in ticks.
		 *
		 * @param ticks Delay between spawn attempts in ticks.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setEntitySpawnTickRate(int ticks) {
			this.entitySpawnTickRate = ticks;
			return this;
		}

		/**
		 * Sets spawn rate in seconds.
		 *
		 * @param seconds Delay between spawn attempts in seconds.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setEntitySpawnSeconds(int seconds) {
			this.entitySpawnTickRate = seconds * 20;
			return this;
		}

		/**
		 * Sets spawn rate in minutes.
		 *
		 * @param minutes Delay between spawn attempts in minutes.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setEntitySpawnMinutes(int minutes) {
			this.entitySpawnTickRate = minutes * 60 * 20;
			return this;
		}

		/**
		 * Sets how many rolls this spawner performs per attempt.
		 *
		 * @param rolls Number of pool rolls.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setPoolRolls(int rolls) {
			this.poolRolls = rolls;
			return this;
		}

		/**
		 * Sets the local spawn cap for this spawner.
		 *
		 * @param cap Maximum number of concurrent entities.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setlocalSpawnCap(int cap) {
			this.localSpawnCap = cap;
			return this;
		}

		/**
		 * Sets the chance to skip a spawn attempt.
		 *
		 * @param chance Chance from 0 to 100.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setRandomDiscardChance(int chance) {
			this.randomDiscardChance = chance;
			return this;
		}

		/**
		 * Disables this spawner for future use (e.g., during future content additions).
		 *
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder disableForFutureSpawning() {
			this.shouldDisableFutureSpawn = true;
			return this;
		}

		/**
		 * Forces all spawns to occur at the spawner's position.
		 *
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder enableNonRandomSpawning() {
			this.enableInsideSpawning = true;
			this.enableInsideSpawningChance = 100;
			return this;
		}

		/**
		 * Enables spawning at the spawner's position based on a chance value.
		 *
		 * @param chance Chance from 0 to 100.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder enableNonRandomSpawning(int chance) {
			this.enableInsideSpawning = true;
			this.enableInsideSpawningChance = chance;
			return this;
		}

		/**
		 * Sets whether the spawned entity should be persistent.
		 *
		 * @param isPersistenceRequired Whether to prevent entity despawn.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setPersistenceRequired(boolean isPersistenceRequired) {
			this.isPersistenceRequired = isPersistenceRequired;
			return this;
		}

		/**
		 * Enables respawning behavior when the entity dies.
		 *
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setRespawnOnDeath() {
			this.shouldRespawnOnDeath = true;
			return this;
		}
		/**
		 * Set a bounding box offset for larger entities
		 * 
		 * @param bbInflateFactor The offset to apply to this spawner bounding box
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setSpawnBBOffset(Vector3f bbInflateFactor) {
			this.bbInflateFactor = bbInflateFactor;
			return this;
		}
		/**
		 * Set a spawn offset for larger entities
		 * 
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder setSpawnOffset2x2() {
			this.bbInflateFactor = new Vector3f(0.5f, 1, 0.5f);
			return this;
		}
		/**
		 * Builds the {@link ExtendedStructureEntitySpawner} with the current configuration.
		 *
		 * @param id       The resource ID for the spawner.
		 * @param isCustom Whether this spawner is user-defined.
		 * @return The configured spawner.
		 */
		public ExtendedStructureEntitySpawner build(ResourceLocation id, boolean isCustom) {
			ExtendedStructureEntitySpawner spawner = new ExtendedStructureEntitySpawner(id, entries, spawnType, setHomePositionOnSpawn, setHomeRandom, homeRadius,
				entitySpawnTickRate, playerMinSpawnDistance, playerMaxSpawnDistance, checkBoundaries, spawnRegion, poolRolls, localSpawnCap, shouldDisableFutureSpawn,
				randomDiscardChance, enableInsideSpawning, enableInsideSpawningChance, isPersistenceRequired, shouldRespawnOnDeath, bbInflateFactor);
			spawner.isCustom = isCustom;
			return spawner;
		}

		/**
		 * Builds and registers the spawner using the given consumer and string ID.
		 *
		 * @param pConsumer Callback to save the spawner.
		 * @param pId       The string resource ID.
		 * @return The configured spawner.
		 */
		public ExtendedStructureEntitySpawner save(Consumer<ExtendedStructureEntitySpawner> pConsumer, String pId) {
			return save(pConsumer, new ResourceLocation(pId));
		}

		/**
		 * Builds and registers the spawner using the given consumer and resource location.
		 *
		 * @param pConsumer Callback to save the spawner.
		 * @param pId       The resource ID.
		 * @return The configured spawner.
		 */
		public ExtendedStructureEntitySpawner save(Consumer<ExtendedStructureEntitySpawner> pConsumer, ResourceLocation pId) {
			ExtendedStructureEntitySpawner spawner = this.build(pId, false);
			pConsumer.accept(spawner);
			return spawner;
		}
		/**
		 * Static factory method to create a new builder with the specified {@link SpawnerType}.
		 *
		 * @param spawnType Type of the spawner.
		 * @return A new builder instance.
		 */
		public static ExtendedStructureEntitySpawner.Builder spawner(SpawnerType spawnType) {
			return new ExtendedStructureEntitySpawner.Builder(spawnType);
		}
		/**
		 * Static factory method to create a new builder and set home info.
		 *
		 * @param spawnType              Type of the spawner.
		 * @param setHomePositionOnSpawn Whether to set home position.
		 * @param homeRadius             Radius for home position.
		 * @return A new builder instance.
		 */
		public static ExtendedStructureEntitySpawner.Builder spawner(SpawnerType spawnType, boolean setHomePositionOnSpawn, int homeRadius) {
			return new ExtendedStructureEntitySpawner.Builder(spawnType).setHomeInfo(setHomePositionOnSpawn, homeRadius);
		}
		/**
		 * Adds a single-entity spawn entry to the pool.
		 *
		 * @param type          Entity type to spawn.
		 * @param weight        Weight of this entry.
		 * @param minSpawnCount Minimum entities per spawn.
		 * @param maxSpawnCount Maximum entities per spawn.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder addSinglePool(EntityType<?> type, int weight, int minSpawnCount, int maxSpawnCount) {
			entries.add(new ExtendedStructureEntitySpawnerEntry(new SingleSpawnType(type), weight, minSpawnCount, maxSpawnCount));
			return this;
		}
		/**
		 * Adds a multi-entity spawn entry to the pool.
		 *
		 * @param multiPool     Multi-pool spawn type.
		 * @param weight        Weight of this entry.
		 * @param minSpawnCount Minimum entities per spawn.
		 * @param maxSpawnCount Maximum entities per spawn.
		 * @return This builder instance.
		 */
		public ExtendedStructureEntitySpawner.Builder addMultiPool(MultiSpawnType multiPool, int weight, int minSpawnCount, int maxSpawnCount) {
			entries.add(new ExtendedStructureEntitySpawnerEntry(multiPool, weight, minSpawnCount, maxSpawnCount));
			return this;
		}
	}
}
