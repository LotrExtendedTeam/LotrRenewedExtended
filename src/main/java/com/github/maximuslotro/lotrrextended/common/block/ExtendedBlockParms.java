package com.github.maximuslotro.lotrrextended.common.block;

import java.util.function.ToIntFunction;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;

public class ExtendedBlockParms {
	public static ToIntFunction<BlockState> litBlockEmission(int p_235420_0_) {
	      return (p_235421_1_) -> {
	         return p_235421_1_.getValue(BlockStateProperties.LIT) ? p_235420_0_ : 0;
	      };
	   }
}
