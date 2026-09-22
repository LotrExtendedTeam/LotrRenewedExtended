package com.github.maximuslotro.lotrrextended.common.enums;

public enum BanditChance {
	NONE(0),
	RARE(10),
	UNCOMMON(30),
	COMMON(70);
	private int chance;

	private BanditChance(int chance) {
		this.chance = chance;
	}

	public int getChance() {
		return chance;
	}

	@Override
	public String toString() {
		return name();
	}

	public static BanditChance byName(String name) {
		for (BanditChance value : values()) {
			if (value.name().equalsIgnoreCase(name)) {
				return value;
			}
		}
		return NONE;
	}
}