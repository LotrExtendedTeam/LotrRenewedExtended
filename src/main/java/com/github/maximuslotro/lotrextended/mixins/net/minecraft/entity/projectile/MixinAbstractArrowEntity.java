package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity.projectile;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;

/**
 * Mixin targeting {@link AbstractArrowEntity} to extend how arrow damage is calculated
 * based on extended enchantments.
 * <p>
 * Specifically, this mixin overrides {@link AbstractArrowEntity#setEnchantmentEffectsFromEntity}
 * to support float-based enchantment levels from {@link ExtendedModifierUtils}, allowing
 * Power enchantment modifiers to affect the base arrow damage more precisely.
 * @Mixin (AbstractArrowEntity.class)
 */
@Mixin(AbstractArrowEntity.class)
public class MixinAbstractArrowEntity {
	/**
     * Shadowed method to set the base damage of the arrow.
     * @param pBaseDamage Set the base damage
     * @Shadow
     */
	@Shadow
	public void setBaseDamage(double pBaseDamage) {}
	/**
     * Shadowed method to get the base damage of the arrow.
     * @return The base damage of the arrow
     * @Shadow
     */
	@Shadow
	public double getBaseDamage() {return 0;}
	/**
     * Injects after vanilla Power enchantment is applied to modify the base damage
     * using extended float-level modifiers.
     * <p>
     * Extended behavior:
     * <ul>
     *     <li>Retrieves the float-level of the {@link Enchantments#POWER_ARROWS} enchantment using
     *         {@link ExtendedModifierUtils#getEnchantmentLevelFloat}.</li>
     *     <li>Overrides the vanilla integer-based "power" value and applies the float-based
     *         damage addition: {@code properPower * 0.5D + 0.5D}.</li>
     * </ul>
     *
     * @param pShooter the entity that shot the arrow
     * @param pVelocity the velocity multiplier for the shot
     * @param ci callback info to allow canceling or overriding the method
     * @Inject (method="setEnchantmentEffectsFromEntity(Lnet/minecraft/entity/LivingEntity;F)V", at = @At(value="TAIL"))
     */
	@Inject(method="setEnchantmentEffectsFromEntity(Lnet/minecraft/entity/LivingEntity;F)V", at = @At(value="TAIL"))
	public void setEnchantmentEffectsFromEntity$Extended(LivingEntity pShooter, float pVelocity, CallbackInfo ci) {
		float properPower = ExtendedModifierUtils.getEnchantmentLevelFloat(Enchantments.POWER_ARROWS, pShooter);
		if (properPower > 0) {
			this.setBaseDamage(this.getBaseDamage() + properPower * 0.5D + 0.5D);
		}
	}
}
