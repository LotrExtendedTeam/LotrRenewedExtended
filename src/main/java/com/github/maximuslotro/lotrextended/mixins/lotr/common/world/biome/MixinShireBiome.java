package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.ShireBiome;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(ShireBiome.class)
public class MixinShireBiome {

	/**
	 * Add hedgehogs to the Shire
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
	protected final Object[] shireTrees() {
		return new Object[] { ExtendedBiomeFeatures.willowTree(), 10000, LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 4000,
				LOTRBiomeFeatures.oakBees(), 100, LOTRBiomeFeatures.oakFancyBees(), 40,
				LOTRBiomeFeatures.oakParty(), 200, ExtendedBiomeFeatures.chestnut(), 2500,/*Extended Start*/
				ExtendedBiomeFeatures.chestnutFancy(), 1000, ExtendedBiomeFeatures.chestnutBees(), 25,
				ExtendedBiomeFeatures.chestnutFancyBees(), 10, /*Extended End*/LOTRBiomeFeatures.birch(), 250,
				LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.birchBees(), 2,
				LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500,
				LOTRBiomeFeatures.aspenLarge(), 100, LOTRBiomeFeatures.apple(), 50,
				LOTRBiomeFeatures.appleBees(), 1, LOTRBiomeFeatures.pear(), 50,
				LOTRBiomeFeatures.pearBees(), 1, LOTRBiomeFeatures.cherry(), 20,
				LOTRBiomeFeatures.cherryBees(), 1,/*Extended Start*/LOTRBiomeFeatures.maple(), 20,
				LOTRBiomeFeatures.mapleFancy(), 1, LOTRBiomeFeatures.mapleBees(), 1,
				LOTRBiomeFeatures.mapleFancyBees(), 1,
				ExtendedBiomeFeatures.plum(), 50, ExtendedBiomeFeatures.plumBees(), 1/*Extended End*/};
	}
}
