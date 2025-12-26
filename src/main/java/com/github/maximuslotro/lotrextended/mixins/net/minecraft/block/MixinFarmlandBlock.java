package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBerryHedgeBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@Mixin(FarmlandBlock.class)
public class MixinFarmlandBlock {

	@Inject(method = "canSurvive(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorldReader;Lnet/minecraft/util/math/BlockPos;)Z",
			at = @At("HEAD"), cancellable = true)
	private void checkExtendedFarmland(BlockState state, IWorldReader reader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(state.getBlock() instanceof ExtendedBerryHedgeBlock) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "fallOn(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V",
			at = @At("HEAD"), cancellable = true)
	public void fallOn$Extended(World level, BlockPos blockPos, Entity entity, float p_180658_4_, CallbackInfo ci) {
		if (!level.isClientSide && entity instanceof PlayerEntity && !level.mayInteract((PlayerEntity) entity, blockPos)) {
			ci.cancel();
		}
	}
}
