package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedRecipeTypes;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedCrucibleTileEntity;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedCrucibleContainer extends Container {
	private final IInventory container;
	private final IIntArray data;
	protected final World level;
	private ExtendedCrucibleResultSlot secondaryResultSlot;

	public ExtendedCrucibleContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		this(pContainerId, pPlayerInventory, new Inventory(4), new IntArray(4), extraData);
	}

	public ExtendedCrucibleContainer(int pContainerId, PlayerInventory pPlayerInventory, IInventory pContainer, IIntArray pData, PacketBuffer extraData) {
		super(ExtendedContainerTypes.CRUCIBLE.get(), pContainerId);
		checkContainerSize(pContainer, 4);
		checkContainerDataCount(pData, 4);
		this.container = pContainer;
		this.data = pData;
		this.level = pPlayerInventory.player.level;
		this.addSlot(new Slot(pContainer, 0, 45, 15));
		this.addSlot(new ExtendedCrucibleFuelSlot(this, pContainer, 1, 45, 55));
		this.addSlot(new ExtendedCrucibleResultSlot(pPlayerInventory.player, pContainer, 2, 90, 35));
		this.addSlot(secondaryResultSlot = new ExtendedCrucibleResultSlot(pPlayerInventory.player, pContainer, 3, 117, 56) {
			@Override
			public boolean isActive() {
				return hasItem();
				
			}
		});

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

	public boolean showExpandedMenu() {
		return secondaryResultSlot.hasItem();
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
			if (pIndex == 2 || pIndex == 3) {
				if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(itemstack1, itemstack);
			}else if (pIndex != 1 && pIndex != 0) {
				if (this.canMelt(itemstack1)) {
					if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (this.isFuel(itemstack1)) {
					if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (pIndex >= 4 && pIndex < 31) {
					if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
						return ItemStack.EMPTY;
					}
				} else if (pIndex >= 31 && pIndex < 40 && !this.moveItemStackTo(itemstack1, 4, 31, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
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

	protected boolean canMelt(ItemStack pStack) {
		return this.level.getRecipeManager().getRecipeFor(ExtendedRecipeTypes.CRUCIBLE, new Inventory(pStack), this.level).isPresent();
	}


	protected boolean isFuel(ItemStack pStack) {
		return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, ExtendedRecipeTypes.CRUCIBLE) > 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getMeltingProgress() {
		int meltingProgress = this.data.get(2);
		int meltingTotalTime = this.data.get(3);
		return (meltingTotalTime != 0 && meltingProgress != 0) ? meltingProgress * 28 / meltingTotalTime : 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getLitProgress() {
		int litDuration = this.data.get(1);
		if (litDuration == 0) {
			litDuration = 200;
		}
		return this.data.get(0) * 13 / litDuration;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isLit() {
		return this.data.get(0) > 0;
	}

	public class ExtendedCrucibleFuelSlot extends Slot {
		private final ExtendedCrucibleContainer menu;

		public ExtendedCrucibleFuelSlot(ExtendedCrucibleContainer pFurnaceMenu, IInventory pFurnaceContainer, int pSlot, int pXPosition, int pYPosition) {
			super(pFurnaceContainer, pSlot, pXPosition, pYPosition);
			this.menu = pFurnaceMenu;
		}

		@Override
		public boolean mayPlace(ItemStack pStack) {
			return this.menu.isFuel(pStack);
		}
	}

	public static class ExtendedCrucibleResultSlot extends Slot {
		private final PlayerEntity player;
		private int removeCount;

		public ExtendedCrucibleResultSlot(PlayerEntity pPlayer, IInventory pContainer, int pSlot, int pXPosition, int pYPosition) {
			super(pContainer, pSlot, pXPosition, pYPosition);
			this.player = pPlayer;
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		@Override
		public boolean mayPlace(ItemStack pStack) {
			return false;
		}

		/**
		 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
		 */
		@Override
		public ItemStack remove(int pAmount) {
			if (this.hasItem()) {
				this.removeCount += Math.min(pAmount, this.getItem().getCount());
			}

			return super.remove(pAmount);
		}

		@Override
		public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
			if(!player.level.isClientSide) {
				ExtendedCriteriaTriggers.CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, pStack, ExtendedBlocks.CRUCIBLE.get());
			}
			this.checkTakeAchievements(pStack);
			super.onTake(pPlayer, pStack);
			return pStack;
		}

		/**
		 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
		 * internal count then calls onCrafting(item).
		 */
		@Override
		protected void onQuickCraft(ItemStack pStack, int pAmount) {
			this.removeCount += pAmount;
			this.checkTakeAchievements(pStack);
		}

		/**
		 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
		 */
		@Override
		protected void checkTakeAchievements(ItemStack pStack) {
			pStack.onCraftedBy(this.player.level, this.player, this.removeCount);
			if (!this.player.level.isClientSide && this.container instanceof ExtendedCrucibleTileEntity) {
				((ExtendedCrucibleTileEntity)this.container).awardUsedRecipesAndPopExperience(this.player);
			}

			this.removeCount = 0;
			net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, pStack);
		}
	}
}