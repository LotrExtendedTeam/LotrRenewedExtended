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
		ExtendedModifierTypes modifierType = ExtendedModifierUtils.vanillaEnchantToModifier(pEnchantment, false);
		Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
		int newValue = 0;
		if(modifiers.containsKey(modifierType)) {
			newValue = (int) modifiers.get(modifierType).getModifierEffect();
		}
		cir.setReturnValue(vanillaValue+newValue);
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
		ExtendedModifierTypes modifierType = ExtendedModifierUtils.vanillaEnchantToModifier(pEnchantment, false);
		Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
		if(modifiers.containsKey(modifierType)) {
			cir.setReturnValue((int) modifiers.get(modifierType).getModifierEffect());
		}
	}
	/**
	 * Injects into {@link EnchantmentHelper#getDamageBonus(ItemStack, CreatureAttribute)} to
	 * apply additional melee damage from custom modifiers.
	 * <p>
	 * If {@link ExtendedModifierTypes#MELEE_DAMAGE} is present, its effect is added to the
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
		Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(pStack);
		if(modifiers.containsKey(ExtendedModifierTypes.MELEE_DAMAGE)) {
			mutablefloat.add(modifiers.get(ExtendedModifierTypes.MELEE_DAMAGE).getModifierEffect());
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
	 *   <li>{@link ExtendedModifierTypes#PROTECTION_FIRE} for fire damage</li>
	 *   <li>{@link ExtendedModifierTypes#PROTECTION_RANGED} for projectiles</li>
	 *   <li>{@link ExtendedModifierTypes#PROTECTION_BLAST} for explosions</li>
	 *   <li>{@link ExtendedModifierTypes#PROTECTION_FALLING} for fall damage</li>
	 *   <li>{@link ExtendedModifierTypes#PROTECTION} as a general fallback</li>
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
		for(ItemStack stack : pStacks) {
			Map<ExtendedModifierTypes, ExtendedModifier> modifiers = ExtendedModifierUtils.getModifierListByType(stack);
			if(pSource.isFire() && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_FIRE)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_FIRE).getModifierEffect());
				cir.setReturnValue(mutableint.getValue());
			}else if(pSource.isProjectile() && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_RANGED)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_RANGED).getModifierEffect());
				cir.setReturnValue(mutableint.getValue());
			}else if(pSource.isProjectile() && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_BLAST)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_BLAST).getModifierEffect());
				cir.setReturnValue(mutableint.getValue());
			}else if(pSource == DamageSource.FALL && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_FALLING)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_FALLING).getModifierEffect());
				cir.setReturnValue(mutableint.getValue());
			}else if(pSource.getEntity()!=null && containsLongWeapon(pSource.getEntity().getHandSlots()) && modifiers.containsKey(ExtendedModifierTypes.PROTECTION_MITHRIL)) {
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION_MITHRIL).getModifierEffect());
				cir.setReturnValue(mutableint.getValue());
			}else if(modifiers.containsKey(ExtendedModifierTypes.PROTECTION)){
				mutableint.add(modifiers.get(ExtendedModifierTypes.PROTECTION).getModifierEffect());
				cir.setReturnValue(mutableint.getValue());
			}
		}
	}

	public static boolean containsLongWeapon(Iterable<ItemStack> iterable) {
	    for (ItemStack stack : iterable) {
	        if (stack.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, stack).containsKey(ForgeMod.REACH_DISTANCE.get())) {
	        	Multimap<Attribute, AttributeModifier> multimap = stack.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, stack);
	        	if(AttributeUtils.getItemAttributeValue(stack, EquipmentSlotType.MAINHAND, ForgeMod.REACH_DISTANCE.get())>1.3D) {
	        		return true; // Spear, polearms, and lances
	        	}
	        }
	    }
	    return false;
	}
}