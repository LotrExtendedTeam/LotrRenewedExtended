package com.github.maximuslotro.lotrextended.mixins.net.minecraft.enchantment;

import java.util.Map;

import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierTypes;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {

	@Inject(method = "getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", 
			at = @At(value = "RETURN", ordinal = 1), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void getItemEnchantmentLevel$ExtendedMerged(Enchantment pEnchantment, ItemStack pStack, CallbackInfoReturnable<Integer> cir,
			ResourceLocation resourcelocation, ListNBT listnbt, int i, CompoundNBT compoundnbt, ResourceLocation resourcelocation1) {
		if(pStack.isEmpty() || !ExtendedModifierUtils.hasModifiers(pStack)) {
			return;
		}
		int vanillaValue = MathHelper.clamp(compoundnbt.getInt("lvl"), 0, 255);
		ExtendedModifierTypes modifierType = ExtendedModifierUtils.vanillaEnchantToModifierInteger(pEnchantment);
		Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
		int newValue = 0;
		if(modifiers.containsKey(modifierType)) {
			newValue = (int) modifiers.get(modifierType).getModifierEffect();
		}
		cir.setReturnValue(vanillaValue+newValue);
	}

	@Inject(method = "getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", 
			at = @At(value = "RETURN", ordinal = 2), cancellable = true)
	private static void getItemEnchantmentLevel$ExtendedOnlyModifiers(Enchantment pEnchantment, ItemStack pStack, CallbackInfoReturnable<Integer> cir) {
		if(!ExtendedModifierUtils.hasModifiers(pStack)) {
			return;
		}
		ExtendedModifierTypes modifierType = ExtendedModifierUtils.vanillaEnchantToModifierInteger(pEnchantment);
		Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
		if(modifiers.containsKey(modifierType)) {
			cir.setReturnValue((int) modifiers.get(modifierType).getModifierEffect());
		}
	}

	@Inject(method = "getDamageBonus(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/CreatureAttribute;)F", 
			at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	public static void getDamageBonus$Extended(ItemStack pStack, CreatureAttribute pCreatureAttribute, CallbackInfoReturnable<Float> cir, MutableFloat mutablefloat) {
		Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
		if(modifiers.containsKey(ExtendedModifierTypes.MELEE_DAMAGE)) {
			mutablefloat.add(modifiers.get(ExtendedModifierTypes.MELEE_DAMAGE).getModifierEffect());
		}
	}

	@Inject(method = "getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/util/DamageSource;)I", 
			at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	public static void getDamageProtection$Extended(Iterable<ItemStack> pStacks, DamageSource pSource, CallbackInfoReturnable<Integer> cir, MutableInt mutableint) {
		for(ItemStack stack : pStacks) {
			Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(stack);
			if(pSource.isFire() && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_FIRE)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_FIRE).getModifierEffect());
			}else if(pSource.isProjectile() && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_RANGED)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_RANGED).getModifierEffect());
			}else if(pSource.isProjectile() && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_BLAST)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_BLAST).getModifierEffect());
			}else if(pSource == DamageSource.FALL && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_FALLING)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_FALLING).getModifierEffect());
			}else {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION).getModifierEffect());
			}
		}
	}
}
