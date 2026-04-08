package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures.overrides;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedConfiguredFeatures;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedStructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedStructureSettings;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.starts.ExtendedStructureStartV5;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.Structure;

public class ExtendedIsengardStructure extends ExtendedCheckAreaChunkFlatStructure {

	public ExtendedIsengardStructure(ExtendedStructureType type) {
		super(type);
	}

	@Override
	public IStartFactory<ExtendedSinglePoolConfig> getStructureStartFactory() {
		return ExtendedIsengardStructure.Start::new;
	}

	public static class Start extends ExtendedStructureStartV5<ExtendedSinglePoolConfig> {
		public Start(Structure<ExtendedSinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}
		
		@Override
		public ExtendedStructureSettings getHardcodedSettings() {
			return ExtendedStructureSettings.builder().walls(ExtendedConfiguredFeatures.ISENGARD_WALLS_CONFIG).build();
		}
	}
}
