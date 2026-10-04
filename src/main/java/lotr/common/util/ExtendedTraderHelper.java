package lotr.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenCaptainPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenCoinExchangePacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedCOpenTradeMenuPacket;
import com.github.maximuslotro.lotrrextended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedClientsideSeparatedScreenHandler;

import lotr.common.entity.npc.ExtendedIMerchantEntity;
import lotr.common.entity.npc.ExtendedIWanderingTraderEntity;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class ExtendedTraderHelper {

    public static void playTraderSound(Entity entity) {
    	playTraderSound(entity.level, entity.blockPosition());
    }
    public static void playTraderSound(World level, BlockPos pos) {
        level.playSound(null, pos, ExtendedSounds.TRADE.get(), SoundCategory.PLAYERS, 1, 1);
    }

    public static ActionResultType openCoinExchange(NPCEntity entity, PlayerEntity player) {
        ExtendedPacketHandler.sendToServer(new ExtendedCOpenCoinExchangePacket(entity.getId()));
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }
    public static ActionResultType openTradeMenu(NPCEntity entity, PlayerEntity player) {
        ExtendedPacketHandler.sendToServer(new ExtendedCOpenTradeMenuPacket(entity.getId()));
        ((ExtendedIMerchantEntity)entity).updatePlayerInteractingWith(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }
    public static ActionResultType openCaptainMenu(NPCEntity entity, PlayerEntity player) {
    	ExtendedPacketHandler.sendToServer(new ExtendedCOpenCaptainPacket(entity.getId()));
    	return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }
    public static ActionResultType openTraderGui(NPCEntity entity, PlayerEntity player) {
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ExtendedClientsideSeparatedScreenHandler.openTraderMainMenuGui(entity, player));
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
            boughtItems[i] = bought.get(i).getPrice();
        }
        return boughtItems;
    }

    public static int[] getSoldPrices(List<ExtendedTrade> sold) {
        int[] soldPrices = new int[sold.size()];
        for (int i = 0; i < sold.size(); i++) {
            soldPrices[i] = sold.get(i).getPrice();
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

    public static void saveWanderingTraderEscortUUIDs(ExtendedIWanderingTraderEntity trader, List<UUID> escorts, CompoundNBT tag) {
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
