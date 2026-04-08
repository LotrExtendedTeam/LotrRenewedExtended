package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.fixed;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.ExtendedSinglePoolStructure;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;

/**
 * {@link ExtendedStructureIsilostirion} is a custom single-pool structure that generates the tower of Isilostirion in a certain location
 */
public class ExtendedStructureIsilostirion extends ExtendedSinglePoolStructure {

	public ExtendedStructureIsilostirion(ExtendedStructureType type) {
		super(type);
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, ExtendedSinglePoolConfig featureConfig) {
		return chunkX == -686 && chunkZ == 260 && ExtendedServerConfig.canSpawnStructure(ExtendedServerConfig.enableFixedStructureTowerHills);
	}
}
