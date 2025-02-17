package com.github.maximuslotro.lotrrextended.client.codex.serializers;

import com.github.maximuslotro.lotrrextended.client.codex.entry.ExtendedBaseCodexEntry;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

public abstract interface ExtendedBaseSerializer<V extends ExtendedBaseCodexEntry> {

	public abstract V deserializeCodexEntry(JsonObject json);

	public default ResourceLocation getLocation(JsonObject json) {
		return new ResourceLocation(json.get("value").getAsString());
	}

	public default JsonObject serialize(V entry) {
		JsonObject json = new JsonObject();
		json.addProperty("type", getID());
		json.addProperty("value", entry.getLocation().toString());
		return serializeCodexEntry(json, entry);
	}

	public abstract JsonObject serializeCodexEntry(JsonObject json, V entry);

	public abstract String getID();
}
