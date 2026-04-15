package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedWeightedStructureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.starts.ExtendedStructureStartV5;

/**
 * A structure for generating structures from a weighted pool of structure pools during world generation.
 * <p>
 * This structure uses {@link ExtendedWeightedStructureConfig} to determine a pool to use based on configured weights,
 * allowing for probabilistic selection of different pools to introduce variety in generation while respecting defined weights.
 */
public class ExtendedWeightedPoolStructure extends ExtendedSurfaceStructuresV5<ExtendedWeightedStructureConfig> {
	/**
     * Constructs a {@link ExtendedWeightedPoolStructure} with the specified structure type.
     *
     * @param type The structure type used for registry and serialization.
     */
	public ExtendedWeightedPoolStructure(ExtendedStructureType type) {
		super(ExtendedWeightedStructureConfig.CODEC, type);
	}
	/**
     * Returns the factory responsible for creating structure starts for this structure.
     *
     * @return The {@link net.minecraft.world.gen.feature.structure.Structure.IStartFactory} for {@link ExtendedWeightedStructureConfig}.
     */
	@Override
	public IStartFactory<ExtendedWeightedStructureConfig> getStructureStartFactory() {
		return ExtendedStructureStartV5::new;
	}
}
