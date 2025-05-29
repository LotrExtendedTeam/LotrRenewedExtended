package lotr.common.entity.npc;

import java.util.Optional;
import java.util.UUID;

import lotr.common.entity.npc.data.ExtendedNpcTraderInfoHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public interface ExtendedTraderEntity extends ExtendedInteractableEntity {
	void playTradeSound();

	void openCoinExchange(PlayerEntity player);

	void openTradeMenu(PlayerEntity player);

	String getTraderName();

	ResourceLocation getTraderProfileId();
	
	/*
	 * ONLY CALL ON THE SERVER!
	 * */
	default ExtendedNpcTraderInfoHolder getTraderData() {
		return ((ExtendedNPCEntity)this).getTraderInfo();
	}
	/**
	 * Checks if player can trade with the trader
	 * */
	default boolean canTradeWithPlayer(PlayerEntity player) {
		return isFriendlyAndAlignedWithLevel(player, ((NPCEntity)this), getTraderData().getTradingAlignmentRequiredDualSided());
	}
	/**
	 * Checks if player is friendly with the trader
	 * */
	default boolean isFriendly(PlayerEntity player) {
		return isFriendly(player, ((NPCEntity)this));
	}
    boolean isInteractingWithPlayer();

    Optional<UUID> getPlayerInteractingWith();

    void updatePlayerInteractingWith(UUID uuid);

    void removePlayerFromInteracting(PlayerEntity player);

    int setLastTickCooldownReduced();
    void getLastTickCooldownReduced(int cooldown);
}