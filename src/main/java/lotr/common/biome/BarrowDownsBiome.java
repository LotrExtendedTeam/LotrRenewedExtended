package lotr.common.biome;

import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.ExtendedGrassBlends;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

public class BarrowDownsBiome extends LOTRBiomeBase {
	public BarrowDownsBiome(boolean major) {
		this(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.PLAINS).depth(0.3F).scale(0.4F).temperature(0.6F).downfall(0.7F), major);
	}

	protected BarrowDownsBiome(Builder builder, boolean major) {
		super(builder, major);//OldFog 1514787
		biomeColors.setFoliage(5928279).setWater(672836).setGrass(7314486).setSky(9674385).setClouds(11842740).setFog(10197915).setFoggy(true);
	}

	@Override
	protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
		
	}

	@Override
	public Biome getRiver(IWorld world) {
		return LOTRBiomes.getBiomeByRegistryName(ExtendedBiomes.BARROW_DOWNS_RIVER.getRegistryName(), world);
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
		Object[] weightedTrees = { ExtendedBiomeFeatures.willowTree(), 10000, LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakVines(), 2000,
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

	public static class BarrowDownsRiverBiome extends BarrowDownsBiome {
		public BarrowDownsRiverBiome() {
			super(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.RIVER).depth(-0.75F).scale(0.0F).temperature(0.6F).downfall(0.7F), false);
		}

		@Override
		protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
			builder.addSpawn(EntityClassification.WATER_AMBIENT, new Spawners(EntityType.SALMON, 5, 1, 5));
		}

		@Override
		protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
			super.addReeds(builder);
			LOTRBiomeFeatures.addRiverRushes(builder);
		}

		@Override
		protected void addSedimentDisks(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
			LOTRBiomeFeatures.addClayGravelSediments(builder);
		}

		@Override
		protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
			Object[] weightedTrees = { LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakVines(), 2000,
					LOTRBiomeFeatures.oakFancy(), 2000, LOTRBiomeFeatures.oakDead(), 15000,
					LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.spruceDead(), 1000,
					LOTRBiomeFeatures.birch(), 1500, LOTRBiomeFeatures.birchDead(), 150 };
			LOTRBiomeFeatures.addTrees(this, builder, 0, 0.4F, weightedTrees);
			LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
			LOTRBiomeFeatures.addGrass(this, builder, 7, ExtendedGrassBlends.BARROW_DOWNS_GRASS);
			LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_MUTED);
			ExtendedBiomeFeatures.addMoss(builder, 2);
			LOTRBiomeFeatures.addSeagrass(builder, 48, 0.4F);
		}

		@Override
		public boolean isRiver() {
			return true;
		}
	}
}