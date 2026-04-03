package com.github.maximuslotro.lotrrextended.common.inventory;

import lotr.common.entity.npc.ExtendedRidableEntity;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.entity.IEquipable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedWargInventoryContainer extends Container {
	   private final IInventory horseContainer;
	   private final WargEntity warg;
	   public ExtendedWargInventoryContainer(int pContainerId, PlayerInventory pPlayerInventory, IInventory pContainer, final WargEntity pwarg) {
	      super(null, pContainerId);
	      this.horseContainer = pContainer;
	      this.warg = pwarg;
	      int i = 3;
	      pContainer.startOpen(pPlayerInventory.player);
	      int j = -18;
	      this.addSlot(new Slot(pContainer, 0, 8, 18) {
	         /**
	          * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	          */
	         @Override
			public boolean mayPlace(ItemStack pStack) {
	            return pStack.getItem() == Items.SADDLE && !this.hasItem() && ((IEquipable)warg).isSaddleable();
	         }

	         /**
	          * Actualy only call when we want to render the white square effect over the slots. Return always True, except
	          * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
	          */
	         @Override
			@OnlyIn(Dist.CLIENT)
	         public boolean isActive() {
	            return ((IEquipable)warg).isSaddleable();
	         }
	      });
	      this.addSlot(new Slot(pContainer, 1, 8, 36) {
	         /**
	          * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	          */
	         @Override
			public boolean mayPlace(ItemStack pStack) {
	            return ((ExtendedRidableEntity)warg).isArmor(pStack);
	         }

	         /**
	          * Actualy only call when we want to render the white square effect over the slots. Return always True, except
	          * for the armor slot of the Donkey/Mule (we can't interact with the Undead and Skeleton horses)
	          */
	         @Override
			@OnlyIn(Dist.CLIENT)
	         public boolean isActive() {
	            return ((ExtendedRidableEntity)warg).canWearArmor();
	         }

	         /**
	          * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
	          * case of armor slots)
	          */
	         @Override
			public int getMaxStackSize() {
	            return 1;
	         }
	      });
//	      if (warg instanceof AbstractChestedHorseEntity && ((AbstractChestedHorseEntity)warg).hasChest()) {
//	         for(int k = 0; k < 3; ++k) {
//	            for(int l = 0; l < ((AbstractChestedHorseEntity)warg).getInventoryColumns(); ++l) {
//	               this.addSlot(new Slot(pContainer, 2 + l + k * ((AbstractChestedHorseEntity)warg).getInventoryColumns(), 80 + l * 18, 18 + k * 18));
//	            }
//	         }
//	      }

	      for(int i1 = 0; i1 < 3; ++i1) {
	         for(int k1 = 0; k1 < 9; ++k1) {
	            this.addSlot(new Slot(pPlayerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 - 18));
	         }
	      }

	      for(int j1 = 0; j1 < 9; ++j1) {
	         this.addSlot(new Slot(pPlayerInventory, j1, 8 + j1 * 18, 142));
	      }

	   }

	   /**
	    * Determines whether supplied player can use this container
	    */
	   @Override
	public boolean stillValid(PlayerEntity pPlayer) {
	      return this.horseContainer.stillValid(pPlayer) && this.warg.isAlive() && this.warg.distanceTo(pPlayer) < 8.0F;
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
	         int i = this.horseContainer.getContainerSize();
	         if (pIndex < i) {
	            if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
	            if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (this.getSlot(0).mayPlace(itemstack1)) {
	            if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
	            int j = i + 27;
	            int k = j + 9;
	            if (pIndex >= j && pIndex < k) {
	               if (!this.moveItemStackTo(itemstack1, i, j, false)) {
	                  return ItemStack.EMPTY;
	               }
	            } else if (pIndex >= i && pIndex < j) {
	               if (!this.moveItemStackTo(itemstack1, j, k, false)) {
	                  return ItemStack.EMPTY;
	               }
	            } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
	               return ItemStack.EMPTY;
	            }

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

	   /**
	    * Called when the container is closed.
	    */
	   @Override
	public void removed(PlayerEntity pPlayer) {
	      super.removed(pPlayer);
	      this.horseContainer.stopOpen(pPlayer);
	   }
	}
