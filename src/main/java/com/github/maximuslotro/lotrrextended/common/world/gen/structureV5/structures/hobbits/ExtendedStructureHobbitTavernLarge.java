package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.hobbits;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

/**
 * Represents a large Hobbit Tavern structure extending the custom ExtendedSurfaceStructuresV5.
 * <p>
 * This structure uses a specific Jigsaw pool defined by the "hobbit/hobbit_tavern_large_pool" resource location,
 * applies a vertical offset to position the structure lower in the world,
 * and uses full foundations with a special upward offset for basement support.
 */
public class ExtendedStructureHobbitTavernLarge extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {
	/**
     * Constructs a new HobbitTavernLargeStructure with the specified structure type.
     *
     * @param type The structure type.
     */
	public ExtendedStructureHobbitTavernLarge(ExtendedStructureType type) {
		super(NoFeatureConfig.CODEC, type);
	}
	/**
     * Provides the factory that creates the structure start instance for this structure.
     *
     * @return The start factory creating {@link Start} instances.
     */
	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return ExtendedStructureHobbitTavernLarge.Start::new;
	}
	/**
     * Handles the generation logic and configuration for the HobbitTavernLargeStructure.
     * Extends {@link ExtendedStructureStartV5} with {@link NoFeatureConfig}.
     */
	public static class Start extends ExtendedStructureStartV5<NoFeatureConfig> {
		/**
         * Constructs a new Start instance for HobbitTavernLargeStructure.
         *
         * @param structureIn        The structure this start belongs to.
         * @param chunkX             The X chunk coordinate.
         * @param chunkZ             The Z chunk coordinate.
         * @param mutableBoundingBox The bounding box of the structure.
         * @param referenceIn        The reference count.
         * @param seedIn             The world seed.
         */
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}
		/**
         * Returns the resource location for the Jigsaw pool defining the structure pieces.
         *
         * @param config The feature configuration (unused here).
         * @param rand   The random instance.
         * @return The resource location pointing to the "hobbit/hobbit_tavern_large_pool" template pool.
         */
		@Override
		public ResourceLocation getLocation(NoFeatureConfig config, Random rand) {
			return new ResourceLocation(LotrExtendedMod.MODID, "hobbit/hobbit_tavern_large_pool");
		}
		/**
         * Indicates that full structure foundations should be used when placing the structure.
         *
         * @return True, indicating full foundations are used.
         */
		@Override
		public boolean usesFullStructureFoundations() {
			return true;
		}
		/**
         * Returns the default block state used for the foundation material.
         *
         * @return The block state of dirt.
         */
		@Override
		public BlockState defaultFoundationmaterial() {
			return Blocks.DIRT.defaultBlockState();
		}
		 /**
         * Provides an upwards offset for foundation placement to accommodate basement structures.
         *
         * @return The upwards offset for basement special cases (6).
         */
		@Override
		public int foundationUpwardsOffsetBasementSpecial() {
			return 6;
		}
	}
}
