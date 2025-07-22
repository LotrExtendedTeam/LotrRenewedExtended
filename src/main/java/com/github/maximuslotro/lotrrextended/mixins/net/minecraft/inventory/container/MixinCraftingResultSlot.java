package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.inventory.container;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Mixin for {@link CraftingResultSlot}, enabling custom behavior when a player takes an item from the result slot
 * of a standard crafting table. Specifically, this mixin injects a trigger for a custom advancement.
 * @Mixin (CraftingResultSlot.class)
 */
@Mixin(CraftingResultSlot.class)
public abstract class MixinCraftingResultSlot extends Slot {
	/**
     * Constructor required for Mixins that extend a superclass with non-default constructors.
     * This constructor is only used at compile-time and is stripped at runtime.
     *
     * @param pContainer the inventory this slot belongs to
     * @param pIndex     the index of the slot
     * @param pX         the X position of the slot in the GUI
     * @param pY         the Y position of the slot in the GUI
     */
	public MixinCraftingResultSlot(IInventory pContainer, int pIndex, int pX, int pY) {
		super(pContainer, pIndex, pX, pY);
	}
	/**
     * Injects behavior into the {@code onTake} method to trigger a custom advancement
     * when a player takes the crafted item from the result slot of a vanilla crafting table.
     *
     * @param player the player who took the item
     * @param stack  the resulting {@link ItemStack} taken from the slot
     * @param ci     callback info for the returnable {@link ItemStack}, used to preserve original behavior
     * @Inject (method = "onTake", at = @At("HEAD"))
     */
	@Inject(method = "onTake", at = @At("HEAD"))
	public void onTakeExtended(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
		if (!player.level.isClientSide) {
			ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, Blocks.CRAFTING_TABLE);
		}
	}
}
