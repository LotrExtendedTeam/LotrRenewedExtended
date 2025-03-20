package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;


public class ExtendedVegetationPatchFeature extends Feature<ExtendedVegetationPatchConfig> {
	public ExtendedVegetationPatchFeature(Codec<ExtendedVegetationPatchConfig> p_160588_) {
		super(p_160588_);
	}

	@Override
	public boolean place(ISeedReader worldgenlevel, ChunkGenerator chunkGenerator, Random random, BlockPos blockpos, ExtendedVegetationPatchConfig vegetationpatchconfiguration) {
		Predicate<BlockState> predicate = (blockState) -> blockState.is(vegetationpatchconfiguration.replaceable);
		int i = vegetationpatchconfiguration.samplexzRadius(random) + 1;
		int j = vegetationpatchconfiguration.samplexzRadius(random) + 1;
		Set<BlockPos> set = this.placeGroundPatch(worldgenlevel, vegetationpatchconfiguration, random, blockpos, predicate, i, j);
		this.distributeVegetation(chunkGenerator, worldgenlevel, vegetationpatchconfiguration, random, set, i, j);
		return !set.isEmpty();
	}

	//(WorldGenLevel p_160597_, VegetationPatchConfiguration p_160598_, Random p_160599_, BlockPos p_160600_, Predicate<BlockState> predicate, int p_160602_, int p_160603_)
	@SuppressWarnings("deprecation")
	protected Set<BlockPos> placeGroundPatch(ISeedReader worldgenlevel, ExtendedVegetationPatchConfig vegetationpatchconfiguration, Random random, BlockPos pBlockpos, Predicate<BlockState> predicate, int pI, int pJ) {
		Mutable blockpos$mutableblockpos = pBlockpos.mutable();
		Mutable blockpos$mutableblockpos1 = blockpos$mutableblockpos.mutable();
		Direction direction = vegetationpatchconfiguration.surface.getDirection();
		Direction direction1 = direction.getOpposite();
		Set<BlockPos> set = new HashSet<>();
		for(int i = -pI; i <= pI; ++i) {
			boolean isXbounds = i == -pI || i == pI;
			for(int j = -pJ; j <= pJ; ++j) {
				boolean isZbounds = j == -pJ || j == pJ;
				boolean isSomeBounds = isXbounds || isZbounds;
				boolean isBothBounds = isXbounds && isZbounds;
				boolean isOnlyNotBothBounds = isSomeBounds && !isBothBounds;
				if (!isBothBounds && (!isOnlyNotBothBounds || vegetationpatchconfiguration.extraEdgeColumnChance != 0.0F && !(random.nextFloat() > vegetationpatchconfiguration.extraEdgeColumnChance))) {
					blockpos$mutableblockpos.setWithOffset(pBlockpos, i, 0, j);

					for(int k = 0; worldgenlevel.isStateAtPosition(blockpos$mutableblockpos, BlockState::isAir) && k < vegetationpatchconfiguration.verticalRange; ++k) {
						blockpos$mutableblockpos.move(direction);
					}

					for(int i1 = 0; worldgenlevel.isStateAtPosition(blockpos$mutableblockpos, (p_204784_) -> !p_204784_.isAir()) && i1 < vegetationpatchconfiguration.verticalRange; ++i1) {
						blockpos$mutableblockpos.move(direction1);
					}

					blockpos$mutableblockpos1.setWithOffset(blockpos$mutableblockpos, vegetationpatchconfiguration.surface.getDirection());
					BlockState blockstate = worldgenlevel.getBlockState(blockpos$mutableblockpos1);
					if (worldgenlevel.isEmptyBlock(blockpos$mutableblockpos) && blockstate.isFaceSturdy(worldgenlevel, blockpos$mutableblockpos1, vegetationpatchconfiguration.surface.getDirection().getOpposite())) {
						int l = vegetationpatchconfiguration.depth + (vegetationpatchconfiguration.extraBottomBlockChance > 0.0F && random.nextFloat() < vegetationpatchconfiguration.extraBottomBlockChance ? 1 : 0);
						BlockPos blockpos = blockpos$mutableblockpos1.immutable();
						boolean flag5 = this.placeGround(worldgenlevel, vegetationpatchconfiguration, predicate, random, blockpos$mutableblockpos1, l);
						if (flag5) {
							set.add(blockpos);
						}
					}
				}
			}
		}
		return set;
	}

	private void distributeVegetation(ChunkGenerator chunkGenerator, ISeedReader worldgenlevel, ExtendedVegetationPatchConfig config, Random random, Set<BlockPos> blockLists, int p_160619_, int p_160620_) {
		for(BlockPos blockpos : blockLists) {
			if (config.vegetationChance > 0.0F && random.nextFloat() < config.vegetationChance) {
				this.placeVegetation(worldgenlevel, config, chunkGenerator, random, blockpos);
			}
		}
	}

	private boolean placeVegetation(ISeedReader worldgenlevel, ExtendedVegetationPatchConfig config, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos) {
		return config.vegetationFeature.get().place(worldgenlevel, chunkGenerator, random, blockPos.relative(config.surface.getDirection().getOpposite()));
	}

	private boolean placeGround(ISeedReader worldgenlevel, ExtendedVegetationPatchConfig config, Predicate<BlockState> predicate, Random random, Mutable blockPosMutable, int p_160610_) {
		for(int i = 0; i < p_160610_; ++i) {
			BlockState blockstate = config.groundState.getState(random, blockPosMutable);
			BlockState blockstate1 = worldgenlevel.getBlockState(blockPosMutable);
			if (!blockstate.is(blockstate1.getBlock())) {
				if (!predicate.test(blockstate1) || !config.shouldUseBlock(random)) {
					return i != 0;
				}

				worldgenlevel.setBlock(blockPosMutable, blockstate, 2);
				blockPosMutable.move(config.surface.getDirection());
			}
		}

		return true;
	}
}