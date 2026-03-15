package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.starts.ExtendedStructureStartV5;

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
		return ExtendedStructureStartV5::new;
	}
}
