package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.AnduinValeBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(AnduinValeBiome.class)
public class MixinAnduinValeBiome {

	/**
	 * Add hedgehogs to the Vales of the Anduin
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
	protected final Object[] anduinTrees() {
		return new Object[] {
				ExtendedBiomeFeatures.willowTree(), 2500,
				LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 100, LOTRBiomeFeatures.oakFancyBees(), 10,
				LOTRBiomeFeatures.birch(), 1500, LOTRBiomeFeatures.birchFancy(), 150,
				LOTRBiomeFeatures.birchBees(), 15, LOTRBiomeFeatures.birchFancyBees(), 2,
				LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.larch(), 1500,
				ExtendedBiomeFeatures.chestnut(), 1000, ExtendedBiomeFeatures.chestnutFancy(), 100,
				ExtendedBiomeFeatures.chestnutBees(), 10, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
				LOTRBiomeFeatures.pine(), 1000, LOTRBiomeFeatures.apple(), 10,
				LOTRBiomeFeatures.appleBees(), 10, LOTRBiomeFeatures.pear(), 10,
				LOTRBiomeFeatures.pearBees(), 10 };
	}
}
