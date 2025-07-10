package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.hobbits;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.Structure;

/**
 * Represents a large Hobbit Tavern structure extending the custom ExtendedSurfaceStructuresV5.
 * <p>
 * This structure applies a vertical offset to position the structure lower in the world,
 * and uses full foundations with a special upward offset for basement support.
 */
public class ExtendedStructureHobbitTavernLarge extends SinglePoolStructure {
	/**
     * Constructs a new HobbitTavernLargeStructure with the specified structure type.
     *
     * @param type The structure type.
     */
	public ExtendedStructureHobbitTavernLarge(ExtendedStructureType type) {
		super(type);
	}
	/**
     * Provides the factory that creates the structure start instance for this structure.
     *
     * @return The start factory creating {@link Start} instances.
     */
	@Override
	public IStartFactory<SinglePoolConfig> getStructureStartFactory() {
		return ExtendedStructureHobbitTavernLarge.Start::new;
	}
	/**
     * Handles the generation logic and configuration for the HobbitTavernLargeStructure.
     * Extends {@link com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.SinglePoolStructure.Start}.
     */
	public static class Start extends SinglePoolStructure.Start {
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
		public Start(Structure<SinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
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
