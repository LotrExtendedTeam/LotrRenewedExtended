package com.github.maximuslotro.lotrrextended.common.codex;

import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedCodexManager extends JsonReloadListener {
	public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
	private static final int PATH_SUFFIX_LENGTH = ".json".length();
	private final String directory = "codex";
	private Map<ResourceLocation, ExtendedCodexEntry> codexEntries = ImmutableMap.of();
	private final LootPredicateManager predicateManager;

	public ExtendedCodexManager(LootPredicateManager predicateManager) {
		super(GSON, "codex");
		this.predicateManager=predicateManager;
	}

	/*
	 * Gets a {@Link ExtendedCodexManager} for a specified ResourceLocation
	 * @param codedID Id of the codex entry
	 * @return An instance of {@Link ExtendedCodexManager} if found, else {@Link ExtendedCodexEntry#UNKNOWN}
	 * */
	public ExtendedCodexEntry get(ResourceLocation codedID) {
		return this.codexEntries.getOrDefault(codedID, ExtendedCodexEntry.UNKNOWN);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		
		Builder<ResourceLocation, ExtendedCodexEntry> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} codex entry, ignoring", ExtendedCodexEntry.UNKNOWN);
		}
		json.forEach((codexId, codexListJson) -> {
			try {
				JsonObject jsonobject = JSONUtils.convertToJsonObject(codexListJson, "codex");
				ExtendedCodexEntry codexEntry = ExtendedCodexEntry.Builder.deserialize(jsonobject, new ConditionArrayParser(codexId, this.predicateManager));
				if (codexEntry != null) {
					builder.put(codexId, codexEntry);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Codex Entry %s from file", codexId);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedCodexEntry.UNKNOWN);
		this.codexEntries = builder.build();
		ExtendedLog.info("Loaded Codex Entries from Json - %s CodexEntries", codexEntries.size()-1);
	}

	public static String serialize(Gson gson, ExtendedCodexEntry data) {
		return gson.toJson(data);
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}

	public static <T> T deserialize(Gson gson, String data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}
}