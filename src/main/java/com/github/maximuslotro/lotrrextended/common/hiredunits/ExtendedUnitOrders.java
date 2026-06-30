package com.github.maximuslotro.lotrrextended.common.hiredunits;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

public enum ExtendedUnitOrders {
	STATIONARY(0, false, false),		// Guard a specific spot and don't move unless attacked, then return
	PATROL_AREA(1, false, true),		// Guard a specific spot, but have a walkable radius. Used for farming to restrict to area
	FOLLOW_PLAYER(2, true, true),		// Follow the player
	DISMISSED(3, false, true),			// Dismissed, un-needed as units have wasHired field now (currently unused)
	UNKNOWN(99, false, true);			// Unknown order type (currently unused)

	private final int unchangingIndex;
	private final boolean followingPlayer;
	private final boolean orderAllowsMovement;

	private ExtendedUnitOrders(int pIndex, boolean pFollowingPlayer, boolean pOrderAllowsMovement) {
		this.followingPlayer = pFollowingPlayer;
		this.unchangingIndex = pIndex;
		this.orderAllowsMovement = pOrderAllowsMovement;
	}

	public boolean isFollowingPlayer() {
		return followingPlayer;
	}

	public int getUnchangingIndex() {
		return unchangingIndex;
	}

	public boolean doesOrderAllowsMovement() {
		return orderAllowsMovement;
	}

	public boolean isUnknown() {
		return this==UNKNOWN;
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
