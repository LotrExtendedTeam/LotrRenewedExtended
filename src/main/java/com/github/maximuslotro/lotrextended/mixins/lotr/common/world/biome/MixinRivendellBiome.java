package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.RivendellBiome;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(RivendellBiome.class)
public class MixinRivendellBiome extends LOTRBiomeBase{

	protected MixinRivendellBiome(Builder builder, boolean major) {
		super(builder, major);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Add hedgehogs to the Rivendell
	 * @param builder The {@link MobSpawnInfo} builder to work with
	 * @param ci The callback info for additional modification ability
	 * */
	@Inject(method = "addAnimals(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAnimalsExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.HEDGEHOG.get(), 5, 2, 3));
	}

	@Override
	protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {

	}
}
