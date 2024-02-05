package lotr.common.structure.StructureV4.standalone;

import com.github.maximuslotro.lotroExtended.enums.StructureType;
import com.mojang.serialization.Codec;

import lotr.common.structure.StructureV4.ExtendedStructureStart;
import lotr.common.structure.StructureV4.ExtendedSurfaceStructure;
import lotr.common.structure.StructureV4.standalone.pieces.ExtendedHobbitBarnPiece;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedHobbitBarnStructure extends ExtendedSurfaceStructure<NoFeatureConfig> {

	public ExtendedHobbitBarnStructure(Codec<NoFeatureConfig> p_i231997_1_) {
		super(p_i231997_1_);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return ExtendedHobbitBarnStructure.Start::new;
	}

	public static class Start extends ExtendedStructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(BlockPos pos, ChunkGenerator chunkGenerator, Direction direction, StructureType structureType) {
			ExtendedHobbitBarnPiece hobbitTavernPiece = new ExtendedHobbitBarnPiece(this.random, pos, direction, structureType);
			pieces.add(hobbitTavernPiece);
		}
	}
}
