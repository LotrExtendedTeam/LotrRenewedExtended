package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.NurnBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;

@Mixin(NurnBiome.class)
public class MixinNurnBiome extends LOTRBiomeBase  {
	protected MixinNurnBiome(Builder builder, boolean major) {
		super(builder, major);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Override
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((NurnBiome)(Object)this, builder, 0, 0.25F, TreeCluster.of(6, 30), 
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 1000, 
			LOTRBiomeFeatures.oakDesert(), 5000, LOTRBiomeFeatures.oakDead(), 2000, 
			LOTRBiomeFeatures.cedar(), 1000, LOTRBiomeFeatures.charred(), 2000);
		LOTRBiomeFeatures.addGrass((NurnBiome)(Object)this, builder, 8, GrassBlends.EXTRA_WHEATGRASS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_WITH_EXTRA_WHEATGRASS);
		LOTRBiomeFeatures.addDefaultFlowers(builder, 1);
		LOTRBiomeFeatures.addMordorGrass(builder, 2);
		LOTRBiomeFeatures.addMordorThorns(builder, 200);
	}

	@Override
	public Biome getRiver(IWorld world) {
		return LOTRBiomes.getBiomeByRegistryName(ExtendedBiomes.NURN_RIVER.getRegistryName(), world);
	}
}
