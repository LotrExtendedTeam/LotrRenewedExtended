package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.AndrastBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(AndrastBiome.class)
public class MixinAndrastBiome {

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((AndrastBiome)(Object)this, builder, 0, 0.4F, TreeCluster.of(10, 24),
				new Object[]{LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakFancy(), 2000,
						LOTRBiomeFeatures.oakBees(), 4, LOTRBiomeFeatures.oakFancyBees(), 2, LOTRBiomeFeatures.birch(),
						500, LOTRBiomeFeatures.birchFancy(), 200, LOTRBiomeFeatures.birchBees(), 1,
						LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.beech(), 500,
						LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechBees(), 1,
						LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.pine(),
						2000, LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.larch(), 2000,
						LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1,
						LOTRBiomeFeatures.pearBees(), 1, ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(),
						1, LOTRBiomeFeatures.oakShrub(), 15000});
		LOTRBiomeFeatures.addGrass((AndrastBiome)(Object)this, builder, 12, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 3, new Object[0]);
	}
}
