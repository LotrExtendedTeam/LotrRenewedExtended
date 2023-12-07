package com.github.maximuslotro.lotroExtended.mixins.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.ShireBiome;
import lotr.common.world.gen.feature.ExtendedFeatures;
import lotr.common.world.gen.feature.structure.HobbitHoleFeatureConfig;
import lotr.common.world.gen.feature.structure.HobbitTavernSmallFeatureConfig;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage.Decoration;

@Mixin(ShireBiome.class)
public class MixinShireBiome {
	@Inject(method = "addStructures(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;)V", at = @At("HEAD"), remap=false)
	private void bootstrapExtendedFeatures(BiomeGenerationSettings.Builder builder, CallbackInfo ci) {
		ExtendedBiomeFeatures.addStructure(builder, Decoration.SURFACE_STRUCTURES, 400, ExtendedFeatures.HOBBIT_BURROW.configured(new HobbitHoleFeatureConfig(LOTRBlocks.MITHRIL_ORE.get().defaultBlockState())));
		ExtendedBiomeFeatures.addStructure(builder, Decoration.SURFACE_STRUCTURES, 800, ExtendedFeatures.HOBBIT_TAVERN_SMALL.configured(new HobbitTavernSmallFeatureConfig(LOTRBlocks.MITHRIL_ORE.get().defaultBlockState())));
		ExtendedBiomeFeatures.addStructure(builder, Decoration.SURFACE_STRUCTURES, 200, ExtendedFeatures.HOBBIT_TAVERN_SMALL.configured(new HobbitTavernSmallFeatureConfig(LOTRBlocks.COPPER_ORE.get().defaultBlockState())));
	}
}
