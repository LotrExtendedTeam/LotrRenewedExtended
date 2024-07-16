package com.github.maximuslotro.lotrrextended.common.enums;

public enum EntityGender {
	MALE("male"), 
	FEMALE("female"), 
	GENDERLESS("genderless"), 
	UNKNOWN("unknown");

	private final String id;
	
	EntityGender(String pId) {
		this.id = pId;
	}

	public String getId() {
		return id;
	}

	public static EntityGender byName(String queryName) {
		for (EntityGender gender : values()) {
			if (gender.id.equals(queryName)) {
				return gender;
			}
		}
		return EntityGender.UNKNOWN;
	}
}
