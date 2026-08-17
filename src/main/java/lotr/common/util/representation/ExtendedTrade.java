package lotr.common.util.representation;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ExtendedTrade {

	private ItemStack item;

	private final int price;

	private int quantityLeft;

	private int originalQuantity;

	private boolean shouldTradePersist;

	public ExtendedTrade(ItemStack item, int pPrice, int quantityLeft, int originalQuantity, boolean shouldTradePersist) {
		this.item = item;
		this.price = pPrice;
		this.quantityLeft = quantityLeft;
		this.originalQuantity = originalQuantity;
		this.shouldTradePersist = shouldTradePersist;
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

	public void incrementQuantityLeft() {
		this.quantityLeft += 1;
	}

	public void setQuantityLeft(int quantityLeft) {
		this.quantityLeft = quantityLeft;
	}

	public boolean shouldTradePersist() {
		return shouldTradePersist;
	}

	public CompoundNBT save(CompoundNBT tag) {
		CompoundNBT itemTag = new CompoundNBT();
		itemTag = item.save(itemTag);
		tag.put("item", itemTag);
		tag.putInt("quantityLeft", quantityLeft);
		tag.putInt("price", price);
		tag.putInt("startingQuantity", originalQuantity);
		tag.putBoolean("shouldTradePersist", shouldTradePersist);
		return tag;
	}

	public static ExtendedTrade load(CompoundNBT tag) {
		ItemStack item = ItemStack.of(tag.getCompound("item"));
		int quantity = tag.getInt("quantityLeft");
		int amount;
		if(tag.contains("price")) {
			amount = tag.getInt("price");
		}else {
			amount = tag.getInt("amount"); // fallback to older name for value
		}
		boolean shouldTradePersist = true; // Handle a case where this previously didn't exist
		if(tag.contains("shouldTradePersist")) {
			shouldTradePersist = tag.getBoolean("shouldTradePersist");
		}
		int originalQuantity = tag.getInt("startingQuantity");
		// handle a case in which it was never set - use default of 100
		if (originalQuantity == 0) {
			originalQuantity = 100;
		}

		return new ExtendedTrade(item, amount, quantity, originalQuantity, shouldTradePersist);
	}

	public int getOriginalQuantity() {
		return originalQuantity;
	}

	@Override
	public String toString() {
		return "ExtendedTrade{item=" + item + ", price=" + price + ", quantityLeft=" + quantityLeft + ", originalQuantity="+ originalQuantity +"}";
	}

}
