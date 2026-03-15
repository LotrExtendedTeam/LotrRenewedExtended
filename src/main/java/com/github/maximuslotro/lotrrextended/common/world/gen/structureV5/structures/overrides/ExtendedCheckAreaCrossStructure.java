package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.overrides;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.ExtendedSinglePoolStructure;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;

/**
 * {@link ExtendedCheckAreaCrossStructure} is a custom single-pool structure representing a smithy building.
 * <p>
 * It overrides the terrain suitability check to ensure the structure only generates
 * on relatively flat terrain by sampling surface heights within the chunk.
 */
public class ExtendedCheckAreaCrossStructure extends ExtendedSinglePoolStructure {
	/**
     * Constructs an ExtendedStructureCrossCheckArea structure with the specified structure type.
     *
     * @param type The type of the structure.
     */
	public ExtendedCheckAreaCrossStructure(ExtendedStructureType type) {
		super(type);
	}
	/**
	 * Performs an additional terrain suitability check before structure generation.
	 * <p>
	 * Scans the chunk's surface height at 4-block intervals within the chunk,
	 * determining the minimum and maximum surface heights.
	 * If the height difference exceeds 4 blocks, the terrain is considered too uneven
	 * for structure placement, and generation is aborted.
	 *
	 * @param chunkGenerator The chunk generator providing terrain height data.
	 * @param biomeSource    The biome provider (not used in this check).
	 * @param seed           The world seed (not used in this check).
	 * @param chunkRandom    The random generator for the chunk (not used in this check).
	 * @param chunkX         The X coordinate of the chunk.
	 * @param chunkZ         The Z coordinate of the chunk.
	 * @param biome          The biome of the chunk (not used in this check).
	 * @param chunkPos       The chunk position object.
	 * @param featureConfig  The configuration for this feature (not used in this check).
	 * @return True if the terrain is flat enough (height difference ≤ 4), false otherwise.
	 */
	@Override
	public boolean additionalCheck(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, ExtendedSinglePoolConfig featureConfig) {
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		int[][] chunkOffsets = {{0, 0},{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

		for (int[] offset : chunkOffsets) {
		    int chunkOffsetX = offset[0];
		    int chunkOffsetZ = offset[1];

		    for (int dx = 0; dx < 16; dx += 4) {
		        for (int dz = 0; dz < 16; dz += 4) {
		            int blockX = ((chunkX + chunkOffsetX) << 4) + dx;
		            int blockZ = ((chunkZ + chunkOffsetZ) << 4) + dz;
		            int height = chunkGenerator.getFirstOccupiedHeight(blockX, blockZ, net.minecraft.world.gen.Heightmap.Type.WORLD_SURFACE_WG);
		            minY = Math.min(minY, height);
		            maxY = Math.max(maxY, height);
		            if (maxY - minY > 6) {
		                return false; // Too uneven across cardinal + center chunks
		            }
		        }
		    }
		}
		return true; // Flat enough across cardinal + center chunks
	}
}
