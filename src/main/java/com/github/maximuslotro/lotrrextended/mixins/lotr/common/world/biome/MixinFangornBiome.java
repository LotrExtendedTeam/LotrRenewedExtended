package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.FangornBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(FangornBiome.class)
public class MixinFangornBiome {
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((FangornBiome)(Object)this, builder, 14, 0.1F,
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakBees(), 1, 
			LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakTallBees(), 1, 
			LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakFancyBees(), 1, 
			LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchBees(), 1, 
			LOTRBiomeFeatures.birchFancy(), 200, LOTRBiomeFeatures.birchFancyBees(), 1, 
			LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechBees(), 1, 
			LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechFancyBees(), 1, 
			LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100, 
			LOTRBiomeFeatures.darkOak(), 4000, LOTRBiomeFeatures.oakFangorn(), 300, 
			LOTRBiomeFeatures.beechFangorn(), 75, LOTRBiomeFeatures.oakShrub(), 600, 
			LOTRBiomeFeatures.darkOakShrub(), 600);
		LOTRBiomeFeatures.addGrass((FangornBiome)(Object)this, builder, 12, GrassBlends.WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 6);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 1);
		LOTRBiomeFeatures.addFallenLogs(builder, 3);
	}
}
