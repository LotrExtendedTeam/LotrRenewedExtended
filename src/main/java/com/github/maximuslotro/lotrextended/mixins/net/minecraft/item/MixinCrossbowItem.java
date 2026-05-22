package com.github.maximuslotro.lotrextended.mixins.net.minecraft.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedCrossbowItem;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(CrossbowItem.class)
public class MixinCrossbowItem {
	@Shadow
	public static boolean containsChargedProjectile(ItemStack pCrossbowStack, Item pAmmoItem) {return false;}

	@Inject(method = "getShootingPower(Lnet/minecraft/item/ItemStack;)F", at = @At("HEAD"), cancellable = true)
	private static void getShootingPower$Extended(ItemStack pCrossbowStack, CallbackInfoReturnable<Float> cir) {
		ExtendedModifier modifier = ExtendedModifierUtils.getModifierByTypeSafe(pCrossbowStack, ExtendedModifierType.RANGED_SPEED);
		if(!modifier.isUnknown()) {
			float defaultSeed = pCrossbowStack.getItem() == Items.CROSSBOW && containsChargedProjectile(pCrossbowStack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
			if(ExtendedCrossbowItem.getAdditionalLaunchSpeed(pCrossbowStack)>0) {
				defaultSeed = defaultSeed * ExtendedCrossbowItem.getAdditionalLaunchSpeed(pCrossbowStack);
			}
			cir.setReturnValue(defaultSeed * modifier.getModifierEffect());
		}
	}

	@Inject(method = "getArrow", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void getArrow$Extended(World pLevel, LivingEntity pLivingEntity, ItemStack pCrossbowStack, ItemStack pAmmoStack, 
			CallbackInfoReturnable<AbstractArrowEntity> cir, ArrowItem arrowitem, AbstractArrowEntity abstractarrowentity) {
		ExtendedModifier modifier = ExtendedModifierUtils.getModifierByTypeSafe(pCrossbowStack, ExtendedModifierType.RANGED_KNOCKBACK);
		if(!modifier.isUnknown()) {
			abstractarrowentity.setKnockback((int) modifier.getModifierEffect());
		}
	}

	@Inject(method = "getChargeDuration", at = @At("HEAD"), cancellable = true)
	private static void getChargeDuration$Extended(ItemStack pCrossbowStack, CallbackInfoReturnable<Integer> cir) {
		if(pCrossbowStack.getItem() instanceof ExtendedCrossbowItem) {
			int drawSpeed = ExtendedCrossbowItem.getDrawSpeedTicks(pCrossbowStack);
			int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, pCrossbowStack);
			// Also fix minecraft negative draw speed
			cir.setReturnValue(i == 0 ? drawSpeed : Math.max(1, drawSpeed - 5 * i));
		}
	}
}
