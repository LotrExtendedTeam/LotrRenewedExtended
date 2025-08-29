package com.github.maximuslotro.lotrrextended.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifierRarity;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifierScroll;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedModifiers;

import lotr.common.init.ExtendedItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ExtendedSmithScroll extends Item {

	public ExtendedSmithScroll(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
		ExtendedModifiers modifier = getModifier(pStack);
		pTooltip.add(new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()).append(": ")
        		.append(new StringTextComponent((modifier.getModifierEffect()>0 ? modifier.getModifierType().getFrontValue() : "")+String.valueOf(modifier.getModifierEffect())+ " "))
        		.append(new TranslationTextComponent(modifier.getModifierType().getKeyLocal()))
        		.withStyle(TextFormatting.GRAY));
	}

	/**
	 * Returns the name of this scroll. This accepts an ItemStack so different stacks can have different names based on their NBT data.
	 * @param pStack The stack to get the name for
	 * @return An text component representation of the name for this scroll
	 */
	@Override
	public ITextComponent getName(ItemStack pStack) {
		if (pStack.getItem() == ExtendedItems.MODIFIER_SCROLL.get() && pStack.hasTag()) {
			ExtendedModifiers modifier = getModifier(pStack);
			return new TranslationTextComponent(this.getDescriptionId() + ".named", new TranslationTextComponent("enchant.lotrextended."+modifier.getLocalKey()));
		}
		return super.getName(pStack);
	}

	@Override
	public void fillItemCategory(ItemGroup pGroup, NonNullList<ItemStack> pItems) {
		if (this.allowdedIn(pGroup)) {
			for(ExtendedModifiers modifier : ExtendedModifiers.MODIFIER_MAP.values()) {
				if((modifier.getRarity()==ExtendedModifierRarity.BASIC || modifier.getRarity()==ExtendedModifierRarity.SKILLFUL) && modifier.getModifierLevel()>0) {
					if(modifier.getScroll()!=ExtendedModifierScroll.GOLD && pGroup != ItemGroup.TAB_SEARCH) {
						continue;
					}
					pItems.add(createForModifier(modifier));
				}
			}
		}
	}

	public static ItemStack createForModifier(ExtendedModifiers pInstance) {
		ItemStack itemstack = new ItemStack(ExtendedItems.MODIFIER_SCROLL.get());
		setModifier(itemstack, pInstance);
		return itemstack;
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
		return false;
	}

	public static void setModifier(ItemStack stack, ExtendedModifiers modifier) {
		stack.getOrCreateTag().putString("ExtendedModifierType", modifier.getNbtKey());
	}

	public static ExtendedModifiers getModifier(ItemStack stack) {
		if(!stack.hasTag() || !stack.getTag().contains("ExtendedModifierType")) {
			return ExtendedModifiers.UNKNOWN;
		}
		return ExtendedModifiers.MODIFIER_MAP.getOrDefault(stack.getTag().getString("ExtendedModifierType"), ExtendedModifiers.UNKNOWN);
	}
}
