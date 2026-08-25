package com.github.maximuslotro.lotrrextended.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;

public class ExtendedDaggerItem extends ExtendedSwordItem {
	public static final String poisoned = "poisoned";

	public ExtendedDaggerItem(IItemTier pItemTeir, Properties pProperties) {
		super(pItemTeir, 0, -1.6F, -0.5F, pProperties);
	}

	public ExtendedDaggerItem(IItemTier pItemTeir, ItemGroup pCategory) {
		this(pItemTeir, new Properties().tab(pCategory));
	}

	@Override
	public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
		if(isPoisoned(pStack)) {
			pTarget.addEffect(new EffectInstance(Effects.POISON, 20 * 4));
		}
		pStack.hurtAndBreak(1, pAttacker, (entity) -> {
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	@Override
	public void fillItemCategory(ItemGroup pGroup, NonNullList<ItemStack> pItems) {
		if (this.allowdedIn(pGroup)) {
			pItems.add(new ItemStack(this));
			if(pGroup == ItemGroup.TAB_SEARCH) {
				ItemStack itemstack = new ItemStack(this);
				setPoisoned(itemstack);
				pItems.add(itemstack);
			}
		}
	}

	public static boolean isPoisoned(ItemStack itemStack) {
		CompoundNBT compoundnbt = itemStack.getTag();
		if(compoundnbt != null && compoundnbt.contains(poisoned)) {
			return compoundnbt.getBoolean(poisoned);
		}
		return false;
	}

	public static void setPoisoned(ItemStack itemStack) {
		itemStack.addTagElement(poisoned, ByteNBT.valueOf(true));
	}

	public static void removePoison(ItemStack itemStack) {
		itemStack.removeTagKey(poisoned);
	}
}
