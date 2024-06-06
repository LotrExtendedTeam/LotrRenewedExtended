package lotr.common.util;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;

import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.client.ExtendedTraderHelperClientside;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSPacketOpenCoinExchange;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedSPacketOpenTradeMenu;

public class ExtendedTraderHelper {

    private ExtendedTraderHelper() {}

    public static void playTraderSound(World level, BlockPos pos) {
        level.playSound(null, pos, ExtendedSounds.TRADE.get(), SoundCategory.MASTER, 1, 1);
    }

    public static ActionResultType openCoinExchange(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenCoinExchange(entity.getId(), true));
        trader.updatePlayerTradingWith(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTradeMenu(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenTradeMenu(entity.getId()));
        trader.updatePlayerTradingWith(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTraderGui(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        if (trader.getPlayerTradingWith().isPresent()) {
            if (!entity.level.isClientSide) player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_busy"), player.getUUID());
            return ActionResultType.sidedSuccess(entity.level.isClientSide());
        }

        trader.updatePlayerTradingWith(player.getUUID());

        if (entity.level.isClientSide()) {
            ExtendedTraderHelperClientside.openTraderGui(entity, player);
        }
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static boolean isInteractingWithPlayer(Optional<UUID> uuid) {
        return uuid.isPresent();
    }

    public static String getTraderName(ITextComponent displayName) {
        String result = displayName.getString();
        if (result.split(",").length > 0) {
            return result.split(",")[0];
        } else {
            return result;
        }
    }

    public static int[] getBoughtPrices(ArrayList<ExtendedTrade> bought) {
        int[] boughtItems = new int[bought.size()];
        for (int i = 0; i < bought.size(); i++) {
            boughtItems[i] = bought.get(i).getAmount();
        }
        return boughtItems;
    }

    public static int[] getSoldPrices(ArrayList<ExtendedTrade> sold) {
        int[] soldPrices = new int[sold.size()];
        for (int i = 0; i < sold.size(); i++) {
            soldPrices[i] = sold.get(i).getAmount();
        }
        return soldPrices;
    }

    public static int[] getSoldQuantity(ArrayList<ExtendedTrade> sold) {
        int[] soldQuantity = new int[sold.size()];
        for (int i = 0; i < sold.size(); i++) {
            soldQuantity[i] = sold.get(i).getQuantityLeft();
        }
        return soldQuantity;
    }

    public static int[] getBoughtQuantity(ArrayList<ExtendedTrade> bought) {
        int[] boughtQuantity = new int[bought.size()];
        for (int i = 0; i < bought.size(); i++) {
            boughtQuantity[i] = bought.get(i).getQuantityLeft();
        }
        return boughtQuantity;
    }

    public static int[] getSoldOriginalQuantity(ArrayList<ExtendedTrade> sold) {
        int[] soldOriginalQuantity = new int[sold.size()];
        for (int i = 0; i < sold.size(); i++) {
            soldOriginalQuantity[i] = sold.get(i).getOriginalQuantity();
        }
        return soldOriginalQuantity;
    }

    public static int[] getBoughtOriginalQuantity(ArrayList<ExtendedTrade> bought) {
        int[] boughtOriginalQuantity = new int[bought.size()];
        for (int i = 0; i < bought.size(); i++) {
            boughtOriginalQuantity[i] = bought.get(i).getOriginalQuantity();
        }
        return boughtOriginalQuantity;
    }

    public static ActionResultType mobInteract(NPCEntity npc, ExtendedTraderEntity trader, PlayerEntity player, Hand hand) {
        if (player.getItemInHand(hand).getItem() instanceof SpawnEggItem
                || player.getItemInHand(hand).getItem() instanceof LOTRSpawnEggItem) {
            return ActionResultType.SUCCESS;
        }
        if (trader.canTradeWith(player)) {
            return openTraderGui(npc, player, trader);
        } else {
            if (!player.level.isClientSide)
                player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_alignment_too_low"), player.getUUID());
            return ActionResultType.FAIL;
        }
    }

}
