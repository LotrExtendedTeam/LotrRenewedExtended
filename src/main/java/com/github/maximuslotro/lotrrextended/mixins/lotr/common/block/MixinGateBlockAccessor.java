package com.github.maximuslotro.lotrrextended.mixins.lotr.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import lotr.common.block.GateBlock;

@Mixin(GateBlock.class)
public interface MixinGateBlockAccessor {

	@Accessor(remap=false, value = "isCutoutGate")
	boolean isCutoutGate();
}
