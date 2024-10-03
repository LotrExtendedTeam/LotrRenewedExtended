package com.github.maximuslotro.lotrrextended.common.inventory.container;

import com.github.maximuslotro.lotrrextended.common.tileentity.ExtendedGrinderTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ExtendedGrinderResultSlot extends Slot {
	   private final PlayerEntity player;
	   private int removeCount;

	   public ExtendedGrinderResultSlot(PlayerEntity pPlayer, IInventory pContainer, int pSlot, int pXPosition, int pYPosition) {
	      super(pContainer, pSlot, pXPosition, pYPosition);
	      this.player = pPlayer;
	   }

	   /**
	    * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	    */
	   public boolean mayPlace(ItemStack pStack) {
	      return false;
	   }

	   /**
	    * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
	    */
	   public ItemStack remove(int pAmount) {
	      if (this.hasItem()) {
	         this.removeCount += Math.min(pAmount, this.getItem().getCount());
	      }

	      return super.remove(pAmount);
	   }

	   public ItemStack onTake(PlayerEntity pPlayer, ItemStack pStack) {
	      this.checkTakeAchievements(pStack);
	      super.onTake(pPlayer, pStack);
	      return pStack;
	   }

	   /**
	    * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
	    * internal count then calls onCrafting(item).
	    */
	   protected void onQuickCraft(ItemStack pStack, int pAmount) {
	      this.removeCount += pAmount;
	      this.checkTakeAchievements(pStack);
	   }

	   /**
	    * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
	    */
	   protected void checkTakeAchievements(ItemStack pStack) {
	      pStack.onCraftedBy(this.player.level, this.player, this.removeCount);
	      if (!this.player.level.isClientSide && this.container instanceof ExtendedGrinderTileEntity) {
	         ((ExtendedGrinderTileEntity)this.container).awardUsedRecipesAndPopExperience(this.player);
	      }

	      this.removeCount = 0;
	      //net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, pStack);
	   }
	}