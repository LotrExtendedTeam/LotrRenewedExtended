package com.github.maximuslotro.lotrextended.mixins.net.minecraft.enchantment;

import java.util.Map;

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
import net.minecraft.item.ItemStack;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {

	@Inject(method = "getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", 
			at = @At(value = "RETURN", ordinal = 1), cancellable = true, locals = LocalCapture.PRINT)
	private static void getItemEnchantmentLevel$ExtendedMerged(Enchantment pEnchantment, ItemStack pStack, CallbackInfoReturnable<Integer> cir) {
		if(pStack.isEmpty() || !ExtendedModifierUtils.hasModifiers(pStack)) {
			return;
		}
		ExtendedModifierTypes modifierType = ExtendedModifierUtils.vanillaEnchantToModifierInteger(pEnchantment);
		Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
		int newValue = 0;
		if(modifiers.containsKey(modifierType)) {
			newValue = (int) modifiers.get(modifierType).getModifierEffect();
		}
//		ResourceLocation resourcelocation = Registry.ENCHANTMENT.getKey(pEnchantment);
//        ListNBT listnbt = pStack.getEnchantmentTags();
//        for(int i = 0; i < listnbt.size(); ++i) {
//           CompoundNBT compoundnbt = listnbt.getCompound(i);
//           ResourceLocation resourcelocation1 = ResourceLocation.tryParse(compoundnbt.getString("id"));
//           if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
//              return MathHelper.clamp(compoundnbt.getInt("lvl"), 0, 255);
//           }
//        }
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
}
