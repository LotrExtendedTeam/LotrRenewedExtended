package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ExtendedBookshelfContainer extends Container {
	private final IInventory container;
	public final int containerRows = 3;

	public static ExtendedBookshelfContainer threeRows(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		return new ExtendedBookshelfContainer(pContainerId, pPlayerInventory, new Inventory(27));
	}

	public static ExtendedBookshelfContainer threeRows(int pContainerId, PlayerInventory pPlayerInventory, IInventory bookshelfInventory, PacketBuffer extraData) {
		return new ExtendedBookshelfContainer(pContainerId, pPlayerInventory, bookshelfInventory);
	}

	public ExtendedBookshelfContainer(int pContainerId, PlayerInventory pPlayerInventory, IInventory bookshelfInventory) {
		super(ExtendedContainerTypes.BOOKSHELF.get(), pContainerId);
		checkContainerSize(bookshelfInventory, 27);
		this.container = bookshelfInventory;
		bookshelfInventory.startOpen(pPlayerInventory.player);
		int i = (this.containerRows - 4) * 18;

		for(int j = 0; j < this.containerRows; ++j) {
			for(int k = 0; k < 9; ++k) {
				this.addSlot(new ExtendedBookshelfSlot(bookshelfInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}

		for(int l = 0; l < 3; ++l) {
			for(int j1 = 0; j1 < 9; ++j1) {
				this.addSlot(new Slot(pPlayerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
			}
		}

		for(int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new Slot(pPlayerInventory, i1, 8 + i1 * 18, 161 + i));
		}
	}

	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		return this.container.stillValid(p_75145_1_);
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < this.containerRows * 9) {
				if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemstack;
	}

	@Override
	public void removed(PlayerEntity p_75134_1_) {
		super.removed(p_75134_1_);
		this.container.stopOpen(p_75134_1_);
	}
}