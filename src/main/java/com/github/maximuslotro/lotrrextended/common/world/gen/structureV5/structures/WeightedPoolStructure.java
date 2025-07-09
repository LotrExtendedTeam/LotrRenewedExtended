package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.StructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.WeightedStructureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.Structure;

public class WeightedPoolStructure extends ExtendedSurfaceStructuresV5<WeightedStructureConfig> {

	public WeightedPoolStructure(StructureType type) {
		super(WeightedStructureConfig.CODEC, type);
	}

	@Override
	public IStartFactory<WeightedStructureConfig> getStructureStartFactory() {
		return WeightedPoolStructure.Start::new;
	}

	public static class Start extends ExtendedStructureStartV5<WeightedStructureConfig> {
		public Start(Structure<WeightedStructureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

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
