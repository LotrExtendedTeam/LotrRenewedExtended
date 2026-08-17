package lotr.common.biome;

import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedConfiguredFeatures;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.FangornBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.BiomeGenerationSettings;

public class ExtendedFangornWastelandBiome extends FangornBiome implements ExtendedBiomeSetter {

	public ExtendedFangornWastelandBiome(boolean major) {
		super(major);
		this.biomeColors.setGrass(6782028).resetFog();
	}

	@Override
	public Builder getExtendedBuilder() {
		return new Builder().precipitation(RainType.RAIN).biomeCategory(Category.PLAINS).depth(0.2F).scale(0.4F).temperature(0.7F).downfall(0.4F);
	}

	@Override
	protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.CRABAN.get(), 1, 1, 1));
	}

	@Override
	protected void addVegetation(final BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees(this, builder, 1, 0.25f, new Object[]{LOTRBiomeFeatures.charred(), 5000,
				LOTRBiomeFeatures.oakDead(), 3000, LOTRBiomeFeatures.beechDead(), 1000, 
				LOTRBiomeFeatures.birchDead(), 200, ExtendedBiomeFeatures.charredFangorn(), 100,
				ExtendedBiomeFeatures.deadOakFangorn(), 60, ExtendedBiomeFeatures.deadBeechFangorn(), 20,
				ExtendedBiomeFeatures.deadBirchFangorn(), 1});
		LOTRBiomeFeatures.addGrass(this, builder, 5, GrassBlends.WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 1, new Object[0]);
		LOTRBiomeFeatures.addFallenLogs(builder, 2);
	}

	@Override
	protected void addBoulders(final BiomeGenerationSettings.Builder builder) {
		ExtendedBiomeFeatures.addScorchedLand(builder, ExtendedConfiguredFeatures.FANGORN_WASTELAND_SCORCHED_LAND, 60);
	}
}