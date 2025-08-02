package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

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

/**
 * A structure processor that ensures a solid foundation below structures at Y=0 by filling
 * air or liquid blocks beneath the structure down to a non-liquid block with the biome's 
 * under material. This prevents floating structures by generating a single-piece foundation.
 */
public class SinglePieceFoundationProcessor extends StructureProcessor {
	/** Codec for serialization and deserialization of this processor. */
	public static final Codec<SinglePieceFoundationProcessor> CODEC = Codec.unit(SinglePieceFoundationProcessor::new);
	/** Default constructor. */
	public SinglePieceFoundationProcessor() {}
	/**
	 * Processes blocks during structure placement. For blocks at Y=0 in the local structure coordinate,
	 * this method fills empty or liquid blocks beneath in the world with the biome-specific under material
	 * until reaching a non-liquid block, creating a continuous foundation.
	 *
	 * @param world the world reader used to query block states
	 * @param pos the current structure position (unused)
	 * @param blockPos the position of the block being processed in the world
	 * @param blockInfoLocal block information relative to the structure
	 * @param blockInfoWorld block information in the world
	 * @param placmentData placement settings containing random and rotation info
	 * @param template the structure template (nullable)
	 * @return modified block information for placement, or original if no change
	 */
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
					ExtendedLog.warn("Structure did not make foundation because processor was passed a weird world that didnt extend IWorld!");
				}
			}
		}
		return blockInfoWorld;
	}
	/**
	 * Returns the processor type for this structure processor.
	 *
	 * @return the structure processor type representing the wood palette processor
	 */
	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.WOOD_PALETTE_PROCESSOR;
	}
}
