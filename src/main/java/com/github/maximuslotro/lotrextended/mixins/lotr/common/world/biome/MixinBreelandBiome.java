package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.BreelandBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(BreelandBiome.class)
public class MixinBreelandBiome {

	/**
	 * Add hedgehogs to Bree
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
	protected final Object[] breelandTrees() {
		return new Object[] { ExtendedBiomeFeatures.willowTree(), 2500, LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 3000,
				LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 3,
				LOTRBiomeFeatures.beech(), 3000, LOTRBiomeFeatures.beechFancy(), 750,
				LOTRBiomeFeatures.beechBees(), 3, LOTRBiomeFeatures.beechFancyBees(), 1,
				LOTRBiomeFeatures.maple(), 2000, LOTRBiomeFeatures.mapleFancy(), 500,
				LOTRBiomeFeatures.mapleBees(), 2, LOTRBiomeFeatures.mapleFancyBees(), 1,
				ExtendedBiomeFeatures.chestnut(), 3000, ExtendedBiomeFeatures.chestnutFancy(), 750,
				ExtendedBiomeFeatures.chestnutBees(), 30, ExtendedBiomeFeatures.chestnutFancyBees(), 7,
				LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 200,
				LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1,
				LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100,
				LOTRBiomeFeatures.apple(), 30, LOTRBiomeFeatures.pear(), 30,
				LOTRBiomeFeatures.appleBees(), 15, LOTRBiomeFeatures.pearBees(), 15 };
	}
}
