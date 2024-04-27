package com.github.maximuslotro.lotrrextended.world.gen.structureV5.extendedclasses;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class ExtendedStructureStartV5<C extends IFeatureConfig> extends StructureStart<C>{
	public abstract String getLocation();
	
	public ExtendedStructureStartV5(Structure<C> feature, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
		super(feature, chunkX, chunkZ, boundingBox, references, seed);
	}

	@Override
	public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, C config) {
		// Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        int x = (chunkX << 4) + 7;
        int z = (chunkZ << 4) + 7;
        BlockPos blockpos = new BlockPos(x, 0, z);

        JigsawManager.addPieces(dynamicRegistryManager,
                new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                		.get(new ResourceLocation(LotrExtendedMod.MODID, getLocation())), maxDepth()), AbstractVillagePiece::new, 
                chunkGenerator, templateManagerIn, blockpos, this.pieces, this.random, false,true);

        this.pieces.forEach(piece -> piece.move(0, yShift(), 0));
        
//        Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
//        int xOffset = blockpos.getX() - structureCenter.getX();
//        int zOffset = blockpos.getZ() - structureCenter.getZ();
//        for(StructurePiece structurePiece : this.pieces){
//            structurePiece.move(xOffset, 0, zOffset);
//        }
        this.calculateBoundingBox();
	}

	public int yShift() {
		return 0;
	}

	public int maxDepth() {
		return 7;
	}

	public boolean usesFoundations() {
		return false;
	}

	public int foundationUpwardsOffsetBasementSpecial() {
		return 0;
	}

	public BlockState defaultFoundationmaterial() {
		return Blocks.COBBLESTONE.defaultBlockState();
	}

	@Override
	public void placeInChunk(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox boundingBox, ChunkPos chunkPos) {
		super.placeInChunk(world, structureManager, chunkGenerator, random, boundingBox, chunkPos);
		if(usesFoundations()) {
			int baseYvalue = this.boundingBox.y0 + foundationUpwardsOffsetBasementSpecial();

			for (int xValue = boundingBox.x0; xValue <= boundingBox.x1; ++xValue) {
				for (int zValue = boundingBox.z0; zValue <= boundingBox.z1; ++zValue) {
					BlockPos blockpos = new BlockPos(xValue, baseYvalue, zValue);
					if (!world.isEmptyBlock(blockpos) && this.boundingBox.isInside(blockpos)) {
						boolean hasPieceAtLowestYValue = false;
						for (StructurePiece structurepiece : this.pieces) {
							if (structurepiece.getBoundingBox().isInside(blockpos)) {
								hasPieceAtLowestYValue = true;
								break;
							}
						}
						if (hasPieceAtLowestYValue) {
							for (int yColumnValue = baseYvalue - 1; yColumnValue > 1; --yColumnValue) {
								BlockPos blockpos1 = new BlockPos(xValue, yColumnValue, zValue);
								if (!world.isEmptyBlock(blockpos1) && !world.getBlockState(blockpos1).getMaterial().isLiquid()) {
									break;
								}
								world.setBlock(blockpos1, defaultFoundationmaterial(), 2);
							}
						}
					}
				}
			}
		}
	}
}
