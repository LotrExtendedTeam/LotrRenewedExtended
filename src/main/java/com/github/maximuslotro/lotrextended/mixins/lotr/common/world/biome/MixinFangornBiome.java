package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.FangornBiome;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FangornBiome.class)
public class MixinFangornBiome {
	/**
	 * Add hedgehogs to Fangorn
	 * @param builder The {@link MobSpawnInfo} builder to work with
	 * @param ci The callback info for additional modification ability
	 * */
	@Inject(method = "addAnimals(Lnet/minecraft/world/biome/MobSpawnInfo$Builder;)V", at = @At("TAIL"), remap = false)
	protected void addAnimalsExtended(MobSpawnInfo.Builder builder, CallbackInfo ci) {
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.HEDGEHOG.get(), 1, 2, 3));
		builder.addSpawn(EntityClassification.CREATURE, new Spawners(ExtendedEntities.CRABAN.get(), 1, 1, 1));
	}

	/**
	 * @author maximuslotro
	 * @reason don't want to replace class
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTrees((FangornBiome)(Object)this, builder, 14, 0.1F,
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakBees(), 1, 
			LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakTallBees(), 1, 
			LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakFancyBees(), 1, 
			LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchBees(), 1, 
			LOTRBiomeFeatures.birchFancy(), 200, LOTRBiomeFeatures.birchFancyBees(), 1, 
			LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechBees(), 1, 
			LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechFancyBees(), 1, 
			LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100, 
			LOTRBiomeFeatures.darkOak(), 4000, LOTRBiomeFeatures.oakFangorn(), 300, 
			LOTRBiomeFeatures.beechFangorn(), 75, LOTRBiomeFeatures.oakShrub(), 600, 
			LOTRBiomeFeatures.darkOakShrub(), 600);
		LOTRBiomeFeatures.addGrass((FangornBiome)(Object)this, builder, 12, GrassBlends.WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_WITH_FERNS);
		LOTRBiomeFeatures.addForestFlowers(builder, 6);
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 1);
		LOTRBiomeFeatures.addFallenLogs(builder, 3);
	}
}
