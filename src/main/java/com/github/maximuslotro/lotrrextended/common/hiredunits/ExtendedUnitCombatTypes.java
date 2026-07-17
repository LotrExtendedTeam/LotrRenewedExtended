package com.github.maximuslotro.lotrrextended.common.hiredunits;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

/*
 * Primarily used to determine what GUI options should appear for units
 * Also used during data generation to validate inputs 
 * */
public enum ExtendedUnitCombatTypes {
	MELEE("melee", false, false), 
	RANGED("ranged", true, false), 
	MOUNTED_MELEE("mounted_melee", false, true), 
	MOUNTED_RANGED("mounted_ranged", true, true),
	FARMHAND("farmhard", false, false);

	private final String name;
	private final boolean ranged;
	private final boolean mounted;

	private ExtendedUnitCombatTypes(String pName, boolean ranged, boolean mounted) {
		this.name = pName;
		this.ranged = ranged;
		this.mounted = mounted;
	}

	public static ExtendedUnitCombatTypes byName(String name) {
		for(ExtendedUnitCombatTypes type : values()) {
			if(type.getOperationTypeName().equals(name)) {
				return type;
			}
		}
		ExtendedLog.error("Tried to decode unknown unit attack type: "+name+", defaulted to "+ExtendedUnitCombatTypes.MELEE+"!");
		return ExtendedUnitCombatTypes.MELEE;
	}

	public String getOperationTypeName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isRanged() {
		return ranged;
	}

	public boolean isMounted() {
		return mounted;
	}

	public boolean isFarming() {
		return this==FARMHAND;
	}
}
