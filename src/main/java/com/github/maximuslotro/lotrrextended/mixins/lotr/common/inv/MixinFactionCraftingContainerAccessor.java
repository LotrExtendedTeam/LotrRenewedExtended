package com.github.maximuslotro.lotrrextended.mixins.lotr.common.inv;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import lotr.common.inv.FactionCraftingContainer;
import net.minecraft.block.Block;

@Mixin(FactionCraftingContainer.class)
public interface MixinFactionCraftingContainerAccessor {

	@Invoker(remap=false, value = "getCraftingBlock")
	Block invokeCraftingBlock();
}
