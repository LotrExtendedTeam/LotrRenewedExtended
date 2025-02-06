package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedHiredUnitDataManager extends ReloadListener<Map<ResourceLocation, JsonElement>> {
	public static final ExtendedHiredUnitDataManager INSTANCE = new ExtendedHiredUnitDataManager();
	public static final Gson GSON = GsonSerializers.createHiredUnitSerializer().disableHtmlEscaping().create();
	private static final int PATH_SUFFIX_LENGTH = ".json".length();
	private final String directory = "hired_unit_profiles";
	private Map<ResourceLocation, ExtendedHiredUnitProfile> hiredUnitProfiles = ImmutableMap.of();

	/*
	 * Keep Private
	 * Don't allow more than one instance than {@Link ExtendedHiredUnitDataManager#INSTANCE }
	 * */
	private ExtendedHiredUnitDataManager() {}

	/*
	 * Gets a {@Link ExtendedHiredUnitProfile} for a specified ResourceLocation
	 * @param unitProfileId Id of the Profile
	 * @return An instance of {@Link ExtendedHiredUnitProfile} if found, else {@Link ExtendedHiredUnitProfile#EMPTY}
	 * */
	public ExtendedHiredUnitProfile get(ResourceLocation unitProfileId) {
		return this.hiredUnitProfiles.getOrDefault(unitProfileId, ExtendedHiredUnitProfile.EMPTY);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedHiredUnitProfile> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(ExtendedSpawners.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} loot table, ignoring", ExtendedHiredUnitProfile.EMPTY);
		}
		json.forEach((spawnName, spawnListJson) -> {
			try {
				ExtendedHiredUnitProfile hiredUnitProfile = deserialize(GSON, spawnListJson, ExtendedHiredUnitProfile.class);
				if (hiredUnitProfile != null) {
				//	if(hiredUnitProfile.isCustom) {
				//		ExtendedLog.warn("Loaded a Entity Structure Spawner with the customLoad flag enabled, THIS SHOULD NOT BE SET IN JSON! Disabling flag!", spawnName);
				//		hiredUnitProfile.isCustom = false;
				//	}
					builder.put(spawnName, hiredUnitProfile);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Hired Unit Profile %s from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(ExtendedSpawners.EMPTY, ExtendedHiredUnitProfile.EMPTY);
		ImmutableMap<ResourceLocation, ExtendedHiredUnitProfile> immutablemap = builder.build();
		// TODO validate loaded data if applicable
		this.hiredUnitProfiles = immutablemap;
		ExtendedLog.info("Loaded Hired Unit Profile from Json - %s hiredUnitProfiles", hiredUnitProfiles.size()-1);
	}

	/**
	 * Code copied from {@Link JsonReloadListener} due to if this class extended it, it would cause server crashes
	 * when running a reload since all data files would disappear?
	 * Extending {@Link ReloadListener} and copying the method fixes this data corruption
	 * @version Minecraft 1.16.5
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

	public static String serialize(Gson gson, ExtendedHiredUnitProfile data) {
		return gson.toJson(data);
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}

	public static <T> T deserialize(Gson gson, String data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}

	public Set<ResourceLocation> getIds() {
		return this.hiredUnitProfiles.keySet();
	}
}