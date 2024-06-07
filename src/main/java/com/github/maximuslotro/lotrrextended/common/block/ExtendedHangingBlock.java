package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class ExtendedHangingBlock extends Block {
	private static final VoxelShape SHAPE = Block.box(4.0D, 1.0D, 4.0D, 11.0D, 16.0D, 11.0D);

	public ExtendedHangingBlock(Properties properties) {
		super(properties);
	}

	public ExtendedHangingBlock() {
		super(Properties.of(Material.PLANT).noOcclusion().noCollission());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return canSupportCenter(world, pos.above(), Direction.DOWN) || world.getBlockState(pos.above()).getBlock() instanceof LeavesBlock;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState blockState, Direction direction, BlockState directionBlockState, IWorld world, BlockPos blockPos, BlockPos directionBlockPos) {
		return direction == Direction.UP && !canSurvive(blockState, world, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, directionBlockState, world, blockPos, directionBlockPos);
	}
}
