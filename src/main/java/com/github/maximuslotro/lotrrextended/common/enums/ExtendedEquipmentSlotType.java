package com.github.maximuslotro.lotrrextended.common.enums;

public enum ExtendedEquipmentSlotType {
	HEAD(0, "head", false),
	CHEST(1, "chest", false),
	LEGS(2, "legs", false),
	FEET(3, "feet", false),
	MELEE(4, "melee", true),
	RANGED(5, "ranged", true),
	MOUNTED(6, "mounted", true),
	IDLE(7, "idle", true),
	IDLE_MOUNTED(8, "idle_mounted", true),
	OFFHAND(9, "offhand", false);

	private final int index;
	private final String name;
	private final boolean isRenewedHandling;

	private ExtendedEquipmentSlotType(int pIndex, String pName, boolean pIsRenewedHandling) {
		this.index = pIndex;
		this.name = pName;
		this.isRenewedHandling = pIsRenewedHandling;
	}

	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}

	public boolean isRenewedHandling() {
		return isRenewedHandling;
	}
}
