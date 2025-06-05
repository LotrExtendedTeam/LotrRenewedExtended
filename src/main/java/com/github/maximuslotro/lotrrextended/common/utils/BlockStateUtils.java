package com.github.maximuslotro.lotrrextended.common.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraftforge.fml.RegistryObject;

public class BlockStateUtils {
	public static BlockState copyBlockStateToBlock(RegistryObject<Block> regBlock, BlockState previousBlockState) {
		return copyBlockStateToBlock(regBlock.get(), previousBlockState);
	}
	public static BlockState copyBlockStateToBlock(Block block, BlockState previousBlockState) {
		BlockState newBlockState = block.defaultBlockState();
		for (Property<?> property : previousBlockState.getBlock().getStateDefinition().getProperties()) {
			if (newBlockState.hasProperty(property)) {
				newBlockState = copyProperty(previousBlockState, newBlockState, property);
			}
		}
		return newBlockState;
	}

	private static <T extends Comparable<T>> BlockState copyProperty(BlockState previousBlockState, BlockState newBlockState, Property<T> property) {
		return newBlockState.setValue(property, previousBlockState.getValue(property));
	}
}
