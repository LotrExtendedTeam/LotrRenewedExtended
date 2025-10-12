package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtendedElvenLanternBlock extends ExtendedLanternBlock {
	private static final VoxelShape GROUND_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
	private static final VoxelShape HANGING_SHAPE = Block.box(6.0D, 2.0D, 6.0D, 10.0D, 16.0D, 10.0D);

	public ExtendedElvenLanternBlock(int lightLevel) {
		super(lightLevel);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : GROUND_SHAPE;
	}
}
