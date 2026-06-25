package lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public interface ExtendedTraderEntity extends ExtendedInteractableEntity {
	/**
     * Plays the trade sound effect.
     */
	void playTradeSound();
	/**
     * Opens the coin exchange interface for the given player.
     *
     * @param player The player to open the coin exchange for.
     */
	void openCoinExchange(PlayerEntity player);
	/**
     * Opens the trade menu for the given player.
     *
     * @param player The player to open the trade menu for.
     */
	void openTradeMenu(PlayerEntity player);
	/**
     * Gets the trader's display name.
     *
     * @return The trader's name as a String.
     */
	String getTraderName();
	/**
     * Gets the trader's profile identifier.
     *
     * @return A ResourceLocation representing the trader's profile ID.
     */
	ResourceLocation getTraderProfileId();
	
	/**
     * Retrieves trader data.
     * <p><b>Server only:</b> This method should only be called on the server side.</p>
     *
     * @return The trader's data holder.
     */
	default ExtendedNpcTraderInfoHolder getTraderData() {
		return ((ExtendedNPCEntity)this).getTraderInfo();
	}
	/**
     * Checks if the given player can trade with this trader.
     *
     * @param player The player to check.
     * @return True if the player can trade, false otherwise.
     */
	default boolean canTradeWithPlayer(PlayerEntity player) {
		return isFriendlyAndAlignedWithLevel(player, ((NPCEntity)this), getTraderData().getTradingAlignmentRequiredDualSided());
	}
	/**
     * Checks if the player is friendly with the trader.
     * <p><b>Note:</b> This currently only works server-side. TODO: Make work on client side.</p>
     *
     * @param player The player to check.
     * @return True if the player is friendly, false otherwise.
     */
	default boolean isFriendly(PlayerEntity player) {
		return isFriendly(player, ((NPCEntity)this));
	}
	/**
     * Checks if the trader is currently interacting with any player.
     *
     * @return True if interacting with a player, false otherwise.
     */
    boolean isInteractingWithPlayer();
    /**
     * Gets the UUID of the player currently interacting with the trader, if any.
     *
     * @return An Optional containing the UUID of the interacting player, or empty if none.
     */
    Optional<UUID> getPlayerInteractingWith();
    /**
     * Updates the currently interacting player by UUID.
     *
     * @param uuid The UUID of the player interacting with the trader.
     */
    void updatePlayerInteractingWith(UUID uuid);
    /**
     * Removes the specified player from interacting with the trader.
     *
     * @param player The player to remove.
     */
    void removePlayerFromInteracting(PlayerEntity player);
}