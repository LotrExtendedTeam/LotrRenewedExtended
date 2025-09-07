package com.github.maximuslotro.lotrrextended.common.inventory.container;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedContainerTypes;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedRecipeTypes;
import com.github.maximuslotro.lotrrextended.common.recipe.ExtendedISmithingRecipe;

import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedSmithingTableContainer extends AbstractRepairContainer {
	public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.upgrade");
	private final World level;
	@Nullable
	private SmithingRecipe selectedRecipe;
	private ExtendedISmithingRecipe selectedExtendedRecipe;
	private final List<SmithingRecipe> recipes;
	/**
	 * Recipes added by extended injected into the logic
	 */
	private final List<ExtendedISmithingRecipe> extendedRecipes;

	public ExtendedSmithingTableContainer(int pContainerId, PlayerInventory pPlayerInventory) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL, new PacketBuffer(Unpooled.buffer()));
	}

	public ExtendedSmithingTableContainer(int pContainerId, PlayerInventory pPlayerInventory, PacketBuffer extraData) {
		this(pContainerId, pPlayerInventory, IWorldPosCallable.NULL, extraData);
	}

	public ExtendedSmithingTableContainer(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess) {
		this(pContainerId, pPlayerInventory, pAccess, new PacketBuffer(Unpooled.buffer()));
	}

	public ExtendedSmithingTableContainer(int pContainerId, PlayerInventory pPlayerInventory, IWorldPosCallable pAccess, PacketBuffer extraData) {
		super(ExtendedContainerTypes.EXTENDED_SMITHING.get(), pContainerId, pPlayerInventory, pAccess);
		this.level = pPlayerInventory.player.level;
		this.recipes = this.level.getRecipeManager().getAllRecipesFor(IRecipeType.SMITHING);
		// Extended Start
		this.extendedRecipes = this.level.getRecipeManager().getAllRecipesFor(ExtendedRecipeTypes.EXTENDED_SMITHING);
		// Extended End
	}

	@Override
	protected boolean isValidBlock(BlockState pState) {
		return pState.is(Blocks.SMITHING_TABLE);
	}

	@Override
	protected boolean mayPickup(PlayerEntity pPlayer, boolean pHasStack) {
		return (this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, this.level))
				||(this.selectedExtendedRecipe != null && this.selectedExtendedRecipe.matches(this.inputSlots, this.level));
	}

	@Override
	protected ItemStack onTake(PlayerEntity pPlayer, ItemStack pInputItem) {
		pInputItem.onCraftedBy(pPlayer.level, pPlayer, pInputItem.getCount());
		this.resultSlots.awardUsedRecipes(pPlayer);
		if(selectedExtendedRecipe!=null) {
			selectedExtendedRecipe.postProcess(inputSlots, 0, pInputItem);
			selectedExtendedRecipe.postProcess(inputSlots, 1, pInputItem);
		}else if(selectedRecipe!=null) {
			this.shrinkStackInSlot(0);
			this.shrinkStackInSlot(1);
		}else {
			ExtendedLog.info("Development error, missing smithing recipe");
		}
		this.access.execute((p_234653_0_, p_234653_1_) -> {
			p_234653_0_.levelEvent(1044, p_234653_1_, 0);
		});
		return pInputItem;
	}

	private void shrinkStackInSlot(int pIndex) {
		ItemStack itemstack = this.inputSlots.getItem(pIndex);
		itemstack.shrink(1);
		this.inputSlots.setItem(pIndex, itemstack);
	}

	/**
	 * called when the Anvil Input Slot changes, calculates the new result and puts it in the output slot
	 */
	 @Override
	 public void createResult() {
		 List<SmithingRecipe> list = this.level.getRecipeManager().getRecipesFor(IRecipeType.SMITHING, this.inputSlots, this.level);
		 if (list.isEmpty()) {
			 //Extended Start
			 List<ExtendedISmithingRecipe> extendedList = this.level.getRecipeManager().getRecipesFor(ExtendedRecipeTypes.EXTENDED_SMITHING, this.inputSlots, this.level);
			 if (list.isEmpty()) {
				 this.resultSlots.setItem(0, ItemStack.EMPTY);
				 this.selectedRecipe = null;
				 this.selectedExtendedRecipe = null;
			 } else {
				 this.selectedRecipe = null;
				 this.selectedExtendedRecipe = extendedList.get(0);
				 ItemStack itemstack = this.selectedExtendedRecipe.assemble(this.inputSlots);
				 this.resultSlots.setRecipeUsed(this.selectedExtendedRecipe);
				 this.resultSlots.setItem(0, itemstack);
			 }
			 //Extended End
		 } else {
			 this.selectedExtendedRecipe = null;
			 this.selectedRecipe = list.get(0);
			 ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots);
			 this.resultSlots.setRecipeUsed(this.selectedRecipe);
			 this.resultSlots.setItem(0, itemstack);
		 }
	 }

	 @Override
	 protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
		 return this.recipes.stream().anyMatch((recipe) -> {return recipe.isAdditionIngredient(pStack);}) 
				 || this.extendedRecipes.stream().anyMatch((recipe) -> {return recipe.isAdditionalIngredient(pStack);});
	 }

	 /**
	  * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
	  * null for the initial slot that was double-clicked.
	  */
	 @Override
	 public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
		 return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
	 }
}