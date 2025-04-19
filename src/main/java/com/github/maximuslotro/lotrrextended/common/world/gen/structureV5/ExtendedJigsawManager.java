package com.github.maximuslotro.lotrrextended.common.world.gen.structureV5;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw.ExtendedSingleJigsawPiece;
import com.github.maximuslotro.lotrrextended.common.world.gen.structure.processor.wood.WoodPaletteProcessor;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import net.minecraft.block.BlockState;
import net.minecraft.block.JigsawBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
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
 * Copied from {@see JigsawManager} and modified to add needed functionality
 * @version 1.16.5
 * */
public class ExtendedJigsawManager {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void addPieces(DynamicRegistries registry, VillageConfig structureConfig, ExtendedJigsawManager.IPieceFactory villagePieceBuilder, ChunkGenerator chunkGenerator, TemplateManager templateManager, BlockPos blockpos, List<? super AbstractVillagePiece> pieces, Random random, boolean doExpansionHack, boolean projectStartToHeightmap) {
		Structure.bootstrap();
		MutableRegistry<JigsawPattern> mutableregistry = registry.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
		Rotation rotation = Rotation.getRandom(random);
		JigsawPattern jigsawpattern = structureConfig.startPool().get();
		JigsawPiece jigsawpiece = jigsawpattern.getRandomTemplate(random);
		// Extended start add
		Optional<WoodPaletteProcessor> carryOverProcessor = Optional.empty();
		if(jigsawpiece instanceof SingleJigsawPiece) {
			List<StructureProcessor> processors = new ArrayList<>(((SingleJigsawPiece)jigsawpiece).processors.get().list());
			boolean foundProcessor = false;
			for (StructureProcessor processor : ((SingleJigsawPiece)jigsawpiece).processors.get().list()) {
				if (processor instanceof WoodPaletteProcessor) {
					if(foundProcessor) {
						IllegalStateException e =  new IllegalStateException("Structure Processor can't have more than one WoodPaletteProcessor instance!");
						e.printStackTrace();
						LOGGER.info("Error adding wood processor present in "+jigsawpattern.getName().toString()+" to parent stack! Skipping structure");
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
			structureStartHeight = blockpos.getY() + chunkGenerator.getFirstFreeHeight(structureBBAverageX, structureBBAverageZ, Heightmap.Type.WORLD_SURFACE_WG);
		} else {
			structureStartHeight = blockpos.getY();
		}

		int relativeStructureYShift = mutableboundingbox.y0 + abstractvillagepiece.getGroundLevelDelta();
		abstractvillagepiece.move(0, structureStartHeight - relativeStructureYShift, 0);
		pieces.add(abstractvillagepiece);
		// Extended Start
		int maxDepth = structureConfig.maxDepth();
		if(jigsawpiece instanceof ExtendedSingleJigsawPiece) {
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
			AxisAlignedBB axisalignedbb = new AxisAlignedBB(structureBBAverageX - maxPlaceDistance, structureStartHeight - maxPlaceDistance, structureBBAverageZ - maxPlaceDistance, structureBBAverageX + maxPlaceDistance + 1, structureStartHeight + maxPlaceDistance + 1, structureBBAverageZ + maxPlaceDistance + 1);
			ExtendedJigsawManager.Assembler jigsawmanager$assembler = new ExtendedJigsawManager.Assembler(mutableregistry, maxDepth, villagePieceBuilder, chunkGenerator, templateManager, pieces, random, carryOverProcessor);
			jigsawmanager$assembler.placing.addLast(new ExtendedJigsawManager.Entry(abstractvillagepiece, new MutableObject<>(VoxelShapes.join(VoxelShapes.create(axisalignedbb), VoxelShapes.create(AxisAlignedBB.of(mutableboundingbox)), IBooleanFunction.ONLY_FIRST)), structureStartHeight + 80, 0));

			while(!jigsawmanager$assembler.placing.isEmpty()) {
				ExtendedJigsawManager.Entry jigsawmanager$entry = jigsawmanager$assembler.placing.removeFirst();
				jigsawmanager$assembler.tryPlacingChildren(jigsawmanager$entry.piece, jigsawmanager$entry.free, jigsawmanager$entry.boundsTop, jigsawmanager$entry.depth, doExpansionHack);
			}

		}
	}

	//Half rebuilt kind function
	public static void addPieces(DynamicRegistries registry, AbstractVillagePiece preBuiltAbstractVillagePiece, int buildLevels, ExtendedJigsawManager.IPieceFactory villagePieceBuilder, ChunkGenerator chunkGenerator, TemplateManager templateManager, List<? super AbstractVillagePiece> listBuildablePieces, Random random) {
		MutableRegistry<JigsawPattern> mutableregistry = registry.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
		ExtendedJigsawManager.Assembler jigsawmanager$assembler = new ExtendedJigsawManager.Assembler(mutableregistry, buildLevels, villagePieceBuilder, chunkGenerator, templateManager, listBuildablePieces, random, Optional.empty());
		jigsawmanager$assembler.placing.addLast(new ExtendedJigsawManager.Entry(preBuiltAbstractVillagePiece, new MutableObject<>(VoxelShapes.INFINITY), 0, 0));

		while(!jigsawmanager$assembler.placing.isEmpty()) {
			ExtendedJigsawManager.Entry jigsawmanager$entry = jigsawmanager$assembler.placing.removeFirst();
			jigsawmanager$assembler.tryPlacingChildren(jigsawmanager$entry.piece, jigsawmanager$entry.free, jigsawmanager$entry.boundsTop, jigsawmanager$entry.depth, false);
		}

	}

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

		// Extended added optional carry over processors
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
							// Handle structure mounds for the current piece, include inside, or change bounds size
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
								//Extended End
								// Try spawning the piece in different directions until it works
								for(Rotation rotation1 : Rotation.getShuffled(this.random)) {
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
										LOGGER.warn("JigsawBlock tried to attach piece without jigsaw blocks: "+connectingPoolJigsawPiece.toString());
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
												IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(blockpos2.getX(), blockpos2.getZ());
												BlockState topBlock1 = columnOfBlocks.getBlockState(new BlockPos(blockpos2.getX(), chunkGeneratorUsableSpawnHeight-1, blockpos2.getZ()));
												boolean waterPresentBelow = !topBlock1.getFluidState().isEmpty();
												
												correctedYconnectingPieceSpawnHeight = chunkGeneratorUsableSpawnHeight - connectingPoolJigsawPieceRelativeYValue + (waterPresentBelow ? 1 : 0);
											}
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
												// Extended End
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
												if (depth + 1 <= this.maxDepth && !doesIgnoreBB) {
													// Extended End
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

	static final class Entry {
		private final AbstractVillagePiece piece;
		private final MutableObject<VoxelShape> free;
		private final int boundsTop;
		private final int depth;

		private Entry(AbstractVillagePiece pPiece, MutableObject<VoxelShape> pFree, int pBoundsTop, int pDepth) {
			this.piece = pPiece;
			this.free = pFree;
			this.boundsTop = pBoundsTop;
			this.depth = pDepth;
		}
	}

	public interface IPieceFactory {
		AbstractVillagePiece create(TemplateManager templateManager, JigsawPiece jigsawPiece, BlockPos position, int groundLevelDelta, Rotation structureRotation, MutableBoundingBox structureBoundingBox);
	}
}