package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedRecipesTypes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface ExtendedISmeltingRecipe extends IRecipe<IInventory> {
	@Override
	default IRecipeType<?> getType() {
		return ExtendedRecipesTypes.EXTENDED_SMELTING;
	}

	abstract boolean isAdditionIngredient(ItemStack pAddition);

	default void postProcess(IInventory slots, int pIndex){
		ItemStack itemstack = slots.getItem(pIndex);
		itemstack.shrink(1);
		slots.setItem(pIndex, itemstack);
	}
}