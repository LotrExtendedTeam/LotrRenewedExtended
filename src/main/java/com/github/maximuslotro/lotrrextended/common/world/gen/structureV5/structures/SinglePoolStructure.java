package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.structures;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.ExtendedJigsawManager;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.StructureType;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.SinglePoolConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

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
    	public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, SinglePoolConfig config) {
    		// Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            BlockPos blockpos = new BlockPos(x, 0, z);

            String pool = config.getPool();
            ResourceLocation location;
            if ("empty".equals(pool)) {
            	ExtendedLog.error("Tried generating a single pool structure using an empty pool!");
            	return;
            }
            if(pool.contains(":")) {
            	location = new ResourceLocation(pool);
            } else {
            	location = new ResourceLocation(LotrExtendedMod.MODID, pool);
            }

            ExtendedJigsawManager.addPieces(dynamicRegistryManager,
                    new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(location), maxDepth()),
                    AbstractVillagePiece::new, chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random, false, true);
            
            this.pieces.forEach(piece -> piece.move(0, yShift(), 0));
            this.calculateBoundingBox();
    	}

		@Override
		public String getLocation() {
			return "unused";
		}
    }
}
