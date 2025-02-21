package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.advancements.criterion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkTriggers;

import net.minecraft.advancements.criterion.PlacedBlockTrigger;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@Mixin(PlacedBlockTrigger.class)
public class MixinPlacedBlockTrigger {
	@Inject(method = "trigger", at = @At("HEAD"))
	public void injectExtendedTrigger(ServerPlayerEntity pPlayer, BlockPos pPos, ItemStack pItem, CallbackInfo ci) {
		ExtendedBenchmarkTriggers.PLACED_BLOCK.trigger(pPlayer, pPos, pItem);
	}
}
