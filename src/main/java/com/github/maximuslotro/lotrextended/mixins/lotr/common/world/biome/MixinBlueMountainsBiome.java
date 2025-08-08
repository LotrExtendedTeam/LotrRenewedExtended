package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.BlueMountainsBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.Biome.Builder;

@Mixin(BlueMountainsBiome.class)
public abstract class MixinBlueMountainsBiome extends LOTRBiomeBase{

	/**
	 * Mixins with extend classes must add a constructor, but this will be stripped on runtime and will cease to exist
	 * @param builder A placeholder variable instance of LOTR Biome Builder
	 * @param major A placeholder variable determining if this biome is major
	 * */
	protected MixinBlueMountainsBiome(Builder builder, boolean major) {
		super(builder, major);
	}

	/**
	 * Gets if this mountain biome is a foothills instance
	 * @return True if is a foothills instance
	 * */
	@Shadow(remap=false)
	protected boolean isFoothills() {
		return false;
	}

	/**
	 * Gets if this mountain biome is a foothills instance
	 * @param builder The {@link MobSpawnInfo} builder to work with
	 * @param ci The callback info for additional modification ability
	 * */
	@Inject(method = "addAnimals(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAnimalsExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		this.addBoars(builder, 2);
	}
	/**
	 * @author maximuslotro
	 * @reason Needed to add new stone blob type builders
	 * */
	@Override
	@Overwrite(remap = false)
	protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		ExtendedBiomeFeatures.addAndesiteBlobs(builder);
		ExtendedBiomeFeatures.addDeepDioriteBlobs(builder);
		ExtendedBiomeFeatures.addLuigonBlobs(builder);
		ExtendedBiomeFeatures.addStoneBlobs(builder);
	}

	/**
	 * @author maximuslotro
	 * @reason Needed to add new trees, can't inject into addTreesWithClusters
	 * */
	@Override
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((BlueMountainsBiome)(Object)this, builder, 0, 0.4F, TreeCluster.of(10, 12),
			/*Extended Start*/ExtendedBiomeFeatures.willowTree(), 2500,/*Extended End*/
			LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakFancy(), 1000, 
			LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.spruceDead(), 50, 
			LOTRBiomeFeatures.birch(), 4000, LOTRBiomeFeatures.pine(), 5000, 
			LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.fir(), 5000);
		LOTRBiomeFeatures.addGrass((BlueMountainsBiome)(Object)this, builder, 6, GrassBlends.MUTED);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MUTED);
		LOTRBiomeFeatures.addMountainsFlowers(builder, 1, LOTRBlocks.DWARFWORT.get(), 1);
	}

	/**
	 * @author maximuslotro
	 * @reason To lazy to inject
	 * */
	@Override
	@Overwrite(remap = false)
	protected void setupSurface(MiddleEarthSurfaceConfig config) {
		// Extended Start
		config.addSubSoilLayer(LOTRBlocks.BLUE_ROCK.get().defaultBlockState(), 1000);
		// Extended End
		if (!isFoothills()) {
			config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(110).state(Blocks.SNOW_BLOCK).topOnly(), MountainTerrainProvider.MountainLayer.layerBuilder().above(90).state(LOTRBlocks.BLUE_ROCK).excludeStone()));
		}
	}
}
