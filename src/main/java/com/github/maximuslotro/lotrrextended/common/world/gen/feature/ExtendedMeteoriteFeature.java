package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.init.ExtendedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

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

				double depth = dist <= innerR ? 5 : 5 * (1 - (dist - innerR) / (outerR - innerR));

				for (int i = 0; i < depth; i++) {
					level.setBlock(surface.below(i), Blocks.AIR.defaultBlockState(), 2);
				}

				placeCraterGround(level, rand, surface.below((int)depth - 1), dist, innerR, outerR);
			}
		}
		// Meteor sphere
		placeMeteorSphere(level, center.below(1), innerR);
		// Debris
		scatterDebris(level, rand, center, outerR + 4, config.debrisChance);

		return true;
	}

	private void placeMeteorSphere(ISeedReader level, BlockPos center, int radius) {
		BlockPos.Mutable pos$Mutable = new BlockPos.Mutable();
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					double dist = Math.sqrt(x*x + y*y + z*z);
					if (dist <= radius) {
						pos$Mutable.set(center.getX() + x, center.getY() + y, center.getZ() + z);
						level.setBlock(pos$Mutable, ExtendedBlocks.METEORITE_ORE.get().defaultBlockState(), 2);
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
			BlockState debris = rand.nextBoolean() ? ExtendedBlocks.METEORITE_ORE.get().defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState();
			if (level.isEmptyBlock(top)) {
				level.setBlock(top, debris, 2);
			}
		}
	}

	private void placeCraterGround(ISeedReader level, Random rand, BlockPos surface, double dist, int innerR, int outerR) {
		BlockState grass = Blocks.GRASS_BLOCK.defaultBlockState();
		BlockState cobble = Blocks.COBBLESTONE.defaultBlockState();
		BlockState slab = ExtendedBlocks.GRASS_BLOCK_SLAB.get().defaultBlockState();
		if (dist < innerR) {
			level.setBlock(surface, rand.nextFloat() < 0.4F ? cobble : grass, 2);
		} 
		else if (dist < outerR - 1) {
			level.setBlock(surface, slab, 2);
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
				minY = Math.min(minY, y);
				maxY = Math.max(maxY, y);
			}
		}
		return (maxY - minY) <= maxDelta;
	}
}