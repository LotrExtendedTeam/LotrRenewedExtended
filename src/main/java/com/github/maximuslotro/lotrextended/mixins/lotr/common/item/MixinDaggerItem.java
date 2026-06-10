package com.github.maximuslotro.lotrextended.mixins.lotr.common.item;

import org.spongepowered.asm.mixin.Mixin;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedDaggerItem;

import lotr.common.item.DaggerItem;
import lotr.common.item.LOTRSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

@Mixin(DaggerItem.class)
public abstract class MixinDaggerItem extends LOTRSwordItem {

	public MixinDaggerItem(IItemTier tier) {
		super(tier);
	}

	@Override
	public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
		if(ExtendedDaggerItem.isPoisoned(pStack)) {
			pTarget.addEffect(new EffectInstance(Effects.POISON, 20 * 4));
		}
		pStack.hurtAndBreak(1, pAttacker, (entity) -> {
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});
		return true;
	}
}
