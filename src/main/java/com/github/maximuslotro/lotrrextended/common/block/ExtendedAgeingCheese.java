package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Optional;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCheeseState;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedBlockStateUtils;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public interface ExtendedAgeingCheese extends ExtendedChangingOverTimeBlock<ExtendedCheeseState> {
	Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder().put(ExtendedBlocks.CHEESE_WHEEL.get(), ExtendedBlocks.AGED_CHEESE_WHEEL.get()).build());
	Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

	public static Optional<Block> getPrevious(Block pBlock) {
		return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(pBlock));
	}

	public static Block getFirst(Block pBlock) {
		Block block = pBlock;

		for(Block block1 = PREVIOUS_BY_BLOCK.get().get(pBlock); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
			block = block1;
		}

		return block;
	}

	public static Optional<BlockState> getPrevious(BlockState pState) {
		return getPrevious(pState.getBlock()).map((pBlock) -> ExtendedBlockStateUtils.copyBlockStateToBlock(pBlock, pState));
	}

	public static Optional<Block> getNext(Block pBlock) {
		return Optional.ofNullable(NEXT_BY_BLOCK.get().get(pBlock));
	}

	@Override
	public default Optional<BlockState> getNext(BlockState pState) {
		return getNext(pState.getBlock()).map((block) -> ExtendedBlockStateUtils.copyBlockStateToBlock(block, pState));
	}

	@Override
	public default float getChanceModifier() {
		return this.getAge() == ExtendedCheeseState.UNAFFECTED ? 0.75F : 1.0F;
	}
}
