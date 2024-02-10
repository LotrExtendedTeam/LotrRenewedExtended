package lotr.common.structureV5.structures.hobbits;

import net.minecraft.util.math.MutableBoundingBox;
import lotr.common.structureV5.extendedClasses.ExtendedStructureStartV5;
import lotr.common.structureV5.extendedClasses.ExtendedSurfaceStructuresV5;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class HobbitWindmillStructure extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {

	public HobbitWindmillStructure() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return HobbitWindmillStructure.Start::new;
	}
    
    public static class Start extends ExtendedStructureStartV5<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

		@Override
		public String getLocation() {
			return "hobbit/hobbit_windmill_pool";
		}
    }
}
