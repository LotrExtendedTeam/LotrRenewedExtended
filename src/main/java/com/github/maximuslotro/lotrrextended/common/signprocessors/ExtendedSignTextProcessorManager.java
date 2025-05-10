package com.github.maximuslotro.lotrrextended.common.signprocessors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class ExtendedSignTextProcessorManager extends ReloadListener<Map<String, ExtendedSignTextList>> {
	public static final ExtendedSignTextProcessorManager INSTANCE = new ExtendedSignTextProcessorManager();
	private static final int PATH_SUFFIX_LENGTH = ".txt".length();
	private final String directory = "structure_sign_texts";
	private Map<String, ExtendedSignTextList> signtexts = ImmutableMap.of();

	// Don't allow more than one instance than {@see ExtendedSignTextProcessorManager#INSTANCE }
	private ExtendedSignTextProcessorManager() {}

	public ExtendedSignTextList get(String key) {
		return this.signtexts.getOrDefault(key.toLowerCase(), ExtendedSignTextList.EMPTY);
	}

	@Override
	protected void apply(Map<String, ExtendedSignTextList> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		signtexts = json;
		ExtendedLog.infoParms("Loaded Structure Sign Texts from Json - %s lists", signtexts.size());
	}

	@Override
	protected Map<String, ExtendedSignTextList> prepare(IResourceManager pResourceManager, IProfiler pProfiler) {
		Map<String, ExtendedSignTextList> map = Maps.newHashMap();

		for(ResourceLocation resourcelocation : pResourceManager.listResources(this.directory, (file) -> file.endsWith(".txt"))) {
			if(!resourcelocation.getNamespace().equals((LotrExtendedMod.MODID))){
				continue;
			}
			String fileName = resourcelocation.getPath().substring(directory.length()+1, resourcelocation.getPath().length()-PATH_SUFFIX_LENGTH);
			try (IResource iresource = pResourceManager.getResource(resourcelocation);
					BufferedReader reader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
					) {
				List<String> names = reader.lines().map(String::trim).collect(Collectors.toList());
				if (!names.isEmpty()) {
					map.put(fileName, new ExtendedSignTextList(names));
				} else {
					ExtendedLog.error("Couldn't load data file {} as it's null or empty", resourcelocation);
				}
			} catch (IOException jsonParseException) {
				ExtendedLog.error("Couldn't parse data file from {}", resourcelocation, jsonParseException);
			}
		}

		return map;
	}
}