package lotr.common.util.representation;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedTrade {

    private ItemStack item;

    private final int amount;

    private int quantityLeft;

    private int originalQuantity;

    public ExtendedTrade(ItemStack item, int amount, int quantityLeft, int originalQuantity) {
        this.item = item;
        this.amount = amount;
        this.quantityLeft = quantityLeft;
        this.originalQuantity = originalQuantity;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public CompoundNBT save(CompoundNBT tag) {
        CompoundNBT itemTag = new CompoundNBT();
        itemTag = item.save(itemTag);
        tag.put("item", itemTag);
        tag.putInt("quantityLeft", quantityLeft);
        tag.putInt("amount", amount);
        tag.putInt("startingQuantity", originalQuantity);
        return tag;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    @Override
    public String toString() {
        return "ExtendedTrade{" +
                "item=" + item +
                ", amount=" + amount +
                ", quantityLeft=" + quantityLeft +
                ", originalQuantity="+ originalQuantity +
                '}';
    }

}
