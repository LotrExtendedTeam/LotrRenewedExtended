package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Random;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.utils.BlockStateUtils;

import lotr.common.block.AxialSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public class ExtendedGrowableAxialSlabBlock extends AxialSlabBlock{
	private final Supplier<Block> slabBase;
	private final Supplier<Block> blockBase;
	private final Supplier<Block> blockGrowable;

	public ExtendedGrowableAxialSlabBlock(Block block, Supplier<Block> slabBase, Supplier<Block> blockBase, Supplier<Block> blockGrowable) {
		super(block);
		this.slabBase = slabBase;
		this.blockBase = blockBase;
		this.blockGrowable = blockGrowable;
	}

	public ExtendedGrowableAxialSlabBlock(Supplier<Block> block, Supplier<Block> slabBase, Supplier<Block> blockBase, Supplier<Block> blockGrowable) {
		this(block.get(), slabBase, blockBase, blockGrowable);
	}

	public Supplier<Block> getBaseForLootTables() {
		return slabBase;
	}

	public static boolean canBeGrass(BlockState givenBlockState, IWorldReader world, BlockPos givenBlockPos, Supplier<Block> growableBlock) {
		BlockPos blockpos = givenBlockPos.above();
		BlockState blockstate = world.getBlockState(blockpos);
		if ((blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowBlock.LAYERS) == 1) || blockstate.is(givenBlockState.getBlock())|| blockstate.is(growableBlock.get())) {
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			if(givenBlockState.hasProperty(SlabBlock.TYPE)) {
				BlockState fixTopSlabError = givenBlockState.setValue(SlabBlock.TYPE, SlabType.DOUBLE);
				int i = LightEngine.getLightBlockInto(world, fixTopSlabError, givenBlockPos, blockstate, blockpos,
						Direction.UP, blockstate.getLightBlock(world, blockpos));
				return i < world.getMaxLightLevel();
			}
			int i = LightEngine.getLightBlockInto(world, givenBlockState, givenBlockPos, blockstate, blockpos,
					Direction.UP, blockstate.getLightBlock(world, blockpos));
			return i < world.getMaxLightLevel();
		}
	}

	public static boolean canPropagate(BlockState p_220256_0_, IWorldReader p_220256_1_, BlockPos p_220256_2_, Supplier<Block> growableBlock) {
		BlockPos blockpos = p_220256_2_.above();
		return canBeGrass(p_220256_0_, p_220256_1_, p_220256_2_, growableBlock) && !p_220256_1_.getFluidState(blockpos).is(FluidTags.WATER);
	}

	@Override
	public void randomTick(BlockState blockState, ServerWorld world, BlockPos thisPos, Random random) {
		if (!canBeGrass(blockState, world, thisPos, blockGrowable)) {
			if (!world.isAreaLoaded(thisPos, 3))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			world.setBlockAndUpdate(thisPos, BlockStateUtils.makeNewBlockstateFromBlockWithPropertiesOf(slabBase.get(), blockState));
		} else {
			if (world.getMaxLocalRawBrightness(thisPos.above()) >= 9) {
				BlockState stateBlock = blockGrowable.get().defaultBlockState();
				for (int i = 0; i < 4; ++i) {
					BlockPos blockpos = thisPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (world.getBlockState(blockpos).is(blockBase.get()) && canPropagate(stateBlock, world, blockpos, blockGrowable)) {
						world.setBlockAndUpdate(blockpos, stateBlock);
						continue;
					}
					if (world.getBlockState(blockpos).is(slabBase.get())) {
						BlockState stateSlab = BlockStateUtils.makeNewBlockstateFromBlockWithPropertiesOf(this, world.getBlockState(blockpos));
						if (canPropagate(stateSlab, world, blockpos, blockGrowable)) {
							world.setBlockAndUpdate(blockpos, stateSlab);
							continue;
						}
					}
				}
			}

		}
	}
}
