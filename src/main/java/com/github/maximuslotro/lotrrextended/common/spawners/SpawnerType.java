package com.github.maximuslotro.lotrrextended.common.spawners;

public enum SpawnerType {
	SINGLE("single_use_spawner", 0, false),
	SINGLE_FAMILY("single_family_spawner", 1, false),
	PERSISTED("persisted_use_spawner", 2, true),
	UNKNOWN("unknown", 66, false);

	private final String name;
	private final int id;
	private final boolean shoudlCheckBoundries;

	SpawnerType(String pName, int pID, boolean shoudlCheckBoundries) {
		this.name = pName;
		this.id = pID;
		this.shoudlCheckBoundries = shoudlCheckBoundries;
	}

	public String getString() {
		return name;
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
