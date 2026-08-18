package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedBlockStateUtils;
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
 * StructureProcessor that simulates natural weathering effects on various brick blocks.
 * <p>
 * During structure placement, this processor randomly replaces blocks such as bricks,
 * slabs, stairs, walls, pillars, and their cracked or mossy variants, as well as vanilla stone bricks
 * and related blocks, with their weathered counterparts to add visual variety and realism.
 * <p>
 * The chance for weathering is randomized per block with the following approximate distribution:
 * <ul>
 *   <li>1 in 8 chance to become cracked</li>
 *   <li>2 in 8 chance to become mossy (for some blocks)</li>
 *   <li>Otherwise, the original block remains unchanged</li>
 * </ul>
 * <p>
 * This processor uses singleton pattern for easy reuse and is serialized via a Codec.
 */
public class BrickWeathererProcessor extends StructureProcessor {
	/** Singleton instance of the processor. */
	public static final BrickWeathererProcessor INSTANCE = new BrickWeathererProcessor();
	/** Codec for serialization and deserialization of this processor. */
	public static final Codec<BrickWeathererProcessor> CODEC = Codec.unit(() -> INSTANCE);
	/** Private constructor to enforce singleton usage. */
	private BrickWeathererProcessor() {}
	/**
	 * Processes blocks during structure placement, applying randomized weathering effects.
	 * If the block is one of the targeted Arnor or stone brick types, it may be replaced with
	 * cracked or mossy variants based on random chance.
	 *
	 * @param world the world reader used to check block states
	 * @param pos the local position within the structure template
	 * @param blockPos the actual world position where the block is placed
	 * @param blockInfoLocal the block info relative to the structure template
	 * @param blockInfoWorld the current block info at the world position
	 * @param placmentData the placement settings for the structure
	 * @return the modified BlockInfo with weathered block states or the original if no changes are applied
	 */
	@Override
	@Nullable
	public Template.BlockInfo processBlock(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData) {
		BlockState blockstate = blockInfoWorld.state;
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_SLAB.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_SLAB.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_SLAB.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_STAIRS.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_STAIRS.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_STAIRS.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_WALL.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_WALL.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_WALL.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_PILLAR.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_ARNOR_PILLAR.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_ARNOR_PILLAR.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_PILLAR_SLAB.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_ARNOR_PILLAR_SLAB.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_ARNOR_PILLAR_SLAB.get(), blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICKS)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(Blocks.CRACKED_STONE_BRICKS, blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICKS, blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICK_SLAB)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_STONE_BRICK_SLAB.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICK_SLAB, blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICK_STAIRS)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_STONE_BRICK_STAIRS.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICK_STAIRS, blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICK_WALL)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_STONE_BRICK_WALL.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICK_WALL, blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.STONE_PILLAR.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_STONE_PILLAR.get(), blockstate), null);
			}
			//			else if (num<=2) {
			//				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_STONE_PILLAR.get(), blockstate), null);
			//			}
		}
		if (blockstate.is(LOTRBlocks.STONE_PILLAR_SLAB.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, ExtendedBlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_STONE_PILLAR_SLAB.get(), blockstate), null);
			}
			//			else if (num<=2) {
			//				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_STONE_PILLAR_SLAB.get(), blockstate), null);
			//			}
		}
		return blockInfoWorld;
	}
	/**
	 * Returns the processor type identifying this processor.
	 *
	 * @return the processor type for ArnorBrickWeathererProcessor
	 */
	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.LOTR_WEATHERER_PROCESSOR;
	}
}