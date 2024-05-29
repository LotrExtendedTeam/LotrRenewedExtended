package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.hobbits;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class HobbitPicnicTableStructure extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {

	public HobbitPicnicTableStructure() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return HobbitPicnicTableStructure.Start::new;
	}
    
    public static class Start extends ExtendedStructureStartV5<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

		@Override
		public String getLocation() {
			return "hobbit/hobbit_picnic_table_pool";
		}
    }
}
