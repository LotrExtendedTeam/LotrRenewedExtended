package lotr.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ExtendedSingleItemDataHolderIInventory implements IInventory{
	private ItemStack inventory = ItemStack.EMPTY;

	@Override
	public void clearContent() {
		inventory = ItemStack.EMPTY;
	}

	@Override
	public int getContainerSize() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return inventory == ItemStack.EMPTY;
	}

	@Override
	public ItemStack getItem(int p_70301_1_) {
		return inventory;
	}

	@Override
	public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
		return inventory = ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeItemNoUpdate(int p_70304_1_) {
		final ItemStack inventory1 = inventory;
		inventory = ItemStack.EMPTY;
		return inventory1;
	}

	@Override
	public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
		inventory = p_70299_2_;
	}

	@Override
	public void setChanged() {}

	@Override
	public boolean stillValid(PlayerEntity p_70300_1_) {
		return true;
	}

}
