package com.github.maximuslotro.lotrextended.mixins.lotr.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;

import org.spongepowered.asm.mixin.injection.Constant;

import lotr.common.block.GateBlock;

@Mixin(GateBlock.class)
public class MixinGateBlock {

    @ModifyConstant(
        method = "getConnectedGatePositions(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/util/List;",
        constant = @Constant(intValue = 16),
        remap = false
    )
    private int replaceMaxDepth(int original) {
        return ExtendedServerConfig.gateToggleLength.get();
    }
}
