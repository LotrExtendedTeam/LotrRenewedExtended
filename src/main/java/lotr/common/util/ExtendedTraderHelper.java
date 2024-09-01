package lotr.common.util;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import lotr.common.entity.npc.ExtendedTraderEntity;
import lotr.common.entity.npc.NPCEntity;

import lotr.common.item.LOTRSpawnEggItem;
import lotr.common.util.client.ExtendedClientsideSeperatedScreenHandler;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketOpenCoinExchange;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCPacketOpenTradeMenu;

public class ExtendedTraderHelper {

    private ExtendedTraderHelper() {}

    public static void playTraderSound(World level, BlockPos pos) {
        level.playSound(null, pos, ExtendedSounds.TRADE.get(), SoundCategory.PLAYERS, 1, 1);
    }

    public static ActionResultType openCoinExchange(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        ExtendedPacketHandler.sendToServer(new ExtendedCPacketOpenCoinExchange(entity.getId()));
        trader.updatePlayerTradingWith(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTradeMenu(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        ExtendedPacketHandler.sendToServer(new ExtendedCPacketOpenTradeMenu(entity.getId()));
        trader.updatePlayerTradingWith(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTraderGui(NPCEntity entity, PlayerEntity player, ExtendedTraderEntity trader) {
        if (trader.getPlayerTradingWith().isPresent()) {
            if (!entity.level.isClientSide) {
            	player.sendMessage(new TranslationTextComponent("gui.lotrextended.trading.trader_busy"), Util.NIL_UUID);
            }
            return ActionResultType.sidedSuccess(entity.level.isClientSide());
        }

        trader.updatePlayerTradingWith(player.getUUID());

        if (entity.level.isClientSide()) {
            ExtendedClientsideSeperatedScreenHandler.openTraderGui(entity, player);
        }
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static void removePlayerFromTrading(PlayerEntity player, EntityDataManager eData, DataParameter<Optional<UUID>> pKey) {
        if (eData.get(pKey).isPresent() && eData.get(pKey).get().equals(player.getUUID())) {
            eData.set(pKey, Optional.empty());
        }
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
        return openTraderGui(npc, player, trader);
    }

    public static void tickTrader(NPCEntity npc, ExtendedTraderEntity trader) {
        if (trader.isWanderingTrader()) {
            if (trader.getWanderingTraderTicksLeft() < 1) {
                // notify players the travelling trader has departed
                for (PlayerEntity player : npc.level.players()) {
                    player.sendMessage(new TranslationTextComponent("chat.wandering_trader.left_the_area", npc.getName().copy().withStyle(TextFormatting.YELLOW)), Util.NIL_UUID);
                }

                npc.remove();

                if (trader.getEscortUUIDs() != null && !trader.getEscortUUIDs().isEmpty()) {
                    AxisAlignedBB initial = new AxisAlignedBB(npc.getX(), npc.getY(), npc.getZ(),
                            npc.getX() + 1, npc.getY() + 1, npc.getZ() + 1);
                    List<NPCEntity> potentialEscorts = npc.level.getEntitiesOfClass(NPCEntity.class, initial.inflate(256));

                    for (NPCEntity entity : potentialEscorts) {
                        if (trader.getEscortUUIDs().contains(entity.getUUID())) {
                            entity.remove();
                        }
                    }
                }

            } else {
                trader.setWanderingTraderTicksLeft(trader.getWanderingTraderTicksLeft() - 1);
                // warn players nearby at 4 minutes left
                if (trader.getWanderingTraderTicksLeft() == 2400) {
                    AxisAlignedBB initial = new AxisAlignedBB(npc.getX(), npc.getY(), npc.getZ(), npc.getX() + 1, npc.getY() + 1, npc.getZ() + 1);
                    List<PlayerEntity> playersInRange = npc.level.getEntitiesOfClass(PlayerEntity.class, initial.inflate(80));

                    for (PlayerEntity player : playersInRange) {
                        player.sendMessage(trader.getDepartureLine(player), Util.NIL_UUID);
                    }
                }
            }
        }
    }

    public static void saveWanderingTraderData(ExtendedTraderEntity trader, List<UUID> escorts, CompoundNBT tag) {
        tag.putInt("wanderingTraderTimeLeft", trader.getWanderingTraderTicksLeft());
        tag.putBoolean("isWanderingTrader", trader.isWanderingTrader());

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
