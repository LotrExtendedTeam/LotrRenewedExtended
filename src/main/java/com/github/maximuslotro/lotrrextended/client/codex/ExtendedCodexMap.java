package com.github.maximuslotro.lotrrextended.client.codex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedCodexEntryType;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedUnknownCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.serializers.ExtendedBaseSerializer;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
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
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new Gson();
	private static final int PATH_SUFFIX_LENGTH = ".json".length();
	private static final String directory = "codexdatabase";
	private static volatile ExtendedCodexMap instance = loadDefault();
	private final Map<String, ExtendedBaseCodexEntry> storage;

	public ExtendedCodexMap(Map<String, ExtendedBaseCodexEntry> pStorage) {
		this.storage = pStorage;
	}

	public ExtendedBaseCodexEntry getOrDefault(String pId) {
		return this.storage.getOrDefault(pId, ExtendedUnknownCodexEntry.UNKNOWN);
	}

	public boolean has(String pId) {
		return this.storage.containsKey(pId);
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
		for(Entry<String, ExtendedBaseCodexEntry> entry : mapInstance.storage.entrySet()) {
			ExtendedLog.info(entry.getKey()+entry.getValue().getLocationID().toString());
		}
	}

	public static ExtendedCodexMap loadDefault() {
		Minecraft mc = Minecraft.getInstance();
		return loadFrom(mc.getResourceManager(), "en_us");
	}

	public static ExtendedCodexMap loadFrom(IResourceManager pManager, String selectedLang) {
		Map<ResourceLocation, JsonElement> map = getJson(pManager);
		Map<String, ExtendedBaseCodexEntry> dataMap = Maps.newHashMap();
		// TODO finish data handeling
		return new ExtendedCodexMap(ImmutableMap.copyOf(dataMap));
	}

	public static Map<ResourceLocation, JsonElement> getJson(IResourceManager pResourceManager) {
		Map<ResourceLocation, JsonElement> map = Maps.newHashMap();
		int i = directory.length() + 1;

		for(ResourceLocation resourcelocation : pResourceManager.listResources(directory, (p_223379_0_) -> {
			return p_223379_0_.endsWith(".json");
		})) {
			// Only load data for loaded mods
			if(!ModList.get().isLoaded(resourcelocation.getNamespace())) {
				continue;
			}
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
					LOGGER.error("Couldn't load data file {} from {} as it's null or empty", resourcelocation1, resourcelocation);
				}
			} catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
				LOGGER.error("Couldn't parse data file {} from {}", resourcelocation1, resourcelocation, jsonparseexception);
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