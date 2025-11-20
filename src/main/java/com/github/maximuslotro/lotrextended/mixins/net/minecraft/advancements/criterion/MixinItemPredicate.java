package com.github.maximuslotro.lotrextended.mixins.net.minecraft.advancements.criterion;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

@Mixin(ItemPredicate.class)
public class MixinItemPredicate {
	@Final
	@Shadow
	private EnchantmentPredicate[] enchantments;

	@Inject(method = "matches(Lnet/minecraft/item/ItemStack;)Z", 
		at = @At(value = "INVOKE_ASSIGN", 
		target = "Lnet/minecraft/enchantment/EnchantmentHelper;deserializeEnchantments(Lnet/minecraft/nbt/ListNBT;)Ljava/util/Map;", ordinal = 0),
		locals = LocalCapture.CAPTURE_FAILHARD)
	 public void containedIn(ItemStack pItem, CallbackInfoReturnable<Boolean> cir, Map<Enchantment, Integer> map) {
		 if(!ExtendedModifierUtils.getModifierByTypeStack(pItem, ExtendedModifierType.TOOL_SILK).isUnknown()) {
			 map.put(Enchantments.SILK_TOUCH, 1);
		 }
	 }
}