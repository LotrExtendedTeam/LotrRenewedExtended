package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LothlorienBiome.Eaves;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(Eaves.class)
public class MixinLothlorienEavesBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((Eaves)(Object)this, builder, 2, 0.5F, 
			ExtendedBiomeFeatures.willowTree(), 1000,
			LOTRBiomeFeatures.mallorn(), 500, LOTRBiomeFeatures.mallornBees(), 5, 
			LOTRBiomeFeatures.mallornBoughs(), 100, LOTRBiomeFeatures.oak(), 3000, 
			LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.oakBees(), 30, 
			LOTRBiomeFeatures.oakFancyBees(), 5, LOTRBiomeFeatures.larch(), 2000, 
			LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 200, 
			LOTRBiomeFeatures.beechBees(), 10, LOTRBiomeFeatures.beechFancyBees(), 2, 
			LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 200);
		LOTRBiomeFeatures.addGrass((Eaves)(Object)this, builder, 10, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addForestFlowers(builder, 2, LOTRBlocks.ELANOR.get(), 10, LOTRBlocks.NIPHREDIL.get(), 10);
	}
}
