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

public class ExtendedHiredUnitProfileManager extends JsonReloadListener {
	public static final Gson GSON = ExtendedGsonSerializers.createHiredUnitSerializer().disableHtmlEscaping().setPrettyPrinting().create();
	public static final ExtendedHiredUnitProfileManager INSTANCE = new ExtendedHiredUnitProfileManager();
	private Map<ResourceLocation, ExtendedHiredUnitProfile> hiredUnitProfiles = ImmutableMap.of();

	/*
	 * Keep Private
	 * Don't allow more than one instance than {@link ExtendedServerHiredUnitProfileManager#INSTANCE }
	 * */
	private ExtendedHiredUnitProfileManager() {
		super(GSON, "hired_unit_profiles");
	}

	/*
	 * Gets a {@link ExtendedServerHiredUnitProfile} for a specified ResourceLocation
	 * @param unitProfileId Id of the Profile
	 * @return An instance of {@link ExtendedServerHiredUnitProfile} if found, else {@link ExtendedServerHiredUnitProfile#EMPTY}
	 * */
	public ExtendedHiredUnitProfile get(ResourceLocation unitProfileId) {
		return this.hiredUnitProfiles.getOrDefault(unitProfileId, ExtendedHiredUnitProfile.EMPTY);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> json, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
		Builder<ResourceLocation, ExtendedHiredUnitProfile> builder = ImmutableMap.builder();
		JsonElement jsonelement = json.remove(LotrExtendedMod.EMPTY);
		if (jsonelement != null) {
			ExtendedLog.warn("Datapack tried to redefine {} hired unit pool, ignoring", ExtendedHiredUnitProfile.EMPTY);
		}
		json.forEach((spawnName, spawnListJson) -> {
			try {
				ExtendedHiredUnitProfile hiredUnitProfile = deserialize(GSON, spawnListJson, ExtendedHiredUnitProfile.class);
				if (hiredUnitProfile != null) {
					if(!hiredUnitProfile.getId().equals(spawnName)) {
						ExtendedLog.warn("Loaded a Hired Unit Profile that has a diffrent file id and file name, THEY SHOULD BE THE SAME!, CHANGING TO FILE NAME ID! {}", spawnName);
						hiredUnitProfile = new ExtendedHiredUnitProfile(spawnName, hiredUnitProfile.stringsAreTranslated(), hiredUnitProfile.getEntityUnitTitle(), 
							hiredUnitProfile.getEntityUnitShorthandName(), hiredUnitProfile.getEntityUnitTypeName(), hiredUnitProfile.getEquipmentPools(),
							hiredUnitProfile.getHiringCost(), hiredUnitProfile.isHiringRequiresPledge(), hiredUnitProfile.getHiringAlignmentRequired(), hiredUnitProfile.getUnitType(), hiredUnitProfile.getUnitEntityType());
					}
					builder.put(spawnName, hiredUnitProfile);
				}
			} catch (Exception exception) {
				ExtendedLog.error("Failed to load Hired Unit Profile {} from file", spawnName);
				exception.printStackTrace();
			}

		});
		builder.put(LotrExtendedMod.EMPTY, ExtendedHiredUnitProfile.EMPTY);
		this.hiredUnitProfiles = builder.build();
		ExtendedLog.info("Loaded Hired Unit Profile from Json - {} hiredUnitProfiles", hiredUnitProfiles.size()-1);
	}

	public static <T> T deserialize(Gson gson, JsonElement data, Class<T> classToConvert) {
		return gson.fromJson(data, classToConvert);
	}
}