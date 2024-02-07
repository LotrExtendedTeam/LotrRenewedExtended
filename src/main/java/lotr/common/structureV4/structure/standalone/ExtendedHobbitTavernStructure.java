package lotr.common.structureV4.structure.standalone;

import com.github.maximuslotro.lotroExtended.enums.StructureType;
import com.mojang.serialization.Codec;

import lotr.common.structureV4.structure.ExtendedStructureStart;
import lotr.common.structureV4.structure.ExtendedSurfaceStructure;
import lotr.common.structureV4.structure.standalone.pieces.ExtendedHobbitTavernPiece;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedHobbitTavernStructure extends ExtendedSurfaceStructure<NoFeatureConfig> {

	public ExtendedHobbitTavernStructure(Codec<NoFeatureConfig> p_i231997_1_) {
		super(p_i231997_1_);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return ExtendedHobbitTavernStructure.Start::new;
	}

	public static class Start extends ExtendedStructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void generatePieces(BlockPos pos, ChunkGenerator chunkGenerator, Direction direction, StructureType structureType) {
			System.out.println("Adding hobbit tavern piece");
			ExtendedHobbitTavernPiece hobbitTavernPiece = new ExtendedHobbitTavernPiece(this.random, pos, direction, structureType);
			pieces.add(hobbitTavernPiece);
		}
	}
}
