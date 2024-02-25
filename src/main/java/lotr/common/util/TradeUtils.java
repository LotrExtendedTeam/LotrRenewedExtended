package lotr.common.util;

import lotr.common.util.representation.ExtendedTrade;
import lotr.common.util.representation.ExtendedTradeEntries;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;

public class TradeUtils {

    private TradeUtils() {}

    public static ArrayList<ExtendedTrade> loadAllTrades(ExtendedTradeEntries.TradeType type, CompoundNBT tag) {
        ListNBT listnbt = tag.getList(type.name(), 10);
        if (listnbt.isEmpty()) {
            return null;
        }

        ArrayList<ExtendedTrade> result = new ArrayList<>();

        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            ItemStack item = ItemStack.of(compoundnbt.getCompound("item"));
            int quantity = compoundnbt.getInt("quantityLeft");
            int amount = compoundnbt.getInt("amount");
            int originalQuantity = compoundnbt.getInt("startingQuantity");

            // handle a case in which it was never set - use default of 100
            if (originalQuantity == 0) {
                originalQuantity = 100;
            }

            ExtendedTrade trade = new ExtendedTrade(item, amount, quantity, originalQuantity);
            result.add(trade);
        }

        return result;
    }

    public static void saveAllTrades(ExtendedTradeEntries.TradeType type, ArrayList<ExtendedTrade> trades, CompoundNBT tag) {
        ListNBT listnbt = new ListNBT();

        for (ExtendedTrade trade : trades) {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt = trade.save(compoundnbt);
            listnbt.add(compoundnbt);
        }

        tag.put(type.name(), listnbt);
    }

   public static Inventory generateDisplayInventory(List<ExtendedTrade> buys, List<ExtendedTrade> sells) {
        Inventory result = new Inventory(18);

        int max = buys.size();
        if (buys.size() > 9) max = 9;
        for (int i = 0; i < max; i++) {
            ExtendedTrade trade = buys.get(i);
            result.setItem(i, trade.getItem());
        }

       max = sells.size();
       if (sells.size() > 9) max = 9;
       for (int i = 0; i < max; i++) {
           ExtendedTrade trade = sells.get(i);
           result.setItem(i + 9, trade.getItem());
       }

        return result;
   }

   public static boolean isValidSellItem(ItemStack stack, List<ExtendedTrade> itemsBought) {
        for (ExtendedTrade trade : itemsBought) {
           ItemStack itemBought = trade.getItem();
           if (itemBought.getItem().equals(stack.getItem())) {
               return true;
           }
        }

        return false;
   }
}
