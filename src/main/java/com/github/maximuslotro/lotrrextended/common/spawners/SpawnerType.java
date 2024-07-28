package com.github.maximuslotro.lotrrextended.common.spawners;

public enum SpawnerType {
	SINGLE("single_use_spawner", 0),
	SINGLE_FAMILY("single_family_spawner", 1),
	PERSISTED("persisted_use_spawner", 2),
	UNKNOWN("unknown", 66);

	private final String name;
	private final int id;
	SpawnerType(String pName, int pID) {
		this.name = pName;
		this.id = pID;
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
