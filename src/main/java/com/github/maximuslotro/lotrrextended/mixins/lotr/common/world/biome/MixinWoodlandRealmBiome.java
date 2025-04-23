package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.WoodlandRealmBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(WoodlandRealmBiome.class)
public class MixinWoodlandRealmBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((WoodlandRealmBiome)(Object)this, builder, 2, 0.33F, TreeCluster.of(16, 10),
				ExtendedBiomeFeatures.willowTree(), 2500,
				LOTRBiomeFeatures.greenOak(), 5000, LOTRBiomeFeatures.greenOakBees(), 5,
				LOTRBiomeFeatures.greenOakParty(), 800, LOTRBiomeFeatures.redOak(), 400,
				LOTRBiomeFeatures.redOakBees(), 5, LOTRBiomeFeatures.redOakParty(), 100,
				LOTRBiomeFeatures.greenOakShrub(), 8000, LOTRBiomeFeatures.oak(), 500,
				LOTRBiomeFeatures.oakBees(), 1, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakFancyBees(), 1, LOTRBiomeFeatures.spruce(), 1000,
				ExtendedBiomeFeatures.chestnut(), 500, ExtendedBiomeFeatures.chestnutBees(), 1,
				ExtendedBiomeFeatures.chestnutFancy(), 500, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechBees(), 1,
				LOTRBiomeFeatures.beechFancy(), 1000, LOTRBiomeFeatures.beechFancyBees(), 1,
				LOTRBiomeFeatures.larch(), 500, LOTRBiomeFeatures.fir(), 1000,
				LOTRBiomeFeatures.pine(), 500, LOTRBiomeFeatures.aspen(), 500,
				LOTRBiomeFeatures.aspenLarge(), 100);
		LOTRBiomeFeatures.addGrass((WoodlandRealmBiome)(Object)this, builder, 5, GrassBlends.WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 3);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 1);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
	}
}
