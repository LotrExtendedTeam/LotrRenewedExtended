package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructuresV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.StructureType;
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

public abstract class ExtendedSurfaceStructuresV5<C extends IFeatureConfig> extends Structure<C>{
	private final StructureType structureType;
	public ExtendedSurfaceStructuresV5(Codec<C> p_i231997_1_, StructureType type) {
		super(p_i231997_1_);
		this.structureType = type;
	}

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

	@Override
	public IStartFactory<C> getStartFactory() {
		return getStructureStartFactory();
	}

	public boolean canSpawnInWater() {
		return false;
	}

	public boolean canSpawnNearRoad() {
		return false;
	}

	public abstract IStartFactory<C> getStructureStartFactory();

	//TODO add checking for stuff
	@Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, C featureConfig) {
        BlockPos centerOfChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);

        // Grab height of land. Will stop at first non-air block.
        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), net.minecraft.world.gen.Heightmap.Type.WORLD_SURFACE_WG);

        // Grabs column of blocks at given position. In overworld, this column will be made of stone, water, and air.
        // In nether, it will be netherrack, lava, and air. End will only be endstone and air. It depends on what block
        // the chunk generator will place for that dimension.
        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());

        // Combine the column of blocks with land height and you get the top block itself which you can test.
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

        // Now we test to make sure our structure is not spawning on water or other fluids.
        // You can do height check instead too to make it spawn at high elevations.
        boolean waterPresent = !topBlock.getFluidState().isEmpty(); //landHeight > 100;

        // Check if can spawn in water
        if(!canSpawnInWater() && waterPresent) {
        	return false;
        }
        // Check if structure intersects a road
        boolean roadExistsInRange = (MapSettingsManager.serverInstance().getCurrentLoadedMap().getRoadPointCache().getRoadCentreCloseness(centerOfChunk.getX(), centerOfChunk.getZ(), 16) >= 0.0F);

        if(!canSpawnNearRoad() && roadExistsInRange) {
        	return false;
        }

        int i = chunkX >> 4;
        int j = chunkZ >> 4;
        chunkRandom.setSeed((long)(i ^ j << 4) ^ seed);
        chunkRandom.nextInt();
        if(structureType==StructureType.RANGER) {
        	if(isNearStructure(ExtendedStructuresV5.GUNDABAD_CAMP.get(), chunkGenerator, seed, chunkRandom, chunkX, chunkZ, 10) || isNearStructure(ExtendedStructuresV5.GUNDABAD_ENCAMPMENT.get(), chunkGenerator, seed, chunkRandom, chunkX, chunkZ, 10)) {
        		return false;
        	}
        }
        return true;
    }

	private boolean isNearStructure(Structure<?> structure, ChunkGenerator chunkGenerator, long seed, SharedSeedRandom sharedSeedRandom, int chunkPosX, int chunkPosZ, int checkRadius) {
	      StructureSeparationSettings structureseparationsettings = chunkGenerator.getSettings().getConfig(Structure.VILLAGE);
	      if (structureseparationsettings == null) {
	         return false;
	      } else {
	         for(int i = chunkPosX - checkRadius; i <= chunkPosX + checkRadius; ++i) {
	            for(int j = chunkPosZ - checkRadius; j <= chunkPosZ + checkRadius; ++j) {
	               ChunkPos chunkpos = structure.getPotentialFeatureChunk(structureseparationsettings, seed, sharedSeedRandom, i, j);
	               if (i == chunkpos.x && j == chunkpos.z) {
	                  return true;
	               }
	            }
	         }

	         return false;
	      }
	   }
}
