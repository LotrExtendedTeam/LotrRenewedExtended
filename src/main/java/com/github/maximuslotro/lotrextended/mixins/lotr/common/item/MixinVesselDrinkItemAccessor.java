package com.github.maximuslotro.lotrextended.mixins.lotr.common.item;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.item.VesselDrinkItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

@Mixin(VesselDrinkItem.class)
public interface MixinVesselDrinkItemAccessor {

	/**
	 * Access the drink damage field
	 *
	 * @return the {@link Float} damageAmount for the Drink
	 * @Accessor (remap=false, value = "drinkDamage")
	 */
	@Accessor(remap=false, value = "drinkDamage")
	float getDrinkDamage();

	@Invoker(remap=false, value = "getAlcoholicityForStrength")
	float invokeGetAlcoholicityForStrength(ItemStack stack);

	@Invoker(remap=false, value = "getDrinkDamageForStrength")
	float invokeGetDrinkDamageForStrength(ItemStack stack);

	@Invoker(remap=false, value = "addPreEffectsTooltip")
	void invokeAddPreEffectsTooltip(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag);

	@Invoker(remap=false, value = "convertPotionEffectsForStrengthAndEntity")
	List<EffectInstance> invokeConvertPotionEffectsForStrengthAndEntity(ItemStack stack, LivingEntity entity);
}
