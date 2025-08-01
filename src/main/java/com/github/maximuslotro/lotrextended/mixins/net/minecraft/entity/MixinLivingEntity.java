package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.block.beds.ExtendedBedrollBlock;

import lotr.common.util.CalendarUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

	@Inject(method = "setPosToBed(Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), cancellable = true)
	private void setPosToBed(BlockPos blockPos, CallbackInfo cl) {
		BlockState blockstate = ((LivingEntity)(Object)this).level.getBlockState(blockPos);
		if(blockstate.getBlock() instanceof ExtendedBedrollBlock) {
			if (CalendarUtil.isAprilFools()) {
				return;
			}
			((LivingEntity)(Object)this).setPos(blockPos.getX() + 0.5D, blockPos.getY() + 0.25D, blockPos.getZ() + 0.5D);
			cl.cancel();
		}
	}
}
