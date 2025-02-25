package com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.github.maximuslotro.lotrrextended.common.utils.BlockStateUtils;
import com.mojang.serialization.Codec;

import lotr.common.init.ExtendedBlocks;
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

public class ArnorBrickWeathererProcessor extends StructureProcessor {
	public static final ArnorBrickWeathererProcessor INSTANCE = new ArnorBrickWeathererProcessor();
	public static final Codec<ArnorBrickWeathererProcessor> CODEC = Codec.unit(() -> INSTANCE);

	private ArnorBrickWeathererProcessor() {}

	@Override
	@Nullable
	public Template.BlockInfo processBlock(IWorldReader world, BlockPos pos, BlockPos blockPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoWorld, PlacementSettings placmentData) {
		BlockState blockstate = blockInfoWorld.state;
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_SLAB.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_SLAB.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_SLAB.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_STAIRS.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_STAIRS.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_STAIRS.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_WALL.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_WALL.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_WALL.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_PILLAR.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_ARNOR_PILLAR.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_ARNOR_PILLAR.get(), blockstate), null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_PILLAR_SLAB.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_ARNOR_PILLAR_SLAB.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_ARNOR_PILLAR_SLAB.get(), blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICKS)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(Blocks.CRACKED_STONE_BRICKS, blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICKS, blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICK_SLAB)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_STONE_BRICK_SLAB.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICK_SLAB, blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICK_STAIRS)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_STONE_BRICK_STAIRS.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICK_STAIRS, blockstate), null);
			}
		}
		if (blockstate.is(Blocks.STONE_BRICK_WALL)) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_STONE_BRICK_WALL.get(), blockstate), null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(Blocks.MOSSY_STONE_BRICK_WALL, blockstate), null);
			}
		}
//		if (blockstate.is(LOTRBlocks.STONE_PILLAR.get())) {
//			Random rand = new Random();
//			int num = rand.nextInt(8);
//			if(num==0) {
//				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_STONE_PILLAR.get(), blockstate), (CompoundNBT)null);
//			}
//			else if (num<=2) {
//				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_STONE_PILLAR.get(), blockstate), (CompoundNBT)null);
//			}
//		}
//		if (blockstate.is(LOTRBlocks.STONE_PILLAR_SLAB.get())) {
//			Random rand = new Random();
//			int num = rand.nextInt(8);
//			if(num==0) {
//				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.CRACKED_STONE_PILLAR_SLAB.get(), blockstate), (CompoundNBT)null);
//			}
//			else if (num<=2) {
//				return new Template.BlockInfo(blockInfoWorld.pos, BlockStateUtils.copyBlockStateToBlock(ExtendedBlocks.MOSSY_STONE_PILLAR_SLAB.get(), blockstate), (CompoundNBT)null);
//			}
//		}
		return blockInfoWorld;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.ARNOR_WEATHERER_PROCESSOR;
	}
}