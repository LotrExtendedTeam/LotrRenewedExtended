package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.EriadorBiome;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(EriadorBiome.class)
public class MixinEriadorBiome {

	/**
	 * Add hedgehogs to Eriador
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
	protected final Object[] eriadorTrees() {
		return new Object[] { ExtendedBiomeFeatures.willowTree(), 10000, LOTRBiomeFeatures.oak(), 10000, LOTRBiomeFeatures.oakFancy(), 1000,
				LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 1,
				LOTRBiomeFeatures.birch(), 1000, LOTRBiomeFeatures.birchFancy(), 100,
				LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1,
				LOTRBiomeFeatures.spruce(), 2000, LOTRBiomeFeatures.beech(), 200,
				LOTRBiomeFeatures.beechFancy(), 20, LOTRBiomeFeatures.beechBees(), 1,
				LOTRBiomeFeatures.beechFancyBees(), 1, ExtendedBiomeFeatures.chestnut(), 1000,
				ExtendedBiomeFeatures.chestnutFancy(), 100, ExtendedBiomeFeatures.chestnutBees(), 10,
				ExtendedBiomeFeatures.chestnutFancyBees(), 1, LOTRBiomeFeatures.maple(), 50,
				LOTRBiomeFeatures.mapleFancy(), 5, LOTRBiomeFeatures.mapleBees(), 1,
				LOTRBiomeFeatures.mapleFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500,
				LOTRBiomeFeatures.aspenLarge(), 50, LOTRBiomeFeatures.apple(), 20,
				LOTRBiomeFeatures.pear(), 20, LOTRBiomeFeatures.appleBees(), 1,
				LOTRBiomeFeatures.pearBees(), 1 };
	}
}
