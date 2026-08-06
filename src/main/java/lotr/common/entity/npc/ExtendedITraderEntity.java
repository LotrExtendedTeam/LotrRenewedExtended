package lotr.common.entity.npc;

import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.player.PlayerEntity;

public interface ExtendedITraderEntity extends ExtendedIInteractableEntity {
	/**
     * Opens the coin exchange interface for the given player.
     *
     * @param player The player to open the coin exchange for.
     */
	default void openCoinExchange(PlayerEntity player) {
		ExtendedTraderHelper.openCoinExchange(getNPC(), player);
	};
	/**
     * Gets the trader's display name.
     *
     * @return The trader's name as a String.
     */
	default String getTraderName() {
		return ExtendedTraderHelper.getTraderName(getNPC().getDisplayName());
	}
	/**
     * Checks if the given player can exchange coins with this trader, handles friendly and alignment checks.
     *
     * @param player The player to check.
     * @return True if the player can trade, false otherwise.
     */
	default boolean canExchangeCoinsWithPlayer(PlayerEntity player) {
		return isFriendlyAndAlignedWithLevel(player, 0);
	}
}