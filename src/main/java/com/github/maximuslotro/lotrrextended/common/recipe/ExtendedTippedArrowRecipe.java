package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedTippedArrowRecipe extends SpecialRecipe {
	public ExtendedTippedArrowRecipe(ResourceLocation p_i48184_1_) {
		super(p_i48184_1_);
	}

	@Override
	public boolean matches(CraftingInventory craftInv, World world) {
		if (craftInv.getWidth() == 3 && craftInv.getHeight() == 3) {
			for(int i = 0; i < craftInv.getWidth(); ++i) {
				for(int j = 0; j < craftInv.getHeight(); ++j) {
					ItemStack itemstack = craftInv.getItem(i + j * craftInv.getWidth());
					if (itemstack.isEmpty()) {
						return false;
					}

					Item item = itemstack.getItem();
					if (i == 1 && j == 1) {
						if (item != ExtendedItems.BOTTLE_OF_POISON.get()) {
							return false;
						}
					} else if (item != Items.ARROW) {
						return false;
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemStack assemble(CraftingInventory p_77572_1_) {
		ItemStack itemstack = p_77572_1_.getItem(1 + p_77572_1_.getWidth());
		if (itemstack.getItem() != ExtendedItems.BOTTLE_OF_POISON.get()) {
			return ItemStack.EMPTY;
		} else {
			ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
			PotionUtils.setPotion(itemstack1, Potions.POISON);
			PotionUtils.setCustomEffects(itemstack1, PotionUtils.getCustomEffects(itemstack));
			return itemstack1;
		}
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width > 2 && height > 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.EXTENDED_TIPPED_ARROW.get();
	}
}