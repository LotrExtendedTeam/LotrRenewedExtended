package com.github.maximuslotro.lotrrextended.common.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface ExtendedISmithingRecipe extends IRecipe<IInventory> {


	abstract boolean isAdditionalIngredient(ItemStack pAddition);

	default void postProcess(IInventory slots, int pIndex, ItemStack craftedItem){
		ItemStack itemstack = slots.getItem(pIndex);
		itemstack.shrink(1);
		slots.setItem(pIndex, itemstack);
	}

	@Override
	default ItemStack getToastSymbol() {
		return new ItemStack(Blocks.SMITHING_TABLE);
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	@Override
	default boolean canCraftInDimensions(int pWidth, int pHeight) {
		return pWidth * pHeight >= 2;
	}

	@Override
	default IRecipeType<?> getType() {
		return ExtendedRecipeTypes.EXTENDED_SMITHING;
	}
}