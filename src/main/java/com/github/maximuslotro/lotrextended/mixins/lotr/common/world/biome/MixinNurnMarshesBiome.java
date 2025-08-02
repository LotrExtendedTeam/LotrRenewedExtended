package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.NurnBiome.Marshes;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(Marshes.class)
public class MixinNurnMarshesBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { 
				ExtendedBiomeFeatures.willowTree(), 1500,
				LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakFancy(), 100, 
				LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakDead(), 1500, 
				LOTRBiomeFeatures.oakShrub(), 6000 };
		LOTRBiomeFeatures.addTreesBelowTreeline((Marshes)(Object)this, builder, 2, 0.5F, 63, weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((Marshes)(Object)this, builder, 3, 0.5F, 64, weightedTrees);
		LOTRBiomeFeatures.addGrass((Marshes)(Object)this, builder, 10, GrassBlends.MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 10, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addSwampFlowers(builder, 2);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 4);
		LOTRBiomeFeatures.addSwampSeagrass(builder);
		LOTRBiomeFeatures.addFallenLogs(builder, 2);
	}
}
