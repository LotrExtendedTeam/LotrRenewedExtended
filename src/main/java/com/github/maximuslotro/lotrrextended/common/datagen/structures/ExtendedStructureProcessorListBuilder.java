package com.github.maximuslotro.lotrrextended.common.datagen.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.ExtendedBlockReplacementProcessor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessorList;

// TODO add builder support for wood pallet processors
public class ExtendedStructureProcessorListBuilder {

	private final List<StructureProcessor> processors = new ArrayList<>();

	public static ExtendedStructureProcessorListBuilder create() {
		return new ExtendedStructureProcessorListBuilder();
	}

	public ExtendedStructureProcessorListBuilder addProcessor(StructureProcessor processor) {
		this.processors.add(processor);
		return this;
	}

	public ExtendedStructureProcessorListBuilder addReplacementProcessor(Block targetBlock, float chance, Consumer<WeightedBlockStateBuilder> weightConsumer) {
		WeightedBlockStateBuilder weightBuilder = new WeightedBlockStateBuilder();
		weightConsumer.accept(weightBuilder);
		return this.addProcessor(new ExtendedBlockReplacementProcessor(targetBlock,  chance, weightBuilder.build()));
	}

	public StructureProcessorList build() {
		return new StructureProcessorList(this.processors);
	}

	public static class WeightedBlockStateBuilder {
		private final WeightedBlockStateProvider provider = new WeightedBlockStateProvider();

		public WeightedBlockStateBuilder add(Block block, int weight) {
			this.provider.add(block.defaultBlockState(), weight);
			return this;
		}

		public WeightedBlockStateBuilder add(Supplier<? extends Block> blockSupplier, int weight) {
			return this.add(blockSupplier.get(), weight);
		}

		public WeightedBlockStateBuilder add(BlockState state, int weight) {
			this.provider.add(state, weight);
			return this;
		}

		public WeightedBlockStateProvider build() {
			return this.provider;
		}
	}
}