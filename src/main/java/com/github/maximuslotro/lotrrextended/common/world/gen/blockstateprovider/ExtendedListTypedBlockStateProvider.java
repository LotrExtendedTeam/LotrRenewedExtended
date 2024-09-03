package com.github.maximuslotro.lotrrextended.common.world.gen.blockstateprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlockStateProviderTypes;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;

public class ExtendedListTypedBlockStateProvider extends BlockStateProvider {
	public static final Codec<ExtendedListTypedBlockStateProvider> CODEC = BlockState.CODEC.listOf().promotePartial(Util.prefix("BlockState data: ", ExtendedLog::error))
		.xmap(ExtendedListTypedBlockStateProvider::new, (p_236810_0_) -> {
		return p_236810_0_.states;
	});
	private final List<BlockState> states;

	public ExtendedListTypedBlockStateProvider(List<BlockState> states) {
		this.states = states;
	}

	public ExtendedListTypedBlockStateProvider(BlockState blockstate) {
		states = new ArrayList<>();
		states.add(blockstate);
	}

	public ExtendedListTypedBlockStateProvider addBlock(BlockState blockstate) {
		states.add(blockstate);
		return this;
	}

	protected BlockStateProviderType<?> type() {
		return ExtendedBlockStateProviderTypes.BUSH_STATE_PROVIDER.get();
	}

	public BlockState getState(Random pRandom, BlockPos pBlockPos) {
		if(states==null || states.isEmpty()) {
			ExtendedLog.error("A CRITICAL ERROR JUST HAPPENED! LISTTYPEDBLOCKSTATEPROVIDER TRIED SELECTING FROM NULL OR EMPTY LIST!");
			ExtendedLog.error("DEFAULTING TO minecraft:air, PLEASE REPORT THIS!");
			return Blocks.AIR.defaultBlockState();
		}
		return states.get(pRandom.nextInt(states.size()));
	}
}