package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.bree;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.StructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.Structure;

public class SingleBreeVillagePoolStructure extends ExtendedSurfaceStructuresV5<SinglePoolConfig> {

	public SingleBreeVillagePoolStructure(StructureType type) {
		super(SinglePoolConfig.CODEC, type);
	}

	@Override
	public IStartFactory<SinglePoolConfig> getStructureStartFactory() {
		return SingleBreeVillagePoolStructure.Start::new;
	}

    public static class Start extends SinglePoolStructure.Start {
        public Start(Structure<SinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

		@Override
		public int maxDepth() {
			return 12;
		}
    }
}
