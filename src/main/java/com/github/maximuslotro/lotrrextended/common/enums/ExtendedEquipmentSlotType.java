package com.github.maximuslotro.lotrrextended.common.enums;

import net.minecraft.inventory.EquipmentSlotType;

public enum ExtendedEquipmentSlotType {
	HEAD(0, "head", false, EquipmentSlotType.HEAD),
	CHEST(1, "chest", false, EquipmentSlotType.CHEST),
	LEGS(2, "legs", false, EquipmentSlotType.LEGS),
	FEET(3, "feet", false, EquipmentSlotType.FEET),
	MELEE(4, "melee", true, EquipmentSlotType.MAINHAND),
	RANGED(5, "ranged", true, EquipmentSlotType.MAINHAND),
	MOUNTED(6, "mounted", true, EquipmentSlotType.MAINHAND),
	IDLE(7, "idle", true, EquipmentSlotType.MAINHAND),
	IDLE_MOUNTED(8, "idle_mounted", true, EquipmentSlotType.MAINHAND),
	OFFHAND(9, "offhand", false, EquipmentSlotType.OFFHAND);

	private final int index;
	private final String name;
	private final boolean isRenewedHandling;
	private final EquipmentSlotType vanillaEquilibrant;

	private ExtendedEquipmentSlotType(int pIndex, String pName, boolean pIsRenewedHandling, EquipmentSlotType pVanillaEquilibrant) {
		this.index = pIndex;
		this.name = pName;
		this.isRenewedHandling = pIsRenewedHandling;
		this.vanillaEquilibrant = pVanillaEquilibrant;
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

	public EquipmentSlotType getVanillaEquilibrant() {
		return vanillaEquilibrant;
	}
}
