package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.special_placement;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.StructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedBMSmithy extends SinglePoolStructure {

	public ExtendedBMSmithy(StructureType type) {
		super(type);
	}


	@Override
	public IStartFactory<SinglePoolConfig> getStructureStartFactory() {
		return ExtendedBMSmithy.Start::new;
	}

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

	public static class Start extends SinglePoolStructure.Start {
        public Start(Structure<SinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

    	@Override
    	public int generatePieceOffset() {
    		return 0;
    	}
    }
}
