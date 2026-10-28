package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public abstract class ExtendedSixDirectionPlantBlock extends HorizontalFaceBlock {

	public ExtendedSixDirectionPlantBlock(Properties pProperties) {
		super(pProperties);
		ExtendedDeferredComposterInjector.addCompostable(this, 0.65F);
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		Direction direction = getConnectedDirection(pState).getOpposite();
		BlockPos blockpos = pPos.relative(direction);
		return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
	}

	abstract boolean mayPlaceOn(BlockState blockState, IBlockReader pLevel, BlockPos blockpos);

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 30;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState pState, IBlockReader pReader, BlockPos pPos) {
		return pState.getFluidState().isEmpty();
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return pType == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(pState, pLevel, pPos, pType);
	}
}
