package lotr.common.biome;

import com.github.maximuslotro.lotrrextended.common.init.ExtendedBlocks;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedConfiguredFeatures;

import lotr.common.init.ExtendedEntities;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.RohanBiome;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

public class ExtendedAdornlandBiome extends LOTRBiomeBase {
	public ExtendedAdornlandBiome(boolean major) {
		this(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.PLAINS).depth(0.2F).scale(0.2F).temperature(0.7F).downfall(0.6F), major);
	}

	protected ExtendedAdornlandBiome(Builder builder, boolean major) {
		super(builder, major);
		biomeColors.setFoliage(7838543).setGrass(7838543);
	}
	@Override
	protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
		super.addAnimals(builder);
		this.addHorsesDonkeys(builder, 6);
		this.addBears(builder);
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.CRABAN.get(), 10, 2, 4));
	}

	@Override
	protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		ExtendedBiomeFeatures.addScorchedLand(builder, ExtendedConfiguredFeatures.URUK_HIGHLAND_SCORCHED_LAND, 200);
		ExtendedBiomeFeatures.addBoulders(builder, ExtendedBlocks.WASTE_BLOCK.get().defaultBlockState(), 1, 3, 256, 3, 3);
		ExtendedBiomeFeatures.addBoulders(builder, LOTRBlocks.ROHAN_ROCK.get().defaultBlockState(), 1, 4, 64, 3, 3);
		ExtendedBiomeFeatures.addBoulders(builder, Blocks.STONE.defaultBlockState(), 1, 4, 64, 3, 3);
	}

	@Override
	protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addCommonGranite(builder);
		LOTRBiomeFeatures.addRohanRockPatches(builder);
	}

	@Override
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.01F, TreeCluster.of(13, 30), new Object[] {
				ExtendedBiomeFeatures.willowTree(), 8000,
				LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakFancy(), 3000, 
				LOTRBiomeFeatures.oakBees(), 30, LOTRBiomeFeatures.oakFancyBees(), 30, 
				LOTRBiomeFeatures.spruce(), 3000, 
				LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchFancy(), 100, 
				LOTRBiomeFeatures.birchBees(), 2, LOTRBiomeFeatures.birchFancyBees(), 1, 
				LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechFancy(), 100, 
				LOTRBiomeFeatures.beechBees(), 2, LOTRBiomeFeatures.beechFancyBees(), 1,
				ExtendedBiomeFeatures.chestnut(), 500, ExtendedBiomeFeatures.chestnutFancy(), 100,
				ExtendedBiomeFeatures.chestnutBees(), 5, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.fir(), 3000, LOTRBiomeFeatures.pine(), 3000, 
				LOTRBiomeFeatures.apple(), 20, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pear(), 20, LOTRBiomeFeatures.pearBees(), 1 });
		LOTRBiomeFeatures.addGrass(this, builder, 12, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 2);
		LOTRBiomeFeatures.addSimbelmyneChance(builder, 120);
	}

	@Override
	public RoadBlockProvider getRoadBlockProvider() {
		return RoadBlockProvider.ROHAN;
	}

	@Override
	protected void setupSurface(MiddleEarthSurfaceConfig config) {
		config.addSubSoilLayer(LOTRBlocks.ROHAN_ROCK.get().defaultBlockState(), 8, 10);
	}
}