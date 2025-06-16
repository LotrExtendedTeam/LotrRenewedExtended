package com.github.maximuslotro.lotrrextended.mixins.lotr.common.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;

import lotr.common.inv.ForgeResultSlot;
import lotr.common.tileentity.AbstractAlloyForgeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Mixin for {@link ForgeResultSlot}, allowing injection of custom behavior when a player takes an item from the result slot.
 * Specifically, this mixin extends functionality to trigger custom crafting advancements when used with {@link AbstractAlloyForgeTileEntity}.
 *
 * @Mixin (ForgeResultSlot.class)
 */
@Mixin(ForgeResultSlot.class)
public abstract class MixinForgeResultSlot extends Slot {
	/**
     * Constructor required for Mixins that extend a superclass with non-default constructors.
     * This constructor is only used at compile-time and is stripped at runtime.
     *
     * @param pContainer the inventory this slot belongs to
     * @param pIndex     the index of the slot
     * @param pX         the X position of the slot in the GUI
     * @param pY         the Y position of the slot in the GUI
     */
	public MixinForgeResultSlot(IInventory pContainer, int pIndex, int pX, int pY) {
		super(pContainer, pIndex, pX, pY);
	}
	/**
     * Injected behavior that triggers a custom advancement when the player takes the output item from the forge.
     * This only applies if the container is an instance of {@link AbstractAlloyForgeTileEntity} and the world is server-side.
     *
     * @param player the player taking the item
     * @param stack  the item stack being taken
     * @param ci     callback info for the returnable {@link Block}, used to preserve method behavior
     * @Inject (method = "onTake", at = @At("HEAD"))
     */
	@Inject(method = "onTake", at = @At("HEAD"))
	public void onTakeExtended(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
		if (!player.level.isClientSide && container instanceof AbstractAlloyForgeTileEntity) {
			ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, ((AbstractAlloyForgeTileEntity) container).getBlockState().getBlock());
		}
	}
}
