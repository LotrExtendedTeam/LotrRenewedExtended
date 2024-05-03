package com.github.maximuslotro.lotrrextended.world.gen.structure.processor;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class SinglePieceFoundationProcessor extends StructureProcessor{
	public static final Codec<SinglePieceFoundationProcessor> CODEC = Codec.unit(SinglePieceFoundationProcessor::new);

	public SinglePieceFoundationProcessor() {}

	@Override
	public Template.BlockInfo process(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData, @Nullable Template template) {
		if(blockInfoLocal.pos.getY()==0) {
			for (int yColumnValue = blockInfoWorld.pos.getY() - 1; yColumnValue > 1; --yColumnValue) {
				BlockPos blockpos1 = new BlockPos(blockInfoWorld.pos.getX(), yColumnValue, blockInfoWorld.pos.getY());
				if (!world.isEmptyBlock(blockpos1) && !world.getBlockState(blockpos1).getMaterial().isLiquid()) {
					break;
				}
				Biome biome = world.getBiome(blockpos1);
				if(world instanceof IWorld) {
					((IWorld)world).setBlock(blockpos1, biome.getGenerationSettings().getSurfaceBuilder().get().config.getUnderMaterial(), 2);
				}else {
					ExtendedLog.info("Structure did not make foundation because processor was passed a weird world that didnt extend IWorld!");
				}
			}
		}
		return blockInfoWorld;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.WOOD_PALETTE_PROCESSOR;
	}
}
