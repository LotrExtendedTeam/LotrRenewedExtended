package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.NanCurunirBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;

@Mixin(NanCurunirBiome.class)
public class MixinNanCurunirBiome extends LOTRBiomeBase {
	protected MixinNanCurunirBiome(Builder builder, boolean major) {
		super(builder, major);
	}

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Override
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((NanCurunirBiome)(Object)this, builder, 0, 0.05F,
			ExtendedBiomeFeatures.willowTree(), 1500,
			LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakTall(), 1000, 
			LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakDead(), 2000, 
			LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.spruceDead(), 1000, 
			LOTRBiomeFeatures.charred(), 3000);
		LOTRBiomeFeatures.addGrass((NanCurunirBiome)(Object)this, builder, 4, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
	}

	@Override
	public Biome getRiver(IWorld world) {
		return null;
	}
}
