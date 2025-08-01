package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LongMarshesBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(LongMarshesBiome.class)
public class MixinLongMarshesBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { 
				ExtendedBiomeFeatures.willowTree(), 250,
				LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100, 
				LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakShrub(), 3000, 
				LOTRBiomeFeatures.oakDead(), 500, LOTRBiomeFeatures.spruce(), 500, 
				LOTRBiomeFeatures.spruceDead(), 500, LOTRBiomeFeatures.fir(), 200, 
				LOTRBiomeFeatures.pine(), 200 };
		LOTRBiomeFeatures.addTreesBelowTreeline((LongMarshesBiome)(Object)this, builder, 1, 0.5F, 63, weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((LongMarshesBiome)(Object)this, builder, 3, 0.5F, 64, weightedTrees);
		LOTRBiomeFeatures.addGrass((LongMarshesBiome)(Object)this, builder, 6, GrassBlends.MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 12, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addSwampFlowers(builder, 2);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
		LOTRBiomeFeatures.addWaterLilies(builder, 4);
		LOTRBiomeFeatures.addSwampSeagrass(builder);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
	}
}
