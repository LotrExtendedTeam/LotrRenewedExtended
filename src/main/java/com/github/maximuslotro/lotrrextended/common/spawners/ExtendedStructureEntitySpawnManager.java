package com.github.maximuslotro.lotrrextended.common.spawners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
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

public class ExtendedStructureEntitySpawnManager extends ReloadListener<Map<ResourceLocation, JsonElement>> {
	public static final ExtendedStructureEntitySpawnManager INSTANCE = new ExtendedStructureEntitySpawnManager();
	public static final Gson GSON = GsonSerializers.createStructureSpawnerSerializer().disableHtmlEscaping().create();
	private static final int PATH_SUFFIX_LENGTH = ".json".length();
	private final String directory = "entity_structure_spawners";
	private Map<ResourceLocation, ExtendedStructureEntitySpawner> spawners = ImmutableMap.of();
	private HashMap<String, List<String>> cachedSpawnLists = new HashMap<>();

	// Don't allow more than one instance than {@Link ExtendedStructureEntitySpawnManager#INSTANCE }
	private ExtendedStructureEntitySpawnManager() {}

	public ExtendedStructureEntitySpawner get(ResourceLocation p_186521_1_) {
		return this.spawners.getOrDefault(p_186521_1_, ExtendedStructureEntitySpawner.EMPTY);
	}

	public List<String> getEntities(String location, List<ExtendedStructureEntitySpawnerEntry> defaultEntrys){
		if(cachedSpawnLists.containsKey(location)) {
			return cachedSpawnLists.get(location);
		}
		List<String> returnList = new ArrayList<>();
		for(ExtendedStructureEntitySpawnerEntry entry : defaultEntrys) {
			entry.getAllEntities().forEach(stringEntity -> {
				if(!returnList.contains(stringEntity)) {
					returnList.add(stringEntity);
				}
			});
		}
		cachedSpawnLists.put(location, returnList);
		return returnList;
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedStructureEntitySpawner> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} loot table, ignoring", LotrExtendedMod.EMPTY);
		}
		json.forEach((spawnName, spawnListJson) -> {
			try {
				ExtendedStructureEntitySpawner spawnerData = deserialize(GSON, spawnListJson, ExtendedStructureEntitySpawner.class);
				if (spawnerData != null) {
					if(spawnerData.isCustom) {
						ExtendedLog.warn("Loaded a Entity Structure Spawner with the customLoad flag enabled, THIS SHOULD NOT BE SET IN JSON! Disabling flag!", spawnName);
						spawnerData.isCustom = false;
					}
					builder.put(spawnName, spawnerData);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Extended Entity Structure Spawner %s from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedStructureEntitySpawner.EMPTY);
		ImmutableMap<ResourceLocation, ExtendedStructureEntitySpawner> immutablemap = builder.build();
		// TODO validate loaded data if applicable
		this.spawners = immutablemap;
		ExtendedLog.info("Loaded Structure Entity Spawners from Json - %s spawners", spawners.size()-1);
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

	public static String serialize(Gson gson, ExtendedStructureEntitySpawner data) {
		return gson.toJson(data);
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}

	public static <T> T deserialize(Gson gson, String data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}

	public Set<ResourceLocation> getIds() {
		return this.spawners.keySet();
	}

}