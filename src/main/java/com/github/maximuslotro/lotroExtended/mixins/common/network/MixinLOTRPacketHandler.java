package com.github.maximuslotro.lotroExtended.mixins.common.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.network.ExtendedPacketHandler;
import lotr.common.network.LOTRPacketHandler;

@Mixin(LOTRPacketHandler.class)
public class MixinLOTRPacketHandler {
	
	@Inject(method = "register()V", at = @At("TAIL"), remap=false)
	private static void injected(CallbackInfo ci) {
		ExtendedPacketHandler.register();
	}
}
