package com.github.maximuslotro.lotrextended.mixins.lotr.common.inv;

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
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * Mixin for {@link FactionCraftingResultSlot} that injects custom advancement trigger behavior
 * when a player takes the result item from a faction crafting station.
 *
 * <p>This mixin targets the {@code onTake} method to trigger a custom advancement when crafting is completed.</p>
 * @Mixin (FactionCraftingResultSlot.class)
 */
@Mixin(FactionCraftingResultSlot.class)
public class MixinFactionCraftingResultSlot {
	/**
     * Reference to the associated {@link FactionCraftingContainer}, shadowed from the target class.
     * Used to access contextual information about the crafting block.
     * @Shadow (remap=false)
     * @Final
     */
	@Shadow(remap=false)
	@Final
	private FactionCraftingContainer tableContainer;
	/**
     * Injects at the head of the {@code onTake} method to trigger a custom advancement
     * when an item is taken from the result slot of the faction crafting container.
     *
     * @param player the player taking the item
     * @param stack  the item stack being taken
     * @param ci     callback info for the returnable {@link Block}, used to preserve method behavior
     * @Inject (method = "onTake", at = @At("HEAD"))
     */
	@Inject(method = "onTake", at = @At("HEAD"))
	public void onTakeExtended(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
		if(!player.level.isClientSide()) {
			if(tableContainer.isStandardCraftingActive()) {
				ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, Blocks.CRAFTING_TABLE);
			}else {
				ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, ((MixinFactionCraftingContainerAccessor)tableContainer).invokeCraftingBlock());
			}
		}
	}
}
