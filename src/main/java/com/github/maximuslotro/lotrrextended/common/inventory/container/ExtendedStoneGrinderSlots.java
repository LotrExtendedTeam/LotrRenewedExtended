package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedStoneGrinderTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ExtendedStoneGrinderSlots {
	public static class ExtendedStoneGrinderLubricationSlot extends Slot {
		public static final ResourceLocation EMPTY_BUCKET_TEXTURE = new ResourceLocation("lotr", "item/empty_keg_slot_bucket");
		private final ExtendedStoneGrinderContainer menu;

		public ExtendedStoneGrinderLubricationSlot(ExtendedStoneGrinderContainer grinderMenu, IInventory pContainer, int pSlot, int pXPosition, int pYPosition) {
			super(pContainer, pSlot, pXPosition, pYPosition);
			this.menu = grinderMenu;
			setBackground(PlayerContainer.BLOCK_ATLAS, EMPTY_BUCKET_TEXTURE);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		@Override
		public boolean mayPlace(ItemStack pStack) {
			return this.menu.isLubrication(pStack) || isBucket(pStack);
		}

		@Override
		public int getMaxStackSize(ItemStack pStack) {
			return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
		}

		public static boolean isBucket(ItemStack pStack) {
			return pStack.getItem() == Items.BUCKET;
		}
	}

	public static class ExtendedStoneGrinderResultSlot extends Slot {
		private final PlayerEntity player;
		private int removeCount;

		public ExtendedStoneGrinderResultSlot(PlayerEntity pPlayer, IInventory pContainer, int pSlot, int pXPosition, int pYPosition) {
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
				ExtendedCriteriaTriggers.CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, pStack, ExtendedBlocks.STONE_GRINDER.get());
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
			if (!this.player.level.isClientSide && this.container instanceof ExtendedStoneGrinderTileEntity) {
				((ExtendedStoneGrinderTileEntity)this.container).awardUsedRecipesAndPopExperience(this.player);
			}

			this.removeCount = 0;
			//net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, pStack);
		}
	}
}
