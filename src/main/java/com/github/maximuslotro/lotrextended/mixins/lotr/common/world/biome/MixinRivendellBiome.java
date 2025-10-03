package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.RivendellBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(RivendellBiome.class)
public class MixinRivendellBiome {

	/**
	 * Add hedgehogs to the Rivendell
	 * @param builder The {@link MobSpawnInfo} builder to work with
	 * @param ci The callback info for additional modification ability
	 * */
	@Inject(method = "addAnimals(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAnimalsExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.HEDGEHOG.get(), 5, 2, 3));
	}

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		Object[] weightedTrees = {
				ExtendedBiomeFeatures.willowTree(), 5000,
				LOTRBiomeFeatures.oak(), 5000, LOTRBiomeFeatures.oakFancy(), 2000,
				LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakFancyBees(), 20,
				LOTRBiomeFeatures.birch(), 5000, LOTRBiomeFeatures.birchFancy(), 1000,
				LOTRBiomeFeatures.birchBees(), 50, LOTRBiomeFeatures.birchFancyBees(), 10,
				LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechFancy(), 200,
				LOTRBiomeFeatures.beechBees(), 5, LOTRBiomeFeatures.beechFancyBees(), 2,
				ExtendedBiomeFeatures.chestnut(), 500, ExtendedBiomeFeatures.chestnutFancy(), 100,
				ExtendedBiomeFeatures.chestnutBees(), 5, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.larch(), 1500, LOTRBiomeFeatures.aspen(), 1000,
				LOTRBiomeFeatures.aspenLarge(), 500, LOTRBiomeFeatures.apple(), 50,
				LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pearBees(), 1 };
		LOTRBiomeFeatures.addTreesWithClusters((RivendellBiome)(Object)this, builder, 0, 0.25F, TreeCluster.of(12, 16), weightedTrees);
		LOTRBiomeFeatures.addTreesAboveTreelineIncrease((RivendellBiome)(Object)this, builder, 2, 0.2F, 2, 85, weightedTrees);
		LOTRBiomeFeatures.addGrass((RivendellBiome)(Object)this, builder, 6, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 5);
	}
}
