package com.github.maximuslotro.lotrrextended.common.recipe;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedIEffectItem;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedSuspiciousStewRecipe extends SpecialRecipe {
	public ExtendedSuspiciousStewRecipe(ResourceLocation p_i50020_1_) {
		super(p_i50020_1_);
	}

	@Override
	public boolean matches(CraftingInventory pInv, World pLevel) {
		boolean hasIngrediant = false;
		boolean hasRedMushroom = false;
		boolean hasBrownMushroom = false;
		boolean hasBowl = false;
		for(int i = 0; i < pInv.getContainerSize(); ++i) {
			ItemStack itemstack = pInv.getItem(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() == Blocks.BROWN_MUSHROOM.asItem() && !hasBrownMushroom) {
					hasBrownMushroom = true;
				} else if (itemstack.getItem() == Blocks.RED_MUSHROOM.asItem() && !hasRedMushroom) {
					hasRedMushroom = true;
				} else if ((itemstack.getItem() instanceof ExtendedIEffectItem || (itemstack.getItem() instanceof BlockItem && ((BlockItem)itemstack.getItem()).getBlock() instanceof ExtendedIEffectItem)) && !hasIngrediant) {
					hasIngrediant = true;
				} else if (itemstack.getItem() != Items.BOWL && !hasBowl) {
					hasBowl = true;
				}else {
					return false;
				}
			}
		}
		return hasIngrediant && hasBrownMushroom && hasRedMushroom && hasBowl;
	}


	@Override
	public ItemStack assemble(CraftingInventory pInv) {
		ItemStack ingrediantItem = ItemStack.EMPTY;

		for(int i = 0; i < pInv.getContainerSize(); ++i) {
			ItemStack itemstack1 = pInv.getItem(i);
			if (!itemstack1.isEmpty() && itemstack1.getItem().is(ItemTags.SMALL_FLOWERS)) {
				ingrediantItem = itemstack1;
				break;
			}
		}
		ItemStack itemstack2 = new ItemStack(Items.SUSPICIOUS_STEW, 1);
		ExtendedIEffectItem flowerblock=null;
		if (ingrediantItem.getItem() instanceof ExtendedIEffectItem) {
			flowerblock = (ExtendedIEffectItem)ingrediantItem.getItem();
		}else if (ingrediantItem.getItem() instanceof BlockItem && ((BlockItem)ingrediantItem.getItem()).getBlock() instanceof ExtendedIEffectItem) {
			flowerblock = (ExtendedIEffectItem)((BlockItem)ingrediantItem.getItem()).getBlock();
		}
		if(flowerblock!=null) {
			Effect effect = flowerblock.getSuspiciousStewEffect();
			SuspiciousStewItem.saveMobEffect(itemstack2, effect, flowerblock.getEffectDuration());
		}
		return itemstack2;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return pWidth >= 2 && pHeight >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ExtendedRecipeSerializers.EXTENDED_SUSPICIOUS_STEW.get();
	}
}