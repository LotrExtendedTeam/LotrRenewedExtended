package lotr.common.entity.npc;

import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExtendedTraderEntity {

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

	int[] getBoughtPrices();

	int[] getBoughtQuantities();

	int[] getSoldPrices();

	int[] getSoldQuantities();

	int[] getSoldOriginalQuantities();

	int[] getBoughtOriginalQuantities();

	List<ExtendedTrade> getSells();

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
}
