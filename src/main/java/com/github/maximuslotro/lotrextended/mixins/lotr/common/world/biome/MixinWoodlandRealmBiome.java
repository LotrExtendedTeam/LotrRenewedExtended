package com.github.maximuslotro.lotrextended.mixins.lotr.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.world.biome.ExtendedDefaultBiomeFeatures;
import com.github.maximuslotro.lotrrextended.common.world.gen.feature.ExtendedFeatures;

import lotr.common.init.ExtendedEntities;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.LOTRBiomeFeatures;
import lotr.common.world.biome.WoodlandRealmBiome;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

@Mixin(WoodlandRealmBiome.class)
public class MixinWoodlandRealmBiome {

	/**
	 * Add hedgehogs to the Woodland Realm
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
	 * @param builder The biome generation settings builder to which vegetation features are added
	 * */
	@Overwrite(remap = false)
	protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
		LOTRBiomeFeatures.addTreesWithClusters((WoodlandRealmBiome)(Object)this, builder, 2, 0.33F, TreeCluster.of(16, 10),
			ExtendedBiomeFeatures.willowTree(), 2500,
			LOTRBiomeFeatures.greenOak(), 5000, LOTRBiomeFeatures.greenOakBees(), 5,
			LOTRBiomeFeatures.greenOakParty(), 800, LOTRBiomeFeatures.redOak(), 400,
			LOTRBiomeFeatures.redOakBees(), 5, LOTRBiomeFeatures.redOakParty(), 100,
			LOTRBiomeFeatures.greenOakShrub(), 8000, LOTRBiomeFeatures.oak(), 500,
			LOTRBiomeFeatures.oakBees(), 1, LOTRBiomeFeatures.oakFancy(), 1000,
			LOTRBiomeFeatures.oakFancyBees(), 1, LOTRBiomeFeatures.spruce(), 1000,
			ExtendedBiomeFeatures.chestnut(), 500, ExtendedBiomeFeatures.chestnutBees(), 1,
			ExtendedBiomeFeatures.chestnutFancy(), 500, ExtendedBiomeFeatures.chestnutFancyBees(), 1,
			LOTRBiomeFeatures.beech(), 500, LOTRBiomeFeatures.beechBees(), 1,
			LOTRBiomeFeatures.beechFancy(), 1000, LOTRBiomeFeatures.beechFancyBees(), 1,
			LOTRBiomeFeatures.larch(), 500, LOTRBiomeFeatures.fir(), 1000,
			LOTRBiomeFeatures.pine(), 500, LOTRBiomeFeatures.aspen(), 500,
			LOTRBiomeFeatures.aspenLarge(), 100);
		LOTRBiomeFeatures.addGrass((WoodlandRealmBiome)(Object)this, builder, 5, GrassBlends.WITH_FERNS);
		LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_FERNS);
		// Extended Start
		ExtendedDefaultBiomeFeatures.addFangornFlowers(builder, ExtendedFeatures.FLOWER_FOREST3);
		// Extended End
		LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
		LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 1);
		LOTRBiomeFeatures.addFallenLogs(builder, 1);
	}
}
