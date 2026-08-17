package com.github.maximuslotro.lotrrextended.common.utils;

import javax.annotation.Nullable;

import net.minecraft.inventory.Inventory;
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
	/**
	 * Checks whether an item stack can be added to the target inventory.
	 * <p>
	 * If {@code pFlagExtra} is {@code false}, standard inventory insertion rules apply via {@link Inventory#canAddItem(ItemStack)}.
	 * If {@code pFlagExtra} is {@code true}, insertion is restricted to at most one stack of the matching item type:
	 * <ul>
	 *   <li>If a matching stack already exists, returns {@code true} only if it has remaining capacity.</li>
	 *   <li>If no matching stack exists, returns {@code true} if an empty slot is available.</li>
	 * </ul>
	 * </p>
	 *
	 * @param pInv   the inventory to check
	 * @param pStack the item stack seeking insertion
	 * @param pFlagExtra  whether to enforce single-stack restricted insertion rules
	 * @return {@code true} if the item stack can be added; {@code false} otherwise
	 */
	public static boolean canAddItemToInventory(Inventory pInv, ItemStack pStack, boolean pFlagExtra) {
	    if (!pFlagExtra) {
	        return pInv.canAddItem(pStack);
	    }
	    boolean hasEmptySlot = false;
	    for (int i = 0; i < pInv.getContainerSize(); i++) {
	        ItemStack slotStack = pInv.getItem(i);
	        if (slotStack.isEmpty()) {
	            hasEmptySlot = true;
	        } else if (slotStack.sameItem(pStack) && ItemStack.tagMatches(slotStack, pStack)) {
	        	return slotStack.getCount() < slotStack.getMaxStackSize();
	        }
	    }
	    return hasEmptySlot;
	}
	/**
	 * Inserts an item stack into the inventory, optionally enforcing single-stack constraints.
	 * <p>
	 * If {@code pFlagExtra} is {@code false}, delegates to standard inventory behavior via {@link Inventory#addItem(ItemStack)}.
	 * <br>
	 * If {@code pFlagExtra} is {@code true}, limits the item type to a single stack total across the entire inventory:
	 * <ul>
	 *   <li>Attempts to merge into an existing matching stack up to its maximum capacity.</li>
	 *   <li>If no matching stack exists, populates the first available empty slot with a new stack.</li>
	 * </ul>
	 * Any excess items that cannot fit within the single-stack limit are retained in the returned stack.
	 * </p>
	 *
	 * @param pInv         the target inventory
	 * @param pInsertStack the item stack to insert
	 * @param pFlagExtra   whether to enforce single-stack restricted insertion rules
	 * @return the remaining item stack containing any items that could not be added
	 */
	public static ItemStack addStackToInventory(Inventory pInv, ItemStack pInsertStack, boolean pFlagExtra) {
		if (!pFlagExtra) {
	        return pInv.addItem(pInsertStack);
	    }
	    int existingSearchSlot = -1;
	    int emptySlot = -1;
	    // Search for an existing stack or the first empty slot
	    for (int i = 0; i < pInv.getContainerSize(); i++) {
	        ItemStack stack = pInv.getItem(i);
	        if (stack.isEmpty()) {
	            if (emptySlot == -1) {
	                emptySlot = i;
	            }
	        } else if (stack.sameItem(pInsertStack) && ItemStack.tagMatches(stack, pInsertStack)) {
	            existingSearchSlot = i;
	            break; // Found the existing stack in output
	        }
	    }
	    if (existingSearchSlot != -1) {
	        ItemStack existing = pInv.getItem(existingSearchSlot);
	        int spaceLeft = existing.getMaxStackSize() - existing.getCount();
	        if (spaceLeft > 0) {
	            int toAdd = Math.min(spaceLeft, pInsertStack.getCount());
	            existing.grow(toAdd);
	            pInsertStack.shrink(toAdd);
	        }
	        return pInsertStack;
	    }
	    if (emptySlot != -1) {
	        int toAdd = Math.min(pInsertStack.getMaxStackSize(), pInsertStack.getCount());
	        ItemStack newSeedStack = pInsertStack.copy();
	        newSeedStack.setCount(toAdd);
	        pInv.setItem(emptySlot, newSeedStack);
	        pInsertStack.shrink(toAdd);
	        return pInsertStack;
	    }
	    return pInsertStack;
	}
}