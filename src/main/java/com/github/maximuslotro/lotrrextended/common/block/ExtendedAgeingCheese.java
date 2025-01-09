package com.github.maximuslotro.lotrrextended.common.block;

import java.util.Optional;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCheeseState;
import com.github.maximuslotro.lotrrextended.common.utils.BlockStateUtils;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public interface ExtendedAgeingCheese extends ExtendedChangingOverTimeBlock<ExtendedCheeseState> {
	   Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> {
	      return ImmutableBiMap.<Block, Block>builder().put(ExtendedBlocks.CHEESE_WHEEL.get(), ExtendedBlocks.AGED_CHEESE_WHEEL.get()).build();
	   });
	   Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> {
	      return NEXT_BY_BLOCK.get().inverse();
	   });

	   static Optional<Block> getPrevious(Block p_154891_) {
	      return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(p_154891_));
	   }

	   static Block getFirst(Block p_154898_) {
	      Block block = p_154898_;

	      for(Block block1 = PREVIOUS_BY_BLOCK.get().get(p_154898_); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
	         block = block1;
	      }

	      return block;
	   }

	   static Optional<BlockState> getPrevious(BlockState p_154900_) {
	      return getPrevious(p_154900_.getBlock()).map((p_154903_) -> {
	         return BlockStateUtils.copyBlockStateToBlock(p_154903_, p_154900_);
	      });
	   }

	   static Optional<Block> getNext(Block p_154905_) {
	      return Optional.ofNullable(NEXT_BY_BLOCK.get().get(p_154905_));
	   }

	   @Override
	default Optional<BlockState> getNext(BlockState p_154893_) {
	      return getNext(p_154893_.getBlock()).map((p_154896_) -> {
	         return BlockStateUtils.copyBlockStateToBlock(p_154896_, p_154893_);
	      });
	   }

	   @Override
	default float getChanceModifier() {
	      return this.getAge() == ExtendedCheeseState.UNAFFECTED ? 0.75F : 1.0F;
	   }
}
