package com.github.maximuslotro.lotrrextended.common.enums;

public enum EntityGender {
	MALE("male", 0),
	FEMALE("female", 1),
	GENDERLESS("genderless", 2),
	UNKNOWN("unknown", 3);

	private final String name;
	private final int id;
	EntityGender(String pName, int pID) {
		this.name = pName;
		this.id = pID;
	}

	public String getGenderName() {
		return name;
	}

	public int getGenderId() {
		return id;
	}

	public boolean isMale() {
		return this == MALE;
	}
	public static EntityGender byGenderName(String queryName) {
		for (EntityGender gender : values()) {
			if (gender.name.equals(queryName)) {
				return gender;
			}
		}
		return EntityGender.UNKNOWN;
	}

	public static EntityGender byGenderId(int queryID) {
		for (EntityGender gender : values()) {
			if (gender.id == queryID) {
				return gender;
			}
		}
		return EntityGender.UNKNOWN;
	}
}
