package com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood.WoodPaletteProcessor;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import net.minecraft.block.JigsawBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

/**
 * Manages the generation and assembly of jigsaw-structured village pieces.
 * Provides static methods to add and assemble structure pieces into villages
 * using extended logic, including custom processor handling and placement constraints.
 * Copied from {@link net.minecraft.world.gen.feature.jigsaw.JigsawManager} and modified to add needed functionality
 * @version 1.16.5
 */
public class ExtendedJigsawManager {
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Adds pieces to a village structure based on configuration, using extended logic for wood palette processing and placement constraints.
	 *
	 * <p>This method bootstraps structure pools, picks a start template from the configured pool,
	 * handles optional WoodPaletteProcessor logic if present,
	 * calculates structure positioning relative to heightmaps,
	 * and recursively places child pieces up to a maximum depth and within a placement distance.</p>
	 *
	 * @param registry                the dynamic registries, used to fetch template pools
	 * @param structureConfig         configuration describing the village structure to build
	 * @param villagePieceBuilder     factory to create village piece instances from templates
	 * @param chunkGenerator          chunk generator, used for heightmap queries and terrain context
	 * @param templateManager         manages loading and placement of structure templates
	 * @param chunkPos                starting position for the structure
	 * @param pieces                  list to which generated village pieces will be added
	 * @param random                  random instance for randomized choices
	 * @param doExpansionHack         if true, applies additional placement logic during assembly (specific to implementation)
	 * @param projectStartToHeightmap if true, adjusts vertical placement to surface height from the heightmap
	 * @param pRotation               Set a fixed rotation for the structure
	 * @param offset                  A x, y, z based offset for spawning
	 *
	 * @see ExtendedSingleJigsawPiece
	 * @see WoodPaletteProcessor
	 * @see ExtendedJigsawManager.Assembler
	 * @see net.minecraft.world.gen.feature.structure.Structure
	 */
	public static void addPieces(DynamicRegistries registry, VillageConfig structureConfig, ExtendedJigsawManager.IPieceFactory villagePieceBuilder, 
			ChunkGenerator chunkGenerator, TemplateManager templateManager, ChunkPos chunkPos, List<? super AbstractVillagePiece> pieces, Random random, 
			boolean doExpansionHack, boolean projectStartToHeightmap, Rotation pRotation, BlockPos offset) {
		Structure.bootstrap();
		MutableRegistry<JigsawPattern> mutableregistry = registry.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
		// Extended start
		Rotation rotation = pRotation;
		if(rotation==null) {
			rotation = Rotation.getRandom(random);
		}
		// Extended end
		JigsawPattern jigsawpattern = structureConfig.startPool().get();
		JigsawPiece jigsawpiece = jigsawpattern.getRandomTemplate(random);
		// Extended start
		// Turns the chunk coordinates into actual coordinates we can use. (From corner of the chunk and offset)
		int cornerOffset = 7;
		if(jigsawpiece instanceof ExtendedSingleJigsawPiece) {
			cornerOffset = ((ExtendedSingleJigsawPiece)jigsawpiece).getChunkCornerOffset();
		}
		BlockPos blockpos = new BlockPos(chunkPos.getMinBlockX()+cornerOffset+offset.getX(), 0+offset.getY(), chunkPos.getMinBlockZ()+cornerOffset+offset.getZ());

		Optional<WoodPaletteProcessor> carryOverProcessor = Optional.empty();
		if(jigsawpiece instanceof SingleJigsawPiece) {
			List<StructureProcessor> processors = new ArrayList<>(((SingleJigsawPiece)jigsawpiece).processors.get().list());
			boolean foundProcessor = false;
			for (StructureProcessor processor : ((SingleJigsawPiece)jigsawpiece).processors.get().list()) {
				if (processor instanceof WoodPaletteProcessor) {
					if(foundProcessor) {
						IllegalStateException e =  new IllegalStateException("Structure Processor can't have more than one WoodPaletteProcessor instance!");
						e.printStackTrace();
						LOGGER.info("Error adding wood processor present in {} to parent stack! Skipping structure", jigsawpattern.getName().toString());
						return;
					}
					foundProcessor = true;
					WoodPaletteProcessor processorWood = (WoodPaletteProcessor)processor;
					processors.removeIf(pro -> pro instanceof WoodPaletteProcessor);
					// Fix case where server dominated cashed processor keeps past picked wood pallet
					processorWood.generateAndSetPallet(random);
					processors.add(processorWood);
					StructureProcessorList list = new StructureProcessorList(processors);
					((SingleJigsawPiece)jigsawpiece).processors = () -> list;
					carryOverProcessor = Optional.of(processorWood);
					//ExtendedLog.info("Adding wood processor present in "+jigsawpattern.getName().toString()+" to parent stack: "+processorWood.getPickedPalletName());
				}
			}
		}
		// Extended end
		AbstractVillagePiece abstractvillagepiece = villagePieceBuilder.create(templateManager, jigsawpiece, blockpos, jigsawpiece.getGroundLevelDelta(), rotation, jigsawpiece.getBoundingBox(templateManager, blockpos, rotation));
		MutableBoundingBox mutableboundingbox = abstractvillagepiece.getBoundingBox();
		int structureBBAverageX = (mutableboundingbox.x1 + mutableboundingbox.x0) / 2;
		int structureBBAverageZ = (mutableboundingbox.z1 + mutableboundingbox.z0) / 2;
		int structureStartHeight;
		if (projectStartToHeightmap) {
			// blockpos.getY() should always equal 0 when projecting to heightmap
			structureStartHeight = blockpos.getY() + chunkGenerator.getFirstFreeHeight(structureBBAverageX, structureBBAverageZ, Heightmap.Type.WORLD_SURFACE_WG);
			// Extended Start
			if (structureStartHeight < 62 && jigsawpiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)jigsawpiece).shouldSnapToWaterHeight()) {
				structureStartHeight = 62;
			}
			// Extended End
		} else {
			structureStartHeight = blockpos.getY();
		}
		int relativeStructureYShift = mutableboundingbox.y0 + abstractvillagepiece.getGroundLevelDelta();
		abstractvillagepiece.move(0, structureStartHeight - relativeStructureYShift, 0);
		pieces.add(abstractvillagepiece);
		// Extended Start
		int maxDepth = structureConfig.maxDepth();
		if(jigsawpiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)jigsawpiece).getMaxDepth()!=-1) {
			maxDepth = ((ExtendedSingleJigsawPiece)jigsawpiece).getMaxDepth();
		}
		// Extended End
		if (maxDepth > 0) {
			// Extended Start
			int maxPlaceDistance = 80; // Max generation distance
			if(jigsawpiece instanceof ExtendedSingleJigsawPiece) {
				maxPlaceDistance = ((ExtendedSingleJigsawPiece)jigsawpiece).getMaxPlaceDistance();
			}
			// Extended End
			AxisAlignedBB maxStructureSizeBB = new AxisAlignedBB(structureBBAverageX - maxPlaceDistance, structureStartHeight - maxPlaceDistance, structureBBAverageZ - maxPlaceDistance, structureBBAverageX + maxPlaceDistance + 1, structureStartHeight + maxPlaceDistance + 1, structureBBAverageZ + maxPlaceDistance + 1);
			ExtendedJigsawManager.Assembler jigsawmanager$assembler = new ExtendedJigsawManager.Assembler(mutableregistry, maxDepth, villagePieceBuilder, chunkGenerator, templateManager, pieces, random, carryOverProcessor);
			jigsawmanager$assembler.placing.addLast(new ExtendedJigsawManager.Entry(abstractvillagepiece, new MutableObject<>(VoxelShapes.join(VoxelShapes.create(maxStructureSizeBB), VoxelShapes.create(AxisAlignedBB.of(mutableboundingbox)), IBooleanFunction.ONLY_FIRST)), structureStartHeight + 80, 0));

			while(!jigsawmanager$assembler.placing.isEmpty()) {
				ExtendedJigsawManager.Entry jigsawmanager$entry = jigsawmanager$assembler.placing.removeFirst();
				jigsawmanager$assembler.tryPlacingChildren(jigsawmanager$entry.piece, jigsawmanager$entry.free, jigsawmanager$entry.boundsTop, jigsawmanager$entry.depth, doExpansionHack);
			}

		}
	}

	/**
	 * Adds pieces to a village starting from a pre-built piece, assembling child pieces recursively up to a specified build depth.
	 *
	 * <p>This method constructs the village structure starting from an existing {@link AbstractVillagePiece},
	 * and attempts to place child pieces by traversing the jigsaw pools up to {@code buildLevels} deep.
	 * This is a lower-level assembly method that can be used when you already have a starting piece.</p>
	 *
	 * @param registry             the dynamic registries, used to fetch template pools
	 * @param preBuiltAbstractVillagePiece the starting village piece to build from
	 * @param buildLevels          maximum depth of recursive piece placement
	 * @param villagePieceBuilder  factory to create village piece instances from templates
	 * @param chunkGenerator       chunk generator, used for terrain context and height queries
	 * @param templateManager      manages loading and placement of structure templates
	 * @param listBuildablePieces  collection to which placed pieces will be added
	 * @param random               random instance for randomized choices
	 */
	public static void addPieces(DynamicRegistries registry, AbstractVillagePiece preBuiltAbstractVillagePiece, int buildLevels, ExtendedJigsawManager.IPieceFactory villagePieceBuilder, ChunkGenerator chunkGenerator, TemplateManager templateManager, List<? super AbstractVillagePiece> listBuildablePieces, Random random) {
		MutableRegistry<JigsawPattern> mutableregistry = registry.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
		ExtendedJigsawManager.Assembler jigsawmanager$assembler = new ExtendedJigsawManager.Assembler(mutableregistry, buildLevels, villagePieceBuilder, chunkGenerator, templateManager, listBuildablePieces, random, Optional.empty());
		jigsawmanager$assembler.placing.addLast(new ExtendedJigsawManager.Entry(preBuiltAbstractVillagePiece, new MutableObject<>(VoxelShapes.INFINITY), 0, 0));

		while(!jigsawmanager$assembler.placing.isEmpty()) {
			ExtendedJigsawManager.Entry jigsawmanager$entry = jigsawmanager$assembler.placing.removeFirst();
			jigsawmanager$assembler.tryPlacingChildren(jigsawmanager$entry.piece, jigsawmanager$entry.free, jigsawmanager$entry.boundsTop, jigsawmanager$entry.depth, false);
		}

	}
	/**
	 * Responsible for assembling village pieces recursively by managing piece placement,
	 * checking placement constraints, and handling structure pools during generation.
	 * 
	 * <p>This class maintains state during the assembly process, including the pool registry,
	 * maximum recursion depth, piece factory, chunk generator for terrain data,
	 * structure manager for template loading, list of generated pieces, and a random source.
	 * It also optionally handles a {@link WoodPaletteProcessor} to carry over wood palette information
	 * across pieces.</p>
	 */
	static final class Assembler {
		private final Registry<JigsawPattern> pools;
		private final int maxDepth;
		private final ExtendedJigsawManager.IPieceFactory factory;
		private final ChunkGenerator chunkGenerator;
		private final TemplateManager structureManager;
		private final List<? super AbstractVillagePiece> pieces;
		private final Random random;
		private final Deque<ExtendedJigsawManager.Entry> placing = Queues.newArrayDeque();
		// Extended add optional carry over processors
		private final Optional<WoodPaletteProcessor> carryOverProcessor;
		private final List<String> blacklisted_structures = new ArrayList<>();

		/**
		 * Constructs an assembler for generating village structures.
		 *
		 * @param pPools             registry of jigsaw pattern pools used for piece selection
		 * @param pMaxDepth          maximum recursion depth for piece placement
		 * @param pFactory           factory used to create village pieces from templates
		 * @param pChunkGenerator    chunk generator providing terrain and heightmap information
		 * @param pStructureManager  manages loading and handling of structure templates
		 * @param pPieces            collection where generated pieces will be added
		 * @param pRandom            random source for randomized choices
		 * @param pCarryOverProcessor optional processor to carry over wood palette data between pieces
		 */
		private Assembler(Registry<JigsawPattern> pPools, int pMaxDepth, ExtendedJigsawManager.IPieceFactory pFactory, ChunkGenerator pChunkGenerator, TemplateManager pStructureManager, List<? super AbstractVillagePiece> pPieces, Random pRandom, Optional<WoodPaletteProcessor> pCarryOverProcessor) {
			this.pools = pPools;
			this.maxDepth = pMaxDepth;
			this.factory = pFactory;
			this.chunkGenerator = pChunkGenerator;
			this.structureManager = pStructureManager;
			this.pieces = pPieces;
			this.random = pRandom;
			this.carryOverProcessor = pCarryOverProcessor;
		}
		/**
		 * Attempts to place child pieces connected to a given parent piece within the village structure.
		 *
		 * <p>This method iterates through all jigsaw blocks in the parent piece, resolves their respective pools,
		 * selects possible child pieces from those pools, and attempts to place them respecting spatial constraints
		 * and maximum depth. It uses voxel shape checks to avoid overlapping structures.</p>
		 *
		 * @param villagePiece      the parent village piece to place children for
		 * @param freeVoxelShapes   voxel shapes representing free space available for placing children
		 * @param boundsTop         the upper boundary Y coordinate limiting placement
		 * @param depth             current recursion depth in the piece placement process
		 * @param doExpansionHack   whether to apply additional placement logic (specific to implementation)
		 */
		private void tryPlacingChildren(AbstractVillagePiece villagePiece, MutableObject<VoxelShape> freeVoxelShapes, int boundsTop, int depth, boolean doExpansionHack) {
			JigsawPiece jigsawpiece = villagePiece.getElement();
			BlockPos blockpos = villagePiece.getPosition();
			Rotation rotation = villagePiece.getRotation();
			JigsawPattern.PlacementBehaviour jigsawPiecePlacementBehaviour = jigsawpiece.getProjection();
			boolean isParentPlacementRidged = jigsawPiecePlacementBehaviour == JigsawPattern.PlacementBehaviour.RIGID;
			MutableObject<VoxelShape> structureShapeInterpolated = new MutableObject<>();
			MutableBoundingBox mutableboundingbox = villagePiece.getBoundingBox();
			int jigsawPieceRelativeBottomYValue = mutableboundingbox.y0;

			mainStrLoop:
				// Loop through each JigsawBlock present in this piece
				for(Template.BlockInfo parentPieceJigsawBlockInfo : jigsawpiece.getShuffledJigsawBlocks(this.structureManager, blockpos, rotation, this.random)) {
					Direction direction = JigsawBlock.getFrontFacing(parentPieceJigsawBlockInfo.state);
					BlockPos blockpos1 = parentPieceJigsawBlockInfo.pos;
					BlockPos blockpos2 = blockpos1.relative(direction);
					int jigsawYRelativeToActualPieceBottom = blockpos1.getY() - jigsawPieceRelativeBottomYValue;
					int chunkGeneratorUsableSpawnHeight = -1;
					ResourceLocation jigsawPoolResourceLocation = new ResourceLocation(parentPieceJigsawBlockInfo.nbt.getString("pool"));
					Optional<JigsawPattern> jigsawPool = this.pools.getOptional(jigsawPoolResourceLocation);
					// Check if the selected JigsawBLock has a valid pool and the pool size is not empty or the pool is JigsawPatternRegistry.EMPTY
					if (jigsawPool.isPresent() && (jigsawPool.get().size() != 0 || Objects.equals(jigsawPoolResourceLocation, JigsawPatternRegistry.EMPTY.location()))) {
						ResourceLocation jigsawPoolFallbackResourceLocation = jigsawPool.get().getFallback();
						Optional<JigsawPattern> jigsawFallbackPool = this.pools.getOptional(jigsawPoolFallbackResourceLocation);
						// Check if selected JigsawBLock fallback pool exists and the pool size is not empty or the pool is JigsawPatternRegistry.EMPTY
						if (jigsawFallbackPool.isPresent() && (jigsawFallbackPool.get().size() != 0 || Objects.equals(jigsawPoolFallbackResourceLocation, JigsawPatternRegistry.EMPTY.location()))) {
							boolean isConnectingJigsawInsideCurrentStructure = mutableboundingbox.isInside(blockpos2);
							MutableObject<VoxelShape> childPieceStructureShapeInterpolated;
							int parentPieceBoundsTop;
							// Handle structure bounds for the current piece, include inside, or change bounds size
							if (isConnectingJigsawInsideCurrentStructure) {
								childPieceStructureShapeInterpolated = structureShapeInterpolated;
								parentPieceBoundsTop = jigsawPieceRelativeBottomYValue;
								if (structureShapeInterpolated.getValue() == null) {
									structureShapeInterpolated.setValue(VoxelShapes.create(AxisAlignedBB.of(mutableboundingbox)));
								}
							} else {
								childPieceStructureShapeInterpolated = freeVoxelShapes;
								parentPieceBoundsTop = boundsTop;
							}
							// Add all the pieces inside the selected Jigsaw pool
							List<JigsawPiece> jigsawPiecesConnectingPool = Lists.newArrayList();
							if (depth != this.maxDepth) {
								jigsawPiecesConnectingPool.addAll(jigsawPool.get().getShuffledTemplates(this.random));
							}

							jigsawPiecesConnectingPool.addAll(jigsawFallbackPool.get().getShuffledTemplates(this.random));
							// Loop through each piece until find one that can be spawned
							for(JigsawPiece connectingPoolJigsawPiece : jigsawPiecesConnectingPool) {
								if (connectingPoolJigsawPiece == EmptyJigsawPiece.INSTANCE) {
									break;
								}
								// Extended Start
								if (connectingPoolJigsawPiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).isBlacklistSet()) {
									if (blacklisted_structures.contains(((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).getBlacklistName())) {
										continue;
									}
								}
								boolean shouldBreakRotation = false;
								//Extended End
								// Try spawning the piece in different directions until it works
								for(Rotation rotation1 : Rotation.getShuffled(this.random)) {
									if(shouldBreakRotation) {
										break;
									}
									//if(enableLogging) {ExtendedLog.info("Trying rotation: "+rotation1.toString());}
									List<Template.BlockInfo> connectingPieceJigsawRandom = connectingPoolJigsawPiece.getShuffledJigsawBlocks(this.structureManager, BlockPos.ZERO, rotation1, this.random);
									MutableBoundingBox rotatedBBforJigsawRandom = connectingPoolJigsawPiece.getBoundingBox(this.structureManager, BlockPos.ZERO, rotation1);
									int expansionHackModifierValue;
									// Run vanilla villages black magic spawning
									if (doExpansionHack && rotatedBBforJigsawRandom.getYSpan() <= 16) {
										expansionHackModifierValue = connectingPieceJigsawRandom.stream().mapToInt((blockInfo) -> {
											if (!rotatedBBforJigsawRandom.isInside(blockInfo.pos.relative(JigsawBlock.getFrontFacing(blockInfo.state)))) {
												return 0;
											} else {
												ResourceLocation connectedPieceEXEesourceLocation = new ResourceLocation(blockInfo.nbt.getString("pool"));
												Optional<JigsawPattern> connectedPieceEX = this.pools.getOptional(connectedPieceEXEesourceLocation);
												Optional<JigsawPattern> connectedPieceFallbackEX = connectedPieceEX.flatMap((p_242843_1_) -> this.pools.getOptional(p_242843_1_.getFallback()));
												int pieceMaxSize = connectedPieceEX.map((p_242842_1_) -> p_242842_1_.getMaxSize(this.structureManager)).orElse(0);
												int pieceFallbackMaxSize = connectedPieceFallbackEX.map((p_242840_1_) -> p_242840_1_.getMaxSize(this.structureManager)).orElse(0);
												return Math.max(pieceMaxSize, pieceFallbackMaxSize);
											}
										}).max().orElse(0);
									} else {
										expansionHackModifierValue = 0;
									}
									// Extended Start
									if(connectingPieceJigsawRandom.size()==0) {
										LOGGER.warn("JigsawBlock tried to attach piece without jigsaw blocks: {}", connectingPoolJigsawPiece.toString());
									}
									// Extended End
									// Try spawning the piece at a random connecting Jigsaw point
									for(Template.BlockInfo connectingPieceJigsawBlockInfo : connectingPieceJigsawRandom) {
										if (JigsawBlock.canAttach(parentPieceJigsawBlockInfo, connectingPieceJigsawBlockInfo)) {
											BlockPos blockpos3 = connectingPieceJigsawBlockInfo.pos;
											BlockPos blockpos4 = new BlockPos(blockpos2.getX() - blockpos3.getX(), blockpos2.getY() - blockpos3.getY(), blockpos2.getZ() - blockpos3.getZ());
											MutableBoundingBox mutableboundingbox2 = connectingPoolJigsawPiece.getBoundingBox(this.structureManager, blockpos4, rotation1);
											int connectingPoolJigsawPieceRelativeBottomYValue = mutableboundingbox2.y0;
											JigsawPattern.PlacementBehaviour connectingPoolJigsawPiecePlacementBehaviour = connectingPoolJigsawPiece.getProjection();
											boolean isAttachedPlacementRidged = connectingPoolJigsawPiecePlacementBehaviour == JigsawPattern.PlacementBehaviour.RIGID;
											int connectingPoolJigsawPieceRelativeYValue = blockpos3.getY();
											int relativeYconnectingPieceSpawnHeight = jigsawYRelativeToActualPieceBottom - connectingPoolJigsawPieceRelativeYValue + JigsawBlock.getFrontFacing(parentPieceJigsawBlockInfo.state).getStepY();
											int correctedYconnectingPieceSpawnHeight;
											// Determine the correct spawn placement of the piece, ridged or terrain matching
											if (isParentPlacementRidged && isAttachedPlacementRidged) {
												// Ridged placement
												correctedYconnectingPieceSpawnHeight = jigsawPieceRelativeBottomYValue + relativeYconnectingPieceSpawnHeight;
											} else {
												// Get the ground height for terrain matching
												if (chunkGeneratorUsableSpawnHeight == -1) {
													chunkGeneratorUsableSpawnHeight = this.chunkGenerator.getFirstFreeHeight(blockpos1.getX(), blockpos1.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
												}
												correctedYconnectingPieceSpawnHeight = chunkGeneratorUsableSpawnHeight - connectingPoolJigsawPieceRelativeYValue;
												// Extended Start
												if (correctedYconnectingPieceSpawnHeight < 62 && connectingPoolJigsawPiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).shouldSnapToWaterHeight()) {
													correctedYconnectingPieceSpawnHeight = 62;
												}
												// Extended End
											}
											// Extended Start
											if (connectingPoolJigsawPiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).disableSpawnAboveGround()) {
												//if(61<mutableboundingbox2.y1) {
												//	shouldBreakRotation = true;
												//	break;
												//}
												Vector3i center = mutableboundingbox2.getCenter();
												if(chunkGenerator.getFirstFreeHeight(center.getX(), center.getZ(), Heightmap.Type.WORLD_SURFACE_WG)<=mutableboundingbox2.y1 + 1) {
													shouldBreakRotation = true;
													break;
												}
											}
											if (connectingPoolJigsawPiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).shouldProtectYLevel()) {
												if(correctedYconnectingPieceSpawnHeight < ((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).getProtectableYLevelHeight()){
													continue;
												}
											}
											// Extended End
											// Set the y-offset for the piece placement
											int connectingPieceBBShiftYoffset = correctedYconnectingPieceSpawnHeight - connectingPoolJigsawPieceRelativeBottomYValue;
											MutableBoundingBox mutableboundingbox3 = mutableboundingbox2.moved(0, connectingPieceBBShiftYoffset, 0);
											BlockPos blockpos5 = blockpos4.offset(0, connectingPieceBBShiftYoffset, 0);
											if (expansionHackModifierValue > 0) {
												int expansionHackModifidYvalue = Math.max(expansionHackModifierValue + 1, mutableboundingbox3.y1 - mutableboundingbox3.y0);
												mutableboundingbox3.y1 = mutableboundingbox3.y0 + expansionHackModifidYvalue;
											}
											// Extended Start
											boolean doesIgnoreBB = false; 
											if(jigsawpiece instanceof ExtendedSingleJigsawPiece) {
												doesIgnoreBB = ((ExtendedSingleJigsawPiece)jigsawpiece).doesIngnoreBBconflicts();
											}
											// Extended End
											// Check if bounding box for structure accepts the piece
											if (!VoxelShapes.joinIsNotEmpty(childPieceStructureShapeInterpolated.getValue(), VoxelShapes.create(AxisAlignedBB.of(mutableboundingbox3).deflate(0.25D)), IBooleanFunction.ONLY_SECOND)) {
												childPieceStructureShapeInterpolated.setValue(VoxelShapes.joinUnoptimized(childPieceStructureShapeInterpolated.getValue(), VoxelShapes.create(AxisAlignedBB.of(mutableboundingbox3)), IBooleanFunction.ONLY_FIRST));
												int pieceGroundLevelData = villagePiece.getGroundLevelDelta();
												int connectingPoolJigsawPieceGroundLevelData;
												// Get the ground level for placement type
												if (isAttachedPlacementRidged) {
													connectingPoolJigsawPieceGroundLevelData = pieceGroundLevelData - relativeYconnectingPieceSpawnHeight;
												} else {
													connectingPoolJigsawPieceGroundLevelData = connectingPoolJigsawPiece.getGroundLevelDelta();
												}
												// Extended start
												// Handle Wood palette processors
												if(carryOverProcessor.isPresent() && connectingPoolJigsawPiece instanceof SingleJigsawPiece) {
													List<StructureProcessor> processors = new ArrayList<>(((SingleJigsawPiece)connectingPoolJigsawPiece).processors.get().list());
													processors.removeIf(pro -> pro instanceof WoodPaletteProcessor);
													processors.add(carryOverProcessor.get());
													StructureProcessorList list = new StructureProcessorList(processors);
													((SingleJigsawPiece)connectingPoolJigsawPiece).processors = () -> list;
													//ExtendedLog.info("Added wood processor to structure piece from parent: "+carryOverProcessor.get().getPickedPalletName());
												}
												// Add structure to blacklist since we are actually spawning it
												if (connectingPoolJigsawPiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).isBlacklistSet()) {
													blacklisted_structures.add(((ExtendedSingleJigsawPiece)connectingPoolJigsawPiece).getBlacklistName());
												}
												// Extended end
												// Make another AbstractVillagePiece for the children pieces for this one
												AbstractVillagePiece abstractvillagepiece = this.factory.create(this.structureManager, connectingPoolJigsawPiece, blockpos5, connectingPoolJigsawPieceGroundLevelData, rotation1, mutableboundingbox3);
												int connectingPoolJigsawPieceSpawnHeight;
												if (isParentPlacementRidged) {
													connectingPoolJigsawPieceSpawnHeight = jigsawPieceRelativeBottomYValue + jigsawYRelativeToActualPieceBottom;
												} else if (isAttachedPlacementRidged) {
													connectingPoolJigsawPieceSpawnHeight = correctedYconnectingPieceSpawnHeight + connectingPoolJigsawPieceRelativeYValue;
												} else {
													if (chunkGeneratorUsableSpawnHeight == -1) {
														chunkGeneratorUsableSpawnHeight = this.chunkGenerator.getFirstFreeHeight(blockpos1.getX(), blockpos1.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
													}
													connectingPoolJigsawPieceSpawnHeight = chunkGeneratorUsableSpawnHeight + relativeYconnectingPieceSpawnHeight / 2;

												}
												// Add junction information for this+children JigsawBlocks
												villagePiece.addJunction(new JigsawJunction(blockpos2.getX(), connectingPoolJigsawPieceSpawnHeight - jigsawYRelativeToActualPieceBottom + pieceGroundLevelData, blockpos2.getZ(), relativeYconnectingPieceSpawnHeight, connectingPoolJigsawPiecePlacementBehaviour));
												abstractvillagepiece.addJunction(new JigsawJunction(blockpos1.getX(), connectingPoolJigsawPieceSpawnHeight - connectingPoolJigsawPieceRelativeYValue + connectingPoolJigsawPieceGroundLevelData, blockpos1.getZ(), -relativeYconnectingPieceSpawnHeight, jigsawPiecePlacementBehaviour));
												this.pieces.add(abstractvillagepiece);
												// Extended Start
												if(jigsawpiece instanceof ExtendedSingleJigsawPiece && ((ExtendedSingleJigsawPiece)jigsawpiece).shouldResetMaxDepth()) {
													depth = 0;
												}
												// Extended End
												if (depth + 1 <= this.maxDepth /* Extended Start */&& !doesIgnoreBB /*Extended End*/) {
													this.placing.addLast(new ExtendedJigsawManager.Entry(abstractvillagepiece, childPieceStructureShapeInterpolated, parentPieceBoundsTop, depth + 1));
												}
												continue mainStrLoop;
											}
										}
									}
								}
							}
						} else {
							LOGGER.warn("Empty or none existent fallback pool: {}", jigsawPoolFallbackResourceLocation);
						}
					} else {
						LOGGER.warn("Empty or none existent pool: {}", jigsawPoolResourceLocation);
					}
				}

		}
	}
	/**
	 * Represents a single placement entry in the assembly queue for village structure generation.
	 * 
	 * <p>Holds the village piece to be placed, the current free voxel shape available for placement,
	 * the top boundary height for placement, and the recursion depth.</p>
	 */
	static final class Entry {
		private final AbstractVillagePiece piece;
		private final MutableObject<VoxelShape> free;
		private final int boundsTop;
		private final int depth;
		/**
		 * Creates a new placement entry.
		 *
		 * @param pPiece      the village piece to be placed
		 * @param pFree       the voxel shape representing free space for placement
		 * @param pBoundsTop  the Y coordinate representing the top bounds limit for placement
		 * @param pDepth      the current depth of recursive placement
		 */
		private Entry(AbstractVillagePiece pPiece, MutableObject<VoxelShape> pFree, int pBoundsTop, int pDepth) {
			this.piece = pPiece;
			this.free = pFree;
			this.boundsTop = pBoundsTop;
			this.depth = pDepth;
		}
	}
	/**
	 * Factory interface for creating village pieces from jigsaw templates.
	 */
	public interface IPieceFactory {
		/**
		 * Creates an {@link AbstractVillagePiece} instance from the given parameters.
		 *
		 * @param templateManager       manages structure templates and resources
		 * @param jigsawPiece           the jigsaw piece template to base the village piece on
		 * @param position              the position to place the piece at
		 * @param groundLevelDelta      vertical offset relative to ground level
		 * @param structureRotation     rotation to apply to the structure
		 * @param structureBoundingBox  bounding box defining the structure's spatial bounds
		 * @return                      a new {@link AbstractVillagePiece} instance representing the generated piece
		 */
		AbstractVillagePiece create(TemplateManager templateManager, JigsawPiece jigsawPiece, BlockPos position, int groundLevelDelta, Rotation structureRotation, MutableBoundingBox structureBoundingBox);
	}
}