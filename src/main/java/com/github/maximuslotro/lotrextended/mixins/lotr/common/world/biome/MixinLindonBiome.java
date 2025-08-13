package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.LindonBiome;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(LindonBiome.class)
public class MixinLindonBiome {

	/**
	 * Add hedgehogs to Lindon
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
	 * @return An array containing tree features followed by their spawn weights.
	 * */
	@Overwrite(remap = false)
	protected final Object[] lindonTrees() {
		return new Object[] {
				ExtendedBiomeFeatures.willowTree(), 5000,
				LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakFancy(), 250,
				LOTRBiomeFeatures.oakBees(), 1, LOTRBiomeFeatures.oakFancyBees(), 1,
				LOTRBiomeFeatures.birch(), 10000, LOTRBiomeFeatures.birchFancy(), 2000,
				LOTRBiomeFeatures.birchBees(), 10, LOTRBiomeFeatures.birchFancyBees(), 2,
				LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 250,
				LOTRBiomeFeatures.beechBees(), 1, LOTRBiomeFeatures.beechFancyBees(), 1,
				ExtendedBiomeFeatures.chestnut(), 400, ExtendedBiomeFeatures.chestnutFancy(), 100,
				ExtendedBiomeFeatures.chestnutBees(), 4, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.aspen(), 2000, LOTRBiomeFeatures.aspenLarge(), 1000,
				LOTRBiomeFeatures.apple(), 20, LOTRBiomeFeatures.pear(), 20,
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pearBees(), 1 };
	}
}
