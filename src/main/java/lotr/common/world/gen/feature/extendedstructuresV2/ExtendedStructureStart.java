package lotr.common.world.gen.feature.extendedstructuresV2;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ExtendedStructureStart<C extends IFeatureConfig> extends StructureStart<C>{

	public ExtendedStructureStart(Structure<C> feature, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
        super(feature, chunkX, chunkZ, boundingBox, references, seed);
     }

	@Override
	public void generatePieces(DynamicRegistries registry, ChunkGenerator chunkGenerator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, C config) {
	}
}
