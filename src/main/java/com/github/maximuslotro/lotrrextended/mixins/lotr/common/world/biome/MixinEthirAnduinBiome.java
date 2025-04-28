package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.EthirAnduinBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(EthirAnduinBiome.class)
public class MixinEthirAnduinBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = { 
				ExtendedBiomeFeatures.willowTree(), 500,
				LOTRBiomeFeatures.oak(), 100, LOTRBiomeFeatures.oakFancy(), 100, 
				LOTRBiomeFeatures.oakSwamp(), 300, LOTRBiomeFeatures.birch(), 600, 
				LOTRBiomeFeatures.cypress(), 1200, LOTRBiomeFeatures.oakShrub(), 3000 };
		LOTRBiomeFeatures.addTreesBelowTreeline((EthirAnduinBiome)(Object)this, builder, 4, 0.5F, 63, weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((EthirAnduinBiome)(Object)this, builder, 3, 0.5F, 64, weightedTrees);
		LOTRBiomeFeatures.addGrass((EthirAnduinBiome)(Object)this, builder, 10, GrassBlends.MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 8, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
		LOTRBiomeFeatures.addSwampFlowers(builder, 4, LOTRBlocks.MALLOS.get(), 4);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 3);
		LOTRBiomeFeatures.addSwampSeagrass(builder);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
		LOTRBiomeFeatures.addAthelasChance(builder);
	}
}
