package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class ExtendedWasteBlock extends Block {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	public ExtendedWasteBlock(AbstractBlock.Properties p_i48326_1_) {
		super(p_i48326_1_);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState pState, IBlockReader pReader, BlockPos pPos) {
		return VoxelShapes.block();
	}

	@Override
	public VoxelShape getVisualShape(BlockState pState, IBlockReader pReader, BlockPos pPos, ISelectionContext pContext) {
		return VoxelShapes.block();
	}

	@Override
	public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
		return false;
	}

	@Override
	public void onPlantGrow(BlockState state, IWorld world, BlockPos pos, BlockPos source) {}
}