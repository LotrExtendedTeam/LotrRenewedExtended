package com.github.maximuslotro.lotrrextended.common.block.beds;

import com.github.maximuslotro.lotrrextended.common.block.ExtendedBedBlock;
import com.github.maximuslotro.lotrrextended.common.tileentity.beds.ExtendedOrcBedTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtendedBedrollBlock extends ExtendedBedBlock{
	protected static final VoxelShape BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

	public ExtendedBedrollBlock(Properties properties) {
		super(DyeColor.WHITE, properties);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new ExtendedOrcBedTileEntity();
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return BASE;
	}
}
