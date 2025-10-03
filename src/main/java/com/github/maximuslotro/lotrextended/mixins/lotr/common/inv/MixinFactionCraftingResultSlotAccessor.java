package com.github.maximuslotro.lotrextended.mixins.lotr.common.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import lotr.common.inv.FactionCraftingContainer;
import lotr.common.inv.FactionCraftingResultSlot;

/**
 * Accessor mixin for {@link FactionCraftingResultSlot}, exposing the non-public {@code tableContainer} field.
 *
 * <p>This allows other mixins or injected code to invoke the original method without modifying the base class directly.</p>
 * @Mixin (FactionCraftingContainer.class)
 */
@Mixin(FactionCraftingResultSlot.class)
public interface MixinFactionCraftingResultSlotAccessor {
	/**
     * Accesses the {@code tableContainer} method from {@link FactionCraftingResultSlot}, which returns the {@link FactionCraftingContainer} associated with the crafting operation.
     *
     * @return the {@link FactionCraftingContainer} used in the faction crafting process
     * @Accessor (remap=false, value = "tableContainer")
     */
	@Accessor(remap=false, value = "tableContainer")
	FactionCraftingContainer getTableContainer();
}
