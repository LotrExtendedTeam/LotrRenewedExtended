package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.mojang.serialization.Codec;

import lotr.common.world.map.MapSettingsManager;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

/**
 * Base class for surface structures in LOTR Extended that require customized spawn checks and conditions.
 * <p>
 * Handles water checks, road proximity checks, type-based spawn restrictions (e.g., Ranger camps, Bree settlements),
 * and provides hooks for additional condition checking on a per-structure basis.
 *
 * @param <C> The feature config type used for the structure.
 */
public abstract class ExtendedSurfaceStructuresV5<C extends IFeatureConfig> extends Structure<C>{
	/**
	 * The type of structure used for conditional checks during generation.
	 */
	private final ExtendedStructureType structureType;
	/**
	 * Constructs a new ExtendedSurfaceStructuresV5 with the specified codec and structure type.
	 *
	 * @param codec The codec for serializing the feature config.
	 * @param type  The structure type for controlling generation behavior.
	 */
	public ExtendedSurfaceStructuresV5(Codec<C> codec, ExtendedStructureType type) {
		super(codec);
		this.structureType = type;
	}
	/**
	 * Returns the generation stage for this structure, which is set to {@code SURFACE_STRUCTURES}.
	 *
	 * @return The decoration stage for generation.
	 */
	@Override
	public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}
	/**
	 * Returns the start factory for this structure, delegating to {@link #getStructureStartFactory()}.
	 *
	 * @return The start factory.
	 */
	@Override
	public IStartFactory<C> getStartFactory() {
		return getStructureStartFactory();
	}
	/**
	 * Determines if this structure can spawn in water.
	 * Override to allow structures to spawn in water.
	 *
	 * @return {@code false} by default.
	 */
	public boolean canSpawnInWater() {
		return false;
	}
	/**
	 * Determines if this structure can spawn near roads.
	 * Override to allow structures to spawn near roads.
	 *
	 * @return {@code false} by default.
	 */
	public boolean canSpawnNearRoad() {
		return false;
	}
	/**
	 * Provides the start factory for this structure type.
	 *
	 * @return The start factory implementation for the structure.
	 */
	public abstract IStartFactory<C> getStructureStartFactory();

	/**
	 * Checks if the structure can spawn at the specified chunk coordinates under various conditions:
	 * <ul>
	 *     <li>Water presence check unless {@link #canSpawnInWater()} is true.</li>
	 *     <li>Road proximity check unless {@link #canSpawnNearRoad()} is true.</li>
	 *     <li>Structure type-specific proximity restrictions (e.g., for Ranger camps and Bree settlements).</li>
	 *     <li>Calls {@link #additionalCheck} for further extensible checks.</li>
	 * </ul>
	 *
	 * @return {@code true} if the structure can spawn, {@code false} otherwise.
	 */
	@Override
	protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, C featureConfig) {
		int minX = chunkX << 4;
		int minZ = chunkZ << 4;
		int maxX = minX + 15;
		int maxZ = minZ + 15;

		// All 4 opposite corners
		int[][] corners = {
				{minX, minZ}, // NW
				{maxX, minZ}, // NE
				{minX, maxZ}, // SW
				{maxX, maxZ}  // SE
		};
		boolean waterPresent = false;
		for (int[] c : corners) {
			int x = c[0];
			int z = c[1];

			// Grab height of land. Will stop at first non-air block.
			int landHeight = chunkGenerator.getFirstOccupiedHeight(x, z, net.minecraft.world.gen.Heightmap.Type.WORLD_SURFACE_WG);

			// Grabs column of blocks at given position.
			IBlockReader column = chunkGenerator.getBaseColumn(x, z);

			// Top solid block at this corner
			BlockState topBlock = column.getBlockState(new BlockPos(x, landHeight, z));

			// Check for water
			if (!topBlock.getFluidState().isEmpty()) {
				waterPresent = true;
				break; // no need to check the rest
			}
		}

		// Check if can spawn in water
		if(waterPresent && !canSpawnInWater()) {
			return false;
		}

		// Check if structure intersects a road
		boolean roadExistsInRange = (MapSettingsManager.serverInstance().getCurrentLoadedMap().getRoadPointCache().getRoadCentreCloseness(minX+7, minZ+7, 16) >= 0.0F);

		if(!canSpawnNearRoad() && roadExistsInRange) {
			return false;
		}

		if(structureType==ExtendedStructureType.RANGER || structureType==ExtendedStructureType.RANGER_OPTIONAL_TOWER_HILLS) {
			if(structureType==ExtendedStructureType.RANGER_OPTIONAL_TOWER_HILLS) {
				// Anarostirion
				if(calculateChunkDistance(chunkX, chunkZ, -742, 6)<5) {
					return false;
				}
				// Elostirion
				if(calculateChunkDistance(chunkX, chunkZ, -796, 102)<5) {
					return false;
				}
				// Isilostirion
				if(calculateChunkDistance(chunkX, chunkZ, -686, 260)<5) {
					return false;
				}
			}
			int i = chunkX >> 4;
			int j = chunkZ >> 4;
			chunkRandom.setSeed((long)(i ^ j << 4) ^ seed);
			chunkRandom.nextInt();
			if(isNearStructure(ExtendedStructuresV5.GUNDABAD_CAMP.get(), chunkGenerator, seed, chunkRandom, chunkX, chunkZ, 10) || isNearStructure(ExtendedStructuresV5.GUNDABAD_ENCAMPMENT.get(), chunkGenerator, seed, chunkRandom, chunkX, chunkZ, 10)) {
				return false;
			}
		}
		if(structureType==ExtendedStructureType.BREE_HAMLET) {
			// Bree Town
			if(calculateChunkDistance(chunkX, chunkZ, 860, 36)<16) {
				return false;
			}
			// Staddle
			if(calculateChunkDistance(chunkX, chunkZ, 916, 36)<10) {
				return false;
			}
			// Combe
			if(calculateChunkDistance(chunkX, chunkZ, 940, 12)<10) {
				return false;
			}
			// Archet
			if(calculateChunkDistance(chunkX, chunkZ, 948, -12)<10) {
				return false;
			}
		}
		if(structureType==ExtendedStructureType.BREE_VILLAGE) {
			// Bree Town
			if(calculateChunkDistance(chunkX, chunkZ, 860, 36)<16) {
				return false;
			}
			// Staddle
			if(calculateChunkDistance(chunkX, chunkZ, 916, 36)<10) {
				return false;
			}
			// Combe
			if(calculateChunkDistance(chunkX, chunkZ, 940, 12)<10) {
				return false;
			}
			// Archet
			if(calculateChunkDistance(chunkX, chunkZ, 948, -12)<10) {
				return false;
			}
			int i = chunkX >> 4;
			int j = chunkZ >> 4;
			chunkRandom.setSeed((long)(i ^ j << 4) ^ seed);
			chunkRandom.nextInt();
			if(isNearStructure(ExtendedStructuresV5.BREE_HAMLET.get(), chunkGenerator, seed, chunkRandom, chunkX, chunkZ, 10)) {
				return false;
			}
		}
		if(structureType==ExtendedStructureType.RIVENDELL) {
			// Rivendell waypoint
			if(calculateChunkDistance(chunkX, chunkZ, 2988, -76)<10) {
				return false;
			}
		}
		if(structureType==ExtendedStructureType.TOWER_HILLS) {
			// Anarostirion
			if(calculateChunkDistance(chunkX, chunkZ, -742, 6)<5) {
				return false;
			}
			// Elostirion
			if(calculateChunkDistance(chunkX, chunkZ, -796, 102)<5) {
				return false;
			}
			// Isilostirion
			if(calculateChunkDistance(chunkX, chunkZ, -686, 260)<5) {
				return false;
			}
		}
		return additionalCheck(chunkGenerator, biomeSource, seed, chunkRandom, chunkX, chunkZ, biome, chunkPos, featureConfig);
	}
	/**
	 * Hook for subclasses to add additional structure spawn checks.
	 * Override to impose extra restrictions specific to your structure generation.
	 * <p>
	 * This method is called during structure placement to determine whether
	 * the structure can generate at the specified chunk position under the given conditions.
	 * By default, it returns {@code true}, allowing generation unless further restricted.
	 *
	 * @param chunkGenerator the chunk generator used for terrain generation
	 * @param biomeSource the biome provider used for biome lookups
	 * @param seed the world seed
	 * @param chunkRandom the random instance seeded for this chunk position
	 * @param chunkX the X coordinate of the chunk in chunk coordinates
	 * @param chunkZ the Z coordinate of the chunk in chunk coordinates
	 * @param biome the biome at the intended generation location
	 * @param chunkPos the position of the chunk
	 * @param featureConfig the structure's configuration
	 * @return {@code true} if the structure is allowed to generate at this location under these conditions, {@code false} otherwise
	 */
	public boolean additionalCheck(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, C featureConfig) {
		return true;
	}
	/**
	 * Checks if another structure exists within a specified radius of the target chunk position.
	 *
	 * @param structure    The structure to check proximity for.
	 * @param chunkGenerator The chunk generator.
	 * @param seed         The world seed.
	 * @param sharedSeedRandom The shared random generator.
	 * @param chunkPosX    The X chunk coordinate.
	 * @param chunkPosZ    The Z chunk coordinate.
	 * @param checkRadius  The radius in chunks to check.
	 * @return {@code true} if a structure exists within the specified radius, {@code false} otherwise.
	 */
	private boolean isNearStructure(Structure<?> structure, ChunkGenerator chunkGenerator, long seed, SharedSeedRandom sharedSeedRandom, int chunkPosX, int chunkPosZ, int checkRadius) {
		StructureSeparationSettings structureseparationsettings = chunkGenerator.getSettings().getConfig(Structure.VILLAGE);
		if (structureseparationsettings != null) {
			for (int i = chunkPosX - checkRadius; i <= chunkPosX + checkRadius; ++i) {
				for (int j = chunkPosZ - checkRadius; j <= chunkPosZ + checkRadius; ++j) {
					ChunkPos chunkpos = structure.getPotentialFeatureChunk(structureseparationsettings, seed, sharedSeedRandom, i, j);
					if (i == chunkpos.x && j == chunkpos.z) {
						return true;
					}
				}
			}

		}
		return false;
	}
	/**
	 * Calculates the Euclidean distance in chunk coordinates between two chunk positions.
	 *
	 * @param chunkX1 First chunk X.
	 * @param chunkZ1 First chunk Z.
	 * @param chunkX2 Second chunk X.
	 * @param chunkZ2 Second chunk Z.
	 * @return The calculated distance.
	 */
	public static double calculateChunkDistance(int chunkX1, int chunkZ1, int chunkX2, int chunkZ2) {
		// Calculate the difference in x and z coordinates
		int deltaX = chunkX2 - chunkX1;
		int deltaZ = chunkZ2 - chunkZ1;

		// Apply the distance formula
		return Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
	}
}
