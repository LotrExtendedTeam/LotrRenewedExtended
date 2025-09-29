package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedRecipeSerializers;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedGemstoneColoringItem;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ExtendedGemSmithingRecipe extends ExtendedSpecialSmithingRecipe {

	public ExtendedGemSmithingRecipe(ResourceLocation pId) {
		super(pId);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(IInventory pInv, World pLevel) {
		if(pInv.getItem(0).isEmpty() || pInv.getItem(1).isEmpty() || !isAdditionalIngredient(pInv.getItem(1))) {
			return false;
		}
		if(this.canApplyColor(pInv.getItem(0), ((ExtendedGemstoneColoringItem)pInv.getItem(1).getItem()).getExtendedGemstoneColor())) {
			return true;
		}
		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(IInventory pInv) {
		return applyColor(pInv.getItem(0).copy(), ((ExtendedGemstoneColoringItem)pInv.getItem(1).getItem()).getExtendedGemstoneColor());
	}

	@Override
	public boolean isAdditionalIngredient(ItemStack pAddition) {
		return pAddition.getItem() instanceof ExtendedGemstoneColoringItem;
	}

	public ItemStack applyColor(ItemStack stack, TextFormatting gemColor) {
		IFormattableTextComponent newName = stack.getHoverName().copy();
		stack.setHoverName(newName.withStyle(gemColor));
		return stack;
	}

	public boolean canApplyColor(ItemStack stack, TextFormatting gemColor) {
		if(!stack.hasCustomHoverName()) {
			return true;
		}
		ITextComponent name = stack.getHoverName();
		// TODO better color checking for more formatted item names
		if(name.getStyle().getColor()==Color.fromLegacyFormat(gemColor)) {
			return false;
		}
		return true;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.EXTENDED_SMITHING_GEM.get();
	}
}
