package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LothlorienBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(LothlorienBiome.class)
public class MixinLothlorienBiome {
	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((LothlorienBiome)(Object)this, builder, 3, 0.1F, 
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.mallorn(), 3000, LOTRBiomeFeatures.mallornBees(), 30, 
			LOTRBiomeFeatures.mallornBoughs(), 6000, LOTRBiomeFeatures.mallornParty(), 1000, 
			LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakFancy(), 500, 
			LOTRBiomeFeatures.oakBees(), 30, LOTRBiomeFeatures.oakFancyBees(), 5, 
			LOTRBiomeFeatures.oakParty(), 100, LOTRBiomeFeatures.larch(), 2000, 
			LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 200, 
			LOTRBiomeFeatures.beechBees(), 10, LOTRBiomeFeatures.beechFancyBees(), 2, 
			LOTRBiomeFeatures.beechParty(), 100, LOTRBiomeFeatures.aspen(), 1000, 
			LOTRBiomeFeatures.aspenLarge(), 200, LOTRBiomeFeatures.lairelosse(), 500);
		LOTRBiomeFeatures.addGrass((LothlorienBiome)(Object)this, builder, 8, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addForestFlowers(builder, 6, LOTRBlocks.ELANOR.get(), 20, LOTRBlocks.NIPHREDIL.get(), 20);
		LOTRBiomeFeatures.addTreeTorches(builder, 120, 60, 110, LOTRBlocks.GOLD_MALLORN_WALL_TORCH.get().defaultBlockState(), LOTRBlocks.BLUE_MALLORN_WALL_TORCH.get().defaultBlockState(), LOTRBlocks.GREEN_MALLORN_WALL_TORCH.get().defaultBlockState(), LOTRBlocks.SILVER_MALLORN_WALL_TORCH.get().defaultBlockState());
	}
}
