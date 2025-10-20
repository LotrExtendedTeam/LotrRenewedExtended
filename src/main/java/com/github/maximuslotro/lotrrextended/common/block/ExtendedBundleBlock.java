package com.github.maximuslotro.lotrrextended.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ExtendedBundleBlock extends ExtendedDirectionalBlock {
	public ExtendedBundleBlock(MaterialColor color) {
		super(Properties.of(Material.WOOL, color).strength(0.4F).sound(SoundType.WOOL));
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 20;
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 40;
	}
}
