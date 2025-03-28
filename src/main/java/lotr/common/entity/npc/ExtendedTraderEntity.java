package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lotr.common.util.ExtendedTraderHelper;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;

public interface ExtendedTraderEntity extends ExtendedInteractableEntity {

	default void saveAllTrades(CompoundNBT tag) {
		TradeUtils.saveAllTrades(ExtendedTradeEntries.TradeType.BOUGHT, getBuys(), tag);
		TradeUtils.saveAllTrades(ExtendedTradeEntries.TradeType.SOLD, getSells(), tag);
	}

	default void loadAllTrades(CompoundNBT tag) {
		List<ExtendedTrade> buys = TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.BOUGHT, tag);
		List<ExtendedTrade> sells = TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.SOLD, tag);
        if (buys != null) {
        	setBuys(TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.BOUGHT, tag));
        }else {
        	setBuys(new ArrayList<>());
        }
        if (sells != null) {
        	setSells(TradeUtils.loadAllTrades(ExtendedTradeEntries.TradeType.SOLD, tag));
        }else {
        	setSells(new ArrayList<>());
        }
	}

	void playTradeSound();

	void openCoinExchange(PlayerEntity player);

	void openTradeMenu(PlayerEntity player);

	String getTraderName();

	default Inventory getDisplayInventory() {
		return TradeUtils.generateDisplayInventory(getSells(), getBuys());
	}

	List<ExtendedTrade> getBuys();
	void setBuys(ArrayList<ExtendedTrade> trades);

	default int[] getBoughtPrices() {
		return ExtendedTraderHelper.getBoughtPrices(getBuys());
	}

	default int[] getBoughtQuantities() {
		return ExtendedTraderHelper.getBoughtQuantity(getBuys());
	}

	default int[] getSoldPrices() {
		return ExtendedTraderHelper.getSoldPrices(getSells());
	}

	default int[] getSoldQuantities() {
		return ExtendedTraderHelper.getSoldQuantity(getSells());
	}

	default int[] getSoldOriginalQuantities() {
		return ExtendedTraderHelper.getSoldOriginalQuantity(getSells());
	}

	default int[] getBoughtOriginalQuantities() {
		return ExtendedTraderHelper.getBoughtOriginalQuantity(getBuys());
	}

	List<ExtendedTrade> getSells();
	void setSells(ArrayList<ExtendedTrade> trades);

	boolean canTradeWith(PlayerEntity player);

    boolean isInteractingWithPlayer();

    Optional<UUID> getPlayerInteractingWith();

    void updatePlayerInteractingWith(UUID uuid);

    void removePlayerFromInteracting(PlayerEntity player);

    /**
     * Gets if this trader is wandering. Should ONLY be set to true if instance of ExtendedWanderingTraderEntity
     * Even then, ExtendedWanderingTraderEntity can be false when set from an egg.
     * @return True if should handle NPC as wandering.
     * */
    default boolean isTraderWandering() {
    	return false;
    }

    int setLastTickCooldownReduced();
    void getLastTickCooldownReduced(int cooldown);
}
