package com.github.maximuslotro.lotrextended.mixins.lotr.common.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.inv.FactionCraftingContainer;
import net.minecraft.block.Block;

/**
 * Accessor mixin for {@link FactionCraftingContainer}, exposing the non-public {@code getCraftingBlock} method.
 *
 * <p>This allows other mixins or injected code to invoke the original method without modifying the base class directly.</p>
 * @Mixin (FactionCraftingContainer.class)
 */
@Mixin(FactionCraftingContainer.class)
public interface MixinFactionCraftingContainerAccessor {
	/**
     * Invokes the {@code getCraftingBlock} method from {@link FactionCraftingContainer}, which returns the block associated with the crafting operation.
     *
     * @return the {@link Block} used in the faction crafting process
     * @Invoker (remap=false, value = "getCraftingBlock")
     */
	@Invoker(remap=false, value = "getCraftingBlock")
	Block invokeCraftingBlock();
}
