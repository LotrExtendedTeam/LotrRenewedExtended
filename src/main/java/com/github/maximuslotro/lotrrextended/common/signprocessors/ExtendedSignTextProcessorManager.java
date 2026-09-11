package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.serialization.ExtendedGsonSerializers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedSignTextProcessorManager extends ReloadListener<Map<String, JsonElement>> {
	public static final ExtendedSignTextProcessorManager INSTANCE = new ExtendedSignTextProcessorManager();
	public static final Gson GSON = ExtendedGsonSerializers.createSignTextSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	private static final int PATH_SUFFIX_LENGTH = ".json".length();
	private final String directory = "structure_sign_texts";
	private Map<String, ExtendedSignTextList> signtexts = ImmutableMap.of();
	private String loadingLanguage = "en_us";

	// Don't allow more than one instance than {@see ExtendedSignTextProcessorManager#INSTANCE }
	private ExtendedSignTextProcessorManager() {}

	public ExtendedSignTextList get(String key) {
		if(signtexts.containsKey(key)) {
			return signtexts.get(key);
		}
		ExtendedLog.warn("Sign asked for missing signTextList {}, passing empty (maybe disable this message for other modded structure files being placed?)", key);
		return ExtendedSignTextList.EMPTY;
	}

	@Override
	protected void apply(Map<String, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<String, ExtendedSignTextList> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY.getPath());
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} sign text lists, ignoring", ExtendedSignTextList.EMPTY);
		}
		json.forEach((listName, signTextListJson) -> {
			try {
				ExtendedSignTextList signTextList = deserialize(GSON, signTextListJson, ExtendedSignTextList.class);
				if (signTextList != null) {
					builder.put(listName, signTextList);
				}
			} catch (JsonSyntaxException exception) {
				ExtendedLog.error("Failed to load Sign Text List {} from file", listName);
				exception.printStackTrace();
			}

		});
		this.signtexts = builder.build();
		ExtendedLog.info("Loaded Structure Sign Texts from Json - {} lists", signtexts.size());
	}

	@Override
	protected Map<String, JsonElement> prepare(IResourceManager pResourceManager, IProfiler pProfiler) {
		Map<String, JsonElement> map = loadJson(pResourceManager, "en_us");
		if(!"en_us".equals(loadingLanguage)) {
			map.putAll(loadJson(pResourceManager, loadingLanguage));
		}
		return map;
	}

	protected Map<String, JsonElement> loadJson(IResourceManager pResourceManager, String selectedLang){
		Map<String, JsonElement> map = Maps.newHashMap();
		for(ResourceLocation resourcelocation : pResourceManager.listResources(this.directory, (path) -> {return path.endsWith(".json") && path.contains(selectedLang);})) {
			if(!resourcelocation.getNamespace().equals((LotrExtendedMod.MODID))){
				continue;
			}
			String fileName = resourcelocation.getPath().substring(directory.length()+1, resourcelocation.getPath().length() -(selectedLang.length()+1) -PATH_SUFFIX_LENGTH);
			try (IResource iresource = pResourceManager.getResource(resourcelocation);
					BufferedReader reader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
					) {
				JsonElement jsonelement = JSONUtils.fromJson(GSON, reader, JsonElement.class);
				if (jsonelement != null) {
					map.put(fileName, jsonelement);
				} else {
					ExtendedLog.error("Couldn't load data file {} from {} as it's null or empty", fileName, resourcelocation);
				}
			} catch (IOException jsonParseException) {
				ExtendedLog.error("Couldn't parse data file {} from {}", fileName, resourcelocation, jsonParseException);
			}
		}
		return map;
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) throws JsonSyntaxException {
		return gson.fromJson(data, classToConvert);
	}
}