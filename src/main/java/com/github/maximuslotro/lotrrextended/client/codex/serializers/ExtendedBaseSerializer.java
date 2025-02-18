package com.github.maximuslotro.lotrrextended.client.codex.serializers;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;
import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedCodexEntryType;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

public abstract interface ExtendedBaseSerializer<V extends ExtendedBaseCodexEntry> {

	/**
	 * Base deserializer method to handle the registry location value
	 * @param locationID A {@Link ResourceLocation} id of the file
	 * @param json The {@Link JsonObject} representation of the output to deserialize
	 * @return {@Link ExtendedBaseCodexEntry} instance of the entry
	 * */
	public abstract V deserializeCodexEntry(ResourceLocation locationID, JsonObject json);

	/**
	 * Internal serialize method for codex entries
	 * MODDERS DO NOT USE!
	 * @param entry the instance of {@Link ExtendedBaseCodexEntry} to serialize
	 * @return {@Link JsonObject} of the entry
	 * */
	public default JsonObject serialize(V entry) {
		JsonObject json = new JsonObject();
		json.addProperty("type", getID().name());
		return serializeCodexEntry(json, entry);
	}

	/**
	 * Base serialize method for codex entries
	 * @param json A template {@Link JsonObject} to append to
	 * @param entry the instance of {@Link ExtendedBaseCodexEntry} to serialize
	 * @return {@Link JsonObject} of the entry
	 * */
	public abstract JsonObject serializeCodexEntry(JsonObject json, V entry);

	/*
	 * Gets the {@Link ExtendedCodexEntryType} for this codex entry
	 * @return A {@Link ExtendedCodexEntryType}
	 * */
	public abstract ExtendedCodexEntryType getID();
}
