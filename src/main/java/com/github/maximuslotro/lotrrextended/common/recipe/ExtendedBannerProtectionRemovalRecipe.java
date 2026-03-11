package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedMCBannerItem;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedBannerProtectionRemovalRecipe extends SpecialRecipe {
	public ExtendedBannerProtectionRemovalRecipe(ResourceLocation p_i48184_1_) {
		super(p_i48184_1_);
	}

	@Override
	public boolean matches(CraftingInventory craftInv, World world) {
		boolean foundBanner = false;
		ItemStack itemstack = ItemStack.EMPTY;
		for(int i = 0; i < craftInv.getContainerSize(); ++i) {
			ItemStack itemstack1 = craftInv.getItem(i);
			if (!itemstack1.isEmpty()) {
				if(itemstack1.getItem() instanceof ExtendedMCBannerItem) {
					if(foundBanner) {
						// Only one slot can be banner
						return false;
					}
					foundBanner = true;
					itemstack = itemstack1;
				}else {
					return false;
				}
			}
		}
		return foundBanner && itemstack.hasTag() && itemstack.getTag().contains("ProtectionData", 10);
	}

	@Override
	public ItemStack assemble(CraftingInventory craftInv) {
		ItemStack itemstack = ItemStack.EMPTY;

		for(int i = 0; i < craftInv.getContainerSize(); ++i) {
			ItemStack itemstack1 = craftInv.getItem(i);
			if (!itemstack1.isEmpty()) {
				itemstack = itemstack1.copy();
				break;
			}
		}
		if(itemstack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		itemstack.setCount(1);
		itemstack.removeTagKey("ProtectionData");
		return itemstack;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width > 0 && height > 0;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.EXTENDED_BANNER_PROT_REMOVAL.get();
	}
}