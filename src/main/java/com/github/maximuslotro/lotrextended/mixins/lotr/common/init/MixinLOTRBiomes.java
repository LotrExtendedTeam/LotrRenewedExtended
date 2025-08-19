package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import lotr.common.biome.BarrowDownsBiome;
import lotr.common.init.ExtendedBiomes;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.PreRegisteredLOTRBiome;
import lotr.common.world.biome.LOTRBiomeBase;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.DatagenModLoader;

/**
 * Used to register new biomes added by Extended
 * @Mixin (LOTRBiomes.class)
 * */
@Mixin(LOTRBiomes.class)
public class MixinLOTRBiomes {

	/**
	 * Get the biome register method from parent class
	 * @Shadow (remap=false)
	 * @param name A String name of the biome
	 * @param wrapperSupplier A LOTRBiomeBase supplier to build
	 * @return A built PreRegisteredLOTRBiome 
	 * */
	@Shadow(remap=false)
	private static PreRegisteredLOTRBiome prepare(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier) {return null;}

	/**
	 * Register our own biomes, with dataGen handling
	 * @param name A String name of the biome
	 * @param wrapperSupplier A LOTRBiomeBase supplier to build
	 * @return A built PreRegisteredLOTRBiome 
	 * */
	@Unique
	private static PreRegisteredLOTRBiome prepareD(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier) {
		if(DatagenModLoader.isRunningDataGen()) {
			DataGenValueHolder.biomeNames.add("biome.lotr."+name);
		}
		return prepare(name, wrapperSupplier);
	}

	/**
	 * @reason We need to register our biomes after Renewed
	 * @Inject (method = "register()V", at = @At("HEAD"), remap = false)
	 * @param ci The local CallbackInfo instance for this Inject
	 * */
	@Inject(method = "register()V", at = @At("HEAD"), remap = false)
	private static void bootstrapExtendedBiomes(CallbackInfo ci) {
		ExtendedBiomes.BARROW_DOWNS = prepareD("barrow_downs", () -> new BarrowDownsBiome(true));
		ExtendedBiomes.BARROW_DOWNS_RIVER = prepareD("barrow_downs_river", () -> new BarrowDownsBiome.BarrowDownsRiverBiome());
	}
}
