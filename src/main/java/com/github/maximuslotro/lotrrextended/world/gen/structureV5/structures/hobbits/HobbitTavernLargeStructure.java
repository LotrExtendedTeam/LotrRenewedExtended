package com.github.maximuslotro.lotrrextended.world.gen.structureV5.structures.hobbits;

import com.github.maximuslotro.lotrrextended.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class HobbitTavernLargeStructure extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {

	public HobbitTavernLargeStructure() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return HobbitTavernLargeStructure.Start::new;
	}
    
    public static class Start extends ExtendedStructureStartV5<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

		@Override
		public String getLocation() {
			return "hobbit/hobbit_tavern_large_pool";
		}

		@Override
		public int yShift() {
			return -6;
		}

		@Override
		public boolean usesFullStructureFoundations() {
			return true;
		}

		@Override
		public BlockState defaultFoundationmaterial() {
			return Blocks.DIRT.defaultBlockState();
		}

		@Override
		public int foundationUpwardsOffsetBasementSpecial() {
			return 6;
		}
    }
}
