package com.github.maximuslotro.lotrrextended.mixins.net.minecraft.block;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedGrowableAxialSlabBlock;
import com.github.maximuslotro.lotrrextended.utils.BlockStateUtils;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

@Mixin(SpreadableSnowyDirtBlock.class)
public abstract class MixinSpreadableSnowyDirtBlock {

	@Shadow
	private static boolean canBeGrass(BlockState p_220257_0_, IWorldReader p_220257_1_, BlockPos p_220257_2_) {return false;}

	@Shadow
	private static boolean canPropagate(BlockState p_220256_0_, IWorldReader p_220256_1_, BlockPos p_220256_2_) {return false;}

	@Inject(method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
			at = @At("HEAD"), cancellable = true)
	public void randomTickExtended(BlockState blockState, ServerWorld world, BlockPos thisPos, Random random, CallbackInfo ci) {
		if(blockState.getBlock().is(Blocks.GRASS_BLOCK)){
			ci.cancel();
			if (!canBeGrass(blockState, world, thisPos)) {
				if (!world.isAreaLoaded(thisPos, 3))
					return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
				world.setBlockAndUpdate(thisPos, Blocks.DIRT.defaultBlockState());
			} else {
				if (world.getMaxLocalRawBrightness(thisPos.above()) >= 9) {
					BlockState blockstate = Blocks.GRASS_BLOCK.defaultBlockState();
					for (int i = 0; i < 4; ++i) {
						BlockPos blockpos = thisPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
						if (world.getBlockState(blockpos).is(Blocks.DIRT) && canPropagate(blockstate, world, blockpos)) {
							world.setBlockAndUpdate(blockpos, blockstate);
							continue;
						}
						if (world.getBlockState(blockpos).is(ExtendedBlocks.DIRT_SLAB.get())) {
							BlockState blockstateSlab = BlockStateUtils.makeNewBlockstateFromBlockWithPropertiesOf(ExtendedBlocks.GRASS_BLOCK_SLAB.get(), world.getBlockState(blockpos));
							if (ExtendedGrowableAxialSlabBlock.canPropagate(blockstateSlab, world, blockpos, ()->Blocks.GRASS_BLOCK)) {
								world.setBlockAndUpdate(blockpos, blockstateSlab);
								continue;
							}
						}
					}
				}
			}
		}
		if(blockState.getBlock().is(Blocks.MYCELIUM)){
			ci.cancel();
			if (!canBeGrass(blockState, world, thisPos)) {
				if (!world.isAreaLoaded(thisPos, 3))
					return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
				world.setBlockAndUpdate(thisPos, Blocks.DIRT.defaultBlockState());
			} else {
				if (world.getMaxLocalRawBrightness(thisPos.above()) >= 9) {
					BlockState blockstate = Blocks.MYCELIUM.defaultBlockState();
					for (int i = 0; i < 4; ++i) {
						BlockPos blockpos = thisPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
						if (world.getBlockState(blockpos).is(Blocks.DIRT) && canPropagate(blockstate, world, blockpos)) {
							world.setBlockAndUpdate(blockpos, blockstate);
							continue;
						}
						if (world.getBlockState(blockpos).is(ExtendedBlocks.DIRT_SLAB.get())) {
							BlockState blockstateSlab = BlockStateUtils.makeNewBlockstateFromBlockWithPropertiesOf(ExtendedBlocks.MYCELIUM_SLAB.get(), world.getBlockState(blockpos));
							if (ExtendedGrowableAxialSlabBlock.canPropagate(blockstateSlab, world, blockpos, ()->Blocks.MYCELIUM)) {
								world.setBlockAndUpdate(blockpos, blockstateSlab);
								continue;
							}
						}
					}
				}
			}
		}
	}
}
