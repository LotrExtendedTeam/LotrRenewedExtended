package lotr.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenCoinExchangePacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenTradeMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.utils.client.ExtendedClientsideSeparatedScreenHandler;

import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.ExtendedWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedTraderHelper {

    private ExtendedTraderHelper() {}

    public static void playTraderSound(World level, BlockPos pos) {
        level.playSound(null, pos, ExtendedSounds.TRADE.get(), SoundCategory.PLAYERS, 1, 1);
    }

    public static ActionResultType openCoinExchange(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        ExtendedPacketHandler.sendToServer(new ExtendedCOpenCoinExchangePacket(entity.getId()));
        trader.updatePlayerInteractingWith(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTradeMenu(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        ExtendedPacketHandler.sendToServer(new ExtendedCOpenTradeMenuPacket(entity.getId()));
        trader.updatePlayerInteractingWith(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTraderGui(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        if (trader.getPlayerInteractingWith().isPresent()) {
            if (!entity.level.isClientSide) {
            	player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_busy"), Util.NIL_UUID);
            }
            return ActionResultType.sidedSuccess(entity.level.isClientSide());
        }

        trader.updatePlayerInteractingWith(player.getUUID());

        if (entity.level.isClientSide()) {
            ExtendedClientsideSeparatedScreenHandler.openTraderMainMenuGui(entity, player);
        }
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static String getTraderName(ITextComponent displayName) {
        String result = displayName.getString();
        if (result.split(",").length > 0) {
            return result.split(",")[0];
        } else {
            return result;
        }
    }

    public static int[] getBoughtPrices(List<ExtendedTrade> bought) {
        int[] boughtItems = new int[bought.size()];
        for (int i = 0; i < bought.size(); i++) {
            boughtItems[i] = bought.get(i).getAmount();
        }
        return boughtItems;
    }

    public static int[] getSoldPrices(List<ExtendedTrade> sold) {
        int[] soldPrices = new int[sold.size()];
        for (int i = 0; i < sold.size(); i++) {
            soldPrices[i] = sold.get(i).getAmount();
        }
        return soldPrices;
    }

    public static int[] getSoldQuantity(List<ExtendedTrade> sold) {
        int[] soldQuantity = new int[sold.size()];
        for (int i = 0; i < sold.size(); i++) {
            soldQuantity[i] = sold.get(i).getQuantityLeft();
        }
        return soldQuantity;
    }

    public static int[] getBoughtQuantity(List<ExtendedTrade> bought) {
        int[] boughtQuantity = new int[bought.size()];
        for (int i = 0; i < bought.size(); i++) {
            boughtQuantity[i] = bought.get(i).getQuantityLeft();
        }
        return boughtQuantity;
    }

    public static int[] getSoldOriginalQuantity(List<ExtendedTrade> sold) {
        int[] soldOriginalQuantity = new int[sold.size()];
        for (int i = 0; i < sold.size(); i++) {
            soldOriginalQuantity[i] = sold.get(i).getOriginalQuantity();
        }
        return soldOriginalQuantity;
    }

    public static int[] getBoughtOriginalQuantity(List<ExtendedTrade> bought) {
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
        return openTraderGui(npc, player, trader);
    }

    public static void tickTrader(NPCEntity npc, ExtendedTraderEntity trader) {
    	// every 1 minute allow one more trade in both buys and sells to be done again
        // TODO: Add support for different trades to tick back at different rates - e.g. a rare powerful sword should take
        //  20-30 minutes to become available again whereas selling another individual pipe-weed might be just a few seconds
        if (trader.setLastTickCooldownReduced() >= 1200) {
            for (ExtendedTrade trade : trader.getTraderData().getSells()) {
                if (trade.getQuantityLeft() < trade.getOriginalQuantity()) {
                    trade.setQuantityLeft(trade.getQuantityLeft() + 1);
                }
            }

            for (ExtendedTrade trade : trader.getTraderData().getBuys()) {
                if (trade.getQuantityLeft() < trade.getOriginalQuantity()) {
                    trade.setQuantityLeft(trade.getQuantityLeft() + 1);
                }
            }
            trader.getLastTickCooldownReduced(0);
        } else {
        	trader.getLastTickCooldownReduced(trader.setLastTickCooldownReduced()+1);
        }
    }

    public static void saveWanderingTraderEscortUUIDs(ExtendedWanderingTraderEntity trader, List<UUID> escorts, CompoundNBT tag) {
        ListNBT listnbt = new ListNBT();
        for (UUID escort : escorts) {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("UUID", escort.toString());
            listnbt.add(compoundnbt);
        }

        tag.put("escorts", listnbt);
    }

    public static List<UUID> loadEscortUUIDs(CompoundNBT tag) {
        ListNBT listnbt = tag.getList("escorts", 10);
        if (listnbt.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<UUID> result = new ArrayList<>();

        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            String strUUID = compoundnbt.getString("UUID");
            UUID uuid = null;
            try {
               uuid = UUID.fromString(strUUID);
            } catch (Exception ex) {
                ExtendedLog.error(strUUID + " could not be parsed as a UUID - ignoring");
                continue;
            }

            result.add(uuid);
        }

        return result;
    }

}
