package com.github.maximuslotro.lotrrextended.common.traders;

import java.util.Map;

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

public class ExtendedTraderProfileManager extends JsonReloadListener {
	public static final Gson GSON = ExtendedGsonSerializers.createTraderSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	public static final ExtendedTraderProfileManager INSTANCE = new ExtendedTraderProfileManager();
	private Map<ResourceLocation, ExtendedTraderProfile> traderProfiles = ImmutableMap.of();

	/*
	 * Keep Private
	 * Don't allow more than one instance than {@link ExtendedTraderProfileManager#INSTANCE }
	 * */
	private ExtendedTraderProfileManager() {
		super(GSON, "trader_profiles");
	}

	/*
	 * Gets a {@link ExtendedTraderProfile} for a specified ResourceLocation
	 * @param traderProfileId Id of the Profile
	 * @return An instance of {@link ExtendedTraderProfile} if found, else {@link ExtendedTraderProfile#EMPTY}
	 * */
	public ExtendedTraderProfile get(ResourceLocation traderProfileId) {
		return this.traderProfiles.getOrDefault(traderProfileId, ExtendedTraderProfile.EMPTY);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedTraderProfile> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} trader profile, ignoring", ExtendedTraderProfile.EMPTY);
		}
		json.forEach((spawnName, spawnListJson) -> {
			try {
				ExtendedTraderProfile traderProfile = deserialize(GSON, spawnListJson, ExtendedTraderProfile.class);
				if (traderProfile != null) {
					builder.put(spawnName, traderProfile);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Trader Profile {} from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedTraderProfile.EMPTY);
		this.traderProfiles = builder.build();
		ExtendedLog.info("Loaded Trader Profile from Json - {} profiles", traderProfiles.size()-1);
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}

	public static <T> T deserialize(Gson gson, String data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}
}