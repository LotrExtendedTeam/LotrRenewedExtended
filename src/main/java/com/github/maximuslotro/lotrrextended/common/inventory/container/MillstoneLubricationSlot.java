package com.github.maximuslotro.lotrrextended.common.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MillstoneLubricationSlot extends Slot {
	private final ExtendedMillstoneContainer menu;

	public MillstoneLubricationSlot(ExtendedMillstoneContainer pFurnaceMenu, IInventory pFurnaceContainer, int pSlot, int pXPosition, int pYPosition) {
		super(pFurnaceContainer, pSlot, pXPosition, pYPosition);
		this.menu = pFurnaceMenu;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 */
	public boolean mayPlace(ItemStack pStack) {
		return this.menu.isLubrication(pStack) || isBucket(pStack);
	}

	public int getMaxStackSize(ItemStack pStack) {
		return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
	}

	public static boolean isBucket(ItemStack pStack) {
		return pStack.getItem() == Items.BUCKET;
	}
}