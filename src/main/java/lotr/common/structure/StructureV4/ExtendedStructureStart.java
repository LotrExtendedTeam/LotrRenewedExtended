package lotr.common.structure.StructureV4;

import java.util.Random;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class ExtendedStructureStart<C extends IFeatureConfig> extends StructureStart<C>{
	private boolean isCreated;

	public ExtendedStructureStart(Structure<C> feature, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
        super(feature, chunkX, chunkZ, boundingBox, references, seed);
     }

	@Override
	public void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, C config) {
		generatePieces(chunkX, chunkZ, chunkGenerator);
		this.calculateBoundingBox();
		this.isCreated = true;
	}

	public abstract void generatePieces(int chunkX, int chunkZ, ChunkGenerator chunkGenerator);
//		int x = chunkX * 16;
//        int z = chunkZ * 16;
//        BlockPos centerPos = new BlockPos(x, 0, z);
//        
//        ExtendedStructurePiece piece = new ExtendedStructurePiece();
//        this.pieces.add(piece);
//        this.calculateBoundingBox();
//	}

	@Override
	public void placeInChunk(ISeedReader p_230366_1_, StructureManager p_230366_2_, ChunkGenerator p_230366_3_, Random p_230366_4_, MutableBoundingBox p_230366_5_, ChunkPos p_230366_6_) {
        if (!this.isCreated) {
           this.pieces.clear();
           this.generatePieces(this.getChunkX(), this.getChunkZ(),p_230366_3_);
        }
        super.placeInChunk(p_230366_1_, p_230366_2_, p_230366_3_, p_230366_4_, p_230366_5_, p_230366_6_);
     }
}
