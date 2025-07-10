package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.WeightedStructureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.Structure;

/**
 * A structure for generating structures from a weighted pool of structure pools during world generation.
 * <p>
 * This structure uses {@link WeightedStructureConfig} to determine a pool to use based on configured weights,
 * allowing for probabilistic selection of different pools to introduce variety in generation while respecting defined weights.
 */
public class WeightedPoolStructure extends ExtendedSurfaceStructuresV5<WeightedStructureConfig> {
	/**
     * Constructs a {@link WeightedPoolStructure} with the specified structure type.
     *
     * @param type The structure type used for registry and serialization.
     */
	public WeightedPoolStructure(ExtendedStructureType type) {
		super(WeightedStructureConfig.CODEC, type);
	}
	/**
     * Returns the factory responsible for creating structure starts for this structure.
     *
     * @return The {@link net.minecraft.world.gen.feature.structure.Structure.IStartFactory} for {@link WeightedStructureConfig}.
     */
	@Override
	public IStartFactory<WeightedStructureConfig> getStructureStartFactory() {
		return WeightedPoolStructure.Start::new;
	}
	/**
     * The {@link ExtendedStructureStartV5} implementation for {@link WeightedPoolStructure},
     * responsible for initializing and placing the structure in the world using the weighted pool configuration.
     */
	public static class Start extends ExtendedStructureStartV5<WeightedStructureConfig> {
		/**
         * Constructs a new {@link Start} instance for {@link WeightedPoolStructure}.
         *
         * @param structureIn        The structure instance.
         * @param chunkX             The chunk X coordinate.
         * @param chunkZ             The chunk Z coordinate.
         * @param mutableBoundingBox The mutable bounding box for structure placement.
         * @param referenceIn        The structure reference.
         * @param seedIn             The world seed used for placement.
         */
		public Start(Structure<WeightedStructureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}
		 /**
         * Resolves the {@link ResourceLocation} of the structure pool to use for generation
         * based on the provided {@link WeightedStructureConfig} using weighted random selection.
         * <p>
         * If no pools are configured, logs an error and returns {@code null}.
         * If the pool contains a namespace (colon), it uses the provided namespace.
         * Otherwise, it uses the mod's namespace as a fallback.
         *
         * @param config The weighted structure configuration specifying pools and weights.
         * @param rand   The random generator used during structure placement.
         * @return The {@link ResourceLocation} of the selected structure pool, or {@code null} if no pools are configured.
         */
		@Override
		public ResourceLocation getLocation(WeightedStructureConfig config, Random rand) {
			String pool = config.getRandomLocation(rand);
			if (pool == null) {
				ExtendedLog.error("Tried generating a weighted pool structure from an null pool!");
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
