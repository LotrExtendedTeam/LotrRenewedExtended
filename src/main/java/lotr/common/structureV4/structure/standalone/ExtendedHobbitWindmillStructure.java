package lotr.common.structureV4.structure.standalone;

import com.github.maximuslotro.lotrExtended.common.enums.StructureType;
import com.mojang.serialization.Codec;

import lotr.common.structureV4.structure.ExtendedStructureStart;
import lotr.common.structureV4.structure.ExtendedSurfaceStructure;
import lotr.common.structureV4.structure.standalone.pieces.ExtendedHobbitWindmillPiece;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedHobbitWindmillStructure extends ExtendedSurfaceStructure<NoFeatureConfig> {

	public ExtendedHobbitWindmillStructure(Codec<NoFeatureConfig> p_i231997_1_) {
		super(p_i231997_1_);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return ExtendedHobbitWindmillStructure.Start::new;
	}

	public static class Start extends ExtendedStructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(BlockPos pos, ChunkGenerator chunkGenerator, Direction direction, StructureType structureType) {
			ExtendedHobbitWindmillPiece hobbitTavernPiece = new ExtendedHobbitWindmillPiece(this.random, pos, direction, structureType);
			pieces.add(hobbitTavernPiece);
		}
	}
}
