package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.special.fixed;

import com.github.maximuslotro.lotrrextended.common.config.ExtendedServerConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;

/**
 * {@link ExtendedStructureLastHomelyHouse} is a custom single-pool structure that generates the main Rivendell build in a certain location
 */
public class ExtendedStructureLastHomelyHouse extends SinglePoolStructure {
	/**
     * Constructs an ExtendedStructureAreaCheckChunk structure with the specified structure type.
     *
     * @param type The type of the structure.
     */
	public ExtendedStructureLastHomelyHouse(ExtendedStructureType type) {
		super(type);
	}

	@Override
	public IStartFactory<SinglePoolConfig> getStructureStartFactory() {
		return ExtendedStructureLastHomelyHouse.Start::new;
	}

	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, SinglePoolConfig featureConfig) {
		return chunkX == 2988 && chunkZ == -76 && ExtendedServerConfig.canSpawnStructure(ExtendedServerConfig.enableFixedStructureImladris);
	}

	public static class Start extends SinglePoolStructure.Start {
		/**
         * Constructs a new {@link Start} instance for {@link SinglePoolStructure}.
         *
         * @param structureIn      The structure instance.
         * @param chunkX           The chunk X coordinate.
         * @param chunkZ           The chunk Z coordinate.
         * @param mutableBoundingBox The mutable bounding box for structure placement.
         * @param referenceIn      The structure reference.
         * @param seedIn           The world seed used for placement.
         */
		public Start(Structure<SinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}
		
		@Override
		public Rotation setRotation() {
			return Rotation.CLOCKWISE_90;
		}
	}
}
