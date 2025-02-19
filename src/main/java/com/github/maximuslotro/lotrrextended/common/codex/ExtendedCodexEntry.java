package com.github.maximuslotro.lotrrextended.common.codex;

import java.lang.reflect.Type;
import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedCodexEntry {
	public static final ExtendedCodexEntry UNKNOWN = new ExtendedCodexEntry(LotrExtendedMod.EMPTY, ExtendedCodexEntryType.UNKNOWN);
	private final ResourceLocation id;
	private final ExtendedCodexEntryType type;

	public ExtendedCodexEntry(ResourceLocation id, ExtendedCodexEntryType type) {
		this.id = id;
		this.type = type;
	}

	/**
	 * Gets the id of this codex entry
	 * @return A {@Link ResourceLocation} id of this
	 * */
	public ResourceLocation getId() {
		return id;
	}

	/**
	 * Gets the codex type of this codex entry
	 * @return The {@Link ExtendedCodexEntryType} type of this
	 * */
	public ExtendedCodexEntryType getType() {
		return type;
	}

	/**
	 * Serializer for {@Link ExtendedCodexEntry}
	 * */
	public static class Serializer implements JsonDeserializer<ExtendedCodexEntry>, JsonSerializer<ExtendedCodexEntry> {
		@Override
		public ExtendedCodexEntry deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedCodexEntry");
			String id = jsonobject.get("id").getAsString();
			if(!id.contains(":")) {
				ExtendedLog.warn("Failed to load hired unit profile due to invalid id: %s!", id);
				return UNKNOWN;
			}
			ExtendedCodexEntryType type = ExtendedCodexEntryType.valueOf(jsonobject.get("type").getAsString());
			return new ExtendedCodexEntry(new ResourceLocation(id), type);
		}

		@Override
		public JsonElement serialize(ExtendedCodexEntry codexEntry, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", codexEntry.getId().toString());
			jsonobject.addProperty("type", codexEntry.getType().name());
			return jsonobject;
		}
	}

	public static class Builder {
		private ExtendedCodexEntryType type;

		public Builder(ExtendedCodexEntryType type) {
			this.type = type;
		}

		/**
		 * Generate a ExtendedStructureEntitySpawner
		 */
		private ExtendedCodexEntry build(ResourceLocation id) {
			ExtendedCodexEntry spawner = new ExtendedCodexEntry(id, type);
			return spawner;
		}

		/**
		 * Generate a ExtendedStructureEntitySpawner and save with a Consumer
		 */
		public ExtendedCodexEntry save(Consumer<ExtendedCodexEntry> pConsumer, String TYPE, ResourceLocation pId) {
			ExtendedCodexEntry spawner = this.build(new ResourceLocation(pId.getNamespace(), TYPE+"_"+pId.getPath()));
			pConsumer.accept(spawner);
			return spawner;
		}
	}
}
