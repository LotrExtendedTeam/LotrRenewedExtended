package com.github.maximuslotro.lotrrextended.common.spawners;

import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.json.GsonSerializers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.LootTables;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedStructureEntitySpawnManager extends JsonReloadListener {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = GsonSerializers.createStructureSpawnerSerializer().create();
	private Map<ResourceLocation, ExtendedStructureEntitySpawner> tables = ImmutableMap.of();

	public ExtendedStructureEntitySpawnManager() {
		super(GSON, "entity_structure_spawners");
	}

	public ExtendedStructureEntitySpawner get(ResourceLocation p_186521_1_) {
		return this.tables.getOrDefault(p_186521_1_, ExtendedStructureEntitySpawner.EMPTY);
	}

	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedStructureEntitySpawner> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(ExtendedStructureEntitySpawners.EMPTY);
		if (jsonelement != null) {
			LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", (Object) LootTables.EMPTY);
		}

		json.forEach((spawnName, spawnListJson) -> {
			try {
				ExtendedStructureEntitySpawner spawnerData = GSON.fromJson(spawnListJson, ExtendedStructureEntitySpawner.class);
				if (spawnerData != null) {
					builder.put(spawnName, spawnerData);
				}
			} catch (Exception exception) {
				ExtendedLog.warn("Failed to load Extended Entity Structure Spawner %s from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(ExtendedStructureEntitySpawners.EMPTY, ExtendedStructureEntitySpawner.EMPTY);
		ImmutableMap<ResourceLocation, ExtendedStructureEntitySpawner> immutablemap = builder.build();
		// TODO validate loaded data if applicable
		this.tables = immutablemap;
	}

	public static JsonElement serialize(ExtendedStructureEntitySpawner data) {
		return GSON.toJsonTree(data);
	}

	public Set<ResourceLocation> getIds() {
		return this.tables.keySet();
	}
}