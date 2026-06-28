package com.github.maximuslotro.lotrrextended.common.hiredunits;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

public enum ExtendedUnitOperationsType {
	MELEE("melee", false, false, false), 
	RANGED("ranged", true, false, false), 
	MOUNTED_MELEE("mounted_melee", false, true, false), 
	MOUNTED_RANGED("mounted_ranged", true, true, false),
	FARMHAND("farmhard", false, false, true);

	private final String name;
	private final boolean ranged;
	private final boolean mounted;
	private final boolean farming;

	private ExtendedUnitOperationsType(String pName, boolean ranged, boolean mounted, boolean farming) {
		this.name = pName;
		this.ranged = ranged;
		this.mounted = mounted;
		this.farming = farming;
	}

	public static ExtendedUnitOperationsType byName(String name) {
		for(ExtendedUnitOperationsType type : values()) {
			if(type.getOperationTypeName().equals(name)) {
				return type;
			}
		}
		ExtendedLog.error("Tried to decode unknown unit attack type: "+name+", defaulted to "+ExtendedUnitOperationsType.MELEE+"!");
		return ExtendedUnitOperationsType.MELEE;
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
		return farming;
	}
}
