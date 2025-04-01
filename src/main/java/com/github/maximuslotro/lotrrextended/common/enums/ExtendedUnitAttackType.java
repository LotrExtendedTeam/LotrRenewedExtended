package com.github.maximuslotro.lotrrextended.common.enums;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

public enum ExtendedUnitAttackType {
	MELEE("melee", false, false), 
	RANGED("ranged", false, true), 
	MOUNTED_MELEE("mounted_melee", true, false), 
	MOUNTED_RANGED("mounted_ranged", true, true);

	private final String name;
	private final boolean isMounted;
	private final boolean canBeRanged;

	private ExtendedUnitAttackType(String pName, boolean pIsMounted, boolean pCanBeRanged) {
		this.name = pName;
		this.isMounted = pIsMounted;
		this.canBeRanged = pCanBeRanged;
	}

	public static ExtendedUnitAttackType byName(String name) {
		for(ExtendedUnitAttackType type : values()) {
			if(type.getAttackTypeName().equals(name)) {
				return type;
			}
		}
		ExtendedLog.error("Tried to decode unknown unit attack type: "+name+", defaulted to "+ExtendedUnitAttackType.MELEE+"!");
		return ExtendedUnitAttackType.MELEE;
	}

	public String getAttackTypeName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isMounted() {
		return isMounted;
	}

	public boolean isCanBeRanged() {
		return canBeRanged;
	}

}
