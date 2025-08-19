package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryHedgeBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Mixin(FarmlandBlock.class)
public class MixinFarmlandBlock {

	@Inject(method = "canSurvive(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorldReader;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At("HEAD"), cancellable = true)
	private void checkExtendedFarmland(BlockState state, IWorldReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		if(state.getBlock() instanceof ExtendedBerryHedgeBlock) {
			ci.setReturnValue(true);
		}
	}
}
