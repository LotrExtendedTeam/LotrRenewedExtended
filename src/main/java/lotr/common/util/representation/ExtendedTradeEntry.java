package lotr.common.util.representation;

import net.minecraft.item.ItemStack;

public class ExtendedTradeEntry {

    private final ItemStack item;

    private final int min;
    private final int max;

    private final int quantity;

    public ExtendedTradeEntry(ItemStack item, int min, int max, int quantity) {
        this.item = item;
        this.min = min;
        this.max = max;
        this.quantity = quantity;
    }

    public ExtendedTradeEntry(ItemStack item, int min, int max) {
       this(item, min, max, 100);
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
