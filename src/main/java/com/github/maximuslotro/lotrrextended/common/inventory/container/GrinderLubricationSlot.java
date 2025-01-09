package com.github.maximuslotro.lotrrextended.common.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class GrinderLubricationSlot extends Slot {
	public static final ResourceLocation EMPTY_BUCKET_TEXTURE = new ResourceLocation("lotr", "item/empty_keg_slot_bucket");
	private final ExtendedGrinderContainer menu;

	public GrinderLubricationSlot(ExtendedGrinderContainer grinderMenu, IInventory pFurnaceContainer, int pSlot, int pXPosition, int pYPosition) {
		super(pFurnaceContainer, pSlot, pXPosition, pYPosition);
		this.menu = grinderMenu;
		setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_BUCKET_TEXTURE);
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 */
	@Override
	public boolean mayPlace(ItemStack pStack) {
		return this.menu.isLubrication(pStack) || isBucket(pStack);
	}

	@Override
	public int getMaxStackSize(ItemStack pStack) {
		return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
	}

	public static boolean isBucket(ItemStack pStack) {
		return pStack.getItem() == Items.BUCKET;
	}
}