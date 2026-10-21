package com.github.maximuslotro.lotrrextended.common.item;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierScroll;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifiers;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedItems;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ExtendedModifierScrollItem extends Item {
	private static final String NBTMODIFIER = "REScrollMod";
	public ExtendedModifierScrollItem(Properties pProperties) {
		super(pProperties);
	}

	/**
	 * Returns the name of this scroll. This accepts an ItemStack so different stacks can have different names based on their NBT data.
	 * @param pStack The stack to get the name for
	 * @return An text component representation of the name for this scroll
	 */
	@Override
	public ITextComponent getName(ItemStack pStack) {
		if(hasModifier(pStack)) {
			return new TranslationTextComponent(this.getDescriptionId() + ".named", new TranslationTextComponent("enchant.lotrextended."+getModifier(pStack).getLocalKey()));
		}
		return super.getName(pStack);
	}

	@Override
	public void fillItemCategory(ItemGroup pGroup, NonNullList<ItemStack> pItems) {
		if (this.allowdedIn(pGroup)) {
			for(ExtendedModifier modifier : ExtendedModifiers.MODIFIER_MAP.values()) {
				if((modifier.getRarity()==ExtendedModifierRarity.BASIC || modifier.getRarity()==ExtendedModifierRarity.SKILLFUL) && modifier.getModifierTeir()>0) {
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

	public static boolean hasModifier(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().contains(NBTMODIFIER, 8)) {
			return true;
		} else {
			return false;
		}
	}

	public static void setModifier(ItemStack stack, ExtendedModifier modifier) {
		stack.getOrCreateTag().putString(NBTMODIFIER, modifier.getNbtKey());
	}

	public static ExtendedModifier getModifier(ItemStack pStack) {
		if(!hasModifier(pStack)) {
			return ExtendedModifiers.UNKNOWN;
		}
		return ExtendedModifiers.MODIFIER_MAP.getOrDefault(pStack.getTag().getString(NBTMODIFIER), ExtendedModifiers.UNKNOWN);
	}
}
