package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.GladdenFieldsBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(GladdenFieldsBiome.class)
public class MixinGladdenFieldsBiome {
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
				LOTRBiomeFeatures.oakSwamp(), 1000, LOTRBiomeFeatures.oakShrub(), 4000, 
				LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 100, 
				LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.larch(), 500 };
		LOTRBiomeFeatures.addTreesBelowTreeline((GladdenFieldsBiome)(Object)this, builder, 2, 0.5F, 63, weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((GladdenFieldsBiome)(Object)this, builder, 3, 0.5F, 64, weightedTrees);
		LOTRBiomeFeatures.addGrass((GladdenFieldsBiome)(Object)this, builder, 8, GrassBlends.MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 8, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addSwampFlowers(builder, 2);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 7, LOTRBlocks.YELLOW_IRIS.get(), 40);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
		LOTRBiomeFeatures.addWaterLiliesWithFlowers(builder, 5);
		LOTRBiomeFeatures.addSwampSeagrass(builder);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
	}
}
