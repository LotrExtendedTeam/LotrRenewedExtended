package lotr.common.structureV5.structures.hobbits;

import net.minecraft.util.math.MutableBoundingBox;
import lotr.common.structureV5.extendedClasses.ExtendedStructureStartV5;
import lotr.common.structureV5.extendedClasses.ExtendedSurfaceStructuresV5;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class HobbitHoleLargeStructure extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {

	public HobbitHoleLargeStructure() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return HobbitHoleLargeStructure.Start::new;
	}
    
    public static class Start extends ExtendedStructureStartV5<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

		@Override
		public String getLocation() {
			return "hobbit/hobbit_hole_large_pool";
		}

		@Override
		public int maxDepth() {
			return 7;
		}

		@Override
		public int yShift() {
			return -1;
		}
    }
}
