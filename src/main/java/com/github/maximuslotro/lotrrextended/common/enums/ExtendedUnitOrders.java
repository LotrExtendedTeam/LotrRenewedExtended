package com.github.maximuslotro.lotrrextended.common.enums;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

public enum ExtendedUnitOrders {
	GUARD_STATIONARY(0, false, false),		// Guard a specific spot and don't move
	GAURD_PATROL(1, false, true),			// Guard a specific spot, but have a walkable radius
	PATROL_PLAYER(2, true, true),			// Follow the player
	DISMISSED(2, false, true);			// Dismissed

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

	public static ExtendedUnitOrders getByUnchangingIndex(int pIndex) {
		for(ExtendedUnitOrders order : values()) {
			if(order.getUnchangingIndex()==pIndex) {
				return order;
			}
		}
		ExtendedLog.error("Recieved an invalid unit order to decifer! ["+ pIndex+"] defaulting to "+GUARD_STATIONARY.name());
		return GUARD_STATIONARY;
	}
}
