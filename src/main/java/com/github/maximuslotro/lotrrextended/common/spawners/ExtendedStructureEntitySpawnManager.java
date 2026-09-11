package com.github.maximuslotro.lotrrextended.common.spawners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.minecraft.util.ResourceLocation;

public class ExtendedStructureEntitySpawnManager extends JsonReloadListener {
	public static final Gson GSON = ExtendedGsonSerializers.createStructureSpawnerSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	public static final ExtendedStructureEntitySpawnManager INSTANCE = new ExtendedStructureEntitySpawnManager();
	private Map<ResourceLocation, ExtendedStructureEntitySpawner> spawners = ImmutableMap.of();
	/**
	 * A Map of spawn ids and connected lists of entity ids for each spawn id
	 * */
	private HashMap<String, List<ResourceLocation>> cachedSpawnLists = new HashMap<>();

	// Don't allow more than one instance than {@see ExtendedStructureEntitySpawnManager#INSTANCE }
	private ExtendedStructureEntitySpawnManager() {
		super(GSON, "entity_structure_spawners");
	}

	public ExtendedStructureEntitySpawner get(ResourceLocation p_186521_1_) {
		return this.spawners.getOrDefault(p_186521_1_, ExtendedStructureEntitySpawner.EMPTY);
	}

	public List<ResourceLocation> getEntities(String location, List<ExtendedStructureEntitySpawnerEntry> defaultEntrys){
		if(cachedSpawnLists.containsKey(location)) {
			return cachedSpawnLists.get(location);
		}
		List<ResourceLocation> returnList = new ArrayList<>();
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
						ExtendedLog.warn("Loaded a Entity Structure Spawner with the customLoad flag enabled {}, THIS SHOULD NOT BE SET IN JSON! Disabling flag!", spawnName);
						spawnerData.isCustom = false;
					}
					builder.put(spawnName, spawnerData);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Extended Entity Structure Spawner {} from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedStructureEntitySpawner.EMPTY);
        // TODO validate loaded data if applicable
		this.spawners = builder.build();
		ExtendedLog.info("Loaded Structure Entity Spawners from Json - {} spawners", spawners.size()-1);
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