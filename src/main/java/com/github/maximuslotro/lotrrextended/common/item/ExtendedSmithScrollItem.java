package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierScroll;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;

import lotr.common.init.ExtendedItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedSmithScrollItem extends Item {

	public ExtendedSmithScrollItem(Properties pProperties) {
		super(pProperties);
	}

	/**
	 * Returns the name of this scroll. This accepts an ItemStack so different stacks can have different names based on their NBT data.
	 * @param pStack The stack to get the name for
	 * @return An text component representation of the name for this scroll
	 */
	@Override
	public ITextComponent getName(ItemStack pStack) {
		if (pStack.getItem() == ExtendedItems.MODIFIER_SCROLL.get() && pStack.hasTag()) {
			ExtendedModifier modifier = getModifier(pStack);
			return new TranslationTextComponent(this.getDescriptionId() + ".named", new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()));
		}
		return super.getName(pStack);
	}

	@Override
	public void fillItemCategory(ItemGroup pGroup, NonNullList<ItemStack> pItems) {
		if (this.allowdedIn(pGroup)) {
			for(ExtendedModifier modifier : ExtendedModifiers.MODIFIER_MAP.values()) {
				if((modifier.getRarity()==ExtendedModifierRarity.BASIC || modifier.getRarity()==ExtendedModifierRarity.SKILLFUL) && modifier.getModifierLevel()>0) {
					if(modifier.getScroll()!=ExtendedModifierScroll.GOLD && pGroup != ItemGroup.TAB_SEARCH) {
						continue;
					}
					pItems.add(createForModifier(modifier));
				}
			}
		}
	}

	public static ItemStack createForModifier(ExtendedModifier pInstance) {
		ItemStack itemstack = new ItemStack(ExtendedItems.MODIFIER_SCROLL.get());
		setModifier(itemstack, pInstance);
		return itemstack;
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
		return false;
	}

	public static void setModifier(ItemStack stack, ExtendedModifier modifier) {
		stack.getOrCreateTag().putString("ExtendedModifierType", modifier.getNbtKey());
	}

	public static ExtendedModifier getModifier(ItemStack stack) {
		if(!stack.hasTag() || !stack.getTag().contains("ExtendedModifierType")) {
			return ExtendedModifiers.UNKNOWN;
		}
		return ExtendedModifiers.MODIFIER_MAP.getOrDefault(stack.getTag().getString("ExtendedModifierType"), ExtendedModifiers.UNKNOWN);
	}
}
