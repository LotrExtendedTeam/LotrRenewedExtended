package com.github.maximuslotro.lotroExtended.mixins.common.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.ExtendedBiomeFeatures;
import lotr.common.world.biome.ShireBiome;
import net.minecraft.world.biome.BiomeGenerationSettings;

@Mixin(ShireBiome.class)
public class MixinShireBiome {
	@Inject(method = "addStructures(Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;)V", at = @At("HEAD"), remap=false)
	private void bootstrapExtendedFeatures(BiomeGenerationSettings.Builder builder, CallbackInfo ci) {
		ExtendedBiomeFeatures.addHobbitHoleStructure(builder, 10, LOTRBlocks.MITHRIL_ORE.get().defaultBlockState());
	}
}
