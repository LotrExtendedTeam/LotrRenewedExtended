package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.advancements.criterion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkTriggers;

import net.minecraft.advancements.criterion.EntityHurtPlayerTrigger;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;

@Mixin(EntityHurtPlayerTrigger.class)
public class MixinEntityHurtPlayerTrigger {

	@Inject(method = "trigger", at = @At("HEAD"))
	public void injectExtendedTrigger(ServerPlayerEntity pPlayer, DamageSource pSource, float pAmountDealt, float pAmountTaken, boolean pWasBlocked, CallbackInfo ci) {
		ExtendedBenchmarkTriggers.ENTITY_HURT_PLAYER.trigger(pPlayer, pSource, pAmountDealt, pAmountTaken, pWasBlocked);
	}
}
