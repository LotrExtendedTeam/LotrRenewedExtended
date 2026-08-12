package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;

import lotr.common.block.RottenLogBlock;
import lotr.common.block.RottenWoodBeamBlock;
import lotr.common.event.CompostingHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class ExtendedMossCarpetBlock extends Block {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	public ExtendedMossCarpetBlock(AbstractBlock.Properties pProperties) {
		super(pProperties);
		CompostingHelper.prepareCompostable(this, 0.25f);
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return SHAPE;
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		BlockPos blockpos = pPos.below();
		BlockState below = pLevel.getBlockState(blockpos);
		if(below.getBlock() instanceof LeavesBlock || below.getBlock() instanceof ExtendedBerryHedgeBlock
			|| below.getBlock() instanceof RottenLogBlock || below.getBlock() instanceof RottenWoodBeamBlock
			|| below.getBlock() instanceof CauldronBlock) {
			return true;
		}
		return below.isFaceSturdy(pLevel, blockpos, Direction.UP) && !below.is(ExtendedBlocks.MOSS_CARPET.get());
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 20;
	}
}