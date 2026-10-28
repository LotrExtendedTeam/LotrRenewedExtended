package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedDefaultBiomeFeatures;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeatures;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.DunlandBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(DunlandBiome.class)
public class MixinDunlandBiome {
	@Inject(method = "addAnimals(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAnimalsExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.CRABAN.get(), 10, 2, 6));
	}

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = {
				ExtendedBiomeFeatures.willowTree(), 2500,
				LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.oak(), 1000,
				LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakFancy(), 200,
				LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakTallBees(), 10,
				LOTRBiomeFeatures.oakFancyBees(), 2, ExtendedBiomeFeatures.chestnut(), 500,
				ExtendedBiomeFeatures.chestnutFancy(), 100, ExtendedBiomeFeatures.chestnutBees(), 5,
				ExtendedBiomeFeatures.chestnutFancyBees(), 1, LOTRBiomeFeatures.pine(), 5000,
				LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.fir(), 5000 };
		LOTRBiomeFeatures.addTreesWithClusters((DunlandBiome)(Object)this, builder, 0, 0.4F, TreeCluster.of(12, 10), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreeline((DunlandBiome)(Object)this, builder, 6, 0.1F, 85, weightedTrees);
		LOTRBiomeFeatures.addGrass((DunlandBiome)(Object)this, builder, 6, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		// Extended Start
		ExtendedDefaultBiomeFeatures.addFangornFlowers(builder, ExtendedFeatures.FLOWER_FOREST2);
		// Extended End
		LOTRBiomeFeatures.addFoxBerryBushes(builder);
	}
}
