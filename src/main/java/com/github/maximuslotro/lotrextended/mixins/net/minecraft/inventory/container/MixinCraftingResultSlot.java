package com.github.maximuslotro.lotrextended.mixins.net.minecraft.inventory.container;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrextended.mixins.lotr.common.inv.MixinFactionCraftingContainerInvoker;
import com.github.maximuslotro.lotrextended.mixins.lotr.common.inv.MixinFactionCraftingResultSlotAccessor;
import com.github.maximuslotro.lotrrextended.common.advancements.ExtendedCriteriaTriggers;

import lotr.common.inv.FactionCraftingContainer;
import lotr.common.inv.FactionCraftingResultSlot;
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
	 * Get the internal player variable in this class
	 * @Shadow
	 * @Final
	 */
	@Shadow
	@Final
	private PlayerEntity player;

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
	 * Injects behavior into the {@code checkTakeAchievements} method to trigger a custom advancement
	 * when a player takes the crafted item from the result slot of a vanilla crafting table.
	 *
	 * @param stack  the resulting {@link ItemStack} taken from the slot
	 * @param ci     callback info
	 * @Inject (method = "onTake", at = @At("HEAD"))
	 */
	@Inject(method = "checkTakeAchievements", at = @At("HEAD"))
	public void checkTakeAchievementsExtended(ItemStack stack, CallbackInfo ci) {
		if (!player.level.isClientSide) {
			if((CraftingResultSlot)(Object)this instanceof FactionCraftingResultSlot) {
				FactionCraftingContainer tableContainer = ((MixinFactionCraftingResultSlotAccessor)(Object)this).getTableContainer();
				if(tableContainer.isStandardCraftingActive()) {
					ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, Blocks.CRAFTING_TABLE);
				}else {
					ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, ((MixinFactionCraftingContainerInvoker)tableContainer).invokeCraftingBlock());
				}
			}else {
				ExtendedCriteriaTriggers.FACTION_CRAFTING_TRIGGER.trigger((ServerPlayerEntity) player, stack, Blocks.CRAFTING_TABLE);
			}
		}
	}

}
