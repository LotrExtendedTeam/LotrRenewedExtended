package com.github.maximuslotro.lotrExtended.mixins.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lotr.common.block.ExtendedBerryBushBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Mixin(FarmlandBlock.class)
public class MixinFarmlandBlock {

	@Inject(method = "canSurvive(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorldReader;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At("HEAD"), cancellable = true)
	private void checkExtendedFarmland(BlockState state, IWorldReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		if(state.getBlock() instanceof ExtendedBerryBushBlock) {
			ci.setReturnValue(true);
		}
	}
}
