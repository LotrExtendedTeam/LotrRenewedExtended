package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedRecipeSerializers;
import com.github.maximuslotro.lotrrextended.common.item.ExtendedChiselItem;

import lotr.common.init.ExtendedItems;
import lotr.common.init.LOTRItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedCoinSmithingTenRecipe extends ExtendedSpecialSmithingRecipe {

	public ExtendedCoinSmithingTenRecipe(ResourceLocation pId) {
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
		//if(ExtendedTags.Items.COIN_MINTING_TEN.contains(pInv.getItem(0).getItem()) && pInv.getItem(1).getDamageValue()+10<=pInv.getItem(1).getMaxDamage()) {
		if(LOTRItems.SILVER_INGOT.get()==pInv.getItem(0).getItem() && pInv.getItem(1).getDamageValue()+10<=pInv.getItem(1).getMaxDamage()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack assemble(IInventory pInv) {
		return new ItemStack(ExtendedItems.SILVER_COIN_TEN.get());
	}

	@Override
	public void postProcess(IInventory slots, int pIndex, ItemStack craftedItem){
		ItemStack itemstack = slots.getItem(pIndex);
		if(pIndex==0) {
			itemstack.shrink(1);
			slots.setItem(pIndex, itemstack);
		}else if(pIndex==1) {
			if(itemstack.getMaxDamage()<=itemstack.getDamageValue()+10) {
				slots.setItem(pIndex, ItemStack.EMPTY);
			}else {
				itemstack.setDamageValue(itemstack.getDamageValue()+10);
				slots.setItem(pIndex, itemstack);
			}
		}
	}

	@Override
	public boolean isAdditionalIngredient(ItemStack pAddition) {
		return pAddition.getItem() instanceof ExtendedChiselItem;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.EXTENDED_SMITHING_COIN_TEN.get();
	}
}
