package com.github.maximuslotro.lotrrextended.world.gen.structure.processor;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedStructureProcessors;
import com.github.maximuslotro.lotrrextended.utils.BlockStateUtils;
import com.mojang.serialization.Codec;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
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

	@Nullable
	public Template.BlockInfo processBlock(IWorldReader p_230386_1_, BlockPos p_230386_2_, BlockPos p_230386_3_, Template.BlockInfo p_230386_4_, Template.BlockInfo p_230386_5_, PlacementSettings p_230386_6_) {
		BlockState blockstate = p_230386_5_.state;
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK.get(), blockstate), (CompoundNBT)null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK.get(), blockstate), (CompoundNBT)null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_SLAB.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_SLAB.get(), blockstate), (CompoundNBT)null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_SLAB.get(), blockstate), (CompoundNBT)null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_STAIRS.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_STAIRS.get(), blockstate), (CompoundNBT)null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_STAIRS.get(), blockstate), (CompoundNBT)null);
			}
		}
		if (blockstate.is(LOTRBlocks.ARNOR_BRICK_WALL.get())) {
			Random rand = new Random();
			int num = rand.nextInt(8);
			if(num==0) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_BRICK_WALL.get(), blockstate), (CompoundNBT)null);
			}
			else if (num<=2) {
				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_BRICK_WALL.get(), blockstate), (CompoundNBT)null);
			}
		}
//		if (blockstate.is(LOTRBlocks.ARNOR_PILLAR.get())) {
//			Random rand = new Random();
//			int num = rand.nextInt(8);
//			if(num==0) {
//				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_PILLAR.get(), blockstate), (CompoundNBT)null);
//			}
//			else if (num==1) {
//				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_PILLAR.get(), blockstate), (CompoundNBT)null);
//			}
//		}
//		if (blockstate.is(LOTRBlocks.ARNOR_PILLAR_SLAB.get())) {
//			Random rand = new Random();
//			int num = rand.nextInt(8);
//			if(num==0) {
//				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.CRACKED_ARNOR_PILLAR_SLAB.get(), blockstate), (CompoundNBT)null);
//			}
//			else if (num<=2) {
//				return new Template.BlockInfo(p_230386_5_.pos, BlockStateUtils.copyBlockStateToBlock(LOTRBlocks.MOSSY_ARNOR_PILLAR_SLAB.get(), blockstate), (CompoundNBT)null);
//			}
//		}
		return p_230386_5_;
	}

	protected IStructureProcessorType<?> getType() {
		return ExtendedStructureProcessors.ARNOR_WEATHERER_PROCESSOR;
	}
}