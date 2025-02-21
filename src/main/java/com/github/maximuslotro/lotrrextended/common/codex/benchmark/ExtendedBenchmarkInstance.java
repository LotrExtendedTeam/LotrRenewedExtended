package com.github.maximuslotro.lotrrextended.common.codex.benchmark;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonObject;

import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

/**
 * Codex implementation based upon {@Link CriterionInstance}'s logic 
 * */
public abstract class ExtendedBenchmarkInstance implements ExtendedIBenchmarkInstance {
	   private final ResourceLocation benchmarkID;
	   private final ExtendedEntityPredicate.AndPredicate player;

	   public ExtendedBenchmarkInstance(ResourceLocation pCriterion, ExtendedEntityPredicate.AndPredicate pPlayer) {
	      this.benchmarkID = pCriterion;
	      this.player = pPlayer;
	   }

	   @Override
	   public ResourceLocation getBenchmark() {
	      return this.benchmarkID;
	   }

	   protected ExtendedEntityPredicate.AndPredicate getPlayerPredicate() {
	      return this.player;
	   }

	   @Override
	   public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	      JsonObject jsonobject = new JsonObject();
	      jsonobject.add("player", this.player.toJson(pConditions));
	      return jsonobject;
	   }

	   public String toString() {
	      return "AbstractBenchmarkInstance{criterion=" + this.benchmarkID + '}';
	   }
	}