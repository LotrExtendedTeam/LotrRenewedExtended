package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.fixed;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.starts.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.ExtendedSinglePoolStructure;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedStructureOrthanc extends ExtendedSinglePoolStructure {

	public ExtendedStructureOrthanc(ExtendedStructureType type) {
		super(type);
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, ExtendedSinglePoolConfig featureConfig) {
		return chunkX == 2340 && chunkZ == 2628 && ExtendedServerConfig.canSpawnStructures();
	}

	@Override
	public IStartFactory<ExtendedSinglePoolConfig> getStructureStartFactory() {
		return ExtendedStructureOrthanc.Start::new;
	}

	public static class Start extends ExtendedStructureStartV5<ExtendedSinglePoolConfig> {
		/**
         * Constructs a new {@link Start} instance for {@link ExtendedSinglePoolStructure}.
         *
         * @param structureIn      The structure instance.
         * @param chunkX           The chunk X coordinate.
         * @param chunkZ           The chunk Z coordinate.
         * @param mutableBoundingBox The mutable bounding box for structure placement.
         * @param referenceIn      The structure reference.
         * @param seedIn           The world seed used for placement.
         */
		public Start(Structure<ExtendedSinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}
		
		@Override
		public Rotation setRotation() {
			return Rotation.NONE;
		}

		@Override
		public BlockPos setoffset() {
			return new BlockPos(0, 0, 0);
		}
	}
}
