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
	public static final ExtendedCaptainProfile EMPTY = new ExtendedCaptainProfile(LotrExtendedMod.EMPTY, new ArrayList<>(), 1000);

	private final ResourceLocation id;
	private final List<ResourceLocation> unitProfileIDs;
	private List<ExtendedServerHiredUnitProfile> cachedUnitProfiles;
	/** Required alignment level to access this trader. */
	private final int hiringAlignmentRequired;

	public ExtendedCaptainProfile(ResourceLocation id, List<ResourceLocation> unitProfileIDs, int hiringAlignmentRequired) {
		this.id = id;
		this.unitProfileIDs = unitProfileIDs;
		this.hiringAlignmentRequired = hiringAlignmentRequired;
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

	public int getInterractionAlignmentRequired() {
		return hiringAlignmentRequired;
	}

	public List<ExtendedServerHiredUnitProfile> getUnitProfiles() {
		if(cachedUnitProfiles!=null) {
			return cachedUnitProfiles;
		}
		List<ExtendedServerHiredUnitProfile> serverProfiles = new ArrayList<>();

		for (ResourceLocation profileId : unitProfileIDs) {
			ExtendedServerHiredUnitProfile unitProfile = ExtendedServerHiredUnitProfileManager.INSTANCE.get(profileId);
			if(unitProfile != null && !unitProfile.isEmptyUnitType()) {
				serverProfiles.add(unitProfile);
			}
		}
		cachedUnitProfiles = serverProfiles;
		return cachedUnitProfiles;
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
				ExtendedLog.warn("Failed to load captain profile due to invalid id: {}!", id);
				return null;
			}
			int interractionAlignment = jsonobject.get("alignmentRequired").getAsInt();
			List<ResourceLocation> unitProfileIDs = new ArrayList<>();
			if (jsonobject.has("unitProfileIDs")) {
				JsonArray array = JSONUtils.getAsJsonArray(jsonobject, "unitProfileIDs");
				for (JsonElement element : array) {
					String unitIdStr = element.getAsString();
					if (unitIdStr.contains(":")) {
						unitProfileIDs.add(new ResourceLocation(unitIdStr));
					} else {
						ExtendedLog.warn("Skipping invalid unit profile ID: {} in captain profile {}", unitIdStr, id);
					}
				}
			}

			return new ExtendedCaptainProfile(new ResourceLocation(id), unitProfileIDs, interractionAlignment);
		}

		@Override
		public JsonElement serialize(ExtendedCaptainProfile captainProfile, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", captainProfile.getId().toString());
			jsonobject.addProperty("alignmentRequired", captainProfile.getInterractionAlignmentRequired());
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
		private final int alignmentRequired;

		public Builder(ResourceLocation id, String modId, int interractionAlignmnetRequired) {
			this.id = id;
			this.modId = modId;
			this.alignmentRequired = interractionAlignmnetRequired;
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
			ExtendedCaptainProfile spawner = new ExtendedCaptainProfile(id, unitProfileIDs, alignmentRequired);
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