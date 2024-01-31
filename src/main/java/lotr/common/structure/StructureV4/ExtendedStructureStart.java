package lotr.common.structure.StructureV4;

import java.util.Iterator;
import java.util.Random;

import com.github.maximuslotro.lotroExtended.enums.StructureType;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class ExtendedStructureStart<C extends IFeatureConfig> extends StructureStart<C>{

	public ExtendedStructureStart(Structure<C> feature, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
        super(feature, chunkX, chunkZ, boundingBox, references, seed);
     }

	@Override
	public void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, C config) {
		generatePieces(registry, chunkGenerator, templateManager, chunkX * 16, chunkZ * 16, biome, config, null, StructureType.NATURAL_SINGLE);
	}

	public void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator, TemplateManager templateManager, int posX, int posZ, Biome biome, C config, Direction direction, StructureType structureType) {
		generatePieces(registry, chunkGenerator, templateManager, new BlockPos(posX, -1, posZ), biome, config, direction, structureType);
	}

	public void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator, TemplateManager templateManager, BlockPos pos, Biome biome, C config, Direction direction, StructureType structureType) {
		generatePieces(pos, chunkGenerator, direction, structureType);
		System.out.println("Generated abstract pieces");
		calculateBoundingBox();
	}

	public abstract void generatePieces(BlockPos pos, ChunkGenerator chunkGenerator, Direction direction, StructureType structureType);
//		int x = chunkX * 16;
//        int z = chunkZ * 16;
//        BlockPos centerPos = new BlockPos(x, 0, z);
//        
//        ExtendedFunctionalStructurePiece piece = new ExtendedFunctionalStructurePiece();
//        this.pieces.add(piece);
//        this.calculateBoundingBox();
//	}

	@Override
	public void placeInChunk(ISeedReader p_230366_1_, StructureManager p_230366_2_, ChunkGenerator p_230366_3_, Random p_230366_4_, MutableBoundingBox p_230366_5_, ChunkPos p_230366_6_) {
		System.out.println("Placing in chunk");
		placeInChunkD(p_230366_1_, p_230366_2_, p_230366_3_, p_230366_4_, p_230366_5_, p_230366_6_);
	}

	public void placeInChunkD(ISeedReader p_230366_1_, StructureManager p_230366_2_, ChunkGenerator p_230366_3_, Random p_230366_4_, MutableBoundingBox p_230366_5_, ChunkPos p_230366_6_) {
		if (!this.pieces.isEmpty()) {
			MutableBoundingBox mutableboundingbox = (pieces.get(0)).getBoundingBox();
			Vector3i vector3i = mutableboundingbox.getCenter();
			BlockPos blockpos = new BlockPos(vector3i.getX(), mutableboundingbox.y0, vector3i.getZ());
			Iterator<StructurePiece> iterator = pieces.iterator();
			System.out.println("Stuff");
			while (iterator.hasNext()) {
				StructurePiece structurepiece = iterator.next();
				if (structurepiece.getBoundingBox().intersects(p_230366_5_) && !structurepiece.postProcess(p_230366_1_, p_230366_2_, p_230366_3_, p_230366_4_, p_230366_5_, p_230366_6_, blockpos)) {
					System.out.println("Removing structure piece");
					iterator.remove();
				}
			}
			System.out.println("Done");
			this.calculateBoundingBox();
		} else {
			System.out.println("Structure pieces are empty");
		}
	}
}
