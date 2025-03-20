package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

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

	Inventory getDisplayInventory();

	List<ExtendedTrade> getBuys();
	void setBuys(ArrayList<ExtendedTrade> trades);

	int[] getBoughtPrices();

	int[] getBoughtQuantities();

	int[] getSoldPrices();

	int[] getSoldQuantities();

	int[] getSoldOriginalQuantities();

	int[] getBoughtOriginalQuantities();

	List<ExtendedTrade> getSells();
	void setSells(ArrayList<ExtendedTrade> trades);

	boolean canTradeWith(PlayerEntity player);

	boolean isWanderingTrader();

	void setWanderingTrader(boolean isWanderingTrader);

	int getWanderingTraderTicksLeft();

	void setWanderingTraderTicksLeft(int ticksLeft);

	TranslationTextComponent getDepartureLine(PlayerEntity player);

	List<UUID> getEscortUUIDs();

	void addEscort(UUID uuid);

	NPCEntity createEscort();

    boolean isInteractingWithPlayer();

    Optional<UUID> getPlayerInteractingWith();

    void updatePlayerInteractingWith(UUID uuid);

    void removePlayerFromInteracting(PlayerEntity player);
}
