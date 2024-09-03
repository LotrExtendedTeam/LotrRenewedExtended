package com.github.maximuslotro.lotrrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(LOTRBiomeBase.class)
public class MixinLOTRBiomeBase {

	@Inject(method = "addAnimals(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAnimals(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.DUCK.get(), 20, 4, 4));
	}

	@Inject(method = "addFeatures(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addBerryBushes(BiomeGenerationSettings.Builder builder, CallbackInfo ci) {
		ExtendedBiomeFeatures.addBushes(builder);
	}
}
