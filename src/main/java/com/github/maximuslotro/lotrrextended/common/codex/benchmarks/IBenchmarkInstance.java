package com.github.maximuslotro.lotrrextended.common.codex.benchmarks;

import com.google.gson.JsonObject;

import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public interface IBenchmarkInstance {
	   ResourceLocation getCriterion();

	   JsonObject serializeToJson(ConditionArraySerializer pConditions);
}
