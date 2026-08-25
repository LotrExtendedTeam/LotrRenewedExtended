package com.github.maximuslotro.lotrrextended.common.traders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.serialization.GsonSerializers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedTraderProfileManager extends ReloadListener<Map<ResourceLocation, JsonElement>> {
	public static final ExtendedTraderProfileManager INSTANCE = new ExtendedTraderProfileManager();
	public static final Gson GSON = GsonSerializers.createTraderSerializer().disableHtmlEscaping().create();
	private static final int PATH_SUFFIX_LENGTH = ".json".length();
	private final String directory = "trader_profiles";
	private Map<ResourceLocation, ExtendedTraderProfile> traderProfiles = ImmutableMap.of();

	/*
	 * Keep Private
	 * Don't allow more than one instance than {@link ExtendedTraderProfileManager#INSTANCE }
	 * */
	private ExtendedTraderProfileManager() {}

	/*
	 * Gets a {@link ExtendedTraderProfile} for a specified ResourceLocation
	 * @param traderProfileId Id of the Profile
	 * @return An instance of {@link ExtendedTraderProfile} if found, else {@link ExtendedTraderProfile#EMPTY}
	 * */
	public ExtendedTraderProfile get(ResourceLocation traderProfileId) {
		return this.traderProfiles.getOrDefault(traderProfileId, ExtendedTraderProfile.EMPTY);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedTraderProfile> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} trader profile, ignoring", ExtendedTraderProfile.EMPTY);
		}
		json.forEach((spawnName, spawnListJson) -> {
			try {
				ExtendedTraderProfile traderProfile = deserialize(GSON, spawnListJson, ExtendedTraderProfile.class);
				if (traderProfile != null) {
					builder.put(spawnName, traderProfile);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Trader Profile {} from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedTraderProfile.EMPTY);
		this.traderProfiles = builder.build();
		ExtendedLog.info("Loaded Trader Profile from Json - {} profiles", traderProfiles.size()-1);
	}

	/**
	 * Code copied from {@link JsonReloadListener} due to if this class extended it, it would cause server crashes
	 * when running a reload since all data files would disappear?
	 * Extending {@link ReloadListener} and copying the method fixes this data corruption
	 * From Minecraft 1.16.5
	 * */
	@Override
	protected Map<ResourceLocation, JsonElement> prepare(IResourceManager pResourceManager, IProfiler pProfiler) {
		Map<ResourceLocation, JsonElement> map = Maps.newHashMap();
		int i = this.directory.length() + 1;

		for(ResourceLocation resourcelocation : pResourceManager.listResources(this.directory, (p_223379_0_) -> {
			return p_223379_0_.endsWith(".json");
		})) {
			String s = resourcelocation.getPath();
			ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), s.substring(i, s.length() - PATH_SUFFIX_LENGTH));
			try (
				IResource iresource = pResourceManager.getResource(resourcelocation);
				InputStream inputstream = iresource.getInputStream();
				Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
				) {
				JsonElement jsonelement = JSONUtils.fromJson(GSON, reader, JsonElement.class);
				if (jsonelement != null) {
					JsonElement jsonelement1 = map.put(resourcelocation1, jsonelement);
					if (jsonelement1 != null) {
						throw new IllegalStateException("Duplicate data file ignored with ID " + resourcelocation1);
					}
				} else {
					ExtendedLog.error("Couldn't load data file {} from {} as it's null or empty", resourcelocation1, resourcelocation);
				}
			} catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
				ExtendedLog.error("Couldn't parse data file {} from {}", resourcelocation1, resourcelocation, jsonparseexception);
			}
		}

		return map;
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}

	public static <T> T deserialize(Gson gson, String data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}
}