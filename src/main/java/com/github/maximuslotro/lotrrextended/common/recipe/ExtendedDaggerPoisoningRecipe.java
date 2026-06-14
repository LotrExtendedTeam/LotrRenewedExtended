package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedDaggerItem;

import lotr.common.init.ExtendedItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedDaggerPoisoningRecipe extends SpecialRecipe {
	public ExtendedDaggerPoisoningRecipe(ResourceLocation p_i48184_1_) {
		super(p_i48184_1_);
	}

	@Override
	public boolean matches(CraftingInventory craftInv, World world) {
		boolean foundDagger = false;
		boolean foundPoison = false;
		ItemStack daggerStack = ItemStack.EMPTY;
		ItemStack poisonStack = ItemStack.EMPTY;
		for(int i = 0; i < craftInv.getContainerSize(); ++i) {
			ItemStack slotStack = craftInv.getItem(i);
			if (!slotStack.isEmpty()) {
				if(slotStack.getItem() instanceof ExtendedDaggerItem) {
					if(foundDagger) {
						return false;
					}
					foundDagger = true;
					daggerStack = slotStack;
				}else if(slotStack.getItem() == ExtendedItems.BOTTLE_OF_POISON.get()) {
					if(foundPoison) {
						return false;
					}
					foundPoison = true;
					poisonStack = slotStack;
				}
			}
		}
		return foundDagger && foundPoison && (!daggerStack.hasTag() || !daggerStack.getTag().contains(ExtendedDaggerItem.poisoned));
	}

	@Override
	public ItemStack assemble(CraftingInventory craftInv) {
		ItemStack daggerStack = ItemStack.EMPTY;
		for(int i = 0; i < craftInv.getContainerSize(); ++i) {
			ItemStack slotStack = craftInv.getItem(i);
			if (!slotStack.isEmpty()) {
				if(slotStack.getItem() instanceof ExtendedDaggerItem) {
					daggerStack = slotStack.copy();
				}
			}
		}
		if(daggerStack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		ExtendedDaggerItem.setPoisoned(daggerStack);
		return daggerStack;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.EXTENDED_DAGGER_POISONING.get();
	}
}