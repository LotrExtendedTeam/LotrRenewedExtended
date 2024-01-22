package com.github.maximuslotro.lotroExtended.mixins.lotr.common.world.gen.feature;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.world.gen.feature.ExtendedFeatures;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.feature.extendedstructures.ExtendedShireStructures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

@Mixin(LOTRFeatures.class)
public class MixinLOTRFeatures {
	@Shadow(remap = false)
	private static <FC extends IFeatureConfig, F extends Feature<FC>> F preRegFeature(String name, F feature) {return null;}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedFeatures(CallbackInfo ci) {
		ExtendedFeatures.SHIRE_STRUCTURES = preRegFeature("shire_structures", new ExtendedShireStructures(NoFeatureConfig.CODEC));
	}
}
