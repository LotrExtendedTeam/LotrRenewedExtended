package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.*;

public class ExtendedBanditStealHelper {

	public static boolean stealItemsFromPlayer(PlayerEntity player, ItemStackHandler npcInventory) {
		List<Integer> filledSlots = getApplicableSlots(player.inventory);

		if (filledSlots.isEmpty()) {
			return false;
		}

		int stealCount = 1 + player.random.nextInt(3); // 1–3
		Collections.shuffle(filledSlots);

		for (int i = 0; i < Math.min(stealCount, filledSlots.size()); i++) {
			int slotIndex = filledSlots.get(i);
			ItemStack stack = player.inventory.getItem(slotIndex).copy();

			player.inventory.removeItem(slotIndex, stack.getCount());

			for (int slot = 0; slot < npcInventory.getSlots(); slot++) {
				ItemStack remainder = npcInventory.insertItem(slot, stack, false);
				if (remainder.isEmpty()) {
					stack = ItemStack.EMPTY;
					break;
				}
				stack = remainder;
			}
			if (!player.inventory.add(stack)) {
				player.level.addFreshEntity(new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack));
			}
		}
		player.sendMessage(new TranslationTextComponent("chat.lotrextended.bandit.stolen"), Util.NIL_UUID);
		player.inventory.setChanged();
		player.containerMenu.broadcastChanges();
		return true;
	}

	public static boolean canSteal(PlayerInventory inventory) {
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			if (!inventory.getItem(i).isEmpty()) {
				boolean isSelected = (i == inventory.selected);
				boolean isArmor = (i >= 36 && i <= 39);
				if (!isSelected && !isArmor) {
					return true; // At least one valid stealable slot
				}
			}
		}
		return false;
	}

	public static List<Integer> getApplicableSlots(PlayerInventory inventory){
		List<Integer> filledSlots = new ArrayList<>();
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			if (!inventory.getItem(i).isEmpty()) {
				boolean isSelected = (i == inventory.selected);
				boolean isArmor = (i >= 36 && i <= 39);
				if (!isSelected && !isArmor) {
					filledSlots.add(i);
				}
			}
		}
		return filledSlots;
	}
}

