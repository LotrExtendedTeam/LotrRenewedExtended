package com.github.maximuslotro.lotroExtended.mixins.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.LOTRMod;
import lotr.common.command.util.ScanHandler;
import lotr.common.init.ExtendedEffects;
import net.minecraftforge.common.MinecraftForge;

@Mixin(LOTRMod.class)
public class MixinLOTRMod {

	@Inject(at = @At("TAIL"), method = "<init>()V", remap = false)
	private void init(CallbackInfo info) {
		ExtendedEffects.register();
		MinecraftForge.EVENT_BUS.register(new ScanHandler());
	}
}
