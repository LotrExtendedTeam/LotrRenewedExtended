package lotr.common.util;

import com.github.maximuslotro.lotrExtended.common.init.ExtendedSounds;
import com.github.maximuslotro.lotrExtended.common.network.ExtendedPacketHandler;
import com.github.maximuslotro.lotrExtended.common.network.ExtendedSPacketOpenCoinExchange;
import com.github.maximuslotro.lotrExtended.common.network.ExtendedSPacketOpenTradeMenu;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.item.VesselDrinkItem;

import lotr.common.util.client.ExtendedTraderHelperClientside;
import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class ExtendedTraderHelper {

    public static ItemStack getStrongAlcohol(Item item) {
       ItemStack stack = new ItemStack(item);
       VesselDrinkItem.setPotency(stack, VesselDrinkItem.Potency.STRONG);
       return stack;
    }
    public static ItemStack getModerateAlcohol(Item item) {
        ItemStack stack = new ItemStack(item);
        VesselDrinkItem.setPotency(stack, VesselDrinkItem.Potency.MODERATE);
        return stack;
    }

    public static ItemStack getLightAlcohol(Item item) {
        ItemStack stack = new ItemStack(item);
        VesselDrinkItem.setPotency(stack, VesselDrinkItem.Potency.LIGHT);
        return stack;
    }


    private ExtendedTraderHelper() {}

    public static void playTraderSound(World level, BlockPos pos) {
        level.playSound(null, pos, ExtendedSounds.TRADE.get(), SoundCategory.MASTER, 1, 1);
    }

    public static ActionResultType openCoinExchange(NPCEntity entity, PlayerEntity player, HashSet<UUID> uuids) {
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenCoinExchange(entity.getId(), true));
        uuids.add(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTradeMenu(NPCEntity entity, PlayerEntity player, HashSet<UUID> uuids) {
        ExtendedPacketHandler.sendToServer(new ExtendedSPacketOpenTradeMenu(entity.getId()));
        uuids.add(player.getUUID());
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

    public static ActionResultType openTraderGui(NPCEntity entity, PlayerEntity player, HashSet<UUID> uuids) {
        // TODO: Not sure if we should keep this but otherwise it could be difficult to sync trades left
        if (!uuids.isEmpty()) {
            player.sendMessage(new StringTextComponent("The trader is busy with another player at the moment."), player.getUUID());
            return ActionResultType.sidedSuccess(entity.level.isClientSide());
        }

        uuids.add(player.getUUID());

        if (entity.level.isClientSide()) {
            ExtendedTraderHelperClientside.openTraderGui(entity, player);
        }
        return ActionResultType.sidedSuccess(entity.level.isClientSide());
    }

        public static boolean isInteractingWithPlayer(HashSet<UUID> uuids) {
        return !uuids.isEmpty();
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
}
