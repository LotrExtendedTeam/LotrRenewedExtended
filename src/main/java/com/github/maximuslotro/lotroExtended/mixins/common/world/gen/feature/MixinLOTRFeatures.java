package com.github.maximuslotro.lotroExtended.mixins.common.world.gen.feature;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.world.gen.feature.ExtendedFeatures;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.feature.structure.HobbitHoleFeature;
import lotr.common.world.gen.feature.structure.HobbitHoleFeatureConfig;
import lotr.common.world.gen.feature.structure.HobbitTavernSmallFeature;
import lotr.common.world.gen.feature.structure.HobbitTavernSmallFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

@Mixin(LOTRFeatures.class)
public class MixinLOTRFeatures {
	@Shadow(remap = false)
	private static <FC extends IFeatureConfig, F extends Feature<FC>> F preRegFeature(String name, F feature) {return null;}

	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedFeatures(CallbackInfo ci) {
		ExtendedFeatures.HOBBIT_BURROW = preRegFeature("hobbit_hole", new HobbitHoleFeature(HobbitHoleFeatureConfig.CODEC));
		ExtendedFeatures.HOBBIT_TAVERN_SMALL = preRegFeature("hobbit_tavern_small", new HobbitTavernSmallFeature(HobbitTavernSmallFeatureConfig.CODEC));
	}
}
