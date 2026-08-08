package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction.Axis;

public class ExtendedBlockStateUtils {
	public static BlockState copyBlockStateToBlock(Block block, BlockState previousBlockState) {
		return copyBlockStateToBlockState(block.defaultBlockState(), previousBlockState);
	}

	public static BlockState copyBlockStateToBlockState(BlockState newBlockStateBlock, BlockState previousBlockState) {
		for (Property<?> property : previousBlockState.getProperties()) {
			if (newBlockStateBlock.hasProperty(property)) {
				newBlockStateBlock = copyProperty(previousBlockState, newBlockStateBlock, property);
			}
		}
		return newBlockStateBlock;
	}
	public static BlockState copyBlockStateToBlockStateOverwriteSlab(BlockState newBlockStateBlock, BlockState previousBlockState) {
		for (Property<?> property : previousBlockState.getProperties()) {
			if (newBlockStateBlock.hasProperty(property)) {
				newBlockStateBlock = copyProperty(previousBlockState, newBlockStateBlock, property);
			}
		}
		if(newBlockStateBlock.hasProperty(BlockStateProperties.AXIS) && previousBlockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
			newBlockStateBlock = copyDiffProperty(previousBlockState, newBlockStateBlock, BlockStateProperties.HORIZONTAL_AXIS, BlockStateProperties.AXIS);
		}else if(newBlockStateBlock.hasProperty(BlockStateProperties.HORIZONTAL_AXIS) && previousBlockState.hasProperty(BlockStateProperties.AXIS) && previousBlockState.getValue(BlockStateProperties.AXIS)!=Axis.Y) {
			newBlockStateBlock = copyDiffProperty(previousBlockState, newBlockStateBlock, BlockStateProperties.AXIS, BlockStateProperties.HORIZONTAL_AXIS);
		}
		return newBlockStateBlock;
	}

	private static <T extends Comparable<T>> BlockState copyProperty(BlockState previousBlockState, BlockState newBlockState, Property<T> property) {
		return newBlockState.setValue(property, previousBlockState.getValue(property));
	}

	private static <T extends Comparable<T>> BlockState copyDiffProperty(BlockState previousBlockState, BlockState newBlockState, Property<T> propertyOld, Property<T> propertyNew) {
		return newBlockState.setValue(propertyNew, previousBlockState.getValue(propertyOld));
	}
}
