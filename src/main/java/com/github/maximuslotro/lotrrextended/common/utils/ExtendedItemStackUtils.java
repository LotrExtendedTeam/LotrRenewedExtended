package com.github.maximuslotro.lotrrextended.common.utils;

import javax.annotation.Nullable;

import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IPlantable;
/**
 * Utility methods for evaluating, manipulating, and extracting properties from {@link ItemStack} instances.
 */
public class ExtendedItemStackUtils {
	/**
     * Compares two item stacks to determine if they contain identical item instances and matching NBT tags.
     *
     * @param pStack      the primary item stack to compare
     * @param pStackOther the secondary item stack to compare
     * @return {@code true} if both stacks have the same item and matching NBT tags; {@code false} otherwise
     */
	public static boolean isSameItemStackItemAndTag(ItemStack pStack, ItemStack pStackOther) {
		return pStack.getItem() == pStackOther.getItem() && ItemStack.tagMatches(pStack, pStackOther);
	}
	/**
     * Compares an item stack against a standalone item instance to check if the stack matches 
     * both the item type and the item's default NBT tag state.
     *
     * @param stack the item stack to compare
     * @param item  the item instance to validate against
     * @return {@code true} if the stack matches the item and possesses clean or equivalent default NBT tags
     */
	public static boolean isSameItemStackItemAndTag(ItemStack stack, Item item) {
        return stack.getItem() == item && ItemStack.tagMatches(stack, new ItemStack(item));
	}
	/**
     * Applies a color to a dyeable item stack using a text formatting color value.
     * <p>
     * If the provided text formatting represents a valid color (non-formatting style code), 
     * the color integer is extracted and applied to the item stack.
     * </p>
     *
     * @param stack the target item stack to color
     * @param color the text formatting entry defining the target color
     * @return the modified item stack with the applied color, or the original stack if formatting is invalid
     */
	public static ItemStack dyeItemStack(ItemStack stack, TextFormatting color) {
		if(color.isColor()) {
			return dyeItemStack(stack, color.getColor());
		}
		return stack;
	}
	/**
     * Applies a raw RGB color integer to an item stack if its underlying item supports dynamic recoloring.
     *
     * @param stack the target item stack to color
     * @param color the RGB color integer value to assign
     * @return the modified item stack containing the updated color data
     */
	public static ItemStack dyeItemStack(ItemStack stack, int color) {
		if(stack.getItem() instanceof IDyeableArmorItem) {
			((IDyeableArmorItem)(stack.getItem())).setColor(stack, color);
		}
		return stack;
	}
	/**
     * Attempts to resolve an {@link IPlantable} instance from an item stack.
     * <p>
     * Evaluates whether the stack's item directly implements plantable logic or wraps a plantable 
     * block item (such as crop seeds or saplings).
     * </p>
     *
     * @param stack the item stack to inspect
     * @return the resolved {@link IPlantable} instance, or {@code null} if the item is not plantable
     */
	public static @Nullable IPlantable getPlantFromItem(ItemStack stack) {
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