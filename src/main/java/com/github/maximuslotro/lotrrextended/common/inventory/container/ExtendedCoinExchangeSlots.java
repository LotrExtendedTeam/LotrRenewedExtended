package com.github.maximuslotro.lotrrextended.common.inventory.container;

import lotr.common.util.CoinUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ExtendedCoinExchangeSlots {
	public static class ExtendedVisualCoinSlot extends Slot {
		public ExtendedVisualCoinSlot(IInventory pContainer, int pIndex, int pX, int pY) {
			super(pContainer, pIndex, pX, pY);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}

		@Override
		public boolean mayPickup(PlayerEntity p_82869_1_) {
			return false;
		}

		@Override
		public ItemStack onTake(PlayerEntity player, ItemStack stack) {
			return null;
		}

		@Override
		public boolean isActive() {
			return !getItem().isEmpty();
		}
	}

	public static class ExtendedOutputCoinSlot extends Slot {
		public ExtendedOutputCoinSlot(IInventory pContainer, int pIndex, int pX, int pY) {
			super(pContainer, pIndex, pX, pY);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}
	}

	public static class ExtendedInputCoinSlot extends Slot {
		public ExtendedInputCoinSlot(IInventory pContainer, int pIndex, int pX, int pY) {
			super(pContainer, pIndex, pX, pY);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return CoinUtils.isValidCoin(stack);
		}

		@Override
		public ItemStack onTake(PlayerEntity player, ItemStack stack) {
			//recalculateResultSlots(stack);
			return stack;
		}
	}
}
