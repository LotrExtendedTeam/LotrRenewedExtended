package lotr.common.biome;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedConfiguredFeatures;

import lotr.common.init.ExtendedBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.RohanBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.BiomeGenerationSettings;

public class ExtendedUrukHighlandsBiome extends RohanBiome {
	public ExtendedUrukHighlandsBiome(boolean major) {
		this(new Builder().precipitation(RainType.RAIN).biomeCategory(Category.PLAINS).depth(0.8F).scale(0.3F).temperature(0.7F).downfall(0.6F), major);
	}

	protected ExtendedUrukHighlandsBiome(Builder builder, boolean major) {
		super(builder, major);
		biomeColors.setFoliage(8295258).setGrass(8295258);
	}

	@Override
	public Biome getRiver(IWorld world) {
		return null;
	}

	@Override
	protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		super.addBoulders(builder);
		ExtendedBiomeFeatures.addScorchedLand(builder, ExtendedConfiguredFeatures.URUK_HIGHLAND_SCORCHED_LAND, 40);
		ExtendedBiomeFeatures.addBoulders(builder, ExtendedBlocks.WASTE_BLOCK.get().defaultBlockState(), 1, 3, 32, 3, 3);

	}

	@Override
	protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addCommonGranite(builder);
	}

	@Override
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.01F, TreeCluster.of(8, 32), rohanTreesNew());
		LOTRBiomeFeatures.addTreesWithClusters(this, builder, 1, 0.01F, TreeCluster.of(0, -1), deadTrees());
		LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
		LOTRBiomeFeatures.addSimbelmyneChance(builder, 60);
	}

	private final Object[] rohanTreesNew() {
		return new Object[] {
				LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakFancy(), 8000, 
				LOTRBiomeFeatures.oakBees(), 40, LOTRBiomeFeatures.oakFancyBees(), 80, 
				LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchFancy(), 100, 
				LOTRBiomeFeatures.birchBees(), 2, LOTRBiomeFeatures.birchFancyBees(), 1, 
				LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechFancy(), 100, 
				LOTRBiomeFeatures.beechBees(), 2, LOTRBiomeFeatures.beechFancyBees(), 1, 
				LOTRBiomeFeatures.pine(), 200, LOTRBiomeFeatures.apple(), 20, 
				LOTRBiomeFeatures.pear(), 20, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pearBees(), 1 };
	}

	private final Object[] deadTrees() {
		return new Object[] {LOTRBiomeFeatures.oakDead(), 1, LOTRBiomeFeatures.charred(), 3};
	}

	@Override
	protected void addStructures(final BiomeGenerationSettings.Builder builder) {}
}