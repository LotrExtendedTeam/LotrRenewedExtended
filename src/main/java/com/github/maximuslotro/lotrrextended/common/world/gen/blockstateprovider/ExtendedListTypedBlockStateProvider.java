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

/**
 * A BlockStateProvider that selects a random BlockState from a predefined list.
 * This provider can be constructed with a list of BlockStates or a single BlockState,
 * and supports adding more BlockStates after construction.
 */
public class ExtendedListTypedBlockStateProvider extends BlockStateProvider {
	/**
	 * Codec for serializing and deserializing this provider.
	 * It serializes a list of BlockStates and logs errors on partial failures.
	 */
	public static final Codec<ExtendedListTypedBlockStateProvider> CODEC = BlockState.CODEC.listOf().promotePartial(Util.prefix("BlockState data: ", ExtendedLog::error))
		.xmap(ExtendedListTypedBlockStateProvider::new, (p_236810_0_) -> p_236810_0_.states);
	/** The list of BlockStates to randomly select from. */
	private final List<BlockState> states;
	/**
	 * Constructs a provider with a given list of BlockStates.
	 * @param states List of BlockStates to select from.
	 */
	public ExtendedListTypedBlockStateProvider(List<BlockState> states) {
		this.states = states;
	}
	/**
	 * Constructs a provider with a single BlockState.
	 * @param blockstate The single BlockState to provide.
	 */
	public ExtendedListTypedBlockStateProvider(BlockState blockstate) {
		states = new ArrayList<>();
		states.add(blockstate);
	}
	/**
	 * Adds a BlockState to the list of selectable states.
	 * @param blockstate The BlockState to add.
	 * @return This provider instance, for chaining.
	 */
	public ExtendedListTypedBlockStateProvider addBlock(BlockState blockstate) {
		states.add(blockstate);
		return this;
	}
	/**
	 * Returns the type of this BlockStateProvider.
	 * @return The BlockStateProviderType associated with this provider.
	 */
	@Override
	protected BlockStateProviderType<?> type() {
		return ExtendedBlockStateProviderTypes.BUSH_STATE_PROVIDER.get();
	}
	/**
	 * Selects a random BlockState from the list.
	 * Logs an error and returns air if the list is empty or null.
	 *
	 * @param pRandom Random instance for selection.
	 * @param pBlockPos Position where the block will be placed (unused).
	 * @return A randomly selected BlockState from the list.
	 */
	@Override
	public BlockState getState(Random pRandom, BlockPos pBlockPos) {
		if(states==null || states.isEmpty()) {
			ExtendedLog.error("A CRITICAL ERROR JUST HAPPENED! LISTTYPEDBLOCKSTATEPROVIDER TRIED SELECTING FROM NULL OR EMPTY LIST!");
			ExtendedLog.error("DEFAULTING TO minecraft:air, PLEASE REPORT THIS!");
			return Blocks.AIR.defaultBlockState();
		}
		return states.get(pRandom.nextInt(states.size()));
	}
}