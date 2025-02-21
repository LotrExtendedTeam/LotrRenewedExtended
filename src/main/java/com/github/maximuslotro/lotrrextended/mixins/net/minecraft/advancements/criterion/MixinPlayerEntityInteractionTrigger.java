package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.advancements.criterion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkTriggers;

import net.minecraft.advancements.criterion.PlayerEntityInteractionTrigger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(PlayerEntityInteractionTrigger.class)
public class MixinPlayerEntityInteractionTrigger {

	@Inject(method = "trigger", at = @At("HEAD"))
	public void injectExtendedTrigger(ServerPlayerEntity pPlayer, ItemStack pStack, Entity pEntity) {
		ExtendedBenchmarkTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(pPlayer, pStack, pEntity);
	}
}
