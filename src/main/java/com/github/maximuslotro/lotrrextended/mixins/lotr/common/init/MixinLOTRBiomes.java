package com.github.maximuslotro.lotrrextended.mixins.lotr.common.init;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.datagen.ExtendedDataGenValueHolder;

import lotr.common.biome.BarrowDownsBiome;
import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import lotr.common.world.biome.LOTRBiomeBase;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.DatagenModLoader;

@Mixin(LOTRBiomes.class)
public class MixinLOTRBiomes {

	@Shadow(remap=false)
	private static PreRegisteredLOTRBiome prepare(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier) {return null;}

	private static PreRegisteredLOTRBiome prepareD(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier) {
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedDataGenValueHolder.biomeNames.add("biome.lotr."+name);
		}
		return prepare(name, wrapperSupplier);
	}

	@Inject(method = "register()V", at = @At("HEAD"), remap = false)
	private static void bootstrapExtendedBiomes(CallbackInfo ci) {
		ExtendedBiomes.BARROW_DOWNS = prepareD("barrow_downs", () -> new BarrowDownsBiome(true));
	}
}
