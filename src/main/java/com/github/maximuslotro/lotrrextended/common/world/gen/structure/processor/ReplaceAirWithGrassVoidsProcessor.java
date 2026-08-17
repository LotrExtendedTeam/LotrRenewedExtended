package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

/**
 * A structure processor that replaces air blocks in generated structures with
 * {@code ExtendedBlocks.GRASS_VOID} blocks.  
 * <p>
 * This is used to prevent underground structure processors from overriding
 * specific structure blocks during generation. By replacing air with grass voids,
 * the original intended block palette for the structure is preserved, even in
 * terrain-heavy or modified environments.
 * </p>
 * <p>
 * This processor is implemented as a singleton, and is registered with a codec
 * to allow it to be serialized/deserialized when loading structure templates.
 * </p>
 *
 * <p><b>Usage:</b>  
 * Add this processor to a structure's placement settings so that, during generation,
 * all air blocks within the template are substituted with grass voids before being
 * finalized into the world.</p>
 *
 * @see StructureProcessor
 * @see ExtendedBlocks#GRASS_VOID
 * @see ExtendedStructureProcessors#GRASS_VOID_AIR_PROCESSOR
 */
@Deprecated
//TODO remove and replace with ExtendedBlockReplacementProcessor
public class ReplaceAirWithGrassVoidsProcessor extends StructureProcessor {
	/** Singleton instance of this processor. */
	public static final ReplaceAirWithGrassVoidsProcessor INSTANCE = new ReplaceAirWithGrassVoidsProcessor();

	/** Codec for serialization and deserialization of this processor. */
	public static final Codec<ReplaceAirWithGrassVoidsProcessor> CODEC = Codec.unit(() -> INSTANCE);

	/** Private constructor to enforce singleton pattern. */
	private ReplaceAirWithGrassVoidsProcessor() {}

	/**
     * Replaces air blocks in the structure with grass void blocks during placement.
     * <p>
     * This method checks the block in the world position being processed. If it is
     * air, it is replaced with a {@code GRASS_VOID} block. Otherwise, the original
     * block is left unchanged.
     * </p>
     *
     * @param world          the world reader used to query block states
     * @param pos            the position within the structure (unused here)
     * @param blockPos       the world position of the block being processed
     * @param blockInfoLocal block info relative to the structure
     * @param blockInfoWorld block info in the world at {@code blockPos}
     * @param placementData  placement settings containing random and rotation info
     * @return a new {@link net.minecraft.world.gen.feature.template.Template.BlockInfo} with grass void replacing air, or the original block info if not air
     */
	@Override
	@Nullable
	public Template.BlockInfo processBlock(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placementData) {
		BlockState blockstate = blockInfoWorld.state;
		if (blockstate.isAir()) {
			return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlocks.GRASS_VOID.get().defaultBlockState(), null);
		} else {
			return blockInfoWorld;
		}
	}

	/**
     * Returns the processor type for this structure processor.
     *
     * @return the {@link IStructureProcessorType} representing this processor
     */
	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.GRASS_VOID_AIR_PROCESSOR;
	}
}