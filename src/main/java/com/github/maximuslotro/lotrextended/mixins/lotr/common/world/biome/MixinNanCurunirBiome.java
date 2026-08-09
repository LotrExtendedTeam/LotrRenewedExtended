package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.NanCurunirBiome;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(NanCurunirBiome.class)
public class MixinNanCurunirBiome extends LOTRBiomeBase {
	protected MixinNanCurunirBiome(Builder builder, boolean major) {
		super(builder, major);
	}
	@Inject(method = "addAnimals(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAnimalsExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.CRABAN.get(), 1, 1, 1));
	}

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Override
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((NanCurunirBiome)(Object)this, builder, 0, 0.05F,
			ExtendedBiomeFeatures.willowTree(), 1500,
			LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakTall(), 1000, 
			LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakDead(), 2000, 
			LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.spruceDead(), 1000, 
			LOTRBiomeFeatures.charred(), 3000);
		LOTRBiomeFeatures.addGrass((NanCurunirBiome)(Object)this, builder, 4, GrassBlends.STANDARD);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
		LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
	}

	@Override
	public Biome getRiver(IWorld world) {
		return null;
	}
}
