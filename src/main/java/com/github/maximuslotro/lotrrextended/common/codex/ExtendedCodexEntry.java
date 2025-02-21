package com.github.maximuslotro.lotrrextended.common.codex;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmark;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedIBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedCodexEntryType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

/**
 * Codex implementation based upon {@Link Advancement}'s logic 
 * */
public class ExtendedCodexEntry {
	public static final ExtendedCodexEntry UNKNOWN = new ExtendedCodexEntry(LotrExtendedMod.EMPTY, ExtendedCodexEntryType.UNKNOWN, new HashMap<>(), new String[0]);
	private final ResourceLocation id;
	private final ExtendedCodexEntryType type;
	private final Map<String, ExtendedBenchmark> benchmarks;
	private final String[] requirements;

	public ExtendedCodexEntry(ResourceLocation id, ExtendedCodexEntryType type, Map<String, ExtendedBenchmark> benchmarks, String[] pRequirements) {
		this.id = id;
		this.type = type;
		this.benchmarks = benchmarks;
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
	 * Get the {@link Benchmarks Benchmark} used to decide the completion of this {@code ExtendedCodexEntry}. Each key-value pair
	 * consists of a {@code Benchmark} and its name.
	 * 
	 * @return the benchmark used to decide the completion of this {@code ExtendedCodexEntry}
	 */
	public Map<String, ExtendedBenchmark> getBenchmarks() {
		return this.benchmarks;
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
	public int getMaxBenchmarksRequired() {
		return this.requirements.length;
	}

	public static class Builder {
		private ExtendedCodexEntryType type;
		private Map<String, ExtendedBenchmark> benchmarks;
		private String[] requirements;

		public Builder(ExtendedCodexEntryType type) {
			this.type = type;
		}

		/**
		 * Adds a benchmark to the list of benchmarks
		 */
		public ExtendedCodexEntry.Builder addBenchmarks(String pKey, ExtendedIBenchmarkInstance pbenchmark) {
			return this.addBenchmark(pKey, new ExtendedBenchmark(pbenchmark));
		}


		/**
		 * Adds a benchmark to the list of benchmarks
		 */
		public ExtendedCodexEntry.Builder addBenchmark(String pKey, ExtendedBenchmark pbenchmark) {
			if (this.benchmarks.containsKey(pKey)) {
				throw new IllegalArgumentException("Duplicate benchmark " + pKey);
			} else {
				this.benchmarks.put(pKey, pbenchmark);
				return this;
			}
		}

		/**
		 * Generate a ExtendedStructureEntitySpawner
		 */
		private ExtendedCodexEntry build(ResourceLocation id) {
			if (this.requirements == null) {
				requirements = benchmarks.keySet().toArray(new String[benchmarks.keySet().size()]);
			}
			ExtendedCodexEntry spawner = new ExtendedCodexEntry(id, type, benchmarks, requirements);
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

		public static ExtendedCodexEntry deserialize(JsonElement json, ConditionArrayParser contextParser) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedCodexEntry");
			String id = jsonobject.get("id").getAsString();
			if(!id.contains(":")) {
				ExtendedLog.warn("Failed to load codex due to invalid id: %s!", id);
				throw new JsonSyntaxException("Failed to load codex due to invalid id");
			}
			ExtendedCodexEntryType type = ExtendedCodexEntryType.valueOf(jsonobject.get("type").getAsString());
			Map<String, ExtendedBenchmark> map = ExtendedBenchmark.benchmarkFromJson(JSONUtils.getAsJsonObject(jsonobject, "benchmarks"), contextParser);
			if (map.isEmpty()) {
				throw new JsonSyntaxException("Codex benchmarks cannot be empty");
			} else {
				JsonArray jsonarray = JSONUtils.getAsJsonArray(jsonobject, "requirements", new JsonArray());
				String[] requirementsList = new String[jsonarray.size()];

				for(int i = 0; i < jsonarray.size(); ++i) {
					requirementsList[i] = JSONUtils.convertToString(jsonarray.get(i), "requirements[" + i + "]");
				}
				// Fix empty requirements list using benchmarks
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
				for(String benchmarkID : requirementsList) {
					if (!map.containsKey(benchmarkID)) {
						throw new JsonSyntaxException("Unknown required benchmark '" + benchmarkID + "'");
					}
				}
				for(String benchmarkID : map.keySet()) {
					if (!ArrayUtils.contains(requirementsList, benchmarkID)) {
						throw new JsonSyntaxException("Benchmark '" + benchmarkID + "' isn't a requirement for completion. This isn't supported behaviour, all benchmarks must be required.");
					}
				}
				return new ExtendedCodexEntry(new ResourceLocation(id), type, map, requirementsList);
			}
		}

	}

	public static JsonElement serialize(ExtendedCodexEntry codexEntry) {
		JsonObject jsonobject = new JsonObject();
		jsonobject.addProperty("id", codexEntry.getId().toString());
		jsonobject.addProperty("type", codexEntry.getType().name());
		JsonObject jsonobject1 = new JsonObject();

		for(Entry<String, ExtendedBenchmark> entry : codexEntry.benchmarks.entrySet()) {
			jsonobject1.add(entry.getKey(), entry.getValue().serializeToJson());
		}
		jsonobject.add("benchmarks", jsonobject1);

		JsonArray jsonarray1 = new JsonArray();
		for(String benchmarkID : codexEntry.requirements) {
			jsonarray1.add(benchmarkID);
		}
		jsonobject.add("requirements", jsonarray1);

		return jsonobject;
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
