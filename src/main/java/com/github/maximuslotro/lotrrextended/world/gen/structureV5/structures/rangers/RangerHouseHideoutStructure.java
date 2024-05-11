package com.github.maximuslotro.lotrrextended.world.gen.structureV5.structures.rangers;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.world.gen.structureV5.extendedclasses.ExtendedStructureStartV5;
import com.github.maximuslotro.lotrrextended.world.gen.structureV5.extendedclasses.ExtendedSurfaceStructuresV5;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class RangerHouseHideoutStructure extends ExtendedSurfaceStructuresV5<NoFeatureConfig> {

	public RangerHouseHideoutStructure() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStructureStartFactory() {
		return RangerHouseHideoutStructure.Start::new;
	}
    
    public static class Start extends ExtendedStructureStartV5<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

    	@Override
    	public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
    		// Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            BlockPos blockpos = new BlockPos(x, 0, z);

            Random ran = new Random();
            if(ran.nextInt(2)==1) {
            	JigsawManager.addPieces(dynamicRegistryManager,
                        new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        		.get(new ResourceLocation(LotrExtendedMod.MODID, "ranger/hideout_house_pool")), maxDepth()), AbstractVillagePiece::new, 
                        chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random, false,true);
            }
            else {
            JigsawManager.addPieces(dynamicRegistryManager,
                    new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                    		.get(new ResourceLocation(LotrExtendedMod.MODID, "ranger/hideout_house_pool2")), maxDepth()), AbstractVillagePiece::new, 
                    chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random, false,true);
            this.pieces.forEach(piece -> piece.move(0, -1, 0));
            }
            this.calculateBoundingBox();
    	}

		@Override
		public String getLocation() {
			return "unused";
		}
    }
}
