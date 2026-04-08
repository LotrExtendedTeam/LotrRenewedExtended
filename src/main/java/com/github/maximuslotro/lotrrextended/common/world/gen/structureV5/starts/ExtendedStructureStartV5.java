package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.starts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw.ExtendedJigsawManager;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw.ExtendedSingleJigsawPiece;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedPoolFeatureConfig;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedStructureSettings;
import com.github.maximuslotro.lotrrextended.common.world.gen.structureV5.config.ExtendedSinglePoolConfig;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
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
public class ExtendedStructureStartV5<C extends ExtendedPoolFeatureConfig> extends StructureStart<C>{
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
     * Resolves the {@link ResourceLocation} of the structure pool to use for generation
     * based on the provided {@link ExtendedSinglePoolConfig}.
     * <p>
     * If the pool name is "empty", logs an error and returns {@code null}.
     * If the pool contains a namespace (colon), it uses the provided namespace.
     * Otherwise, it uses the mod's namespace as a fallback.
     *
     * @param config The structure configuration specifying the pool.
     * @param rand   The random generator used during structure placement.
     * @return The {@link ResourceLocation} of the structure pool, or {@code null} if "empty".
     */
	public ResourceLocation getLocation(C config, Random rand) {
		String pool = config.getRandomPool(rand);
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
	/**
     * Allows for setting a fixed rotation for a structure
     * 
     * @return A fixed rotation for the structure
     */
	public Rotation setRotation() {
		return null;
	}
	/**
     * Allows for setting a fixed block offset for a structure
     * 
     * @return A fixed block pffset for the structure
     */
	public BlockPos setoffset() {
		return BlockPos.ZERO;
	}
	/**
     * Should the structure start be projected onto the heightmap
     * 
     * @return TRUE if structure should be projected onto the heightmap
     */
	public boolean shouldProjectStartToHeightmap() {
		return true;
	}
	public boolean useAboveBlockForFoundation() {
		return false;
	}
	/**
     * Additional hardcoded structure settings
     * 
     * @return A set of additional settings to define structure generation
     */
	public ExtendedStructureSettings getHardcodedSettings() {
		return ExtendedStructureSettings.EMPTY;
	}
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
        ExtendedJigsawManager.addPieces(dynamicRegistryManager,
        	new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(location), 7), AbstractVillagePiece::new,
        	chunkGenerator, templateManagerIn, chunkPos, this.pieces, this.random, false, shouldProjectStartToHeightmap(), setRotation(), setoffset(), getHardcodedSettings());

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
		List<AbstractVillagePiece> cachedRootPostProcessedPieces = new ArrayList<>();
		if(!this.pieces.isEmpty()) {
			for(StructurePiece piece : this.pieces) {
				if(piece instanceof AbstractVillagePiece && ((AbstractVillagePiece)piece).getElement() instanceof ExtendedSingleJigsawPiece
					&& !((ExtendedSingleJigsawPiece)((AbstractVillagePiece)piece).getElement()).getFoundationBlock().isAir()) {
					cachedRootPostProcessedPieces.add(((AbstractVillagePiece)piece));
				}
			}
		}
		super.placeInChunk(world, structureManager, chunkGenerator, random, boundingBox, chunkPos);
		if(!cachedRootPostProcessedPieces.isEmpty()) {
			for(AbstractVillagePiece foundationNeedingPiece: cachedRootPostProcessedPieces) {
				int baseYvalue = foundationNeedingPiece.getBoundingBox().y0;
				for(int xValue = foundationNeedingPiece.getBoundingBox().x0; xValue <= foundationNeedingPiece.getBoundingBox().x1; ++xValue) {
					for(int zValue = foundationNeedingPiece.getBoundingBox().z0; zValue <= foundationNeedingPiece.getBoundingBox().z1; ++zValue) {
						BlockPos blockposBase = new BlockPos(xValue, baseYvalue, zValue);
						if(world.hasChunkAt(blockposBase) && !world.isEmptyBlock(blockposBase) && !world.isWaterAt(blockposBase) && foundationNeedingPiece.getBoundingBox().isInside(blockposBase)) {
							BlockState toPlaceState;
							if(useAboveBlockForFoundation()) {
								toPlaceState = world.getBlockState(blockposBase);
							}else {
								toPlaceState = ((ExtendedSingleJigsawPiece)foundationNeedingPiece.getElement()).getFoundationBlock();
							}
							for(int yColumnValue = baseYvalue - 1; yColumnValue > 1; --yColumnValue) {
								BlockPos blockposRelativeBelow = new BlockPos(xValue, yColumnValue, zValue);
								if(world.isEmptyBlock(blockposRelativeBelow) || //Fill empty blocks
									world.getBlockState(blockposRelativeBelow).getMaterial().isLiquid() || //build down into the water
									world.getBlockState(blockposRelativeBelow).getMaterial().getPushReaction()==PushReaction.DESTROY || //replace weak blocks
									!world.getBlockState(blockposRelativeBelow).getMaterial().blocksMotion()) { //replace non-colliding blocks, probably plants in our case
									world.setBlock(blockposRelativeBelow, toPlaceState, 2);
								}else {
									break;
								}
							}
						}
					}
				}
			}
			cachedRootPostProcessedPieces.clear();
		}
	}
}
