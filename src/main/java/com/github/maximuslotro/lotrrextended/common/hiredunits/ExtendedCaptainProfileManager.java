package com.github.maximuslotro.lotrrextended.common.hiredunits;

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

public class ExtendedCaptainProfileManager extends JsonReloadListener {
	public static final Gson GSON = ExtendedGsonSerializers.createCaptainSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	public static final ExtendedCaptainProfileManager INSTANCE = new ExtendedCaptainProfileManager();
	private Map<ResourceLocation, ExtendedCaptainProfile> captainProfiles = ImmutableMap.of();

	/*
	 * Keep Private
	 * Don't allow more than one instance than {@link ExtendedServerCaptainProfileManager#INSTANCE }
	 * */
	private ExtendedCaptainProfileManager() {
		super(GSON, "captain_profiles");
	}

	/*
	 * Gets a {@link ExtendedServerCaptainProfile} for a specified ResourceLocation
	 * @param captainProfileId Id of the Profile
	 * @return An instance of {@link ExtendedServerCaptainProfile} if found, else {@link ExtendedServerCaptainProfile#EMPTY}
	 * */
	public ExtendedCaptainProfile get(ResourceLocation captainProfileId) {
		return this.captainProfiles.getOrDefault(captainProfileId, ExtendedCaptainProfile.EMPTY);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedCaptainProfile> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} captain pool, ignoring", ExtendedCaptainProfile.EMPTY);
		}
		json.forEach((spawnName, spawnListJson) -> {
			try {
				ExtendedCaptainProfile captainProfile = deserialize(GSON, spawnListJson, ExtendedCaptainProfile.class);
				if (captainProfile != null) {
					if(!captainProfile.getId().equals(spawnName)) {
						ExtendedLog.warn("Loaded a Captain Profile that has a diffrent file id and file name, THEY SHOULD BE THE SAME!, CHANGING TO FILE NAME ID! {}", spawnName);
						captainProfile = new ExtendedCaptainProfile(spawnName, captainProfile.getUnitProfileIDs(), captainProfile.getInterractionAlignmentRequired());
					}
					builder.put(spawnName, captainProfile);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Captain Profile {} from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedCaptainProfile.EMPTY);
		this.captainProfiles = builder.build();
		ExtendedLog.info("Loaded Captain Profile from Json - {} captainProfiles", captainProfiles.size()-1);
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}
}