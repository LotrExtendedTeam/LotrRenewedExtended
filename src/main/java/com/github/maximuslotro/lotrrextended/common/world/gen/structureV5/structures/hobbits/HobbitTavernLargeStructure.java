package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.hobbits;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.StructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class HobbitTavernLargeStructure extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {

	public HobbitTavernLargeStructure(StructureType type) {
		super(NoFeatureConfig.CODEC, type);
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
		public ResourceLocation getLocation(NoFeatureConfig config, Random rand) {
			return new ResourceLocation(LotrExtendedMod.MODID, "hobbit/hobbit_tavern_large_pool");
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
