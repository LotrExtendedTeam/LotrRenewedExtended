package com.github.maximuslotro.lotrextended.mixins.net.minecraft.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBarrenDirt;
import com.github.maximuslotro.lotrrextended.common.block.ExtendedGroundFarmlandBlock;

import lotr.common.block.ExtendedAxialSlabPlantableBlock;
import lotr.common.block.ExtendedAxialSlabStemableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;

@Mixin(Block.class)
public class MixinBlock {
	@Inject(method = "canSustainPlant(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;Lnet/minecraftforge/common/IPlantable;)Z",
			at = @At("HEAD"), cancellable = true, remap=false)
	private void canSustainPlantExtended(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable, CallbackInfoReturnable<Boolean> ci) {
		net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.relative(facing));
		if (net.minecraftforge.common.PlantType.CROP.equals(type)) {
			if (state.getBlock() instanceof ExtendedGroundFarmlandBlock) {
				ci.setReturnValue(true);
			}else if(plantable instanceof StemBlock) {
				if(state.getBlock() instanceof ExtendedBarrenDirt) {
					ci.setReturnValue(true);
				}else if(state.getBlock() instanceof ExtendedAxialSlabStemableBlock && state.getValue(SlabBlock.TYPE)!=SlabType.BOTTOM) {
					if(state.getValue(BlockStateProperties.AXIS)==Axis.Y) {
						ci.setReturnValue(true);
					}else if(state.getValue(BlockStateProperties.AXIS)==Axis.X && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
						ci.setReturnValue(true);
					}else if(state.getValue(BlockStateProperties.AXIS)==Axis.Z && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
						ci.setReturnValue(true);
					}
				}
			}
		}else if (PlantType.PLAINS.equals(type)) {
			if(state.getBlock() instanceof ExtendedAxialSlabPlantableBlock && state.getValue(SlabBlock.TYPE)!=SlabType.BOTTOM) {
				if(state.getValue(BlockStateProperties.AXIS)==Axis.Y) {
					ci.setReturnValue(true);
				}else if(state.getValue(BlockStateProperties.AXIS)==Axis.X && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
					ci.setReturnValue(true);
				}else if(state.getValue(BlockStateProperties.AXIS)==Axis.Z && state.getValue(SlabBlock.TYPE)==SlabType.DOUBLE) {
					ci.setReturnValue(true);
				}
			}
		}
	}
}
