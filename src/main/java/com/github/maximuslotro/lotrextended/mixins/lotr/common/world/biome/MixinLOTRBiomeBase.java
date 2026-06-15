package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.biome.ExtendedBiomeSetter;
import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.LOTRBiomeBase;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(LOTRBiomeBase.class)
public class MixinLOTRBiomeBase {

	@Shadow(remap=false)
	private Biome.Builder unbuiltBiome;

	@Shadow(remap=false)
	protected LOTRBiomeBase.CustomBiomeColors biomeColors;

	@Inject(method = "<init>", at = @At("TAIL"), remap=false)
	protected void constructor(Builder builder, int waterFogColor, boolean major, CallbackInfo info) {
		if(this instanceof ExtendedBiomeSetter) {
			unbuiltBiome = ((ExtendedBiomeSetter)this).getExtendedBuilder();
			biomeColors = new LOTRBiomeBase.CustomBiomeColors(unbuiltBiome);
		}
	}

	@Inject(method = "addAmbientCreatures(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAmbientCreaturesExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.AMBIENT, new Spawners(EntityType.RABBIT, 8, 4, 4));
	}

	@Inject(method = "addLandCreatures(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addLandCreaturesExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.DUCK.get(), 20, 4, 4));
	}

	@Inject(method = "addBoars(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;I)V", at = @At("TAIL"), remap = false)
	protected void addBoarsExtended(MobSpawnInfo.Builder builder, int mul, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.HORSE_BOAR.get(), 10 * mul, 1, 4));
	}
}