package com.github.maximuslotro.lotrrextended.common.utils;

import java.util.UUID;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class ExtendedNBTUtils {
	public static boolean getBoolean(CompoundNBT nbt, String key, boolean defaultValue) {
		if(!nbt.contains(key)) {
			return defaultValue;
		}else {
			return nbt.getBoolean(key);
		}
	}
	public static int getInt(CompoundNBT nbt, String key, int defaultValue) {
		if(!nbt.contains(key)) {
			return defaultValue;
		}else {
			return nbt.getInt(key);
		}
	}

	public static UUID getUUID(CompoundNBT nbt, String key, UUID defaultValue) {
		if(!nbt.contains(key)) {
			return defaultValue;
		}else {
			return nbt.getUUID(key);
		}
	}

	public static void writeInventoryToNBT(CompoundNBT pTag, Inventory inventory, String listName) {
		ListNBT listnbt = new ListNBT();
		int size = inventory.getContainerSize();
		for(int i = 0; i < size; ++i) {
			ItemStack itemstack = inventory.getItem(i);
			if (!itemstack.isEmpty()) {
				CompoundNBT compoundnbt = new CompoundNBT();
				compoundnbt.putByte("Slot", (byte)i);
				itemstack.save(compoundnbt);
				listnbt.add(compoundnbt);
			}
		}

		if (!listnbt.isEmpty()) {
			pTag.put(listName, listnbt);
		}
	}

	public static void readInventoryFromNBT(CompoundNBT pTag, Inventory inventory, String listName) {
		inventory.clearContent();
		if (!pTag.contains(listName, 9)) {
	        return; // Early exit if no data exists
	    }
		ListNBT listnbt = pTag.getList(listName, 10);
		int containerSize = inventory.getContainerSize();
		for(int i = 0; i < listnbt.size(); ++i) {
			CompoundNBT compoundnbt = listnbt.getCompound(i);
			int slot = compoundnbt.getByte("Slot") & 255;
			if (slot >= 0 && slot < containerSize) {
				inventory.setItem(slot, ItemStack.of(compoundnbt));
			}
		}
	}
}
