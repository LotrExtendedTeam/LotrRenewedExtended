package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.MouthsOfEntwashBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(MouthsOfEntwashBiome.class)
public class MixinMouthsOfEntwashBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = {
				ExtendedBiomeFeatures.willowTree(), 100,
				LOTRBiomeFeatures.oak(), 200, 
				LOTRBiomeFeatures.oakFancy(), 200, 
				LOTRBiomeFeatures.oakSwamp(), 500, 
				LOTRBiomeFeatures.oakShrub(), 4000 };
		LOTRBiomeFeatures.addTreesBelowTreeline((MouthsOfEntwashBiome)(Object)this, builder, 2, 0.5F, 63, weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((MouthsOfEntwashBiome)(Object)this, builder, 3, 0.5F, 64, weightedTrees);
		LOTRBiomeFeatures.addGrass((MouthsOfEntwashBiome)(Object)this, builder, 10, GrassBlends.MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 10, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addSwampFlowers(builder, 3);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
		LOTRBiomeFeatures.addWaterLiliesWithRareFlowers(builder, 2);
		LOTRBiomeFeatures.addSwampSeagrass(builder);
		LOTRBiomeFeatures.addFallenLogs(builder, 2);
	}
}
