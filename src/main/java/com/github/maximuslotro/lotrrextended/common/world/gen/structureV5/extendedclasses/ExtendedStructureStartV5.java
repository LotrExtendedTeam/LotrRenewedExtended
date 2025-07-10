package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.extendedclasses;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw.ExtendedJigsawManager;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw.ExtendedSingleJigsawPiece;

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
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

/**
 * Base structure start implementation for Extended Jigsaw-based structures in LotRMod.
 * <p>
 * Supports:
 * <ul>
 *     <li>Dynamic pool-based generation using {@link ExtendedJigsawManager}.</li>
 *     <li>Configurable Y-shifts, chunk center offsets, and generation recursion depth.</li>
 *     <li>Optional full foundation laying beneath structures.</li>
 * </ul>
 * 
 * @param <C> The feature configuration type for this structure.
 */
public abstract class ExtendedStructureStartV5<C extends IFeatureConfig> extends StructureStart<C>{
	public ExtendedSingleJigsawPiece cashedPostProcessedPiece=null;
	/**
     * Constructs a new ExtendedStructureStartV5.
     *
     * @param feature       The structure feature associated with this start.
     * @param chunkX        The X coordinate of the chunk.
     * @param chunkZ        The Z coordinate of the chunk.
     * @param boundingBox   The mutable bounding box for this structure.
     * @param references    Reference count for structure saving/loading.
     * @param seed          World seed for consistent generation.
     */
	public ExtendedStructureStartV5(Structure<C> feature, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
		super(feature, chunkX, chunkZ, boundingBox, references, seed);
	}

	/**
     * Returns the {@link ResourceLocation} of the Jigsaw pool to generate for this structure,
     * depending on the provided configuration and random.
     *
     * @param config The feature configuration.
     * @param rand   The random instance for selection.
     * @return The location of the Jigsaw pool to use for this structure.
     */
	public abstract ResourceLocation getLocation(C config, Random rand);
	/**
     * Generates the pieces for this structure using {@link ExtendedJigsawManager}.
     * <p>
     * Automatically computes the correct chunk coordinates, selects the Jigsaw pool using {@link #getLocation},
     * and places pieces with the configured depth, Y shift, and placement flags.
     *
     * @param dynamicRegistryManager The dynamic registries used for accessing Jigsaw pools.
     * @param chunkGenerator         The chunk generator for terrain and structure generation.
     * @param templateManagerIn      The template manager for structure templates.
     * @param chunkX                 The X coordinate of the chunk.
     * @param chunkZ                 The Z coordinate of the chunk.
     * @param biomeIn                The biome of the chunk.
     * @param config                 The feature configuration.
     */
	@Override
	public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, C config) {
        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);

        // Handle file location stuff here so we don't have duplicated generatePieces methods
        ResourceLocation location = getLocation(config, this.random);
        if(location==null) {
        	ExtendedLog.error("Tried generating a structure from an empty pool! ");
        	return;
        }
        JigsawPiece rootJigsawPiece = ExtendedJigsawManager.addPieces(dynamicRegistryManager,
        	new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(location), 7), AbstractVillagePiece::new,
        	chunkGenerator, templateManagerIn, chunkPos, this.pieces, this.random, false, true);
        if(rootJigsawPiece instanceof ExtendedSingleJigsawPiece) {
        	cashedPostProcessedPiece = ((ExtendedSingleJigsawPiece)rootJigsawPiece);
        	this.pieces.forEach(piece -> piece.move(0, cashedPostProcessedPiece.getVerticalShiftStructureBy(), 0));
        }

//        Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
//        int xOffset = blockpos.getX() - structureCenter.getX();
//        int zOffset = blockpos.getZ() - structureCenter.getZ();
//        for(StructurePiece structurePiece : this.pieces){
//            structurePiece.move(xOffset, 0, zOffset);
//        }
        this.calculateBoundingBox();
	}

	// ############################################ FOUNDATION LAYING LOGIC ###########################################
	/**
     * Returns the upwards offset applied to the base Y value when laying foundations.
     * Override to align the structure basement with terrain.
     *
     * @return The upward Y offset for foundations (default 0).
     */
	public int foundationUpwardsOffsetBasementSpecial() {
		return 0;
	}
	/**
     * Handles placing the structure into the world, including optional foundation filling logic.
     * If {@link ExtendedSingleJigsawPiece#getFoundationBlock()} is not {@link net.minecraft.block.AbstractBlock.AbstractBlockState#isAir()}, this will fill air or liquid blocks beneath
     * the structure down to solid ground using {@link ExtendedSingleJigsawPiece#getFoundationBlock()}.
     *
     * @param world          The world reader for block operations.
     * @param structureManager The structure manager.
     * @param chunkGenerator The chunk generator.
     * @param random         Random for structure placement.
     * @param boundingBox    The bounding box of the structure.
     * @param chunkPos       The chunk position.
     */
	@Override
	public void placeInChunk(ISeedReader world, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox boundingBox, ChunkPos chunkPos) {
		super.placeInChunk(world, structureManager, chunkGenerator, random, boundingBox, chunkPos);
		if(cashedPostProcessedPiece!=null && !cashedPostProcessedPiece.getFoundationBlock().isAir()) {
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
								world.setBlock(blockpos1, cashedPostProcessedPiece.getFoundationBlock(), 2);
							}
						}
					}
				}
			}
		}
	}
}
