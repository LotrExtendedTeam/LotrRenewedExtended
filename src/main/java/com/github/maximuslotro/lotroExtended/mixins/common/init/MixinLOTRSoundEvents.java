package com.github.maximuslotro.lotroExtended.mixins.common.init;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import lotr.common.init.ExtendedSoundEvents;
import lotr.common.init.LOTRSoundEvents;

@Mixin(LOTRSoundEvents.class)
public class MixinLOTRSoundEvents {

	
	@Inject(method = "register()V", at = @At("HEAD"), remap=false)
	private static void bootstrapExtendedSounds(CallbackInfo ci) {
		ExtendedSoundEvents.register();
	}
}
