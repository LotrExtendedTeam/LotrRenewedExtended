package lotr.common.entity.npc;

import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.util.TradeUtils;
import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExtendedTraderEntity {

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

	boolean isInteractingWithPlayer();

	void removePlayerFromTrading(PlayerEntity player);

	void updatePlayerTradingWith(UUID uuid);
	Optional<UUID> getPlayerTradingWith();

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

	default public boolean isFriendlyAndAlignedWithLevel(PlayerEntity player, NPCEntity entity, float level) {
		return entity.isNotFighting(player) && LOTRLevelData.getSidedData(player).getAlignmentData()
				.hasAlignment(entity.getFaction(), AlignmentPredicates.greaterThanOrEqual(level));
	}

	TranslationTextComponent getDepartureLine(PlayerEntity player);

	List<UUID> getEscortUUIDs();

	void addEscort(UUID uuid);

	NPCEntity createEscort();
}
