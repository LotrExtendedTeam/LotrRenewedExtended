package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.mojang.serialization.Codec;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class GraniteWhiteWoolBeehiveThatchRemoverProcessor extends StructureProcessor {
	public static final GraniteWhiteWoolBeehiveThatchRemoverProcessor INSTANCE = new GraniteWhiteWoolBeehiveThatchRemoverProcessor();
	public static final Codec<GraniteWhiteWoolBeehiveThatchRemoverProcessor> CODEC = Codec.unit(() -> INSTANCE);

	private GraniteWhiteWoolBeehiveThatchRemoverProcessor() {}

	@Nullable
	public Template.BlockInfo processBlock(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData) {
		BlockState blockstate = blockInfoWorld.state;
		if (blockstate.is(Blocks.POLISHED_GRANITE)||blockstate.is(Blocks.BEEHIVE)||blockstate.is(Blocks.WHITE_WOOL)||blockstate.is(LOTRBlocks.THATCH.get())) {
			return new Template.BlockInfo(blockInfoWorld.pos, Blocks.AIR.defaultBlockState(), (CompoundNBT)null);
		} else {
			return blockInfoWorld;
		}
	}

	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.THATCH_POLISHED_GRANITE_BEEHIVE_WHITE_WOOL_PROCESSOR;
	}
}