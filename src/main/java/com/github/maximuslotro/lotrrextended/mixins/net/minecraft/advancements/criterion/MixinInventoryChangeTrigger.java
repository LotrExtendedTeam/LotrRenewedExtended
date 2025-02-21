package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.advancements.criterion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkTriggers;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(InventoryChangeTrigger.class)
public class MixinInventoryChangeTrigger {
	@Inject(method = "trigger", at = @At("HEAD"))
	public void injectExtendedTrigger(ServerPlayerEntity pPlayer, PlayerInventory pInventory, ItemStack pStack) {
		ExtendedBenchmarkTriggers.INVENTORY_CHANGE.trigger(pPlayer, pInventory, pStack);
	}
}
