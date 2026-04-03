package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtendedLanternNumenoreanBlock extends ExtendedLanternBlock {
	private static final VoxelShape GROUND_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);
	private static final VoxelShape HANGING_SHAPE = Block.box(1.0D, 4.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public ExtendedLanternNumenoreanBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return state.getValue(HANGING) ? HANGING_SHAPE : GROUND_SHAPE;
	}
}
