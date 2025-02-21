package com.github.maximuslotro.lotrrextended.common.codex;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmark;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.advancements.CriterionProgress;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Codex implementation of {@Link AdvancementProgress}'s logic 
 * */
public class ExtendedCodexProgress implements Comparable<ExtendedCodexProgress> {
	private final Map<String, CriterionProgress> benchmarks = Maps.newHashMap();
	private String[] requirementIDs = new String[0];

	/**
	 * Update this AdvancementProgress' benchmarks and requirements
	 */
	public void update(Map<String, ExtendedBenchmark> benchmarks, String[] pRequirements) {
		Set<String> newCriteriaSet = benchmarks.keySet();
		this.benchmarks.entrySet().removeIf((entrySet) -> {
			return !newCriteriaSet.contains(entrySet.getKey());
		});
		for(String benchmarkID : newCriteriaSet) {
			if (!this.benchmarks.containsKey(benchmarkID)) {
				this.benchmarks.put(benchmarkID, new CriterionProgress());
			}
		}
		this.requirementIDs = pRequirements;
	}

	public boolean isDone() {
		if (this.requirementIDs.length == 0) {
			return false;
		} else {
			// Extended Start
			for(String benchmarkID: requirementIDs) {
				CriterionProgress criterionprogress = this.getBenchmark(benchmarkID);
				if (criterionprogress == null || criterionprogress.isDone()) {
					return false;
				}
			}
			return true;
			// Extended End
		}
	}

	public boolean hasProgress() {
		for(CriterionProgress criterionprogress : this.benchmarks.values()) {
			if (criterionprogress.isDone()) {
				return true;
			}
		}
		return false;
	}

	public boolean grantProgress(String benchmarkID) {
		CriterionProgress criterionprogress = this.benchmarks.get(benchmarkID);
		if (criterionprogress != null && !criterionprogress.isDone()) {
			criterionprogress.grant();
			return true;
		} else {
			return false;
		}
	}

	public boolean revokeProgress(String benchmarkID) {
		CriterionProgress criterionprogress = this.benchmarks.get(benchmarkID);
		if (criterionprogress != null && criterionprogress.isDone()) {
			criterionprogress.revoke();
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return "CodexProgress{benchmarks=" + this.benchmarks + ", requirements=" + Arrays.toString(this.requirementIDs) + '}';
	}

	public void serializeToNetwork(PacketBuffer pBuffer) {
		pBuffer.writeVarInt(this.benchmarks.size());
		for(Entry<String, CriterionProgress> entry : this.benchmarks.entrySet()) {
			pBuffer.writeUtf(entry.getKey());
			entry.getValue().serializeToNetwork(pBuffer);
		}
	}

	public static ExtendedCodexProgress fromNetwork(PacketBuffer pBuffer) {
		ExtendedCodexProgress advancementprogress = new ExtendedCodexProgress();
		int i = pBuffer.readVarInt();
		for(int j = 0; j < i; ++j) {
			advancementprogress.benchmarks.put(pBuffer.readUtf(32767), CriterionProgress.fromNetwork(pBuffer));
		}
		return advancementprogress;
	}

	@Nullable
	public CriterionProgress getBenchmark(String benchmarkID) {
		return this.benchmarks.get(benchmarkID);
	}

	@OnlyIn(Dist.CLIENT)
	public float getPercent() {
		if (this.benchmarks.isEmpty()) {
			return 0.0F;
		} else {
			float f = (float)this.requirementIDs.length;
			float f1 = (float)this.countCompletedRequirements();
			return f1 / f;
		}
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public String getProgressText() {
		if (this.benchmarks.isEmpty()) {
			return null;
		} else {
			int i = this.requirementIDs.length;
			if (i <= 1) {
				return null;
			} else {
				int j = this.countCompletedRequirements();
				return j + "/" + i;
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	private int countCompletedRequirements() {
		int i = 0;
		for(String benchmarkID : this.requirementIDs) {
			CriterionProgress criterionprogress = this.getBenchmark(benchmarkID);
			if (criterionprogress != null && criterionprogress.isDone()) {
				++i;
			}
		}
		return i;
	}

	public Iterable<String> getRemainingBenchmarks() {
		List<String> list = Lists.newArrayList();
		for(Entry<String, CriterionProgress> entry : this.benchmarks.entrySet()) {
			if (!entry.getValue().isDone()) {
				list.add(entry.getKey());
			}
		}
		return list;
	}

	public Iterable<String> getCompletedBenchmarks() {
		List<String> list = Lists.newArrayList();
		for(Entry<String, CriterionProgress> entry : this.benchmarks.entrySet()) {
			if (entry.getValue().isDone()) {
				list.add(entry.getKey());
			}
		}
		return list;
	}

	@Nullable
	public Date getFirstProgressDate() {
		Date date = null;

		for(CriterionProgress criterionprogress : this.benchmarks.values()) {
			if (criterionprogress.isDone() && (date == null || criterionprogress.getObtained().before(date))) {
				date = criterionprogress.getObtained();
			}
		}

		return date;
	}

	public int compareTo(ExtendedCodexProgress p_compareTo_1_) {
		Date date = this.getFirstProgressDate();
		Date date1 = p_compareTo_1_.getFirstProgressDate();
		if (date == null && date1 != null) {
			return 1;
		} else if (date != null && date1 == null) {
			return -1;
		} else {
			return date == null && date1 == null ? 0 : date.compareTo(date1);
		}
	}

	public static class Serializer implements JsonDeserializer<ExtendedCodexProgress>, JsonSerializer<ExtendedCodexProgress> {
		public JsonElement serialize(ExtendedCodexProgress codexProgress, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
			JsonObject jsonobject = new JsonObject();
			JsonObject jsonobject1 = new JsonObject();

			for(Entry<String, CriterionProgress> entry : codexProgress.benchmarks.entrySet()) {
				CriterionProgress criterionprogress = entry.getValue();
				if (criterionprogress.isDone()) {
					jsonobject1.add(entry.getKey(), criterionprogress.serializeToJson());
				}
			}

			if (!jsonobject1.entrySet().isEmpty()) {
				jsonobject.add("benchmarks", jsonobject1);
			}

			jsonobject.addProperty("done", codexProgress.isDone());
			return jsonobject;
		}

		public ExtendedCodexProgress deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "codex");
			JsonObject jsonobject1 = JSONUtils.getAsJsonObject(jsonobject, "benchmarks", new JsonObject());
			ExtendedCodexProgress codexprogress = new ExtendedCodexProgress();

			for(Entry<String, JsonElement> entry : jsonobject1.entrySet()) {
				String s = entry.getKey();
				codexprogress.benchmarks.put(s, CriterionProgress.fromJson(JSONUtils.convertToString(entry.getValue(), s)));
			}

			return codexprogress;
		}
	}
}
