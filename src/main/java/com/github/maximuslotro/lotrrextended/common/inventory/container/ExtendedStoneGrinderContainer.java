package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedTags;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipes;

import lotr.common.init.ExtendedContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public class ExtendedStoneGrinderContainer extends Container {
	private final IInventory container;
	private final IIntArray data;
	protected final World level;

	public ExtendedStoneGrinderContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		this(pContainerId, pPlayerInventory, new Inventory(3), new IntArray(3), extraData);
	}

	public ExtendedStoneGrinderContainer(int pContainerId, PlayerInventory pPlayerInventory, IInventory pContainer, IIntArray pData, PacketBuffer extraData) {
		super(ExtendedContainers.STONE_GRINDER.get(), pContainerId);
		checkContainerSize(pContainer, 3);
		checkContainerDataCount(pData, 3);
		this.container = pContainer;
		this.data = pData;
		this.level = pPlayerInventory.player.level;
		this.addSlot(new Slot(pContainer, 0, 80, 16));
		this.addSlot(new ExtendedStoneGrinderLubricationSlot(this, pContainer, 1, 114, 19));
		this.addSlot(new ExtendedStoneGrinderResultSlot(pPlayerInventory.player, pContainer, 2, 80, 58));

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
		}

		this.addDataSlots(pData);
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean stillValid(PlayerEntity pPlayer) {
		return this.container.stillValid(pPlayer);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack quickMoveStack(PlayerEntity pPlayer, int pIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (pIndex == 2) {
				if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (pIndex != 1 && pIndex != 0) {
				if (this.canMill(itemstack1)) {
					if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (this.isLubrication(itemstack1)) {
					if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (pIndex >= 3 && pIndex < 30) {
					if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (pIndex >= 30 && pIndex < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(pPlayer, itemstack1);
		}

		return itemstack;
	}

	protected boolean canMill(ItemStack pStack) {
		return this.level.getRecipeManager().getRecipeFor(ExtendedRecipes.MILLSTONE, new Inventory(pStack), this.level).isPresent();
	}

	protected boolean isLubrication(ItemStack pStack) {
		return ExtendedTags.Items.STONE_GRINDER_LUBRICANTS.contains(pStack.getItem());
	}

	public int getMillingProgress() {
		int i = this.data.get(0);
		int j = this.data.get(1);
		return j != 0 && i != 0 ? i * 17 / j : 0;
	}

	public boolean isMilling() {
		return this.data.get(0) > 0;
	}

	public boolean isLubed() {
		return this.data.get(2) > 0;
	}
}