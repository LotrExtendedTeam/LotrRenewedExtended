package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ExtendedItemStackUtils {

	public static boolean isSameItemStackItemAndTag(ItemStack pStack, ItemStack pStackOther) {
		return pStack.getItem() == pStackOther.getItem() && ItemStack.tagMatches(pStack, pStackOther);
	}

	public static boolean isSameItemStackItemAndTag(ItemStack stack, Item item) {
        return stack.getItem() == item && ItemStack.tagMatches(stack, new ItemStack(item));
	}

	public static ItemStack dyeItemStack(ItemStack stack, TextFormatting color) {
		if(color.isColor()) {
			return dyeItemStack(stack, color.getColor());
		}
		return stack;
	}

	public static ItemStack dyeItemStack(ItemStack stack, int color) {
		if(stack.getItem() instanceof IDyeableArmorItem) {
			((IDyeableArmorItem)(stack.getItem())).setColor(stack, color);
		}
		return stack;
	}
}