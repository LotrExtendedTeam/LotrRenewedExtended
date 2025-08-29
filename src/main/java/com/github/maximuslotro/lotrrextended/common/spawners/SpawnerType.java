package com.github.maximuslotro.lotrrextended.common.spawners;

/**
 * Enum representing different types of spawners, each with a unique name, ID,
 * and behavioral flags regarding boundary checks and spawn restrictions.
 */
public enum SpawnerType {
    /** A spawner used once, without boundary checks, allowing single spawn type. */
	SINGLE("single_use_spawner", 0, false, true),
    /** A spawner used once per family, without boundary checks, allowing single spawn type. */
	SINGLE_FAMILY("single_family_spawner", 1, false, true),
    /** A persisted spawner that checks boundaries and does not restrict to a single spawn type. */
	PERSISTED("persisted_use_spawner", 2, true, false),
	/** A persisted spawner that does not check boundaries, allowing only a single spawn type. */
	PERSISTED_SINGLE("persisted_use_spawner", 3, false, false),
    /** An unknown spawner type used as a fallback or default. */
	UNKNOWN("unknown", 66, false, true);
    /** Internal name of the spawner type. */
	private final String name;
    /** Internal ID of the spawner type (not stable for saved data). */
	private final int id;
    /** Whether this spawner type requires boundary checks. */
	private final boolean shoudlCheckBoundries;
    /** Whether this spawner type allows only one spawn before deletion. */
	private final boolean singleSpawnType;
	/**
     * Constructs a SpawnerType with specified parameters.
     *
     * @param pName The internal name of the spawner.
     * @param pID The internal ID of the spawner.
     * @param shoudlCheckBoundries Whether boundary checks should be performed.
     * @param singleSpawnType Whether only a single spawn type is allowed.
     */
	SpawnerType(String pName, int pID, boolean shoudlCheckBoundries, boolean singleSpawnType) {
		this.name = pName;
		this.id = pID;
		this.shoudlCheckBoundries = shoudlCheckBoundries;
		this.singleSpawnType = singleSpawnType;
	}
	/**
     * Returns the internal string name of this spawner type.
     *
     * @return The spawner type name.
     */
	public String getString() {
		return name;
	}
	/**
     * Checks if this spawner type is the unknown type.
     *
     * @return True if this is the {@link #UNKNOWN} spawner type or has the unknown ID.
     */
	public boolean isUnknown() {
		return this.equals(UNKNOWN) || this.id == UNKNOWN.id;
	}
	/**
     * Returns the internal ID of the spawner type.
     * <p><b>Warning:</b> This value is not guaranteed to remain constant across updates
     * and should not be used for saving persistent data.</p>
     *
     * @return The internal spawner ID.
     */
	public int getSpawnerIdDoNotUse() {
		return id;
	}
	/**
     * Indicates whether this spawner type should perform boundary checks.
     *
     * @return True if boundary checks are required, false otherwise.
     */
	public boolean isShouldCheckBoundries() {
		return shoudlCheckBoundries;
	}
	/**
     * Indicates whether this spawner type restricts to a single spawn instance before deletion
     *
     * @return True if only a single spawn is allowed, false otherwise.
     */
	public boolean isSingleSpawnType() {
		return singleSpawnType;
	}
	/**
     * Retrieves a {@code SpawnerType} by its internal name.
     *
     * @param queryName The name to search for.
     * @return The matching {@code SpawnerType}, or {@link #UNKNOWN} if no match is found.
     */
	public static SpawnerType bySpawnerName(String queryName) {
		for (SpawnerType type : values()) {
			if (type.name.equals(queryName)) {
				return type;
			}
		}
		return SpawnerType.UNKNOWN;
	}
	/**
     * Retrieves a {@code SpawnerType} by its internal ID.
     *
     * @param queryID The ID to search for.
     * @return The matching {@code SpawnerType}, or {@link #UNKNOWN} if no match is found.
     */
	public static SpawnerType bySpawnerId(int queryID) {
		for (SpawnerType type : values()) {
			if (type.id == queryID) {
				return type;
			}
		}
		return SpawnerType.UNKNOWN;
	}
}
