package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.starts.ExtendedStructureStartV5;

/**
 * A structure for generating a single structure pool during world generation.
 * <p>
 * This structure uses {@link ExtendedSinglePoolConfig} to determine which pool to use for placement,
 * enabling modular and data-driven generation of structures from a single specified pool.
 */
public class ExtendedSinglePoolStructure extends ExtendedSurfaceStructuresV5<ExtendedSinglePoolConfig> {
	/**
     * Constructs a {@link ExtendedSinglePoolStructure} with the specified structure type.
     *
     * @param type The structure type used for registry and serialization.
     */
	public ExtendedSinglePoolStructure(ExtendedStructureType type) {
		super(ExtendedSinglePoolConfig.CODEC, type);
	}
	/**
     * Returns the factory responsible for creating structure starts for this structure.
     *
     * @return The {@link net.minecraft.world.gen.feature.structure.Structure.IStartFactory} for {@link ExtendedSinglePoolConfig}.
     */
	@Override
	public IStartFactory<ExtendedSinglePoolConfig> getStructureStartFactory() {
		return ExtendedStructureStartV5::new;
	}
}
