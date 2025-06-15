package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ExtendedItemStackUtils {

	public static boolean isSameItemStackItemAndTag(ItemStack pStack, ItemStack pStackOther) {
		return pStack.getItem() == pStackOther.getItem() && ItemStack.tagMatches(pStack, pStackOther);
	}

	public static boolean isSameItemStackItemAndTag(ItemStack stack, Item item) {
        return stack.getItem() == item && ItemStack.tagMatches(stack, new ItemStack(item));
    }
}
