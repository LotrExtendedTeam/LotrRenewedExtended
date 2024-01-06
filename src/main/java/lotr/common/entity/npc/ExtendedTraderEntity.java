package lotr.common.entity.npc;

import net.minecraft.entity.player.PlayerEntity;

public interface ExtendedTraderEntity {

    void playTradeSound();

    boolean isInteractingWithPlayer();

    void removePlayerFromTrading(PlayerEntity player);

    void openCoinExchange(PlayerEntity player);

}
