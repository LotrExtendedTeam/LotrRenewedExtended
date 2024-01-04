package lotr.common.entity.npc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public interface ExtendedTraderEntity {

    void playTradeSound();

    boolean isInteractingWithPlayer();

    void removePlayerFromTrading(PlayerEntity player);

    void openCoinExchange(PlayerEntity player);

}
