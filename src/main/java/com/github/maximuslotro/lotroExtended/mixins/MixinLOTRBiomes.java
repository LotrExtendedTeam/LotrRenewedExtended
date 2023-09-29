package com.github.maximuslotro.lotroExtended.mixins;

import net.minecraftforge.common.util.NonNullSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import lotr.common.world.biome.LOTRBiomeBase;

@Mixin(LOTRBiomes.class)
public abstract class MixinLOTRBiomes {
	
	@Shadow(remap=false)
	private static PreRegisteredLOTRBiome prepare(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier) {
		return null;}

	private static boolean modifiedShire = false;
	@Inject(method = "prepare", at = @At("HEAD"), cancellable = true, remap=false)
	private static void injected(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier, CallbackInfoReturnable<PreRegisteredLOTRBiome> callback) {
		//if(name.equals("shire")&&!modifiedShire) {
		//	modifiedShire=true;
		//	callback.setReturnValue(prepare("shire", () -> new DunlandBiome(true)));
		//	return;
		//}
	}
}
