package com.github.maximuslotro.lotrrextended.common.block;

import java.util.function.ToIntFunction;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ExtendedBlockParms {
	public static ToIntFunction<BlockState> litBlockEmission(int level) {
		return (blockState) -> blockState.getValue(BlockStateProperties.LIT) ? level : 0;
	}
	public static Boolean allowSpawnOnLeaves(BlockState blockState, IBlockReader world, BlockPos pos, EntityType<?> entityType) {
		return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
	}
	public static Boolean always(BlockState blockState, IBlockReader world, BlockPos pos, EntityType<?> entityType) {
		return true;
	}
	public static Boolean always(BlockState blockState, IBlockReader world, BlockPos pos) {
		return true;
	}
	public static Boolean never(BlockState blockState, IBlockReader world, BlockPos pos, EntityType<?> entityType) {
		return true;
	}
	public static Boolean never(BlockState blockState, IBlockReader world, BlockPos pos) {
		return true;
	}
}
