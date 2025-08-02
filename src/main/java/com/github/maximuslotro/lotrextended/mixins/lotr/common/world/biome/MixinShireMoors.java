package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.LOTRBlocks;
import lotr.common.util.LOTRUtil;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.ShireBiome;
import lotr.common.world.biome.ShireBiome.Moors;
import lotr.common.world.gen.feature.grassblend.GrassBlends;

@Mixin(Moors.class)
public abstract class MixinShireMoors extends ShireBiome{


	public MixinShireMoors(boolean major) {
		super(major);
	}

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * */
	@Override
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((Moors)(Object)this, builder, 0, 0.1F, LOTRUtil.combineVarargs(shireTrees(),
				LOTRBiomeFeatures.oakFancy(), 80000, LOTRBiomeFeatures.oakFancyBees(), 800,
				ExtendedBiomeFeatures.chestnutFancy(), 20000, ExtendedBiomeFeatures.chestnutFancyBees(), 200,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1));
		LOTRBiomeFeatures.addGrass((Moors)(Object)this, builder, 6, GrassBlends.SHIRE_MOORS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MOORS);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 10, LOTRBlocks.SHIRE_HEATHER.get(), 100);
		LOTRBiomeFeatures.addWildPipeweedChance(builder, 12);
	}
}
