package lotr.common.structure.StructureV4;

import com.github.maximuslotro.lotroExtended.ExtendedLog;
import com.github.maximuslotro.lotroExtended.enums.StructureType;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

@SuppressWarnings("unchecked")
public abstract class ExtendedSurfaceStructure<C extends IFeatureConfig> extends Structure<C>{

	public ExtendedSurfaceStructure(Codec<C> p_i231997_1_) {
		super(p_i231997_1_);
	}

	@Override
	public IStartFactory<C> getStartFactory() {
		return getStructureStartFactory();
	}
	
	public abstract IStartFactory<C> getStructureStartFactory();

	@Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

	private StructureStart<C> createStart(int p_236387_1_, int p_236387_2_, MutableBoundingBox p_236387_3_, int p_236387_4_, long p_236387_5_) {
		return this.getStartFactory().create(this, p_236387_1_, p_236387_2_, p_236387_3_, p_236387_4_, p_236387_5_);
	}

	@Override
	public StructureStart<?> generate(DynamicRegistries p_242785_1_, ChunkGenerator p_242785_2_, BiomeProvider p_242785_3_, TemplateManager p_242785_4_, long p_242785_5_, ChunkPos p_242785_7_, Biome p_242785_8_, int p_242785_9_, SharedSeedRandom p_242785_10_, StructureSeparationSettings p_242785_11_, C p_242785_12_) {
		return this.generate(p_242785_1_, p_242785_2_, p_242785_3_, p_242785_4_, p_242785_5_, p_242785_7_, p_242785_8_, p_242785_9_, p_242785_10_, p_242785_11_, p_242785_12_, null);
	}

	public StructureStart<?> generate(DynamicRegistries p_242785_1_, ChunkGenerator p_242785_2_, BiomeProvider p_242785_3_, TemplateManager p_242785_4_, long p_242785_5_, ChunkPos p_242785_7_, Biome p_242785_8_, int p_242785_9_, SharedSeedRandom p_242785_10_, StructureSeparationSettings p_242785_11_, IFeatureConfig config, Direction direction) {
		C conf= (C)config;
		ChunkPos chunkpos = this.getPotentialFeatureChunk(p_242785_11_, p_242785_5_, p_242785_10_, p_242785_7_.x, p_242785_7_.z);
		if (p_242785_7_.x == chunkpos.x && p_242785_7_.z == chunkpos.z && this.isFeatureChunk(p_242785_2_, p_242785_3_, p_242785_5_, p_242785_10_, p_242785_7_.x, p_242785_7_.z, p_242785_8_, chunkpos, conf)) {
			StructureStart<C> structurestart = this.createStart(p_242785_7_.x, p_242785_7_.z, MutableBoundingBox.getUnknownBox(), p_242785_9_, p_242785_5_);
			if(structurestart instanceof ExtendedStructureStart) {
				System.out.println("Adding modded pieces");
				((ExtendedStructureStart<C>)structurestart).generatePieces(p_242785_1_, p_242785_2_, p_242785_4_, p_242785_7_.x, p_242785_7_.z, p_242785_8_, conf, direction, StructureType.NATURAL_SINGLE);
			}else {
				ExtendedLog.error("Tried generating pieces for a ExtendedSurfaceStructure for a non ExtendedStructureStart");
			}
			System.out.println(structurestart.getPieces().size()+" Pieces made");
			if (structurestart.isValid()) {
				System.out.println("Structure not valid in starter");
				return structurestart;
			}
		}
		System.out.println("Not feature chunk");
		return StructureStart.INVALID_START;
	}

	public StructureStart<?> generateUnckecked(DynamicRegistries p_242785_1_, ChunkGenerator p_242785_2_, BiomeProvider p_242785_3_, TemplateManager p_242785_4_, long p_242785_5_, ChunkPos p_242785_7_, Biome p_242785_8_, int p_242785_9_, SharedSeedRandom p_242785_10_, StructureSeparationSettings p_242785_11_, IFeatureConfig config, Direction direction, StructureType structureType) {
		C conf= (C)config;
		StructureStart<C> structurestart = this.createStart(p_242785_7_.x, p_242785_7_.z, MutableBoundingBox.getUnknownBox(), p_242785_9_, p_242785_5_);
		if(structurestart instanceof ExtendedStructureStart) {
			System.out.println("Adding modded pieces1");
			((ExtendedStructureStart<C>)structurestart).generatePieces(p_242785_1_, p_242785_2_, p_242785_4_, p_242785_7_.x, p_242785_7_.z, p_242785_8_, conf, direction, structureType);
		}else {
			ExtendedLog.error("Tried generating pieces for a ExtendedSurfaceStructure for a non ExtendedStructureStart");
		}
		System.out.println(structurestart.getPieces().size()+" Pieces made1");
		if (structurestart.isValid()) {
			System.out.println("Structure not valid in starter1");
			return structurestart;
		}
		return StructureStart.INVALID_START;
	}

    /*
     * This is where extra checks can be done to determine if the structure can spawn here.
     * This only needs to be overridden if you're adding additional spawn conditions.
     *
     * Fun fact, if you set your structure separation/spacing to be 0/1, you can use
     * isFeatureChunk to return true only if certain chunk coordinates are passed in
     * which allows you to spawn structures only at certain coordinates in the world.
     *
     * Notice how the biome is also passed in. Though, you are not going to
     * do any biome checking here as you should've added this structure to
     * the biomes you wanted already with the biome load event.
     * 
     * Basically, this method is used for determining if the land is at a suitable height,
     * if certain other structures are too close or not, or some other restrictive condition.
     *
     * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village.
     * 
     * Also, please for the love of god, do not do dimension checking here. If you do and
     * another mod's dimension is trying to spawn your structure, the locate
     * command will make minecraft hang forever and break the game.
     *
     * Instead, use the addDimensionalSpacing method in StructureTutorialMain class.
     * If you check for the dimension there and do not add your structure's
     * spacing into the chunk generator, the structure will not spawn in that dimension!
     * 
     * Copied from https://github.com/TelepathicGrunt/StructureTutorialMod/blob/e1ddc4a46b0cb1f10cd55d7ddd57d78fb279091c/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java#L135
     * Which is held under MIT license
     */
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
        //return topBlock.getFluidState().isEmpty(); //landHeight > 100;
        return true;
    }
}
