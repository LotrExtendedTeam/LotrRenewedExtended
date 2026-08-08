package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedBlockStateUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import java.util.Random;

public class ExtendedBlockReplacementProcessor extends StructureProcessor {

	public static final Codec<ExtendedBlockReplacementProcessor> CODEC = RecordCodecBuilder.create(instance ->
	instance.group(Registry.BLOCK.fieldOf("target_block").forGetter(p -> p.targetBlock), 
		Codec.FLOAT.optionalFieldOf("replace_chance", 1.0F).forGetter(p -> p.replaceChance),
		BlockStateProvider.CODEC.fieldOf("replacements").forGetter(p -> p.replacements)).apply(instance, ExtendedBlockReplacementProcessor::new));

	private final Block targetBlock;
	private final BlockStateProvider replacements;
	private final float replaceChance;

	public ExtendedBlockReplacementProcessor(Block targetBlock, float replaceChance, BlockStateProvider replacements) {
		this.targetBlock = targetBlock;
		this.replacements = replacements;
		this.replaceChance = replaceChance;
	}

	@Override
	public Template.BlockInfo process(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo originalBlockInfo, Template.BlockInfo currentBlockInfo, PlacementSettings placementSettings, @Nullable Template template) {
		if (currentBlockInfo.state.getBlock() == this.targetBlock) {
			Random random = placementSettings.getRandom(currentBlockInfo.pos);
			if (random.nextFloat() < this.replaceChance) {
				BlockState newState = replacements.getState(random, blockPos);
				newState = ExtendedBlockStateUtils.copyBlockStateToBlockState(newState, currentBlockInfo.state);
				return new Template.BlockInfo(currentBlockInfo.pos, newState, null);
			}
		}

		return currentBlockInfo;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.BLOCK_REPLACEMENT_PROCESSOR;
	}
}