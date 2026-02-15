package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.world.map.MapSettingsManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class ExtendedWallsFeature extends Feature<ExtendedWallsFeatureConfig> {
	public ExtendedWallsFeature(Codec<ExtendedWallsFeatureConfig> pCodec) {
		super(pCodec);
	}

	@Override
	public boolean place(ISeedReader level, ChunkGenerator generator, Random rand, BlockPos origin, ExtendedWallsFeatureConfig config) {
		BlockPos.Mutable pos$Mutable = new BlockPos.Mutable().set(origin.getX(), config.wallHeight, origin.getZ());
		if(isWallInChunk(config, pos$Mutable)) {
			for (int i = origin.getX(); i<=origin.getX()+15; i++) {
				for (int k= origin.getZ(); k<=origin.getZ()+15; k++) {
					pos$Mutable.set(i, config.wallHeight, k);
					//level.setBlock(pos$Mutable, Blocks.GREEN_WOOL.defaultBlockState(), 2);
					if (isWallBlock(config, i, k)) {
						EdgeType edge = isEdgeBlock(config, i, k);
						boolean roadExistsInRange = (MapSettingsManager.serverInstance().getCurrentLoadedMap().getRoadPointCache().getRoadCentreCloseness(i, k, 8) >= 0.0F);

						for (int j = config.wallHeight; j> (roadExistsInRange ? config.wallPortHeight : 0); j--) {
							pos$Mutable.setY(j);
							if (edge==EdgeType.OUTER && j == config.wallHeight) {
					            // Place a different block for edges if desired
					            level.setBlock(pos$Mutable.above(), config.wallState, 2);
					        }
							level.setBlock(pos$Mutable, config.wallState, 2);
							BlockState below = level.getBlockState(pos$Mutable.below());
							if (below.getBlock() == Blocks.GRASS_BLOCK || below.getBlock() == Blocks.DIRT || below.getBlock() == Blocks.STONE) {
								break;
							}
						}
					}
				}
			}
		}
		return true;
	}

	private EdgeType isEdgeBlock(ExtendedWallsFeatureConfig config, int x, int z) {
	    double halfT = config.wallThickness / 2.0;

	    // Distance of this block from wall center
	    double dist = Math.sqrt((x - config.wallsCenterX) * (x - config.wallsCenterX) + 
	                            (z - config.wallsCenterZ) * (z - config.wallsCenterZ)) - config.wallsRadius;

	    if (dist < -halfT || dist > halfT) return EdgeType.NONE;

	    // Check 4 neighbors (N, S, E, W)
	    int[] dx = {1, -1, 0, 0};
	    int[] dz = {0, 0, 1, -1};

	    for (int i = 0; i < 4; i++) {
	        double neighborDist = Math.sqrt((x + dx[i] - config.wallsCenterX) * (x + dx[i] - config.wallsCenterX) +
	                                        (z + dz[i] - config.wallsCenterZ) * (z + dz[i] - config.wallsCenterZ)) - config.wallsRadius;
	        if (neighborDist < -halfT) return EdgeType.INNER; // neighbor is inside: current block is inner edge
	        if (neighborDist > halfT) return EdgeType.OUTER; // neighbor is outside: current block is outer edge
	    }

	    return EdgeType.NONE; // all neighbors inside band
	}

	private enum EdgeType {
	    NONE, INNER, OUTER
	}

	private boolean isWallInChunk(ExtendedWallsFeatureConfig config, BlockPos chunkOrigin) {
	    int minX = chunkOrigin.getX();
	    int maxX = minX + 15;
	    int minZ = chunkOrigin.getZ();
	    int maxZ = minZ + 15;
	    int midX = minX + 8;
	    int midZ = minZ + 8;
	    return isWallBlock(config, minX, minZ) || isWallBlock(config, minX, maxZ) || isWallBlock(config, maxX, minZ) || isWallBlock(config, maxX, maxZ) || isWallBlock(config, midX, midZ);
	}

	private boolean isWallBlock(ExtendedWallsFeatureConfig config, int x, int z) {
	    double dx = x - config.wallsCenterX;
	    double dz = z - config.wallsCenterZ;
	    double distSq = dx * dx + dz * dz;
	    double halfT = config.wallThickness / 2.0;
	    double minRadius = config.wallsRadius - halfT;
	    double maxRadius = config.wallsRadius + halfT;
	    double minRadiusSq = minRadius * minRadius;
	    double maxRadiusSq = maxRadius * maxRadius;
	    return distSq >= minRadiusSq && distSq <= maxRadiusSq;
	}
}