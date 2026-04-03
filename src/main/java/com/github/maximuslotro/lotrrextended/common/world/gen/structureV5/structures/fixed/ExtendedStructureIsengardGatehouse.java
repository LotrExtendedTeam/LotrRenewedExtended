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

/**
 * {@link ExtendedStructureIsengardGatehouse} is a custom single-pool structure that generates the tower of Isilostirion in a certain location
 */
public class ExtendedStructureIsengardGatehouse extends ExtendedSinglePoolStructure {
	/**
     * Constructs an ExtendedStructureAreaCheckChunk structure with the specified structure type.
     *
     * @param type The type of the structure.
     */
	public ExtendedStructureIsengardGatehouse(ExtendedStructureType type) {
		super(type);
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, ExtendedSinglePoolConfig featureConfig) {
		return chunkX == 2339 && chunkZ == 2653 && ExtendedServerConfig.canSpawnStructures();
	}

	@Override
	public IStartFactory<ExtendedSinglePoolConfig> getStructureStartFactory() {
		return ExtendedStructureIsengardGatehouse.Start::new;
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
			return new BlockPos(3, 86, 3);
		}

		@Override
		public boolean shouldProjectStartToHeightmap(){
			return false;
		}
		@Override
		public boolean useAboveBlockForFoundation() {
			return true;
		}
	}
}
