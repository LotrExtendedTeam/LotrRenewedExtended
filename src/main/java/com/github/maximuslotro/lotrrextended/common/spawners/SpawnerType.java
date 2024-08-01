package com.github.maximuslotro.lotrrextended.common.spawners;

public enum SpawnerType {
	SINGLE("single_use_spawner", 0, false, true),
	SINGLE_FAMILY("single_family_spawner", 1, false, true),
	PERSISTED("persisted_use_spawner", 2, true, false),
	UNKNOWN("unknown", 66, false, true);

	private final String name;
	private final int id;
	private final boolean shoudlCheckBoundries;
	private final boolean singleSpawnType;
	SpawnerType(String pName, int pID, boolean shoudlCheckBoundries, boolean singleSpawnType) {
		this.name = pName;
		this.id = pID;
		this.shoudlCheckBoundries = shoudlCheckBoundries;
		this.singleSpawnType = singleSpawnType;
	}

	public String getString() {
		return name;
	}

	public boolean isUnknown() {
		return this.equals(UNKNOWN) || this.id == UNKNOWN.id;
	}
	/**
	 * Please don't use for saved data, as value is subject to change over updates!
	 * */
	public int getSpawnerIdDoNotUse() {
		return id;
	}

	public boolean isShouldCheckBoundries() {
		return shoudlCheckBoundries;
	}

	public boolean isSingleSpawnType() {
		return singleSpawnType;
	}

	public static SpawnerType bySpawnerName(String queryName) {
		for (SpawnerType type : values()) {
			if (type.name.equals(queryName)) {
				return type;
			}
		}
		return SpawnerType.UNKNOWN;
	}

	public static SpawnerType bySpawnerId(int queryID) {
		for (SpawnerType type : values()) {
			if (type.id == queryID) {
				return type;
			}
		}
		return SpawnerType.UNKNOWN;
	}
}
