package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.BitSet;
import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedBlockStateUtils;
import com.mojang.serialization.Codec;

import lotr.common.block.AxialSlabBlock;
import lotr.common.block.LOTRBlockStates;
import lotr.common.block.VerticalOnlySlabBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;

/**
 * Modified version of {@link OreFeature}
 * Adds support for slab/stair rule tests for stone replacing
 */
public class ExtendedBlobFeature extends Feature<ExtendedBlobFeatureConfig> {
	public ExtendedBlobFeature(Codec<ExtendedBlobFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos originPos, ExtendedBlobFeatureConfig config) {
		float angle = rand.nextFloat() * (float)Math.PI;
		float horizontalRange = (float)config.size / 8.0F;
		int featureRadius = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
		double xStart = (double)originPos.getX() + Math.sin((double)angle) * (double)horizontalRange;
		double xEnd = (double)originPos.getX() - Math.sin((double)angle) * (double)horizontalRange;
		double zStart = (double)originPos.getZ() + Math.cos((double)angle) * (double)horizontalRange;
		double zEnd = (double)originPos.getZ() - Math.cos((double)angle) * (double)horizontalRange;
		int verticalPadding = 2;
		double yStart = (double)(originPos.getY() + rand.nextInt(3) - 2);
		double yEnd = (double)(originPos.getY() + rand.nextInt(3) - 2);
		int xMin = originPos.getX() - MathHelper.ceil(horizontalRange) - featureRadius;
		int yMin = originPos.getY() - 2 - featureRadius;
		int zMin = originPos.getZ() - MathHelper.ceil(horizontalRange) - featureRadius;
		int xzSize = 2 * (MathHelper.ceil(horizontalRange) + featureRadius);
		int ySize = 2 * (2 + featureRadius);

		for(int x = xMin; x <= xMin + xzSize; ++x) {
			for(int z = zMin; z <= zMin + xzSize; ++z) {
				if (yMin <= world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z)) {
					return this.doPlace(world, rand, config, xStart, xEnd, zStart, zEnd, yStart, yEnd, xMin, yMin, zMin, xzSize, ySize);
				}
			}
		}

		return false;
	}

	protected boolean doPlace(IWorld world, Random rand, ExtendedBlobFeatureConfig config, double xStart, double xEnd, double zStart, double zEnd, double yStart, double yEnd, int xMin, int yMin, int zMin, int xzSize, int ySize) {
		int blocksPlaced = 0;
		BitSet bitset = new BitSet(xzSize * ySize * xzSize);
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		int totalBlobs = config.size;
		double[] blobData = new double[totalBlobs * 4];

		for(int blobIndex = 0; blobIndex < totalBlobs; ++blobIndex) {
			float blobFraction = (float)blobIndex / (float)totalBlobs;
			double xPos = MathHelper.lerp((double)blobFraction, xStart, xEnd);
			double yPos = MathHelper.lerp((double)blobFraction, yStart, yEnd);
			double zPos = MathHelper.lerp((double)blobFraction, zStart, zEnd);
			double blobRadiusBase = rand.nextDouble() * (double)totalBlobs / 16.0D;
			double blobRadius = ((double)(MathHelper.sin((float)Math.PI * blobFraction) + 1.0F) * blobRadiusBase + 1.0D) / 2.0D;
			blobData[blobIndex * 4 + 0] = xPos;
			blobData[blobIndex * 4 + 1] = yPos;
			blobData[blobIndex * 4 + 2] = zPos;
			blobData[blobIndex * 4 + 3] = blobRadius;
		}

		for(int i = 0; i < totalBlobs - 1; ++i) {
			if (!(blobData[i * 4 + 3] <= 0.0D)) {
				for(int k3 = i + 1; k3 < totalBlobs; ++k3) {
					if (!(blobData[k3 * 4 + 3] <= 0.0D)) {
						double dx = blobData[i * 4 + 0] - blobData[k3 * 4 + 0];
						double dy = blobData[i * 4 + 1] - blobData[k3 * 4 + 1];
						double dz = blobData[i * 4 + 2] - blobData[k3 * 4 + 2];
						double dr = blobData[i * 4 + 3] - blobData[k3 * 4 + 3];
						if (dr * dr > dx * dx + dy * dy + dz * dz) {
							if (dr > 0.0D) {
								blobData[k3 * 4 + 3] = -1.0D;
							} else {
								blobData[i * 4 + 3] = -1.0D;
							}
						}
					}
				}
			}
		}

		for(int i = 0; i < totalBlobs; ++i) {
			double radius = blobData[i * 4 + 3];
			if (!(radius < 0.0D)) {
				double xCenter = blobData[i * 4 + 0];
				double yCenter = blobData[i * 4 + 1];
				double zCenter = blobData[i * 4 + 2];
				int xInnerStart = Math.max(MathHelper.floor(xCenter - radius), xMin);
				int yInnerStart = Math.max(MathHelper.floor(yCenter - radius), yMin);
				int zInnerStart = Math.max(MathHelper.floor(zCenter - radius), zMin);
				int xInnerEnd = Math.max(MathHelper.floor(xCenter + radius), xInnerStart);
				int yInnerEnd = Math.max(MathHelper.floor(yCenter + radius), yInnerStart);
				int zInnerEnd = Math.max(MathHelper.floor(zCenter + radius), zInnerStart);

				for(int x = xInnerStart; x <= xInnerEnd; ++x) {
					double normalizedX = ((double)x + 0.5D - xCenter) / radius;
					if (normalizedX * normalizedX < 1.0D) {
						for(int y = yInnerStart; y <= yInnerEnd; ++y) {
							double normalizedY = ((double)y + 0.5D - yCenter) / radius;
							if (normalizedX * normalizedX + normalizedY * normalizedY < 1.0D) {
								for(int z = zInnerStart; z <= zInnerEnd; ++z) {
									double normalizedZ = ((double)z + 0.5D - zCenter) / radius;
									if (normalizedX * normalizedX + normalizedY * normalizedY + normalizedZ * normalizedZ < 1.0D) {
										int bitsetIndex = x - xMin + (y - yMin) * xzSize + (z - zMin) * xzSize * ySize;
										if (!bitset.get(bitsetIndex)) {
											bitset.set(bitsetIndex);
											blockpos$mutable.set(x, y, z);
											BlockState state = world.getBlockState(blockpos$mutable);
											if (config.targetBlock.test(state, rand)) {
												world.setBlock(blockpos$mutable, config.stateBlock, 2);
												++blocksPlaced;
												continue;
											}else if (config.targetSlab.test(state, rand)) {
												// Try to preserve the verticality of the existing block VerticalOnlySlabBlock
												if(state.getBlock() instanceof VerticalOnlySlabBlock && !(config.stateSlab.getBlock() instanceof AxialSlabBlock)) {
													VerticalOnlySlabBlock vanilla$crossover = VerticalOnlySlabBlock.getVerticalSlabFor((SlabBlock) config.stateSlab.getBlock());
													if(vanilla$crossover==null) {
														continue;
													}
													// No need for special copying since they are both VerticalOnlySlabBlock
													world.setBlock(blockpos$mutable, ExtendedBlockStateUtils.copyBlockStateToBlock(vanilla$crossover, state), 2);
												// Try to preserve the verticality of the existing block AxialSlabBlock that is vertical
												} else if(state.getBlock() instanceof AxialSlabBlock && !(config.stateSlab.getBlock() instanceof AxialSlabBlock) && state.getValue(LOTRBlockStates.SLAB_AXIS) != Axis.Y) {
													VerticalOnlySlabBlock vanilla$crossover = VerticalOnlySlabBlock.getVerticalSlabFor((SlabBlock) config.stateSlab.getBlock());
													if(vanilla$crossover==null) {
														continue;
													}
													// Also use this copy method so the AXIS can be converted to HORIZONTAL_AXIS
													world.setBlock(blockpos$mutable, ExtendedBlockStateUtils.copyBlockStateToBlockStateOverwriteSlab(vanilla$crossover.defaultBlockState(), state), 2);
												// Try to preserve the vertical nature of the replacing axial block (in theory this should not ever be used)
												} else if(!(state.getBlock() instanceof AxialSlabBlock) && config.stateSlab.getBlock() instanceof AxialSlabBlock && config.stateSlab.getValue(LOTRBlockStates.SLAB_AXIS) != Axis.Y) {
													VerticalOnlySlabBlock vanilla$crossover = VerticalOnlySlabBlock.getVerticalSlabFor((SlabBlock) state.getBlock());
													if(vanilla$crossover==null) {
														continue;
													}
													// No need for secondary blockstate copy since both are in the Y-axis, only copy SlabBlock info
													world.setBlock(blockpos$mutable, ExtendedBlockStateUtils.copyBlockStateToBlock(vanilla$crossover, state), 2);
												// Just copy existing properties over if existing
												} else {
													// Also use this copy method so the HORIZONTAL_AXIS can be converted to AXIS
													world.setBlock(blockpos$mutable, ExtendedBlockStateUtils.copyBlockStateToBlockStateOverwriteSlab(config.stateSlab, state), 2);
												}
												++blocksPlaced;
												continue;
											}else if (config.targetStair.test(state, rand)) {
												world.setBlock(blockpos$mutable, ExtendedBlockStateUtils.copyBlockStateToBlockState(config.stateStair, state), 2);
												++blocksPlaced;
												continue;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return blocksPlaced > 0;
	}
}