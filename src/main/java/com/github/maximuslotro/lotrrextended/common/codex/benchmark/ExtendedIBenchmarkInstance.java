package com.github.maximuslotro.lotrrextended.common.codex.benchmark;

import com.google.gson.JsonObject;

import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

/**
 * Codex implementation based upon {@Link ICriterionInstance}'s logic 
 * */
public interface ExtendedIBenchmarkInstance {
	   ResourceLocation getBenchmark();

	   JsonObject serializeToJson(ConditionArraySerializer pConditions);
}
