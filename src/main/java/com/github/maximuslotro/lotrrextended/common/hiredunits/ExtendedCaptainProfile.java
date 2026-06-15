package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedCaptainProfile {
	public static final ExtendedCaptainProfile EMPTY = new ExtendedCaptainProfile(LotrExtendedMod.EMPTY, new ArrayList<>());

	private final ResourceLocation id;
	private final List<ResourceLocation> unitProfileIDs;
	private List<ExtendedServerHiredUnitProfile> unitProfiles;

	public ExtendedCaptainProfile(ResourceLocation id, List<ResourceLocation> unitProfileIDs) {
		this.id = id;
		this.unitProfileIDs = unitProfileIDs;
	}

	/*
	 * Get the id of this ExtendedServerCaptainProfile
	 * */
	public ResourceLocation getId() {
		return id;
	}

	public List<ResourceLocation> getUnitProfileIDs() {
		return unitProfileIDs;
	}

	public List<ExtendedServerHiredUnitProfile> getUnitProfiles() {
		if(unitProfiles!=null) {
			return unitProfiles;
		}
		List<ExtendedServerHiredUnitProfile> serverProfiles = new ArrayList<>();

		for (ResourceLocation profileId : unitProfileIDs) {
			ExtendedServerHiredUnitProfile unitProfile = ExtendedServerHiredUnitProfileManager.INSTANCE.get(profileId);
			if(unitProfile != null && !unitProfile.isEmptyUnitType()) {
				serverProfiles.add(unitProfile);
			}
		}
		unitProfiles = serverProfiles;
		return unitProfiles;
	}

	/*
	 * Serializer for ExtendedServerCaptainProfile
	 * */
	public static class Serializer implements JsonDeserializer<ExtendedCaptainProfile>, JsonSerializer<ExtendedCaptainProfile> {
		@Override
		public ExtendedCaptainProfile deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedServerCaptainProfile");
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load captain profile due to invalid id: %s!", id);
				return null;
			}
			List<ResourceLocation> unitProfileIDs = new ArrayList<>();
			if (jsonobject.has("unitProfileIDs")) {
				JsonArray array = JSONUtils.getAsJsonArray(jsonobject, "unitProfileIDs");
				for (JsonElement element : array) {
					String unitIdStr = element.getAsString();
					if (unitIdStr.contains(":")) {
						unitProfileIDs.add(new ResourceLocation(unitIdStr));
					} else {
						ExtendedLog.warn("Skipping invalid unit profile ID: %s in captain profile %s", unitIdStr, id);
					}
				}
			}

			return new ExtendedCaptainProfile(new ResourceLocation(id), unitProfileIDs);
		}

		@Override
		public JsonElement serialize(ExtendedCaptainProfile captainProfile, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", captainProfile.getId().toString());
			JsonArray array = new JsonArray();
			for (ResourceLocation unitId : captainProfile.getUnitProfileIDs()) {
				array.add(unitId.toString());
			}
			jsonobject.add("unitProfileIDs", array);
			return jsonobject;
		}
	}

	public static class Builder {
		private final ResourceLocation id;
		private List<ResourceLocation> unitProfileIDs = new ArrayList<>();
		private final String modId;

		public Builder(ResourceLocation id, String modId) {
			this.id = id;
			this.modId = modId;
		}

		public ExtendedCaptainProfile.Builder addProfile(ResourceLocation profile) {
			this.unitProfileIDs.add(profile);
			return this;
		}

		/**
		 * Generates an {@link ExtendedCaptainProfile} instance.
		 *
		 * @return The built ExtendedServerCaptainProfile instance
		 */
		public ExtendedCaptainProfile build() {
			ExtendedCaptainProfile spawner = new ExtendedCaptainProfile(id, unitProfileIDs);
			return spawner;
		}


		/**
		 * Generates and saves an {@link ExtendedCaptainProfile} instance using the given consumer.
		 *
		 * @param pConsumer The consumer to accept the built profile
		 * @return The built and consumed ExtendedServerHiredUnitProfile instance
		 */
		public ExtendedCaptainProfile save(Consumer<ExtendedCaptainProfile> pConsumer) {
			ExtendedCaptainProfile spawner = this.build();
			pConsumer.accept(spawner);
			return spawner;
		}
	}
}