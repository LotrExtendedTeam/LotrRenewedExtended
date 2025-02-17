package com.github.maximuslotro.lotrrextended.client.codex.serializers;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;
import com.google.gson.JsonObject;

public abstract interface ExtendedRegistrySerializer<V extends ExtendedBaseCodexEntry> extends ExtendedBaseSerializer<V>{

	public default JsonObject serializeCodexEntry(JsonObject json, V entry) {
		return serializeRegistryCodexEntry(json, entry);
	}

	public abstract JsonObject serializeRegistryCodexEntry(JsonObject json, V entry);
}
