package lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import lotr.common.util.ExtendedTraderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public interface ExtendedMerchantEntity extends ExtendedTraderEntity {
	/**
     * Opens the trade menu for the given player.
     *
     * @param player The player to open the trade menu for.
     */
	default void openTradeMenu(PlayerEntity player) {
		ExtendedTraderHelper.openTradeMenu(getNPC(), player);
	}
	/**
     * Gets the trader's profile identifier.
     *
     * @return A ResourceLocation representing the trader's profile ID.
     */
	ResourceLocation getTraderProfileId();
	
	/**
     * Retrieves trader data.
     * <p><b>Server only:</b> This method should only be called on the server side.</p>
     * Unless calling method {@code getTradingAlignmentRequiredDualSided}
     *
     * @return The trader's data holder.
     */
	default ExtendedNpcTraderInfoHolder getTraderData() {
		return ((ExtendedNPCEntity)this).getTraderInfo();
	}
	/**
     * Checks if the given player can trade with this trader, handles friendly and alignment checks.
     *
     * @param player The player to check.
     * @return True if the player can trade, false otherwise.
     */
	default boolean canTradeWithPlayer(PlayerEntity player) {
		return isFriendlyAndAlignedWithLevel(player, getTraderData().getTradingAlignmentRequiredDualSided());
	}
	/**
     * Checks if the trader is currently interacting with any player.
     *
     * @return True if interacting with a player, false otherwise.
     */
	@Deprecated
    boolean isInteractingWithPlayer();
    /**
     * Gets the UUID of the player currently interacting with the trader, if any.
     *
     * @return An Optional containing the UUID of the interacting player, or empty if none.
     */
	@Deprecated
    Optional<UUID> getPlayerInteractingWith();
    /**
     * Updates the currently interacting player by UUID.
     *
     * @param uuid The UUID of the player interacting with the trader.
     */
	@Deprecated
    void updatePlayerInteractingWith(UUID uuid);
    /**
     * Removes the specified player from interacting with the trader.
     *
     * @param player The player to remove.
     */
	@Deprecated
    void removePlayerFromInteracting(PlayerEntity player);
}