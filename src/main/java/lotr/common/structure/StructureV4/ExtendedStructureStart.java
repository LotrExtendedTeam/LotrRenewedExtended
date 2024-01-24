package lotr.common.structure.StructureV4;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class ExtendedStructureStart<C extends IFeatureConfig> extends StructureStart<C>{

	public ExtendedStructureStart(Structure<C> feature, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
        super(feature, chunkX, chunkZ, boundingBox, references, seed);
     }

	@Override
	public abstract void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, C config);
//		int x = chunkX * 16;
//        int z = chunkZ * 16;
//        BlockPos centerPos = new BlockPos(x, 0, z);
//        
//        ExtendedStructurePiece piece = new ExtendedStructurePiece();
//        this.pieces.add(piece);
//        this.calculateBoundingBox();
//	}
}
