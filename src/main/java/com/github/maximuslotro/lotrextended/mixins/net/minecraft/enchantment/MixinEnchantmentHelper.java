package com.github.maximuslotro.lotrextended.mixins.net.minecraft.enchantment;

import java.util.Map;

import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifierType;
import com.github.maximuslotro.lotrrextended.common.utils.AttributeUtils;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.google.common.collect.Multimap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeMod;

/**
 * A Mixin targeting {@link EnchantmentHelper} to integrate custom {@link ExtendedModifier}s
 * with vanilla enchantment logic.
 * <p>
 * This mixin extends how enchantment levels, damage bonuses, and protection values are
 * calculated by merging modifier effects from {@link ExtendedModifierUtils} into the
 * corresponding vanilla methods.
 * </p>
 * @Mixin (EnchantmentHelper.class)
 */
@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {
	/**
	 * Injects into {@link EnchantmentHelper#getItemEnchantmentLevel(Enchantment, ItemStack)}
	 * to merge custom modifier levels with vanilla enchantment levels.
	 * <p>
	 * Logic:
	 * <ul>
	 *   <li>If the item has no modifiers, return vanilla behavior unchanged.</li>
	 *   <li>Otherwise, clamp the vanilla enchant level between 0 and 255, then add the
	 *       matching modifier effect from {@link ExtendedModifierUtils}.</li>
	 * </ul>
	 * </p>
	 *
	 * @param pEnchantment       the enchantment being queried
	 * @param pStack             the item stack to check
	 * @param cir                callback used to override the method’s return value
	 * @param resourcelocation   internal vanilla local variable for enchantment ID
	 * @param listnbt            internal vanilla local variable for enchantment list
	 * @param i                  internal vanilla local variable (iteration index)
	 * @param compoundnbt        internal vanilla local variable (enchantment tag data)
	 * @param resourcelocation1  internal vanilla local variable for lookup
	 * @Inject (method = "getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", at = @At(value = "RETURN", ordinal = 1), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	 */
	@Inject(method = "getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", 
		at = @At(value = "RETURN", ordinal = 1), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void getItemEnchantmentLevel$ExtendedMerged(Enchantment pEnchantment, ItemStack pStack, CallbackInfoReturnable<Integer> cir,
		ResourceLocation resourcelocation, ListNBT listnbt, int i, CompoundNBT compoundnbt, ResourceLocation resourcelocation1) {
		if(pStack.isEmpty() || !ExtendedModifierUtils.hasModifiers(pStack)) {
			return;
		}
		int vanillaValue = MathHelper.clamp(compoundnbt.getInt("lvl"), 0, 255);
		ExtendedModifierType modifierType = ExtendedModifierUtils.vanillaEnchantToModifier(pEnchantment, false);
		ExtendedModifier modifier = ExtendedModifierUtils.getModifierByTypeSafe(pStack, modifierType);
		if(!modifier.isUnknown()) {
			cir.setReturnValue(vanillaValue+ (int)modifier.getModifierEffect());
		}
	}
	/**
	 * Injects into {@link EnchantmentHelper#getItemEnchantmentLevel(Enchantment, ItemStack)}
	 * to handle the case where only modifiers exist without a corresponding vanilla enchantment.
	 * <p>
	 * If a modifier matches the queried enchantment type, its effect value is returned directly.
	 * </p>
	 *
	 * @param pEnchantment the enchantment being queried
	 * @param pStack       the item stack to check
	 * @param cir          callback used to override the method’s return value
	 * @Inject (method = "getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
	 */
	@Inject(method = "getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", 
		at = @At(value = "RETURN", ordinal = 2), cancellable = true)
	private static void getItemEnchantmentLevel$ExtendedOnlyModifiers(Enchantment pEnchantment, ItemStack pStack, CallbackInfoReturnable<Integer> cir) {
		if(!ExtendedModifierUtils.hasModifiers(pStack)) {
			return;
		}
		ExtendedModifierType modifierType = ExtendedModifierUtils.vanillaEnchantToModifier(pEnchantment, false);
		ExtendedModifier modifier = ExtendedModifierUtils.getModifierByTypeSafe(pStack, modifierType);
		if(!modifier.isUnknown()) {
			cir.setReturnValue((int) modifier.getModifierEffect());
		}
	}
	/**
	 * Injects into {@link EnchantmentHelper#getDamageBonus(ItemStack, CreatureAttribute)} to
	 * apply additional melee damage from custom modifiers.
	 * <p>
	 * If {@link ExtendedModifierType#MELEE_DAMAGE} is present, its effect is added to the
	 * running {@code mutablefloat} damage bonus value.
	 * </p>
	 *
	 * @param pStack             the attacking item stack
	 * @param pCreatureAttribute the target creature’s attribute (e.g. UNDEAD, ARTHROPOD)
	 * @param cir                callback used to override the method’s return value
	 * @param mutablefloat       local variable tracking the accumulated damage bonus
	 * @Inject (method = "getDamageBonus(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/CreatureAttribute;)F", at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	 */
	@Inject(method = "getDamageBonus(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/CreatureAttribute;)F", 
		at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void getDamageBonus$Extended(ItemStack pStack, CreatureAttribute pCreatureAttribute, CallbackInfoReturnable<Float> cir, MutableFloat mutablefloat) {
		ExtendedModifier modifier = ExtendedModifierUtils.getModifierByTypeSafe(pStack, ExtendedModifierType.MELEE_DAMAGE);
		if(!modifier.isUnknown()) {
			mutablefloat.add(modifier.getModifierEffect());
			cir.setReturnValue(mutablefloat.getValue());
		}
	}
	/**
	 * Injects into {@link EnchantmentHelper#getDamageProtection(Iterable, DamageSource)} to
	 * extend protection calculations with custom modifiers.
	 * <p>
	 * Iterates through all equipped {@link ItemStack}s and applies any protection modifiers
	 * that correspond to the incoming {@link DamageSource}.
	 * <ul>
	 *   <li>{@link ExtendedModifierType#PROTECTION_FIRE} for fire damage</li>
	 *   <li>{@link ExtendedModifierType#PROTECTION_RANGED} for projectiles</li>
	 *   <li>{@link ExtendedModifierType#PROTECTION_BLAST} for explosions</li>
	 *   <li>{@link ExtendedModifierType#PROTECTION_FALLING} for fall damage</li>
	 * </ul>
	 * </p>
	 *
	 * @param pStacks   the armor and held items being checked
	 * @param pSource   the source of incoming damage
	 * @param cir       callback used to override the method’s return value
	 * @param mutableint local variable tracking the accumulated protection value
	 * @Inject (method = "getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/util/DamageSource;)I", at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	 */
	@Inject(method = "getDamageProtection(Ljava/lang/Iterable;Lnet/minecraft/util/DamageSource;)I", 
		at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void getDamageProtection$Extended(Iterable<ItemStack> pStacks, DamageSource pSource, CallbackInfoReturnable<Integer> cir, MutableInt mutableint) {
		int mod = 0;
		for(ItemStack stack : pStacks) {
			Map<ExtendedModifierType, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByTypeSafe(stack);
			if(pSource.isFire() && modifiers.containsKey(ExtendedModifierType.PROTECTION_FIRE)) {
				mod += modifiers.get(ExtendedModifierType.PROTECTION_FIRE).getModifierEffect();
			}
			if(pSource.isProjectile() && modifiers.containsKey(ExtendedModifierType.PROTECTION_RANGED)) {
				mod += modifiers.get(ExtendedModifierType.PROTECTION_RANGED).getModifierEffect();
			}
			if(pSource.isExplosion() && modifiers.containsKey(ExtendedModifierType.PROTECTION_BLAST)) {
				mod += modifiers.get(ExtendedModifierType.PROTECTION_BLAST).getModifierEffect();
			}
			if(pSource == DamageSource.FALL && modifiers.containsKey(ExtendedModifierType.PROTECTION_FALLING)) {
				mod += modifiers.get(ExtendedModifierType.PROTECTION_FALLING).getModifierEffect();
			}
			if(modifiers.containsKey(ExtendedModifierType.PROTECTION_MITHRIL) && pSource.getEntity()!=null && containsLongWeapon$Extended(pSource.getEntity().getHandSlots())) {
				mod += modifiers.get(ExtendedModifierType.PROTECTION_MITHRIL).getModifierEffect();
			}
			//if(modifiers.containsKey(ExtendedModifierType.PROTECTION)){
			//	mod += modifiers.get(ExtendedModifierType.PROTECTION).getModifierEffect();
			//}
		}
		if(mod>0) {
			cir.setReturnValue(mutableint.getValue()+mod);
		}
	}

	@Unique
	private static boolean containsLongWeapon$Extended(Iterable<ItemStack> iterable) {
		for (ItemStack stack : iterable) {
			if (stack.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, stack).containsKey(ForgeMod.REACH_DISTANCE.get())) {
				Multimap<Attribute, AttributeModifier> multimap = stack.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, stack);
				if(AttributeUtils.getItemAttributeValue(stack, EquipmentSlotType.MAINHAND, ForgeMod.REACH_DISTANCE.get())>=1.0D) {
					return true; // Spear, polearms, and lances
				}
			}
		}
		return false;
	}
}