package lotr.common.entity.npc;

import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

import java.util.List;

public interface ExtendedTraderEntity {

    void playTradeSound();

    boolean isInteractingWithPlayer();

    void removePlayerFromTrading(PlayerEntity player);

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

}
