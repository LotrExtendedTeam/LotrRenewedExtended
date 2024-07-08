package com.github.maximuslotro.lotrrextended.common.inv;

import lotr.common.item.CustomColoredNameItem;
import lotr.common.item.RedBookItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BookItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraft.item.WritableBookItem;
import net.minecraft.item.WrittenBookItem;

public class ExtendedBookshelfSlot extends Slot {
	public ExtendedBookshelfSlot(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return isItemValidForPouchSlot(stack);
	}

	public static boolean isItemValidForPouchSlot(ItemStack stack) {
		return (stack.getItem() instanceof BookItem || stack.getItem() instanceof WritableBookItem || stack.getItem() instanceof WrittenBookItem
				 || stack.getItem() instanceof EnchantedBookItem || stack.getItem() instanceof KnowledgeBookItem
				 || stack.getItem() instanceof RedBookItem || stack.getItem() instanceof CustomColoredNameItem);
	}
}
