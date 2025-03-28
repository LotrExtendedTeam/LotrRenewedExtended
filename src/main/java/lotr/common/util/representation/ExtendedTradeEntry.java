package lotr.common.util.representation;

import net.minecraft.item.ItemStack;

public class ExtendedTradeEntry {

    private final ItemStack item;

    private final int min;
    private final int max;

    private final int quantity;

    public ExtendedTradeEntry(int quantity, ItemStack item, int min, int max) {
        this.item = item;
        this.min = min;
        this.max = max;
        this.quantity = quantity;
    }

    public ExtendedTradeEntry(int quantity, ItemStack item, int averagePrice) {
        this.item = item;
        this.min = Math.max(Math.round(averagePrice * 0.75f), 1);
        this.max = Math.max(Math.round(averagePrice * 1.25f), 1);
        this.quantity = quantity;
    }
  
    public ExtendedTradeEntry(ItemStack item, int min, int max) {
       this(100, item, min, max);
    }

    public ExtendedTradeEntry(ItemStack item, int averagePrice) {
        this(100, item, Math.max(Math.round(averagePrice * 0.75f), 1), Math.max(Math.round(averagePrice * 1.25f), 1));
     }
   
    public ItemStack getItem() {
        return item;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getQuantity() {
        return quantity;
    }
}
