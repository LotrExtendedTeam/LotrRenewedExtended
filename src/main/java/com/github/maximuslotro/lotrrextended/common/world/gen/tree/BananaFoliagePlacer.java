package com.github.maximuslotro.lotrrextended.common.world.gen.tree;

import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class BananaFoliagePlacer extends FoliagePlacer {
	public static final Codec<BananaFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> foliagePlacerParts(instance).and(FeatureSpread.codec(0, 16, 8).fieldOf("trunk_height").forGetter(foliage -> foliage.trunkHeightSpread)).apply(instance, BananaFoliagePlacer::new));

	private final FeatureSpread trunkHeightSpread;

	public BananaFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread trunkHeightSpread) {
		super(radius, offset);
		this.trunkHeightSpread = trunkHeightSpread;
	}

	@Override
	protected void createFoliage(IWorldGenerationReader world, Random rand, BaseTreeFeatureConfig config, int p_230372_4_, Foliage foliage, int foliageHeight, int foliageMaxWidth, Set leaves, int foliageOffset, MutableBoundingBox bb) {

	}

	@Override
	public int foliageHeight(Random rand, int trunkHeight, BaseTreeFeatureConfig config) {
		return Math.max(4, trunkHeight - trunkHeightSpread.sample(rand));
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
	protected FoliagePlacerType type() {
		return null;
	}
}
