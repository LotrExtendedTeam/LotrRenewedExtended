package com.github.maximuslotro.lotrrextended.common.hiredunits;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

public enum ExtendedUnitOrders {
	STATIONARY(0,    false, false, true),		// Guard a specific spot and don't move unless attacked, then return
	GUARD_AREA(1,    false, true,  false),		// Guard a specific spot, but have a walkable radius
	FOLLOW_PLAYER(2, true,  true,  true),		// Follow the player
	DISMISSED(3,     false, true,  true),		// Dismissed,
	FARMING(4,       false, false, true);		// Farming

	private final int unchangingIndex;
	private final boolean followingPlayer;
	private final boolean orderAllowsCombat;
	private final boolean orderAllowedForFarmhands;

	private ExtendedUnitOrders(int pIndex, boolean pFollowingPlayer, boolean pOrderAllowsCombat, boolean pOrderAllowedForFarmhands) {
		this.followingPlayer = pFollowingPlayer;
		this.unchangingIndex = pIndex;
		this.orderAllowsCombat = pOrderAllowsCombat;
		this.orderAllowedForFarmhands = pOrderAllowedForFarmhands;
	}

	public int getUnchangingIndex() {
		return unchangingIndex;
	}

	public boolean isFollowingPlayer() {
		return followingPlayer;
	}

	public boolean doesOrderAllowsCombat() {
		return orderAllowsCombat;
	}

	public boolean isOrderAllowedForFarmhands() {
		return orderAllowedForFarmhands;
	}

	public boolean isHasRadius() {
		return this==FARMING || this==GUARD_AREA;
	}

	public static ExtendedUnitOrders getByUnchangingIndex(int pIndex) {
		for(ExtendedUnitOrders order : values()) {
			if(order.getUnchangingIndex()==pIndex) {
				return order;
			}
		}
		ExtendedLog.error("Recieved an invalid unit order to decifer! ["+ pIndex+"] defaulting to "+STATIONARY.name());
		return STATIONARY;
	}
}
