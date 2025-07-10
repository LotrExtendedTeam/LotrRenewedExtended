package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.Structure;

/**
 * A structure for generating a single structure pool during world generation.
 * <p>
 * This structure uses {@link SinglePoolConfig} to determine which pool to use for placement,
 * enabling modular and data-driven generation of structures from a single specified pool.
 */
public class SinglePoolStructure extends ExtendedSurfaceStructuresV5<SinglePoolConfig> {
	/**
     * Constructs a {@link SinglePoolStructure} with the specified structure type.
     *
     * @param type The structure type used for registry and serialization.
     */
	public SinglePoolStructure(ExtendedStructureType type) {
		super(SinglePoolConfig.CODEC, type);
	}
	/**
     * Returns the factory responsible for creating structure starts for this structure.
     *
     * @return The {@link net.minecraft.world.gen.feature.structure.Structure.IStartFactory} for {@link SinglePoolConfig}.
     */
	@Override
	public IStartFactory<SinglePoolConfig> getStructureStartFactory() {
		return SinglePoolStructure.Start::new;
	}
	 /**
     * The {@link ExtendedStructureStartV5} implementation for {@link SinglePoolStructure},
     * responsible for initializing and placing the structure in the world using the specified configuration.
     */
	public static class Start extends ExtendedStructureStartV5<SinglePoolConfig> {
		/**
         * Constructs a new {@link Start} instance for {@link SinglePoolStructure}.
         *
         * @param structureIn      The structure instance.
         * @param chunkX           The chunk X coordinate.
         * @param chunkZ           The chunk Z coordinate.
         * @param mutableBoundingBox The mutable bounding box for structure placement.
         * @param referenceIn      The structure reference.
         * @param seedIn           The world seed used for placement.
         */
		public Start(Structure<SinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}
		/**
         * Resolves the {@link ResourceLocation} of the structure pool to use for generation
         * based on the provided {@link SinglePoolConfig}.
         * <p>
         * If the pool name is "empty", logs an error and returns {@code null}.
         * If the pool contains a namespace (colon), it uses the provided namespace.
         * Otherwise, it uses the mod's namespace as a fallback.
         *
         * @param config The structure configuration specifying the pool.
         * @param rand   The random generator used during structure placement.
         * @return The {@link ResourceLocation} of the structure pool, or {@code null} if "empty".
         */
		@Override
		public ResourceLocation getLocation(SinglePoolConfig config, Random rand) {
			String pool = config.getPool();
			if ("empty".equals(pool)) {
				ExtendedLog.error("Tried generating a single pool structure using an empty pool!");
				return null;
			}
			ResourceLocation location;
			if(pool.contains(":")) {
				location = new ResourceLocation(pool);
			} else {
				location = new ResourceLocation(LotrExtendedMod.MODID, pool);
			}
			return location;
		}
	}
}
