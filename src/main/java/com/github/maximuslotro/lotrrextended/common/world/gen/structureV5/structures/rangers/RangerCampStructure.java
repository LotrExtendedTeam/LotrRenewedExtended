package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.rangers;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class RangerCampStructure extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {

	public RangerCampStructure() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return RangerCampStructure.Start::new;
	}
    
    public static class Start extends ExtendedStructureStartV5<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

		@Override
		public String getLocation() {
			return "ranger/camp_parts/firepit_pool";
		}
    }
}
