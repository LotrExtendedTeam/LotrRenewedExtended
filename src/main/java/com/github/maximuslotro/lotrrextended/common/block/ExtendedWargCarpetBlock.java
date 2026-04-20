package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedWargFurType;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtendedWargCarpetBlock extends CarpetBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private final ExtendedWargFurType wargFurType;

	public ExtendedWargCarpetBlock(ExtendedWargFurType wargFurType) {
		super(wargFurType.getDyeColor(), AbstractBlock.Properties.of(Material.CLOTH_DECORATION, wargFurType.getDyeColor()).strength(0.1F).sound(SoundType.WOOL).noOcclusion());
		this.wargFurType = wargFurType;
	}

	public ExtendedWargFurType getWargFurType() {
		return this.wargFurType;
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPE;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 20;
	}

	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
	    return 1.0F;
	}
}