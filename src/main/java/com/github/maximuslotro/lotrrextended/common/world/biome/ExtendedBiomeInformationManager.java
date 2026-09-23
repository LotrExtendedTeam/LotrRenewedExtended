package com.github.maximuslotro.lotrrextended.common.world.biome;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.serialization.ExtendedGsonSerializers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class ExtendedBiomeInformationManager extends JsonReloadListener {
	public static final Gson GSON = ExtendedGsonSerializers.createBiomeInformationSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	public static final ExtendedBiomeInformationManager INSTANCE = new ExtendedBiomeInformationManager();
	public static final String directory = "biome_information";
	private Map<ResourceLocation, ExtendedBiomeInformation> biomeInformations;

	/*
	 * Keep Private
	 * Don't allow more than one instance than {@link ExtendedBiomeInformationManager#INSTANCE }
	 * */
	private ExtendedBiomeInformationManager() {
		super(GSON, directory);
	}
	public ExtendedBiomeInformation get(RegistryKey<Biome> pBiome) {
		return get(pBiome.location());
	}
	public ExtendedBiomeInformation get(Biome pBiome) {
		return get(pBiome.getRegistryName());
	}
	public ExtendedBiomeInformation get(ResourceLocation biomeID) {
		return this.biomeInformations.getOrDefault(biomeID, ExtendedBiomeInformation.EMPTY);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedBiomeInformation> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} biome information, ignoring", ExtendedBiomeInformation.EMPTY);
		}
		json.forEach((fileName, fileJson) -> {
			try {
				ExtendedBiomeInformation biomeInformation = deserialize(GSON, fileJson, ExtendedBiomeInformation.class);
				if (biomeInformation != null) {
					builder.put(fileName, biomeInformation);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Biome Information {} from file", fileName);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedBiomeInformation.EMPTY);
		this.biomeInformations = builder.build();
		ExtendedLog.info("Loaded Biome Information from Json - {} biomes", biomeInformations.size()-1);
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}
}