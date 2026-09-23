package com.github.maximuslotro.lotrrextended.common.enums;

public enum ExtendedBanditChance {
	NONE(0),
	RARE(10),
	UNCOMMON(30),
	COMMON(70);
	private int chance;

	private ExtendedBanditChance(int chance) {
		this.chance = chance;
	}

	public int getChance() {
		return chance;
	}

	@Override
	public String toString() {
		return name();
	}

	public static ExtendedBanditChance byName(String name) {
		for (ExtendedBanditChance value : values()) {
			if (value.name().equalsIgnoreCase(name)) {
				return value;
			}
		}
		return NONE;
	}
}