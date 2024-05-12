package com.github.maximuslotro.lotrrextended.world.gen.structure.processor;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class ArnorBrickWeathererProcessor extends StructureProcessor {
	public static final ArnorBrickWeathererProcessor INSTANCE = new ArnorBrickWeathererProcessor();
	public static final Codec<ArnorBrickWeathererProcessor> CODEC = Codec.unit(() -> INSTANCE);

	private ArnorBrickWeathererProcessor() {}

	@Nullable
	public Template.BlockInfo processBlock(IWorldReader p_230386_1_, BlockPos p_230386_2_, BlockPos p_230386_3_, Template.BlockInfo p_230386_4_, Template.BlockInfo p_230386_5_, PlacementSettings p_230386_6_) {
		return p_230386_5_;
	}

	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.ARNOR_WEATHERER_PROCESSOR;
	}
}