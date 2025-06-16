package com.github.maximuslotro.lotrrextended.mixins.lotr.common.inv;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;

import lotr.common.inv.FactionCraftingContainer;
import lotr.common.inv.FactionCraftingResultSlot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(FactionCraftingResultSlot.class)
public class MixinFactionCraftingResultSlot {

	@Shadow(remap=false)
	@Final
	private FactionCraftingContainer tableContainer;

	@Inject(method = "onTake", at = @At("HEAD"))
	public void onTakeExtended(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<Block> ci) {
		if(!player.level.isClientSide()) {
			ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, ((MixinFactionCraftingContainerAccessor)tableContainer).invokeCraftingBlock());
		}
	}
}
