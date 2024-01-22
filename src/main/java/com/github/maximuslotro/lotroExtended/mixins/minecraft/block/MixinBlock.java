package com.github.maximuslotro.lotroExtended.mixins.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lotr.common.block.ExtendedGroundFarmlandBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@Mixin(Block.class)
public class MixinBlock {
	@Inject(method = "canSustainPlant(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;Lnet/minecraftforge/common/IPlantable;)Z",
			at = @At("HEAD"), cancellable = true, remap=false)
	   private void canSustainPlantExtended(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable, CallbackInfoReturnable<Boolean> ci) {
	      BlockState plant = plantable.getPlant(world, pos.relative(facing));
	      net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.relative(facing));
	      if (net.minecraftforge.common.PlantType.CROP.equals(type)) {
	    	  ci.setReturnValue(state.getBlock() instanceof ExtendedGroundFarmlandBlock);
	      }
	  }
}
