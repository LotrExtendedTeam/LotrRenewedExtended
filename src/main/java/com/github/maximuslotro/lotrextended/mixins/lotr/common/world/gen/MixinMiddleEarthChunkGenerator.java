package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.gen;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.jigsaw.ExtendedSingleJigsawPiece;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import lotr.common.world.gen.MiddleEarthChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

/**
 * Mixin class for {@link MiddleEarthChunkGenerator} to modify chunk generation behavior,
 * specifically handling structure terrain height processing and custom jigsaw piece filtering.
 * @Mixin (MiddleEarthChunkGenerator.class)
 */
@Mixin(MiddleEarthChunkGenerator.class)
public abstract class MixinMiddleEarthChunkGenerator extends ChunkGenerator {
	/**
	 * Constructor required by Mixin classes extending a base class.
	 * This constructor is stripped at runtime and will cease to exist.
	 *
	 * @param pBiomeSource    The biome provider registry source.
	 * @param pRuntimeSource  The biome provider used at runtime.
	 * @param pSettings       The dimensional structure settings layout.
	 * @param pStrongholdSeed The random seed used for stronghold generation.
	 */
	public MixinMiddleEarthChunkGenerator(BiomeProvider pBiomeSource, BiomeProvider pRuntimeSource, DimensionStructuresSettings pSettings, long pStrongholdSeed) {
		super(pBiomeSource, pRuntimeSource, pSettings, pStrongholdSeed);
	}
	/**
	 * Shadow field representing the default air block state.
	 */
	@Final
	@Shadow(remap=false)
	private static BlockState AIR;
	/**
	 * The size of a noise cell along the vertical axis (Y).
	 * Formerly known as {@code chunkHeight} in vanilla contexts.
	 */
	@Final
	@Shadow(remap=false)
	private int verticalNoiseGranularity;
	/**
	 * The size of a noise cell along the horizontal axes (X/Z).
	 * Formerly known as {@code chunkWidth} in vanilla contexts.
	 */
	@Final
	@Shadow(remap=false)
	private int horizontalNoiseGranularity;
	/**
	 * The total number of noise sampling cells along the X axis.
	 * Formerly known as {@code chunkCountX} in vanilla contexts.
	 */
	@Final
	@Shadow(remap=false)
	private int noiseSizeX;
	/**
	 * The total number of noise sampling cells along the Y axis.
	 * Formerly known as {@code chunkCountY} in vanilla contexts.
	 */
	@Final
	@Shadow(remap=false)
	private int noiseSizeY;
	/**
	 * The total number of noise sampling cells along the Z axis.
	 * Formerly known as {@code chunkCountZ} in vanilla contexts.
	 */
	@Final
	@Shadow(remap=false)
	private int noiseSizeZ;
	/**
	 * Fills a double array buffer with terrain noise values for a single vertical column.
	 *
	 * @param noiseColumn The buffer array to store calculated noise values.
	 * @param noiseX      The cell-space X coordinate.
	 * @param noiseZ      The cell-space Z coordinate.
	 */
	@Shadow(remap=false)
	private void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {}
	/**
	 * Calculates the density modulation weight/contribution of a structure piece at a given distance offset.
	 * Used to flatten or adjust terrain around structures.
	 *
	 * @param x The absolute distance offset on the X axis.
	 * @param y The absolute distance offset on the Y axis.
	 * @param z The absolute distance offset on the Z axis.
	 * @return The weight multiplier for modifying base noise density.
	 */
	@Shadow(remap = false, aliases = {"func_222556_a"})
	private static double getContribution(int x, int y, int z) {return 0.0D;}
	/**
	 * Determines the appropriate block state (e.g., Stone, Dirt, Air) for a specific height based on noise.
	 * Formerly known as {@code generateBaseState} in vanilla contexts.
	 *
	 * @param terrainNoise The final modulated density noise value.
	 * @param height       The absolute world block height.
	 * @return The target BlockState for the coordinates.
	 */
	@Shadow(remap=false)
	protected BlockState getDefaultStateForHeight(double terrainNoise, int height) {return null;}

	/**
	 * Generates the base terrain layout using 3D noise interpolation and accounts for nearby structures.
	 * <p>
	 * This method is directly copied from {@code NoiseChunkGenerator} but with fields renamed to match Renewed's naming:
	 * <ul>
	 * <li>{@code chunkHeight} -> {@link #verticalNoiseGranularity}</li>
	 * <li>{@code chunkWidth} -> {@link #horizontalNoiseGranularity}</li>
	 * <li>{@code chunkCountX} -> {@link #noiseSizeX}</li>
	 * <li>{@code chunkCountY} -> {@link #noiseSizeY}</li>
	 * <li>{@code chunkCountZ} -> {@link #noiseSizeZ}</li>
	 * <li>{@code generateBaseState} -> {@link #getDefaultStateForHeight}</li>
	 * </ul>
	 * Additionally, multiple compiler-generated local variables have been cleaned up for human readability.
	 *
	 * @param pLevel            The world access instance.
	 * @param pStructureManager The manager containing regional structure references.
	 * @param pChunk            The chunk object currently being filled.
	 * @author maximuslotro
	 * @reason Needed to inject custom rules into structure terrain height processing code, 
	 * allowing specific underground jigsaw pieces to bypass land flattening calculations.
	 */
	@Overwrite
	@Override
	public void fillFromNoise(IWorld pLevel, StructureManager pStructureManager, IChunk pChunk) {
		ObjectList<StructurePiece> intersectingStructures = new ObjectArrayList<>(10);
		ObjectList<JigsawJunction> intersectingJunctions = new ObjectArrayList<>(32);
		ChunkPos chunkpos = pChunk.getPos();
		int chunkPosX = chunkpos.x;
		int chunkPosZ = chunkpos.z;
		int worldXOffset = chunkPosX << 4;
		int worldZOffset = chunkPosZ << 4;

		for(Structure<?> structure : Structure.NOISE_AFFECTING_FEATURES) {
			pStructureManager.startsForFeature(SectionPos.of(chunkpos, 0), structure).forEach((structureStart) -> {
				for(StructurePiece piece : structureStart.getPieces()) {
					if (piece.isCloseToChunk(chunkpos, 12)) {
						if (piece instanceof AbstractVillagePiece) {
							AbstractVillagePiece abstractvillagepiece = (AbstractVillagePiece)piece;
							// Extended Start
							if (abstractvillagepiece.getElement() instanceof ExtendedSingleJigsawPiece) {
						        if (((ExtendedSingleJigsawPiece) abstractvillagepiece.getElement()).isUndergroundPiece()) {
						            continue; // Skip adding this piece to land flattening lists entirely
						        }
						    }
							// Extended End
							JigsawPattern.PlacementBehaviour placementbehaviour = abstractvillagepiece.getElement().getProjection();
							if (placementbehaviour == JigsawPattern.PlacementBehaviour.RIGID) {
								intersectingStructures.add(abstractvillagepiece);
							}

							for(JigsawJunction junction : abstractvillagepiece.getJunctions()) {
								int sourceX = junction.getSourceX();
								int sourceZ = junction.getSourceZ();
								if (sourceX > worldXOffset - 12 && sourceZ > worldZOffset - 12 && sourceX < worldXOffset + 15 + 12 && sourceZ < worldZOffset + 15 + 12) {
									intersectingJunctions.add(junction);
								}
							}
						} else {
							intersectingStructures.add(piece);
						}
					}
				}

			});
		}
		// Noise buffers for trilinear interpolation (current X slice and next X slice)
		double[][][] noiseBuffer = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

		for(int zIdx = 0; zIdx < this.noiseSizeZ + 1; ++zIdx) {
			noiseBuffer[0][zIdx] = new double[this.noiseSizeY + 1];
			this.fillNoiseColumn(noiseBuffer[0][zIdx], chunkPosX * this.noiseSizeX, chunkPosZ * this.noiseSizeZ + zIdx);
			noiseBuffer[1][zIdx] = new double[this.noiseSizeY + 1];
		}

		ChunkPrimer chunkprimer = (ChunkPrimer)pChunk;
		Heightmap oceanFloorHeightmap = chunkprimer.getOrCreateHeightmapUnprimed(Heightmap.Type.OCEAN_FLOOR_WG);
		Heightmap worldSurfaceHeightmap = chunkprimer.getOrCreateHeightmapUnprimed(Heightmap.Type.WORLD_SURFACE_WG);
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		ObjectListIterator<StructurePiece> structureIterator = intersectingStructures.iterator();
		ObjectListIterator<JigsawJunction> junctionIterator = intersectingJunctions.iterator();

		// Begin 3D noise interpolation and block placement
		for(int xLoop = 0; xLoop < this.noiseSizeX; ++xLoop) {
			for(int zLoop = 0; zLoop < this.noiseSizeZ + 1; ++zLoop) {
				this.fillNoiseColumn(noiseBuffer[1][zLoop], chunkPosX * this.noiseSizeX + xLoop + 1, chunkPosZ * this.noiseSizeZ + zLoop);
			}

			for(int zLoop = 0; zLoop < this.noiseSizeZ; ++zLoop) {
				ChunkSection currentSection = chunkprimer.getOrCreateSection(15);
				currentSection.acquire();

				for(int yLoop = this.noiseSizeY - 1; yLoop >= 0; --yLoop) {
					// 8 corners of the current noise sampling cell for trilinear interpolation
					double n000 = noiseBuffer[0][zLoop][yLoop];
					double n010 = noiseBuffer[0][zLoop + 1][yLoop];
					double n100 = noiseBuffer[1][zLoop][yLoop];
					double n110 = noiseBuffer[1][zLoop + 1][yLoop];
					double n001 = noiseBuffer[0][zLoop][yLoop + 1];
					double n011 = noiseBuffer[0][zLoop + 1][yLoop + 1];
					double n101 = noiseBuffer[1][zLoop][yLoop + 1];
					double n111 = noiseBuffer[1][zLoop + 1][yLoop + 1];

					for(int ySub = this.verticalNoiseGranularity - 1; ySub >= 0; --ySub) {
						int absoluteY = yLoop * this.verticalNoiseGranularity + ySub;
						int localY = absoluteY & 15;
						int sectionYIndex = (absoluteY >> 4);
						if (currentSection.bottomBlockY() >> 4 != sectionYIndex) {
							currentSection.release();
							currentSection = chunkprimer.getOrCreateSection(sectionYIndex);
							currentSection.acquire();
						}
						double yLerpProgress = (double)ySub / (double)this.verticalNoiseGranularity;
						double lerpY00 = MathHelper.lerp(yLerpProgress, n000, n001);
						double lerpY10 = MathHelper.lerp(yLerpProgress, n100, n101);
						double lerpY01 = MathHelper.lerp(yLerpProgress, n010, n011);
						double lerpY11 = MathHelper.lerp(yLerpProgress, n110, n111);

						for(int xSub = 0; xSub < this.horizontalNoiseGranularity; ++xSub) {
							int absoluteX = worldXOffset + xLoop * this.horizontalNoiseGranularity + xSub;
							int localX = absoluteX & 15;
							double xLerpProgress = (double)xSub / (double)this.horizontalNoiseGranularity;
							double lerpX0 = MathHelper.lerp(xLerpProgress, lerpY00, lerpY10);
							double lerpX1 = MathHelper.lerp(xLerpProgress, lerpY01, lerpY11);

							for(int zSub = 0; zSub < this.horizontalNoiseGranularity; ++zSub) {
								int absoluteZ = worldZOffset + zLoop * this.horizontalNoiseGranularity + zSub;
								int localZ = absoluteZ & 15;
								double zLerpProgress = (double)zSub / (double)this.horizontalNoiseGranularity;
								double finalNoise = MathHelper.lerp(zLerpProgress, lerpX0, lerpX1);
								double clampedNoise = MathHelper.clamp(finalNoise / 200.0D, -1.0D, 1.0D);

								// Flatten/smooth the noise curve
								clampedNoise = clampedNoise / 2.0D - clampedNoise * clampedNoise * clampedNoise / 24.0D;

								// Modulate terrain noise based on nearby structures (e.g., flatten land under villages)
								// Extended Start (replaced the for with while loop)
								while(structureIterator.hasNext()) {
									StructurePiece piece = structureIterator.next();
									MutableBoundingBox box = piece.getBoundingBox();
									int distanceX = Math.max(0, Math.max(box.x0 - absoluteX, absoluteX - box.x1));
									int distanceY = absoluteY - (box.y0 + (piece instanceof AbstractVillagePiece ? ((AbstractVillagePiece)piece).getGroundLevelDelta() : 0));
									int distanceZ = Math.max(0, Math.max(box.z0 - absoluteZ, absoluteZ - box.z1));

									// Add structure weight to push density higher (solid ground) or lower (air)
									clampedNoise += getContribution(distanceX, distanceY, distanceZ) * 0.8D;
									// Extended End
								}
								structureIterator.back(intersectingStructures.size());

								// Modulate terrain noise based on jigsaw junctions
								while(junctionIterator.hasNext()) {
									JigsawJunction junction = junctionIterator.next();
									// Extended Start (move int fields into while)
									int distanceX = absoluteX - junction.getSourceX();
									int distanceY = absoluteY - junction.getSourceGroundY();
									int distanceZ = absoluteZ - junction.getSourceZ();
									// Extended End
									clampedNoise += getContribution(distanceX, distanceY, distanceZ) * 0.4D;
								}
								junctionIterator.back(intersectingJunctions.size());

								// Determine the block state and place it if it's solid
								BlockState blockstate = this.getDefaultStateForHeight(clampedNoise, absoluteY);
								if (blockstate != AIR) {
									mutablePos.set(absoluteX, absoluteY, absoluteZ);
									if (blockstate.getLightValue(chunkprimer, mutablePos) != 0) {
										chunkprimer.addLight(mutablePos);
									}
									currentSection.setBlockState(localX, localY, localZ, blockstate, false);
									oceanFloorHeightmap.update(localX, absoluteY, localZ, blockstate);
									worldSurfaceHeightmap.update(localX, absoluteY, localZ, blockstate);
								}
							}
						}
					}
				}

				currentSection.release();
			}

			// Swap the noise buffer columns for the next iteration step
			double[][] temporarySlice = noiseBuffer[0];
			noiseBuffer[0] = noiseBuffer[1];
			noiseBuffer[1] = temporarySlice;
		}

	}
}
