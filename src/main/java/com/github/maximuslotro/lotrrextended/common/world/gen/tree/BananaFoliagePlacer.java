package com.github.maximuslotro.lotrrextended.common.world.gen.tree;

import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class BananaFoliagePlacer extends ExtendedFoliagePlacer {
	public static final Codec<BananaFoliagePlacer> CODEC = RecordCodecBuilder.create(instance ->
	foliagePlacerParts(instance).and(FeatureSpread.codec(0, 8, 5).fieldOf("leaves_height").forGetter(foliage -> foliage.leavesHeight)).apply(instance, BananaFoliagePlacer::new));

	private final FeatureSpread leavesHeight;

	public BananaFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread trunkHeight) {
		super(radius, offset);
		this.leavesHeight = trunkHeight;
	}

	@Override
	protected void createFoliage(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int maxFreeTreeheight, Foliage leaf, int leavesHeight, int leavesRadius, Set<BlockPos> leaves, int leavesOffset, MutableBoundingBox box) {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		for(Direction dir : Direction.Plane.HORIZONTAL) {
			int actualHeight = rand.nextInt(leavesHeight);
			int radius = 1;
			int finalYValue = 0;
			for(int y=(rand.nextInt(2)==0? -1 : -2); y<actualHeight&& radius<=leavesRadius; y++) {
				int xStep = dir.getStepX()*radius;
				int zStep = dir.getStepZ()*radius;
				blockpos$mutable.setWithOffset(leaf.foliagePos(), xStep, y, zStep);
				doPlaceLeafBlock(world, rand, config, blockpos$mutable, leaves, box);

				blockpos$mutable.setWithOffset(leaf.foliagePos(), xStep, y+1, zStep);
				doPlaceLeafBlock(world, rand, config, blockpos$mutable, leaves, box);

				if(rand.nextInt(3)==0) {
					blockpos$mutable.setWithOffset(leaf.foliagePos(), xStep, y-1, zStep);
					doPlaceAdditionBlock(world, rand, config, blockpos$mutable, leaves, box, ExtendedBiomeFeatures.BANANA_FRUIT);
				}

				radius+=1;
				finalYValue=y;
			}
			if(finalYValue>=actualHeight||!(radius>leavesRadius)) {
				blockpos$mutable.setWithOffset(leaf.foliagePos(), dir.getStepX()*radius, finalYValue+1, dir.getStepZ()*radius);
				doPlaceLeafBlock(world, rand, config, blockpos$mutable, leaves, box);
			}
		}
	}

	@Override
	public int foliageHeight(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
		return this.leavesHeight.sample(rand);
	}

	@Override
	public int foliageRadius(Random rand, int trunkHeight) {
		return super.foliageRadius(rand, trunkHeight);
	}

	@Override
	protected boolean shouldSkipLocation(Random rand, int absX, int layerY, int absZ, int layerWidth, boolean bool6) {
		return false;
	}

	@Override
	protected FoliagePlacerType<BananaFoliagePlacer> type() {
		return ExtendedFoilagePlacers.BANANA_FOLIAGE;
	}
}
