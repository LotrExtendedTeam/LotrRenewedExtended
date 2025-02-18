package com.github.maximuslotro.lotrrextended.client.codex.serializers;

import com.github.maximuslotro.lotrrextended.client.codex.entry.registry.ExtendedRegistryBasedCodexEntry;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

public abstract interface ExtendedRegistrySerializer<V extends ExtendedRegistryBasedCodexEntry<?>> extends ExtendedBaseSerializer<V>{

	/**
	 * Internal serializer method to handle the registry location value
	 * DO NOT CALL! Use {@Link serializeRegistryCodexEntry}
	 * @param json A template {@Link JsonObject} to append to
	 * @param entry the instance of {@Link ExtendedBaseCodexEntry} to serialize
	 * @return {@Link JsonObject} of the entry
	 * */
	public default JsonObject serializeCodexEntry(JsonObject json, V entry) {
		json.addProperty("value", entry.getLocation().toString());
		return serializeRegistryCodexEntry(json, entry);
	}

	/**
	 * Serializer method for {@Link ExtendedRegistryBasedCodexEntry} to use
	 * @param json A template {@Link JsonObject} to append to
	 * @param entry the instance of {@Link ExtendedBaseCodexEntry} to serialize
	 * @return {@Link JsonObject} of the entry
	 * */
	public abstract JsonObject serializeRegistryCodexEntry(JsonObject json, V entry);

	/**
	 * Internal deserializer method to handle the registry location value
	 * DO NOT CALL! Use {@Link deserializeRegistryCodexEntry}
	 * @param locationID A {@Link ResourceLocation} id of the file
	 * @param json The {@Link JsonObject} representation of the output to deserialize
	 * @return {@Link ExtendedRegistryBasedCodexEntry} instance of the entry
	 * */
	public default V deserializeCodexEntry(ResourceLocation locationID, JsonObject json) {
		return deserializeRegistryCodexEntry(locationID, new ResourceLocation(json.get("value").getAsString()), json);
	}

	/**
	 * Deserializer method to handle the registry location value
	 * @param locationID A {@Link ResourceLocation} id of the file
	 * @param location A {@Link ResourceLocation} registry location of the {@Link ExtendedRegistryBasedCodexEntry}
	 * @param json The {@Link JsonObject} representation of the output to deserialize
	 * @return {@Link ExtendedRegistryBasedCodexEntry} instance of the entry
	 * */
	public abstract V deserializeRegistryCodexEntry(ResourceLocation locationID, ResourceLocation location, JsonObject json);
	
}
