package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.checks;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;

/**
 * {@link ExtendedStructureCheckAreaChunkFlat} is a custom single-pool structure representing a building.
 * <p>
 * It overrides the terrain suitability check to ensure the structure only generates
 * on relatively flat terrain by sampling surface heights within the chunk.
 */
public class ExtendedStructureCheckAreaChunkFlat extends SinglePoolStructure {
	/**
     * Constructs an ExtendedStructureAreaCheckChunk structure with the specified structure type.
     *
     * @param type The type of the structure.
     */
	public ExtendedStructureCheckAreaChunkFlat(ExtendedStructureType type) {
		super(type);
	}
	/**
	 * Performs an additional terrain suitability check before structure generation.
	 * <p>
	 * Scans the chunk's surface height at 4-block intervals within the chunk,
	 * determining the minimum and maximum surface heights.
	 * If the height difference exceeds 3 blocks, the terrain is considered too uneven
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
	public boolean additionalCheck(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, SinglePoolConfig featureConfig) {
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (int dx = 0; dx < 16; dx += 4) {
			for (int dz = 0; dz < 16; dz += 4) {
				int blockX = (chunkX << 4) + dx;
				int blockZ = (chunkZ << 4) + dz;
				int height = chunkGenerator.getFirstOccupiedHeight(blockX, blockZ, net.minecraft.world.gen.Heightmap.Type.WORLD_SURFACE_WG);
				minY = Math.min(minY, height);
				maxY = Math.max(maxY, height);
				if (maxY - minY > 4) {
					return false; // Too uneven
				}
			}
		}
		return true; // Flat enough
	}
}
