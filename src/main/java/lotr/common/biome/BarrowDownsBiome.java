package lotr.common.biome;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.ExtendedGrassBlends;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class BarrowDownsBiome extends LOTRBiomeBase {
	public BarrowDownsBiome(boolean major) {
		this(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.PLAINS).depth(0.3F).scale(0.4F).temperature(0.6F).downfall(0.7F), major);
	}

	protected BarrowDownsBiome(Builder builder, boolean major) {
		super(builder, major);//OldFog 10197915
		biomeColors.setFoliage(5928279).setWater(672836).setGrass(5928279).setSky(9674385).setClouds(11842740).setFog(1514787).setFoggy(true);
	}

	@Override
	protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
		//super.addAnimals(builder);
		//this.addHorsesDonkeys(builder);
	}

	@Override
	protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addBoulders(builder, Blocks.STONE.defaultBlockState(), 1, 2, 6, 1);
	}

	@Override
	protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addCommonGranite(builder);
	}

	@Override
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakVines(), 2000,
				LOTRBiomeFeatures.oakFancy(), 2000, LOTRBiomeFeatures.oakDead(), 15000, 
				LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.spruceDead(), 1000,
				LOTRBiomeFeatures.birch(), 1500, LOTRBiomeFeatures.birchDead(), 150 };
		LOTRBiomeFeatures.addTrees(this, builder, 0, 0.1F, weightedTrees);
		LOTRBiomeFeatures.addAthelasChance(builder);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
		LOTRBiomeFeatures.addGrass(this, builder, 7, ExtendedGrassBlends.BARROW_DOWNS_GRASS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_MUTED);
		ExtendedBiomeFeatures.addMoss(builder, 2);
	}

}