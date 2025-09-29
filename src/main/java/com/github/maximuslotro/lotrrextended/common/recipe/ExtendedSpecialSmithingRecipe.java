package com.github.maximuslotro.lotrrextended.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ExtendedSpecialSmithingRecipe implements ExtendedISmithingRecipe {
	private final ResourceLocation id;

	public ExtendedSpecialSmithingRecipe(ResourceLocation pId) {
		this.id = pId;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	/**
	 * If true, this recipe does not appear in the recipe book and does not respect recipe unlocking (and the doLimitedCrafting gamerule)
	 */
	@Override
	public boolean isSpecial() {
		return true;
	}

	/**
	 * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
	 * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
	 */
	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}
}
