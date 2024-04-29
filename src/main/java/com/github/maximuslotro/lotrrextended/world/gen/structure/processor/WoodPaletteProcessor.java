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

public class WoodPaletteProcessor extends StructureProcessor{
	public static final Codec<WoodPaletteProcessor> CODEC = Codec.STRING.fieldOf("location")
			.orElse("minecraft:empty")
			.xmap(WoodPaletteProcessor::new, (p_237078_0_) -> {return p_237078_0_.location;})
			.codec();

	private final String location;
	private String picked;

	public WoodPaletteProcessor(String palette) {
		this.location = palette;
	}

	@Override
	public Template.BlockInfo process(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData, @Nullable Template template) {
		return blockInfoWorld;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.WOOD_PALETTE_PROCESSOR;
	}
}
