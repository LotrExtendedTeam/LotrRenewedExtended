package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.IExtendedSwappableTexture;
import lotr.common.block.LOTRBlockStates;
import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.Tags;

public class ExtendedMeteoriteFeature extends Feature<ExtendedMeteoriteFeatureConfig> {
	public ExtendedMeteoriteFeature(Codec<ExtendedMeteoriteFeatureConfig> pCodec) {
		super(pCodec);
	}

	@Override
	public boolean place(ISeedReader level, ChunkGenerator generator, Random rand, BlockPos origin, ExtendedMeteoriteFeatureConfig config) {
		int innerR = config.centerRadius.sample(rand);
		int outerR = config.outerRadius.sample(rand);
		BlockPos center = level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, origin);
		if (!isAreaFlat(level, center, outerR, 3)) {
			return false;
		}
		BlockPos.Mutable pos$Mutable = new BlockPos.Mutable();
		// Crater shaping
		for (int dx = -outerR; dx <= outerR; dx++) {
			for (int dz = -outerR; dz <= outerR; dz++) {

				double dist = Math.sqrt(dx*dx + dz*dz);
				if (dist > outerR) {
					continue;
				}

				pos$Mutable.set(center.getX() + dx, 0, center.getZ() + dz);
				BlockPos surface = level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos$Mutable);

				double maxDepth = 3;
				double depth = dist <= innerR ? maxDepth : maxDepth * (1 - Math.pow(Math.min(Math.max((dist - innerR) / (outerR - innerR), 0), 1), 1.5));

				for (int i = 0; i < depth; i++) {
					level.setBlock(surface.below(i), Blocks.AIR.defaultBlockState(), 2);
				}
				BlockPos surface1 = level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos$Mutable);
				placeCraterGround(level, rand, surface1, dist, innerR, outerR);
			}
		}
		BlockPos NewCenter = level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, origin);
		// Meteor sphere
		placeMeteorSphere(level, rand, NewCenter.below(rand.nextBoolean()? 2 : 1), innerR);
		// Debris
		scatterDebris(level, rand, center, outerR, config.debrisChance);

		return true;
	}

	private void placeMeteorSphere(ISeedReader level, Random rand, BlockPos center, int radius) {
		BlockPos.Mutable pos$Mutable = new BlockPos.Mutable();
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					double r2 = radius * radius;
					if (x*x + y*y + z*z <= r2 - 0.5) {
						pos$Mutable.set(center.getX() + x, center.getY() + y, center.getZ() + z);
						level.setBlock(pos$Mutable, getMeteoriteBlock(rand, true), 2);
						if(y>=radius && level.getBlockState(pos$Mutable.above()).isAir(level, pos$Mutable.above()) && rand.nextFloat()>0.6f) {
							level.setBlock(pos$Mutable.above(), ExtendedBlocks.MOSS_CARPET.get().defaultBlockState(), 2);
						}
					}
				}
			}
		}
	}

	private void scatterDebris(ISeedReader level, Random rand, BlockPos center, int outerR, float chance) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		for (int i = 0; i < outerR * 20; i++) {
			if (rand.nextFloat() > chance) {
				continue;
			}
			int dx = rand.nextInt(outerR * 2) - outerR;
			int dz = rand.nextInt(outerR * 2) - outerR;
			pos.set(center.getX() + dx, 0, center.getZ() + dz);
			BlockPos top = level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
			if (level.isEmptyBlock(top)) {
				if(level.getBlockState(top).getBlock() instanceof SlabBlock) {
					top = top.below();
				}
				level.setBlock(top.below(), getMeteoriteBlock(rand, false), 2);
			}
		}
	}

	private void placeCraterGround(ISeedReader level, Random rand, BlockPos surface, double dist, int innerR, int outerR) {
		BlockState grass = Blocks.GRASS_BLOCK.defaultBlockState();
		BlockState slab = ExtendedBlocks.GRASS_BLOCK_SLAB.get().defaultBlockState().setValue(IExtendedSwappableTexture.USELOTRTEXTURE, false)
			.setValue(AxialSlabBlock.TYPE, SlabType.BOTTOM).setValue(LOTRBlockStates.SLAB_AXIS, Axis.Y).setValue(SlabBlock.WATERLOGGED, false);
		if (dist < outerR - 1 && level.getBlockState(surface.below()).is(Tags.Blocks.DIRT)) {
			if(rand.nextFloat()>0.6f) {
				level.setBlock(surface, slab, 2);
			}else {
				level.setBlock(surface.below(), grass, 2);
				if(rand.nextFloat()>0.6) {
					level.setBlock(surface, ExtendedBlocks.MOSS_CARPET.get().defaultBlockState(), 2);
				}
			}
		}
	}

	private boolean isAreaFlat(ISeedReader level, BlockPos center, int radius, int maxDelta) {
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		BlockPos.Mutable pos$Mutable = new BlockPos.Mutable();
		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				pos$Mutable.set(center.getX() + dx, 0, center.getZ() + dz);
				BlockPos surface = level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos$Mutable);
				int y = surface.getY();
				// Check for water at the surface
				if (level.getFluidState(surface).is(FluidTags.WATER)) {
					return false; // immediately fail if water is present
				}
				minY = Math.min(minY, y);
				maxY = Math.max(maxY, y);
			}
		}
		return (maxY - minY) <= maxDelta;
	}

	private BlockState getMeteoriteBlock(Random rand, boolean placeOre) {
		float choice = rand.nextFloat();
		if(choice>0.7f && placeOre) {
			return ExtendedBlocks.METEORITE_ORE.get().defaultBlockState();
		} else if(choice>0.3f) {
			return rand.nextFloat()>0.6f ? ExtendedBlocks.SCORCHED_COBBLESTONE.get().defaultBlockState() : ExtendedBlocks.SCORCHED_STONE.get().defaultBlockState();
		}else {
			return Blocks.COBBLESTONE.defaultBlockState();
		}

	}
}