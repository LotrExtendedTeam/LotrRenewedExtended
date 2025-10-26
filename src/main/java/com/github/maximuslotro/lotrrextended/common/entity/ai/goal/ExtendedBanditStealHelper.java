package com.github.maximuslotro.lotrrextended.common.entity.ai.goal;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.*;

public class ExtendedBanditStealHelper {

	public static void stealItemsFromPlayer(PlayerEntity player, ItemStackHandler npcInventory) {
		if (player.level.isClientSide) {
			return;
		}

		Random rand = new Random();

		List<Integer> filledSlots = new ArrayList<>();
		for (int i = 0; i < player.inventory.getContainerSize(); i++) {
			if (!player.inventory.getItem(i).isEmpty()) {
				filledSlots.add(i);
			}
		}

		if (filledSlots.isEmpty()) {
			return;
		}

		int stealCount = 1 + rand.nextInt(3); // 1–3
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

			if (!stack.isEmpty()) {
				player.level.addFreshEntity(new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack));
			}
		}
		player.sendMessage(new TranslationTextComponent("chat.lotrextended.bandit.stolen"), Util.NIL_UUID);
		player.inventory.setChanged();
		player.containerMenu.broadcastChanges();
	}
}

