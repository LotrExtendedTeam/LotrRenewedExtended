package com.github.maximuslotro.lotrextended.mixins.net.minecraft.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Mixin targeting {@link BowItem} to apply extended arrow damage based on float-level enchantments.
 * <p>
 * This mixin intercepts the {@code releaseUsing} method to override the vanilla integer-based
 * Power enchantment and apply a more precise float-based calculation from
 * {@link ExtendedModifierUtils#getEnchantmentLevelFloat}.
 * @Mixin (BowItem.class)
 */
@Mixin(BowItem.class)
public class MixinBowItem {
	/**
     * Injects into {@link BowItem#releaseUsing(ItemStack, World, LivingEntity, int)} after the
     * vanilla Power enchantment calculation to apply extended float-based damage.
     * <p>
     * Extended behavior:
     * <ul>
     *     <li>Retrieves the float-level of {@link Enchantments#POWER_ARROWS} for the shooter.</li>
     *     <li>Resets the vanilla integer "power" to zero.</li>
     *     <li>Adds {@code properPower * 0.5D + 0.5D} to the base damage of the arrow.</li>
     * </ul>
     *
     * @param pStack       the bow item stack being used
     * @param pLevel       the world in which the bow is used
     * @param pShooter     the entity shooting the arrow
     * @param pTimeLeft    the remaining use time of the bow
     * @param ci           callback info for overriding behavior
     * @param playerentity the player using the bow
     * @param infinity     true if the bow has vanilla Infinity enchantment
     * @param itemstack    the arrow item stack being used
     * @param useDuration  the duration of the bow use
     * @param powerForTime the bow pull power based on use duration
     * @param specialInfinity internal flag for infinite arrows
     * @param arrowitem    the type of arrow being fired
     * @param abstractarrowentity the actual arrow entity being spawned
     * @param power        the vanilla integer power value from the enchantment
     * @Inject(method="releaseUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V", at = @At(value="INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", ordinal = 2, shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
     */
	@Inject(method="releaseUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V", 
			at = @At(value="INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I", 
			ordinal = 2, shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
	public void releaseUsing(ItemStack pStack, World pLevel, LivingEntity pShooter, int pTimeLeft, CallbackInfo ci,
			PlayerEntity playerentity,  boolean infinity, ItemStack itemstack, int useDuration, float powerForTime, boolean specialInfinity, 
			ArrowItem arrowitem, AbstractArrowEntity abstractarrowentity, int power) {
		float properPower = ExtendedModifierUtils.getEnchantmentLevelFloat(Enchantments.POWER_ARROWS, pShooter);
		if (properPower > 0) {
			abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + properPower * 0.5D + 0.5D);
		}
	}
}