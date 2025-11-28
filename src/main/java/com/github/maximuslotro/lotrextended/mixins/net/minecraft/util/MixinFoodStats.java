package com.github.maximuslotro.lotrextended.mixins.net.minecraft.util;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import net.minecraft.util.FoodStats;

@Mixin(FoodStats.class)
public class MixinFoodStats {
	@Shadow
	private float exhaustionLevel;

	@Inject(method="addExhaustion(F)V", at = @At(value="HEAD"), cancellable = true)
	public void addExhaustionExtended(float pExhaustion, CallbackInfo ci) {
		if(ExtendedServerConfig.enableHungerDecrease.get()) {
			this.exhaustionLevel = Math.min(this.exhaustionLevel + (pExhaustion * 0.3f), 40.0F);
			ci.cancel();
		}
	}
}
