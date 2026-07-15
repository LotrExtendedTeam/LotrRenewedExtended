package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IPlantable;

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

	public static IPlantable getPlantFromItem(ItemStack stack) {
		// Handle modded plants
		if(stack.getItem() instanceof IPlantable) {
			return (IPlantable) stack.getItem();
		}
		// Handle vanilla and modded plant block items
		if (stack.getItem() instanceof BlockItem && ((BlockItem)stack.getItem()).getBlock() instanceof IPlantable) {
			return ((IPlantable)((BlockItem)stack.getItem()).getBlock());
		}
		return null;
	}
}