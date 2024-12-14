package com.github.maximuslotro.lotrrextended.common.block;

import com.github.maximuslotro.lotrrextended.common.enums.WargFurType;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ExtendedWargCarpetBlock extends CarpetBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private final WargFurType wargFurType;

	public ExtendedWargCarpetBlock(WargFurType wargFurType) {
		super(wargFurType.getDyeColor(), AbstractBlock.Properties.of(Material.CLOTH_DECORATION, wargFurType.getDyeColor()).strength(0.1F).sound(SoundType.WOOL));
		this.wargFurType = wargFurType;
		((FireBlock) Blocks.FIRE).setFlammable(this, 60, 20);
	}

	public WargFurType getWargFurType() {
		return this.wargFurType;
	}

	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPE;
	}

}