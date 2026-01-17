package com.github.maximuslotro.lotrextended.mixins.lotr.common.init;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import lotr.common.biome.BarrowDownsBiome;
import lotr.common.biome.ExtendedGorgorothRiverBiome;
import lotr.common.biome.ExtendedMordorRiverBiome;
import lotr.common.biome.ExtendedMorgulValeRiverBiome;
import lotr.common.biome.ExtendedNurnRiverBiome;
import lotr.common.biome.ExtendedRivendellBiome;
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

	@Final
	@Shadow(remap=false)
	private static List<PreRegisteredLOTRBiome> PRE_REGISTRY;

	/**
	 * Modify biome registering
	 * @author Maximuslotro
	 * @reason Need to handle changing some biomes
	 * @Overwrite (remap=false)
	 * @param name A String name of the biome
	 * @param wrapperSupplier A LOTRBiomeBase supplier to build
	 * @return A built PreRegisteredLOTRBiome 
	 * */
	@Overwrite(remap=false)
	private static PreRegisteredLOTRBiome prepare(String name, NonNullSupplier<LOTRBiomeBase> wrapperSupplier) {
		if(name.equals("rivendell")) {
			wrapperSupplier = () -> new ExtendedRivendellBiome(true);
		}
		PreRegisteredLOTRBiome preparedBiome = new PreRegisteredLOTRBiome(name, wrapperSupplier);
		PRE_REGISTRY.add(preparedBiome);
		return preparedBiome;
	}
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
		ExtendedBiomes.RIVENDELL_CLIFFS = prepareD("rivendell_cliffs", () -> new ExtendedRivendellBiome.ExtendedRivendellCliffsBiome());
		ExtendedBiomes.RIVENDELL_VALLEY = prepareD("rivendell_valley", () -> new ExtendedRivendellBiome.ExtendedRivendellValleyBiome());
		ExtendedBiomes.RIVENDELL_RIVER = prepareD("rivendell_river", () -> new ExtendedRivendellBiome.ExtendedRivendellRiverBiome());

		ExtendedBiomes.MORDOR_RIVER = prepareD("mordor_river", () -> new ExtendedMordorRiverBiome());
		ExtendedBiomes.GORGOROTH_RIVER = prepareD("gorgoroth_river", () -> new ExtendedGorgorothRiverBiome());
		ExtendedBiomes.MORGUL_VALE_RIVER = prepareD("morgul_vale_river", () -> new ExtendedMorgulValeRiverBiome());
		ExtendedBiomes.NURN_RIVER = prepareD("nurn_river", () -> new ExtendedNurnRiverBiome());

	}
}
