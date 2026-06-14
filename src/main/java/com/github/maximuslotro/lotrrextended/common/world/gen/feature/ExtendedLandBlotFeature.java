package com.github.maximuslotro.lotrrextended.common.world.gen.feature;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SkullBlock;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class ExtendedLandBlotFeature extends Feature<ExtendedLandBlotFeatureConfig> {
	public ExtendedLandBlotFeature(Codec<ExtendedLandBlotFeatureConfig> pCodec) {
		super(pCodec);
	}

	@Override
	public boolean place(ISeedReader worldgenlevel, ChunkGenerator chunkGenerator, Random pRandom, BlockPos pBlockpos, ExtendedLandBlotFeatureConfig config) {
		BlockPos centerPos = pBlockpos.below();
		BlockState placeState = worldgenlevel.getBlockState(centerPos);
		if(!config.canReplace.test(placeState, pRandom)) {
			return false;
		}
		int radius = config.radius.sample(pRandom);
		BlockPos.Mutable pos$Mutable = new BlockPos.Mutable();
		for (int dx = -radius; dx <= radius; dx++) {
			for (int dy = -radius; dy <= radius; dy++) {
				for (int dz = -radius; dz <= radius; dz++) {
					pos$Mutable.set(centerPos.getX() + dx, centerPos.getY() + dy, centerPos.getZ() + dz);
					placeState = worldgenlevel.getBlockState(pos$Mutable);
					if(!config.canReplace.test(placeState, pRandom)) {
						continue;
					}
					double radiusCubed = Math.sqrt(dx*dx + dy*dy + dz*dz);
					int chance = Math.max(1, (int) (radiusCubed/config.replaceChance));
					if(pRandom.nextInt(chance)==0) {
						worldgenlevel.setBlock(pos$Mutable, config.placementState, 2);
					}
					if(config.fire && radiusCubed < (radius/config.fireRange) && pRandom.nextInt(config.fireChance)==0 && canReplace(worldgenlevel, pos$Mutable.above())) {
						worldgenlevel.setBlock(pos$Mutable, config.placementState, 2);
						worldgenlevel.setBlock(pos$Mutable.above(), Blocks.FIRE.defaultBlockState(), 2);
					}else if(config.skull && pRandom.nextInt(config.skullChance)==0 && canReplace(worldgenlevel, pos$Mutable.above())) {
						worldgenlevel.setBlock(pos$Mutable, config.placementState, 2);
						worldgenlevel.setBlock(pos$Mutable.above(), Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, pRandom.nextInt(16)), 2);
					}
				}
			}
		}
		return true;
	}

	public static boolean canReplace(ISeedReader worldgenlevel, BlockPos pos) {
		if(worldgenlevel.isEmptyBlock(pos) || !worldgenlevel.getBlockState(pos).getMaterial().blocksMotion()) {
			return true;
		}else {
			return false;
		}
	}
}