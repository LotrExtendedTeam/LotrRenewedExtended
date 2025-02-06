package com.github.maximuslotro.lotrrextended.common.hiredunits;

import java.lang.reflect.Type;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.spawners.ExtendedSpawners;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedHiredUnitEntry {
	public static final ExtendedHiredUnitEntry EMPTY = new ExtendedHiredUnitEntry(ExtendedSpawners.EMPTY, false, "", "", "");
	private final ResourceLocation id;
	private final boolean translateStrings;
	private final String entity_title;
	private final String entity_shortname;
	private final String entity_unit_type;
	public ExtendedHiredUnitEntry(ResourceLocation id, boolean translateStrings, String entity_title, String entity_shortname, String entity_unit_type) {
		this.id = id;
		this.translateStrings = translateStrings;
		this.entity_title = entity_title;
		this.entity_shortname = entity_shortname;
		this.entity_unit_type = entity_unit_type;

	}

	public ResourceLocation getId() {
		return id;
	}

	public boolean isTranslateStrings() {
		return translateStrings;
	}

	public String getEntity_title() {
		return entity_title;
	}

	public String getEntity_shortname() {
		return entity_shortname;
	}

	public String getEntity_unit_type() {
		return entity_unit_type;
	}

	/**
	 * Check if unitType is of EMPTY type
	 * Could mean some data corruption happened
	 */
	public boolean isEmptyUnitType() {
		return this.equals(ExtendedHiredUnitEntry.EMPTY) || id.equals(ExtendedSpawners.EMPTY);
	}

	public static class Serializer implements JsonDeserializer<ExtendedHiredUnitEntry>, JsonSerializer<ExtendedHiredUnitEntry> {
		@Override
		public ExtendedHiredUnitEntry deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedHiredUnitEntry");
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
			return new ExtendedHiredUnitEntry(new ResourceLocation(id), translateStrings, entity_title, entity_shortname, entity_unit_type);
		}

		@Override
		public JsonElement serialize(ExtendedHiredUnitEntry entitySpawner, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", entitySpawner.getId().toString());
			JsonObject languageObject = new JsonObject();
			languageObject.addProperty("translateStrings", entitySpawner.isTranslateStrings());
			languageObject.addProperty("entity_title", entitySpawner.getEntity_title());
			languageObject.addProperty("entity_shortname", entitySpawner.getEntity_shortname());
			languageObject.addProperty("entity_unit_type", entitySpawner.getEntity_unit_type());
			jsonobject.add("language", languageObject);
			return jsonobject;
		}
	}
}