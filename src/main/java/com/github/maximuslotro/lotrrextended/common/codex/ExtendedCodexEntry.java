package com.github.maximuslotro.lotrrextended.common.codex;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.codex.benchmarks.IBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtendedCodexEntry {
	public static final ExtendedCodexEntry UNKNOWN = new ExtendedCodexEntry(LotrExtendedMod.EMPTY, ExtendedCodexEntryType.UNKNOWN, new HashMap<>(), new String[0]);
	private final ResourceLocation id;
	private final ExtendedCodexEntryType type;
	private final Map<String, Criterion> criteria;
	private final String[] requirements;

	public ExtendedCodexEntry(ResourceLocation id, ExtendedCodexEntryType type, Map<String, Criterion> pCriteria, String[] pRequirements) {
		this.id = id;
		this.type = type;
		this.criteria = pCriteria;
		this.requirements = pRequirements;
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
	 * Get the {@link Criterion Criteria} used to decide the completion of this {@code ExtendedCodexEntry}. Each key-value pair
	 * consists of a {@code Criterion} and its name.
	 * 
	 * @return the criteria used to decide the completion of this {@code ExtendedCodexEntry}
	 */
	public Map<String, Criterion> getCriteria() {
		return this.criteria;
	}

	/**
	 * Get the list of requirements this {@code ExtendedCodexEntry} has.
	 * 
	 * @return {@code this.requirements}
	 */
	public String[] getRequirements() {
		return this.requirements;
	}

	/**
	 * Get how many requirements this {@code ExtendedCodexEntry} has.
	 * 
	 * @return {@code this.getRequirements().length}
	 */
	public int getMaxCriteraRequired() {
		return this.requirements.length;
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
				ExtendedLog.warn("Failed to load codex due to invalid id: %s!", id);
				throw new JsonSyntaxException("Failed to load codex due to invalid id");
			}
			ExtendedCodexEntryType type = ExtendedCodexEntryType.valueOf(jsonobject.get("type").getAsString());
			Map<String, Criterion> map = Criterion.criteriaFromJson(JSONUtils.getAsJsonObject(jsonobject, "criteria"), context);
			if (map.isEmpty()) {
				throw new JsonSyntaxException("Advancement criteria cannot be empty");
			} else {
				JsonArray jsonarray = JSONUtils.getAsJsonArray(jsonobject, "requirements", new JsonArray());
				String[] requirementsList = new String[jsonarray.size()];

				for(int i = 0; i < jsonarray.size(); ++i) {
					requirementsList[i] = JSONUtils.convertToString(jsonarray.get(i), "requirements[" + i + "]");
				}
				// Fix empty requirements list using criteria
				if (requirementsList.length == 0) {
					requirementsList = new String[map.size()];
					int k = 0;
					for(String s2 : map.keySet()) {
						requirementsList[k++] = s2;
					}
				}
				// Check for invalid requirements
				if(requirementsList.length==0 && map.isEmpty()) {
					throw new JsonSyntaxException("Requirement entry cannot be empty");
				}
				for(String criteriaID : requirementsList) {
					if (!map.containsKey(criteriaID)) {
						throw new JsonSyntaxException("Unknown required criterion '" + criteriaID + "'");
					}
				}
				for(String criteriaID : map.keySet()) {
					if (!ArrayUtils.contains(requirementsList, criteriaID)) {
						throw new JsonSyntaxException("Criterion '" + criteriaID + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
					}
				}
				return new ExtendedCodexEntry(new ResourceLocation(id), type, map, requirementsList);
			}
		}

		@Override
		public JsonElement serialize(ExtendedCodexEntry codexEntry, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", codexEntry.getId().toString());
			jsonobject.addProperty("type", codexEntry.getType().name());
			JsonObject jsonobject1 = new JsonObject();

			for(Entry<String, Criterion> entry : codexEntry.criteria.entrySet()) {
				jsonobject1.add(entry.getKey(), entry.getValue().serializeToJson());
			}
			jsonobject.add("criteria", jsonobject1);

			JsonArray jsonarray1 = new JsonArray();
			for(String criteriaID : codexEntry.requirements) {
				jsonarray1.add(criteriaID);
			}
			jsonobject.add("requirements", jsonarray1);

			return jsonobject;
		}
	}

	public static class Builder {
		private ExtendedCodexEntryType type;
		private Map<String, Criterion> criteria;
		private String[] requirements;

		public Builder(ExtendedCodexEntryType type) {
			this.type = type;
		}

		/**
		 * Adds a criterion to the list of criteria
		 */
		public ExtendedCodexEntry.Builder addCriterion(String pKey, IBenchmarkInstance pbenchmark) {
			return this.addCriterion(pKey, new Criterion(pbenchmark));
		}


		/**
		 * Adds a criterion to the list of criteria
		 */
		public ExtendedCodexEntry.Builder addCriterion(String pKey, Criterion pbenchmark) {
			if (this.criteria.containsKey(pKey)) {
				throw new IllegalArgumentException("Duplicate benchmark " + pKey);
			} else {
				this.criteria.put(pKey, pbenchmark);
				return this;
			}
		}

		/**
		 * Generate a ExtendedStructureEntitySpawner
		 */
		private ExtendedCodexEntry build(ResourceLocation id) {
			if (this.requirements == null) {
				requirements = criteria.keySet().toArray(new String[criteria.keySet().size()]);
			}
			ExtendedCodexEntry spawner = new ExtendedCodexEntry(id, type, criteria, requirements);
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

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ExtendedCodexEntry)) {
			return false;
		} else {
			ExtendedCodexEntry advancement = (ExtendedCodexEntry)object;
			return this.id.equals(advancement.id);
		}
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
}
