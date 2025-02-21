package com.github.maximuslotro.lotrrextended.common.codex.benchmark;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers.ExtendedIBenchmarkTrigger;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

/**
 * Codex implementation based upon {@Link Criterion}'s logic 
 * */
public class ExtendedBenchmark {
	   private final ExtendedIBenchmarkInstance trigger;

	   public ExtendedBenchmark(ExtendedIBenchmarkInstance pTrigger) {
	      this.trigger = pTrigger;
	   }

	   /**
	    * Deserializes all benchmarks defined within the JSON object.
	    */
	   public static ExtendedBenchmark criterionFromJson(JsonObject pJson, ConditionArrayParser pConditionParser) {
	      ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getAsString(pJson, "trigger"));
	      ExtendedIBenchmarkTrigger<?> ibenchmarktrigger = ExtendedBenchmarkTriggers.getBenchmark(resourcelocation);
	      if (ibenchmarktrigger == null) {
	         throw new JsonSyntaxException("Invalid criterion trigger: " + resourcelocation);
	      } else {
	    	  ExtendedIBenchmarkInstance icriterioninstance = ibenchmarktrigger.createInstance(JSONUtils.getAsJsonObject(pJson, "conditions", new JsonObject()), pConditionParser);
	         return new ExtendedBenchmark(icriterioninstance);
	      }
	   }

	   /**
	    * Deserializes all benchmarks defined within the JSON object.
	    */
	   public static Map<String, ExtendedBenchmark> benchmarkFromJson(JsonObject pJson, ConditionArrayParser pConditionParser) {
	      Map<String, ExtendedBenchmark> map = Maps.newHashMap();
	      for(Entry<String, JsonElement> entry : pJson.entrySet()) {
	         map.put(entry.getKey(), criterionFromJson(JSONUtils.convertToJsonObject(entry.getValue(), "benchmark"), pConditionParser));
	      }
	      return map;
	   }


	   @Nullable
	   public ExtendedIBenchmarkInstance getTrigger() {
	      return this.trigger;
	   }

	   public JsonElement serializeToJson() {
	      JsonObject jsonobject = new JsonObject();
	      jsonobject.addProperty("trigger", this.trigger.getBenchmark().toString());
	      JsonObject jsonobject1 = this.trigger.serializeToJson(ConditionArraySerializer.INSTANCE);
	      if (jsonobject1.size() != 0) {
	         jsonobject.add("conditions", jsonobject1);
	      }
	      return jsonobject;
	   }
	}