package com.github.maximuslotro.lotrrextended.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;

public class BlockStateUtils {
	public static BlockState withPropertiesOf(Block block, BlockState p_152466_) {
		BlockState blockstate = block.defaultBlockState();
		for (Property<?> property : p_152466_.getBlock().getStateDefinition().getProperties()) {
			if (blockstate.hasProperty(property)) {
				blockstate = copyProperty(p_152466_, blockstate, property);
			}
		}
		return blockstate;
	}

	private static <T extends Comparable<T>> BlockState copyProperty(BlockState p_152455_, BlockState p_152456_, Property<T> p_152457_) {
		return p_152456_.setValue(p_152457_, p_152455_.getValue(p_152457_));
	}
}
