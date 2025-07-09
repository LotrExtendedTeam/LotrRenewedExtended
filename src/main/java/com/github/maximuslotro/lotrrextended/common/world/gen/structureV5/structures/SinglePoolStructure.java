package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.StructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.Structure;

public class SinglePoolStructure extends ExtendedSurfaceStructuresV5<SinglePoolConfig> {

	public SinglePoolStructure(StructureType type) {
		super(SinglePoolConfig.CODEC, type);
	}

	@Override
	public IStartFactory<SinglePoolConfig> getStructureStartFactory() {
		return SinglePoolStructure.Start::new;
	}

	public static class Start extends ExtendedStructureStartV5<SinglePoolConfig> {
		public Start(Structure<SinglePoolConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

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
