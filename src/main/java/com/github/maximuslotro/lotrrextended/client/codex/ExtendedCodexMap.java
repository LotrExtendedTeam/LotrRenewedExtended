package com.github.maximuslotro.lotrrextended.client.codex;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedUnknownCodexEntry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.LanguageMap;

public class ExtendedCodexMap {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new Gson();
	/** Pattern that matches numeric variable placeholders in a resource string, such as "%d", "%3$d", "%.2f" */
	private static final Pattern UNSUPPORTED_FORMAT_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
	private final Map<String, ExtendedBaseCodexEntry> storage;
	private static volatile ExtendedCodexMap instance = loadDefault();

	public ExtendedCodexMap(Map<String, ExtendedBaseCodexEntry> pStorage) {
		this.storage = pStorage;
	}

	/**
	 * Return the ExtendedCodexMap singleton instance
	 */
	public static ExtendedCodexMap getInstance() {
		return instance;
	}

	public static void inject(ExtendedCodexMap mapInstance) {
		instance = mapInstance;
	}

	private static ExtendedCodexMap loadDefault() {
		Builder<String, ExtendedBaseCodexEntry> builder = ImmutableMap.builder();
//		try (InputStream inputstream = ExtendedCodexMap.class.getResourceAsStream("/assets/minecraft/lang/en_us.json")) {
//			loadFromJson(inputstream, builder::put);
//		} catch (JsonParseException | IOException ioexception) {
//			LOGGER.error("Couldn't read strings from /assets/minecraft/lang/en_us.json", (Throwable)ioexception);
//		}
		return new ExtendedCodexMap(new HashMap<>(builder.build()));
	}

	public static ExtendedCodexMap loadFrom(IResourceManager pManager, List<String> list) {
		Map<String, ExtendedBaseCodexEntry> dataMap = Maps.newHashMap();
		for(String language : list) {
//			String filePath = String.format("lang/%s.json", language);
//
//			for(String namespace : pManager.getNamespaces()) {
//				try {
//					ResourceLocation resourcelocation = new ResourceLocation(namespace, filePath);
//					appendFrom(pManager.getResources(resourcelocation), dataMap);
//				} catch (FileNotFoundException filenotfoundexception) {
//				} catch (Exception exception) {
//					LOGGER.warn("Skipped language file: {}:{} ({})", namespace, filePath, exception.toString());
//				}
//			}
		}
		return new ExtendedCodexMap(ImmutableMap.copyOf(dataMap));
	}
	public static void loadFromJson(InputStream p_240593_0_, BiConsumer<String, String> p_240593_1_) {
		JsonObject jsonobject = GSON.fromJson(new InputStreamReader(p_240593_0_, StandardCharsets.UTF_8), JsonObject.class);
		for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
			String s = UNSUPPORTED_FORMAT_PATTERN.matcher(JSONUtils.convertToString(entry.getValue(), entry.getKey())).replaceAll("%$1s");
			p_240593_1_.accept(entry.getKey(), s);
		}

	}
	private static void appendFrom(List<IResource> pResources, Map<String, String> pStorage) {
		for(IResource iresource : pResources) {
			try (InputStream inputstream = iresource.getInputStream()) {
				LanguageMap.loadFromJson(inputstream, pStorage::put);
			} catch (IOException ioexception) {
				LOGGER.warn("Failed to load translations from {}", iresource, ioexception);
			}
		}

	}

	public ExtendedBaseCodexEntry getOrDefault(String pId) {
		return this.storage.getOrDefault(pId, ExtendedUnknownCodexEntry.UNKNOWN);
	}

	public boolean has(String pId) {
		return this.storage.containsKey(pId);
	}
}