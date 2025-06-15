package lotr.common.util.representation;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedTrade {

    private ItemStack item;

    private final int price;

    private int quantityLeft;

    private int originalQuantity;

    public ExtendedTrade(ItemStack item, int pPrice, int quantityLeft, int originalQuantity) {
        this.item = item;
        this.price = pPrice;
        this.quantityLeft = quantityLeft;
        this.originalQuantity = originalQuantity;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }

    public ItemStack getStack() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public CompoundNBT save(CompoundNBT tag) {
        CompoundNBT itemTag = new CompoundNBT();
        itemTag = item.save(itemTag);
        tag.put("item", itemTag);
        tag.putInt("quantityLeft", quantityLeft);
        tag.putInt("amount", price);
        tag.putInt("startingQuantity", originalQuantity);
        return tag;
    }

    public static ExtendedTrade load(CompoundNBT tag) {
    	ItemStack item = ItemStack.of(tag.getCompound("item"));
		int quantity = tag.getInt("quantityLeft");
		int amount = tag.getInt("amount");
		int originalQuantity = tag.getInt("startingQuantity");

		// handle a case in which it was never set - use default of 100
		if (originalQuantity == 0) {
			originalQuantity = 100;
		}

        return new ExtendedTrade(item, amount, quantity, originalQuantity);
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    @Override
    public String toString() {
    	return "ExtendedTrade{item=" + item + ", price=" + price + ", quantityLeft=" + quantityLeft + ", originalQuantity="+ originalQuantity +"}";
    }

}
