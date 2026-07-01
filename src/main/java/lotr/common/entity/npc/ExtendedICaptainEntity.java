package lotr.common.entity.npc;

import lotr.common.entity.npc.data.ExtendedNpcCaptainInfoHolder;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public interface ExtendedICaptainEntity extends ExtendedITraderEntity {
	/**
     * Opens the hire unit menu for the given player.
     *
     * @param player The player to open the trade menu for.
     */
	default void openHireUnitMenu(PlayerEntity player) {
		ExtendedTraderHelper.openCaptainMenu(getNPC(), player);
	}

	ResourceLocation getCaptainProfile();
	/**
     * Retrieves captain data.
     * This class should only hold data that is synced entirely to the client
     *
     * @return The trader's data holder.
     */
	default ExtendedNpcCaptainInfoHolder getCaptainData() {
		return ((ExtendedINPCEntity)this).getCaptainInfo();
	}
	/**
     * Checks if the given player can trade with this trader, handles friendly and alignment checks.
     *
     * @param player The player to check.
     * @return True if the player can trade, false otherwise.
     */
	default boolean canHireToPlayer(PlayerEntity player) {
		return isFriendlyAndAlignedWithLevel(player, getCaptainData().getHiringAlignmentRequiredDualSided());
	}
}