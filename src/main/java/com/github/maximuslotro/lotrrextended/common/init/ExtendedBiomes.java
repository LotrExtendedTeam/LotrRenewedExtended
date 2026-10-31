package com.github.maximuslotro.lotrrextended.common.init;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ExtendedBiomes {
	public static final RegistryKey<Biome> BARROW_DOWNS = register("barrow_downs");
	public static final RegistryKey<Biome> BARROW_DOWNS_RIVER = register("barrow_downs_river");
	public static final RegistryKey<Biome> RIVENDELL = register("rivendell");
	public static final RegistryKey<Biome> RIVENDELL_VALLEY = register("rivendell_valley");
	public static final RegistryKey<Biome> RIVENDELL_HILLS = register("rivendell_hills");
	public static final RegistryKey<Biome> RIVENDELL_CLIFFS = register("rivendell_cliffs");
	public static final RegistryKey<Biome> RIVENDELL_RIVER = register("rivendell_river");
	public static final RegistryKey<Biome> NURN_RIVER = register("nurn_river");
	public static final RegistryKey<Biome> MORGUL_VALE_RIVER = register("morgul_vale_river");
	public static final RegistryKey<Biome> MORDOR_RIVER = register("mordor_river");
	public static final RegistryKey<Biome> URUK_HIGHLANDS = register("uruk_highlands");
	public static final RegistryKey<Biome> FANGORN_WASTELAND = register("fangorn_wasteland");

	private static RegistryKey<Biome> register(String pName) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("lotr", pName));
	}
}
