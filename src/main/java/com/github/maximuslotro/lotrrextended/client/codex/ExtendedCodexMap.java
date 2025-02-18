package com.github.maximuslotro.lotrrextended.client.codex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedCodexEntryType;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedUnknownCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class ExtendedCodexMap {
	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
	private static final int PATH_SUFFIX_LENGTH = ".json".length();
	private static final String directory = "codexdatabase";

	/**
	 * Default language to load en_us
	 * */
	private static final String DEFAULTLANG = "en_us";

	/**
	 * Singleton instance of {@Link ExtendedCodexMap}
	 * */
	private static volatile ExtendedCodexMap instance = loadDefault();

	/**
	 * Internal client-side codex database
	 * */
	private final Map<ExtendedCodexEntryType, Map<ResourceLocation, ExtendedBaseCodexEntry>> storage;

	public ExtendedCodexMap(Map<ExtendedCodexEntryType, Map<ResourceLocation, ExtendedBaseCodexEntry>> pStorage) {
		this.storage = pStorage;
	}

	/**
	 * Gets the codex entry from the database
	 * @param type The type of codex entry to search for
	 * @param pId The codex entry's internal id
	 * @return {@Link ExtendedBaseCodexEntry} present in the map, or ExtendedUnknownCodexEntry.UNKNOWN
	 * */
	public ExtendedBaseCodexEntry getOrDefault(ExtendedCodexEntryType type, String pId) {
		return this.storage.containsKey(type) ? this.storage.get(type).getOrDefault(pId, ExtendedUnknownCodexEntry.UNKNOWN) : ExtendedUnknownCodexEntry.UNKNOWN;
	}

	/**
	 * Checks if the loaded client codex database contains the given entry
	 * @param type The type of codex entry to search for
	 * @param pId The codex entry's internal id
	 * */
	public boolean has(ExtendedCodexEntryType type, ResourceLocation pId) {
		return this.storage.containsKey(type) && this.storage.get(type).containsKey(pId);
	}

	/**
	 * Return the ExtendedCodexMap singleton instance
	 * @return The ExtendedCodexMap singleton instance
	 */
	public static ExtendedCodexMap getInstance() {
		return instance;
	}

	/**
	 * Sets the ExtendedCodexMap singleton instance
	 * @param mapInstance The new ExtendedCodexMap singleton instance
	 */
	public static void inject(ExtendedCodexMap mapInstance) {
		instance = mapInstance;
		for(Entry<ExtendedCodexEntryType, Map<ResourceLocation, ExtendedBaseCodexEntry>> map : mapInstance.storage.entrySet()) {
			ExtendedLog.info("######################"+map.getKey().name()+"######################");
			for(Entry<ResourceLocation, ExtendedBaseCodexEntry> mapInside : map.getValue().entrySet()) {
				ExtendedLog.info(mapInside.getKey().toString()+" : "+ mapInside.getValue().toString());
			}
		}
		ExtendedLog.info(mapInstance.storage.toString());
	}

	/**
	 * Loads the default codex map, defaults to en_us (American English)
	 * @return A {@Link ExtendedCodexMap} instance
	 * */
	public static ExtendedCodexMap loadDefault() {
		Minecraft mc = Minecraft.getInstance();
		return loadFrom(mc.getResourceManager(), DEFAULTLANG);
	}

	/**
	 * Loads the client-side Codex database given the selected language
	 * Initials loads the en_us language map, then overrides it with the selected language map
	 * This is the default behavior for Minecraft's language map
	 * @param pManager A {@Link IResourceManager} for loading the client assets
	 * @param selectedLang A {@Link String} code of the selected minecraft localization
	 * @return A {@Link ExtendedCodexMap} instance of the loaded client-side codex database
	 * */
	public static ExtendedCodexMap loadFrom(IResourceManager pManager, String selectedLang) {
		Map<ResourceLocation, JsonElement> map = getJson(pManager, DEFAULTLANG);
		// Check if should load from other languages
		if(!DEFAULTLANG.equals(selectedLang)) {
			map.putAll(getJson(pManager, selectedLang));
		}
		Map<ExtendedCodexEntryType, Map<ResourceLocation, ExtendedBaseCodexEntry>> dataMap = Maps.newHashMap();
		for(Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
			ExtendedBaseCodexEntry codexEntry = getCodexEntry(entry.getKey(), entry.getValue());
			if(!dataMap.containsKey(codexEntry.getID())) {
				dataMap.put(codexEntry.getID(), Maps.newHashMap());
			}
			dataMap.get(codexEntry.getID()).put(codexEntry.getLocationID(), codexEntry);
		}
		return new ExtendedCodexMap(ImmutableMap.copyOf(dataMap));
	}

	public static Map<ResourceLocation, JsonElement> getJson(IResourceManager pResourceManager, String selectedLang) {
		Map<ResourceLocation, JsonElement> map = Maps.newHashMap();
		// Get codex folder plus slash length
		int i = directory.length() + 1;
		// Get language code length plus underscore length
		int langLength = selectedLang.length()+1;

		for(ResourceLocation listedResource : pResourceManager.listResources(directory, (path) -> {
			return path.endsWith(".json") && path.contains(selectedLang);
		})) {
			// Only load data for loaded mods
			if(!ModList.get().isLoaded(listedResource.getNamespace())) {
				continue;
			}
			String s = listedResource.getPath();
			// Build internal id of the codex entry
			ResourceLocation cleanedCodexFIlePath = new ResourceLocation(listedResource.getNamespace(), s.substring(i, s.length() - langLength - PATH_SUFFIX_LENGTH));
			try (
					IResource iresource = pResourceManager.getResource(listedResource);
					InputStream inputstream = iresource.getInputStream();
					Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
					) {
				JsonElement jsonelement = JSONUtils.fromJson(GSON, reader, JsonElement.class);
				if (jsonelement != null) {
					JsonElement existingJson = map.put(cleanedCodexFIlePath, jsonelement);
					if (existingJson != null) {
						throw new IllegalStateException("Duplicate data file ignored with ID " + cleanedCodexFIlePath);
					}
				} else {
					ExtendedLog.error("Couldn't load codex data file {} from {} as it's null or empty", cleanedCodexFIlePath, listedResource);
				}
			} catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
				ExtendedLog.error("Couldn't parse codex data file {} from {}", cleanedCodexFIlePath, listedResource, jsonparseexception);
			}
		}

		return map;
	}

	/**
	 * Deserializes the passed json information and returns a codex entry
	 * @param locationID A {@Link ResourceLocation} id of the file
	 * @param json The {@Link JsonElement} representation of the output to deserialize
	 * @return {@Link ExtendedBaseCodexEntry} representation of the passed json information
	 * */
	public static ExtendedBaseCodexEntry getCodexEntry(ResourceLocation locationID, JsonElement json){
		if (!json.isJsonObject()) {
			throw new JsonSyntaxException("Codex entry must be of JsonObjects");
		}
		JsonObject jsonObject = json.getAsJsonObject();
		String type = JSONUtils.getAsString(jsonObject, "type");
		try {
			ExtendedCodexEntryType cType = ExtendedCodexEntryType.valueOf(type);
			ExtendedBaseSerializer<?> serializer = ExtendedCodexManager.codexEntryTypes.get(cType);
			if (serializer == null) {
				throw new JsonSyntaxException("Unknown codex entry data type: " + type.toString());
			}
			return serializer.deserializeCodexEntry(locationID, jsonObject);
		}catch(IllegalArgumentException e) {
		}
		throw new JsonSyntaxException("Unknown codex entry type: " + type.toString());
	}

	/**
	 * Serialize the passed {@Link ExtendedBaseCodexEntry} to json
	 * @param entry the instance of {@Link ExtendedBaseCodexEntry} to serialize
	 * @return {@Link JsonObject} of the entry
	 * */
	public static <T extends ExtendedBaseCodexEntry> JsonObject serialize(T condition){
		@SuppressWarnings("unchecked")
		ExtendedBaseSerializer<T> serializer = (ExtendedBaseSerializer<T>) ExtendedCodexManager.codexEntryTypes.get(condition.getID());
		if (serializer == null) {
			throw new JsonSyntaxException("Unknown codex entry type: " + condition.getID().toString());
		}
		return serializer.serialize(condition);
	}
}