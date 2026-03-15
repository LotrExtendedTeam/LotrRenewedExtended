package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.fixed;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;

/**
 * {@link ExtendedStructureStoneOfHapless} is a custom single-pool structure that generates the stone of hapless in a certain location
 */
public class ExtendedStructureStoneOfHapless extends SinglePoolStructure {
	/**
     * Constructs an ExtendedStructureAreaCheckChunk structure with the specified structure type.
     *
     * @param type The type of the structure.
     */
	public ExtendedStructureStoneOfHapless(ExtendedStructureType type) {
		super(type);
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, SinglePoolConfig featureConfig) {
		return chunkX == -5773 && chunkZ == -246 && ExtendedServerConfig.canSpawnStructures();
	}
}
