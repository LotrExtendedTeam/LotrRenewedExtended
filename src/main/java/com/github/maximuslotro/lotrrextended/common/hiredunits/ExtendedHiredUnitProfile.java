package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedHiredUnitProfile {
	public static final ExtendedHiredUnitProfile EMPTY = new ExtendedHiredUnitProfile(ExtendedSpawners.EMPTY, false, "", "", "", new ArrayList<>());
	private final ResourceLocation id;
	private final boolean translateStrings;
	private final String entity_title;
	private final String entity_shortname;
	private final String entity_unit_type;
	private final List<ExtendedHiredUnitEquipmentEntry> items;

	public ExtendedHiredUnitProfile(ResourceLocation id, boolean translateStrings, String entity_title, String entity_shortname, String entity_unit_type, List<ExtendedHiredUnitEquipmentEntry> entries) {
		this.id = id;
		this.translateStrings = translateStrings;
		this.entity_title = entity_title;
		this.entity_shortname = entity_shortname;
		this.entity_unit_type = entity_unit_type;
		this.items = entries;
	}

	/*
	 * Get the id of this ExtendedHiredUnitProfile
	 * */
	public ResourceLocation getId() {
		return id;
	}

	/*
	 * Check if the strings of this profile should be localized
	 * */
	public boolean areStringsTranslated() {
		return translateStrings;
	}

	/*
	 * Gets the string or key for the unit's title
	 * */
	public String getUnitTitle() {
		return entity_title;
	}

	/*
	 * Gets the string or key for the unit's shorthand name
	 * */
	public String getUnitShorthandName() {
		return entity_shortname;
	}

	/*
	 * Gets the string or key for the unit's type name
	 * */
	public String getUnitTypeName() {
		return entity_unit_type;
	}

	/**
	 * Check if unitType is of EMPTY type
	 * Could mean some data corruption happened
	 * */
	public boolean isEmptyUnitType() {
		return this.equals(ExtendedHiredUnitProfile.EMPTY) || id.equals(ExtendedSpawners.EMPTY);
	}

	public List<ExtendedHiredUnitEquipmentEntry> getEntries() {
		return new ArrayList<>(items);
	}

	/*
	 * Serializer for ExtendedHiredUnitProfile
	 * */
	public static class Serializer implements JsonDeserializer<ExtendedHiredUnitProfile>, JsonSerializer<ExtendedHiredUnitProfile> {
		@Override
		public ExtendedHiredUnitProfile deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedHiredUnitProfile");
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load structure spawner due to invalid id: %s!", id);
				return null;
			}
			JsonObject languageObject = jsonobject.get("language").getAsJsonObject();
			boolean translateStrings = languageObject.get("translateStrings").getAsBoolean();
			String entity_title = languageObject.get("entity_title").getAsString();
			String entity_shortname = languageObject.get("entity_shortname").getAsString();
			String entity_unit_type = languageObject.get("entity_unit_type").getAsString();
			List<ExtendedHiredUnitEquipmentEntry> alootpool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "items", new ExtendedHiredUnitEquipmentEntry[0], context, ExtendedHiredUnitEquipmentEntry[].class));
			return new ExtendedHiredUnitProfile(new ResourceLocation(id), translateStrings, entity_title, entity_shortname, entity_unit_type, alootpool);
		}

		@Override
		public JsonElement serialize(ExtendedHiredUnitProfile unitProfile, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", unitProfile.getId().toString());
			JsonObject languageObject = new JsonObject();
			languageObject.addProperty("translateStrings", unitProfile.areStringsTranslated());
			languageObject.addProperty("entity_title", unitProfile.getUnitTitle());
			languageObject.addProperty("entity_shortname", unitProfile.getUnitShorthandName());
			languageObject.addProperty("entity_unit_type", unitProfile.getUnitTypeName());
			jsonobject.add("language", languageObject);
			jsonobject.add("items", context.serialize(unitProfile.items));
			return jsonobject;
		}
	}
}