package com.github.maximuslotro.lotroExtended.mixins.lotr.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lotr.common.LOTRMod;
import lotr.common.init.ExtendedEffects;
import lotr.common.structure.scanning.StructureScanner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mixin(LOTRMod.class)
public class MixinLOTRMod {

	@Inject(at = @At("TAIL"), method = "<init>()V", remap = false)
	private void init(CallbackInfo info) {
		ExtendedEffects.register();
		if(!FMLEnvironment.dist.equals(Dist.CLIENT)) {
			MinecraftForge.EVENT_BUS.register(new StructureScanner());
		}
	}
}
