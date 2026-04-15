package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mojang.serialization.Codec;

import lotr.common.world.map.MapSettingsManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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
		// Check if wall is even in check, don't add additional generation time to chunks without walls
		// Convert corner position to chunk position, also sanity check idk
		if(isWallChunkCached(config, origin.getX() >> 4, origin.getZ() >> 4)) {
			// Illiterate through all z-x blocks in chunk
			int startX = origin.getX();
			int startZ = origin.getZ();
			for (int x = startX; x < startX + 16; x++) {
				for (int z = startZ; z < startZ + 16; z++) {
					// Set wall weight
					pos$Mutable.set(x, config.wallHeight, z);
					// Check is wall at this pos in chunk
					// Check if road goes through wall near here
					boolean roadExistsInRange = (MapSettingsManager.serverInstance().getCurrentLoadedMap().getRoadPointCache().getRoadCentreCloseness(x, z, 8) >= 0.0F);
					if (isWallBlock(config, x, z, roadExistsInRange)) {
						// Check for wall edge type
						EdgeType edge = isEdgeBlock(config, x, z, roadExistsInRange);
						// Place wall down from wall height to ground height
						for (int y = config.wallHeight; y> (roadExistsInRange ? config.wallPortHeight : 0); y--) {
							// Check if should place wall edge decoration
							if (y == config.wallHeight && (edge==config.edgeType || (edge!=EdgeType.NONE && config.edgeType==EdgeType.BOTH))) {
								// Place a different blocks for edges if desired
								if ((x + z) % 2 == 0) {
									for(int spikei = config.wallHeight+1; spikei <= config.wallHeight+config.spikeHeight && spikei<256; spikei++) {
										pos$Mutable.setY(spikei);
										level.setBlock(pos$Mutable, config.spikeState, 3);
									}
								}else {
									for(int spikei = config.wallHeight+1; spikei<= config.wallHeight+config.spikeGapHeight && spikei<256; spikei++) {
										pos$Mutable.setY(spikei);
										level.setBlock(pos$Mutable, config.spikeGapState, 3);
									}
								}
							}
							pos$Mutable.setY(y);
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

	private static final Map<Long, Boolean> WALL_CACHE = new HashMap<>();
	public static boolean isWallChunkCached(ExtendedWallsFeatureConfig config, int chunkX, int chunkZ) {
		long key = ChunkPos.asLong(chunkX, chunkZ);
		Boolean cached = WALL_CACHE.get(key);
		if (cached != null) return cached;
		// Convert chunk position back to minimum world cords
		BlockPos chunkOrigin = new BlockPos(chunkX << 4, 0, chunkZ << 4);
		boolean isWall = isWallInChunk(config, chunkOrigin);
		WALL_CACHE.put(key, isWall);
		return isWall;
	}

	private static boolean isWallInChunk(ExtendedWallsFeatureConfig config, BlockPos chunkOrigin) {
		int minX = chunkOrigin.getX();
		int maxX = minX + 15;
		int minZ = chunkOrigin.getZ();
		int maxZ = minZ + 15;
		int midX = minX + 8;
		int midZ = minZ + 8;
		return isWallBlock(config, minX, minZ, false) 
				|| isWallBlock(config, minX, maxZ, false) 
				|| isWallBlock(config, maxX, minZ, false) 
				|| isWallBlock(config, maxX, maxZ, false) 
				|| isWallBlock(config, midX, midZ, false);
	}

	public static boolean isWallBlock(ExtendedWallsFeatureConfig config, int x, int z, boolean roadExistsInRange) {
		double dx = x - config.wallsCenterX;
		double dz = z - config.wallsCenterZ;
		double distSq = dx * dx + dz * dz;
		double halfT = config.wallThickness / 2.0;
		// compute taper for buttress
		double extra = 0;
		if (!roadExistsInRange && config.buttressConfig.count > 0 && config.buttressConfig.widthRad > 0) {
			double angle = getAngleRad(config, x, z);
			if (angle < 0) angle += Math.PI * 2;

			double step = (Math.PI*2) / config.buttressConfig.count;
			double nearest = Math.round(angle / step) * step;

			double distFromCenter = Math.abs(angle - nearest);
			if (distFromCenter > Math.PI) {
				distFromCenter = Math.PI*2 - distFromCenter;
			}

			// cosine taper for rounded edges
			// Also causes extra small buttresses between buttresses, keeping as feature not bug
			extra = config.buttressConfig.maxExtra * Math.cos(distFromCenter / config.buttressConfig.widthRad * Math.PI/2);
			if (extra < 0) extra = 0; // safety clamp
		}
		double minRadius = config.wallsRadius - halfT;
		double maxRadius = config.wallsRadius + halfT + extra;;
		double minRadiusSq = minRadius * minRadius;
		double maxRadiusSq = maxRadius * maxRadius;
		return distSq >= minRadiusSq && distSq <= maxRadiusSq;
	}

	private static double getAngleRad(ExtendedWallsFeatureConfig config, int x, int z) {
		return Math.atan2(z - config.wallsCenterZ, x - config.wallsCenterX);
	}

	private EdgeType isEdgeBlock(ExtendedWallsFeatureConfig config, int x, int z, boolean roadExistsInRange) {
		if (!isWallBlock(config, x, z, roadExistsInRange)) {
			return EdgeType.NONE;
		}

		boolean insideNeighbor = false;
		boolean outsideNeighbor = false;

		int[] dxArr = {1, -1, 0, 0};
		int[] dzArr = {0, 0, 1, -1};

		for (int i = 0; i < 4; i++) {
			boolean neighborWall = isWallBlock(config, x + dxArr[i], z + dzArr[i], roadExistsInRange);

			if (neighborWall) {
				insideNeighbor = true;
			} else {
				outsideNeighbor = true;
			}
		}

		if (insideNeighbor && outsideNeighbor) {
			// thin band edge — treat as outer by default
			return EdgeType.OUTER;
		}

		if (outsideNeighbor) {
			// need to determine inner vs outer
			double dx = x - config.wallsCenterX;
			double dz = z - config.wallsCenterZ;
			double distSq = dx * dx + dz * dz;
			double radiusSq = config.wallsRadius * config.wallsRadius;

			return distSq > radiusSq ? EdgeType.OUTER : EdgeType.INNER;
		}

		return EdgeType.NONE;
	}

	public enum EdgeType {
		NONE(0), INNER(1), OUTER(2), BOTH(3);

		public final int edgeType;

		EdgeType(int i) {
			this.edgeType = i;
		}

		public static EdgeType fromInt(int value) {
			for (EdgeType type : values()) {
				if (type.edgeType == value) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid EdgeType value: " + value);
		}
	}
}