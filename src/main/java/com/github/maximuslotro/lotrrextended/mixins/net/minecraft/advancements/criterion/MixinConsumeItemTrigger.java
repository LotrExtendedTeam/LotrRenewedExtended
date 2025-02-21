package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.advancements.criterion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkTriggers;

import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(ConsumeItemTrigger.class)
public class MixinConsumeItemTrigger {

	@Inject(method = "trigger", at = @At("HEAD"))
	public void injectExtendedTrigger(ServerPlayerEntity pPlayer, ItemStack pItem) {
		ExtendedBenchmarkTriggers.CONSUME_ITEM.trigger(pPlayer, pItem);
	}
}
