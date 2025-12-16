package lotr.common.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.RivendellBiome;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.ExtendedGrassBlends;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

public class ExtendedRivendellBiome extends RivendellBiome {
	public ExtendedRivendellBiome(boolean major) {
		// Valley biome is hiller than the regular biome (depth 0.2 vs 0.12) (scale 0.25 vs 0.2)
		this(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.PLAINS).depth(0.2F).scale(0.25F).temperature(0.7F).downfall(1.0F), major);
	}

	protected ExtendedRivendellBiome(Builder builder, boolean major) {
		super(builder, major);
	}

	@Override
	protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
	}

	@Override
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = {
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 2000,
				LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 20,
				LOTRBiomeFeatures.birch(), 5000, LOTRBiomeFeatures.birchFancy(), 1000,
				LOTRBiomeFeatures.birchBees(), 50, LOTRBiomeFeatures.birchFancyBees(), 10,
				LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200,
				LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2,
				LOTRBiomeFeatures.larch(), 1500, 
				LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 500, 
				LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.pearBees(), 1,
				// Extended Trees
				ExtendedBiomeFeatures.chestnut(), 100, ExtendedBiomeFeatures.chestnutBees(), 5,
				ExtendedBiomeFeatures.chestnutFancy(), 100, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1,
				ExtendedBiomeFeatures.willowTree(), 5000
		};
		LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.25F, TreeCluster.of(12, 16), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreelineIncrease(this, builder, 2, 0.2F, 2, 85, weightedTrees);
		LOTRBiomeFeatures.addGrass(this, builder, 20, ExtendedGrassBlends.STANDARD_WITH_MOORS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 5);
	}

	public static class ExtendedRivendellValleyBiome extends ExtendedRivendellBiome {
		public ExtendedRivendellValleyBiome() {
			// Valley biome is flatter than the base biome (scale 0.2 vs 0.6)
			super(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.PLAINS).depth(0.12F).scale(0.2F).temperature(0.7F).downfall(1.0F), false);
		}

		@Override
		protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		}

		@Override
		protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
			Object[] weightedTrees = {
					LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 2000,
					LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 20,
					LOTRBiomeFeatures.birch(), 5000, LOTRBiomeFeatures.birchFancy(), 1000,
					LOTRBiomeFeatures.birchBees(), 50, LOTRBiomeFeatures.birchFancyBees(), 10,
					LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200,
					LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2,
					LOTRBiomeFeatures.larch(), 1500, 
					LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 500, 
					LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.appleBees(), 1,
					LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.pearBees(), 1,
					// Extended Trees
					ExtendedBiomeFeatures.chestnut(), 100, ExtendedBiomeFeatures.chestnutBees(), 5,
					ExtendedBiomeFeatures.chestnutFancy(), 100, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
					ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1,
					ExtendedBiomeFeatures.willowTree(), 5000
			};
			LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.25F, TreeCluster.of(12, 16), weightedTrees);
			LOTRBiomeFeatures.addTreesAboveTreelineIncrease(this, builder, 2, 0.2F, 2, 85, weightedTrees);
			LOTRBiomeFeatures.addGrass(this, builder, 20, ExtendedGrassBlends.STANDARD_WITH_MOORS);
			LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
			LOTRBiomeFeatures.addPlainsFlowers(builder, 5);
		}

		@Override
		public Biome getRiver(IWorld world) {
			return null;
		}
	}

	public static class ExtendedRivendellCliffsBiome extends ExtendedRivendellBiome {
		public ExtendedRivendellCliffsBiome() {
			// Make steeper than regular hills (depth 4.2 vs 3.7, scale 0.5 vs 1.0)
			super(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.EXTREME_HILLS).depth(4.2F).scale(0.5F).temperature(0.6F).downfall(0.9F), false);
		}

		@Override
		protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
			super.addAnimals(builder);
		}

		@Override
		protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
			LOTRBiomeFeatures.addWaterSprings(builder, 80);
		}

		@Override
		protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
			Object[] weightedTrees = { 
					LOTRBiomeFeatures.pine(), 5000, LOTRBiomeFeatures.pineShrub(), 3000, 
					LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.spruce(), 2000, 
					LOTRBiomeFeatures.larch(), 2000, LOTRBiomeFeatures.aspen(), 1000, 
					LOTRBiomeFeatures.aspenLarge(), 500, LOTRBiomeFeatures.oak(), 1000, 
					LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.oakBees(), 10, 
					LOTRBiomeFeatures.oakFancyBees(), 5, LOTRBiomeFeatures.birch(), 1000, 
					LOTRBiomeFeatures.birchFancy(), 500, LOTRBiomeFeatures.birchBees(), 10, 
					LOTRBiomeFeatures.birchFancyBees(), 5 };
			LOTRBiomeFeatures.addTreesIncrease(this, builder, 7, 0.3F, 3, weightedTrees);
			LOTRBiomeFeatures.addGrass(this, builder, 10, ExtendedGrassBlends.STANDARD_WITH_MOORS);
			LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
			LOTRBiomeFeatures.addMountainsFlowers(builder, 2);
		}

		@Override
		protected void setupSurface(MiddleEarthSurfaceConfig config) {
			super.setupSurface(config);
			config.setFillerDepth(0.0D);
			config.addSubSoilLayer(LOTRBlocks.DIRTY_CHALK.get().defaultBlockState(), 1);
			config.addSubSoilLayer(LOTRBlocks.CHALK.get().defaultBlockState(), 5);
		}
	}

	public static class ExtendedRivendellRiverBiome extends ExtendedRivendellBiome {
		public ExtendedRivendellRiverBiome() {
			super(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.RIVER).depth(-0.65F).scale(0.0F).temperature(0.6F).downfall(1.0F), false);
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
			LOTRBiomeFeatures.addSandSediments(builder);
			LOTRBiomeFeatures.addClayGravelSediments(builder);
		}

		// Entirely custom combination of river biome and rivendell biome features, with fruit trees having a high chance
		@Override
		protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
			Object[] weightedTrees = {
					LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 200,
					LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 20,
					LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 100,
					LOTRBiomeFeatures.birchBees(), 50, LOTRBiomeFeatures.birchFancyBees(), 10,
					LOTRBiomeFeatures.beech(), 50, LOTRBiomeFeatures.beechFancy(), 20,
					LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2,
					LOTRBiomeFeatures.larch(), 150, 
					LOTRBiomeFeatures.aspen(), 100, LOTRBiomeFeatures.aspenLarge(), 50, 
					LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.appleBees(), 1,
					LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.pearBees(), 1,
					// Extended Trees
					ExtendedBiomeFeatures.chestnut(), 10, ExtendedBiomeFeatures.chestnutBees(), 5,
					ExtendedBiomeFeatures.chestnutFancy(), 10, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
					ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1,
					ExtendedBiomeFeatures.willowTree(), 250
			};			
			LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.4F, TreeCluster.of(10, 20), weightedTrees);
			LOTRBiomeFeatures.addGrass(this, builder, 6, ExtendedGrassBlends.STANDARD_WITH_MOORS);
			LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_MUTED);
			LOTRBiomeFeatures.addPlainsFlowers(builder, 5);
			LOTRBiomeFeatures.addSeagrass(builder, 48, 0.4F);
		}

		@Override
		public boolean isRiver() {
			return true;
		}
	}
}