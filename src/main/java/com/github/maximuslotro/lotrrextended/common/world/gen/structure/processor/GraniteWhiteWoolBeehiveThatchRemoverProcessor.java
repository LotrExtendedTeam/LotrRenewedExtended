package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

/**
 * Structure processor that removes specific blocks from a structure during placement by
 * replacing them with air. This processor targets blocks of polished granite, beehive,
 * white wool, and thatch from the LOTR mod, effectively clearing these blocks from the
 * generated structure.
 * <p>
 * Implemented as a singleton instance.
 */
@Deprecated
// TODO clean up structures pieces so that this is not needed
public class GraniteWhiteWoolBeehiveThatchRemoverProcessor extends StructureProcessor {
	/** Singleton instance of this processor. */
	public static final GraniteWhiteWoolBeehiveThatchRemoverProcessor INSTANCE = new GraniteWhiteWoolBeehiveThatchRemoverProcessor();
	/** Codec for serialization and deserialization of this processor. */
	public static final Codec<GraniteWhiteWoolBeehiveThatchRemoverProcessor> CODEC = Codec.unit(() -> INSTANCE);
	/** Private constructor to enforce singleton pattern. */
	private GraniteWhiteWoolBeehiveThatchRemoverProcessor() {}
	/**
	 * Processes each block during structure placement. If the block is polished granite,
	 * a beehive, white wool, or thatch (from LOTR mod), it replaces the block with air,
	 * effectively removing it from the structure.
	 *
	 * @param world the world reader used to query block states
	 * @param pos the position within the structure (unused here)
	 * @param blockPos the world position of the block being processed
	 * @param blockInfoLocal block info relative to the structure
	 * @param blockInfoWorld block info in the world at blockPos
	 * @param placmentData placement settings containing random and rotation info
	 * @return a new BlockInfo with air replacing targeted blocks, or the original block info otherwise
	 */
	@Override
	@Nullable
	public Template.BlockInfo processBlock(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData) {
		BlockState blockstate = blockInfoWorld.state;
		if (blockstate.is(Blocks.POLISHED_GRANITE)||blockstate.is(Blocks.BEEHIVE)||blockstate.is(Blocks.WHITE_WOOL)||blockstate.is(LOTRBlocks.THATCH.get())) {
			return new Template.BlockInfo(blockInfoWorld.pos, Blocks.AIR.defaultBlockState(), null);
		} else {
			return blockInfoWorld;
		}
	}
	/**
	 * Returns the processor type for this structure processor.
	 *
	 * @return the structure processor type representing this removal processor
	 */
	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.THATCH_POLISHED_GRANITE_BEEHIVE_WHITE_WOOL_PROCESSOR;
	}
}