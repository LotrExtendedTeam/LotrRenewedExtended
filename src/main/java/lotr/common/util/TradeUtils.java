package lotr.common.util;

import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedTradeType;

import lotr.common.util.representation.ExtendedTrade;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class TradeUtils {

    public static ArrayList<ExtendedTrade> loadAllTrades(ExtendedTradeType type, CompoundNBT tag) {
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

    public static void saveAllTrades(ExtendedTradeType type, List<ExtendedTrade> trades, CompoundNBT tag) {
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

    	int max = Math.min(buys.size(), 9);
    	for (int i = 0; i < max; i++) {
    		result.setItem(i, buys.get(i).getItem());
    	}

    	max = Math.min(sells.size(), 9);
    	for (int i = 0; i < max; i++) {
    		result.setItem(9 + i, sells.get(i).getItem());
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
