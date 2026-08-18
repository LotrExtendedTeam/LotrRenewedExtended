package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface ExtendedICurcibleRecipe extends IRecipe<IInventory> {

	/**
	 * DO NOT CALL, call {@code assemble1} and {@code assemble2} instead!
	 * @param pInv The inventory container that is being crafted from
	 * @return The fraction result of the recipe
	 */
	@Override
	default ItemStack assemble(IInventory pInv) {
		return this.assemble1(pInv);
	}
	/**
	 * Returns an Item that is the Full result of this recipe
	 * @param pInv The inventory container that is being crafted from
	 * @return The fraction result of the recipe
	 */
	ItemStack assemble1(IInventory pInv);
	/**
	 * Returns an Item that is the Fraction result of this recipe
	 * @param pInv The inventory container that is being crafted from
	 * @return The fraction result of the recipe
	 */
	ItemStack assemble2(IInventory pInv); 
	
	int getMeltingTime();

	float getExperience();

	@Override
	default ItemStack getToastSymbol() {
		return new ItemStack(ExtendedBlocks.CRUCIBLE.get());
	}

	@Override
	default boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	default IRecipeType<?> getType() {
		return ExtendedRecipeTypes.CRUCIBLE;
	}
}