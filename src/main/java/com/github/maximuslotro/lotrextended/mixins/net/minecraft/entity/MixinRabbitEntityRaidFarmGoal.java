package com.github.maximuslotro.lotrextended.mixins.net.minecraft.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundFarmlandBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarrotBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Mixin(targets = "net.minecraft.entity.passive.RabbitEntity$RaidFarmGoal")
public class MixinRabbitEntityRaidFarmGoal {

	@Shadow
	private boolean wantsToRaid;

	@Shadow
	private boolean canRaid;

	@Inject(method = "isValidTarget(Lnet/minecraft/world/IWorldReader;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
	public void checkExtendedFarmland(IWorldReader world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof ExtendedGroundFarmlandBlock && wantsToRaid && !canRaid) {
			pos = pos.above();
			BlockState blockstate = world.getBlockState(pos);
			block = blockstate.getBlock();
			if (block instanceof CarrotBlock && ((CarrotBlock) block).isMaxAge(blockstate)) {
				this.canRaid = true;
				ci.setReturnValue(true);
			}
		}
	}
}
