package com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedAbstractBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

/**
 * Copied to remove connections to several advancement classes
 * */
public class ExtendedPositionTrigger extends ExtendedAbstractBenchmarkTrigger<ExtendedPositionTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "location");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public ExtendedPositionTrigger.Instance createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
	      JsonObject jsonobject = JSONUtils.getAsJsonObject(pJson, "location", pJson);
	      LocationPredicate locationpredicate = LocationPredicate.fromJson(jsonobject);
	      return new ExtendedPositionTrigger.Instance(pEntityPredicate, locationpredicate);
	   }

	   public void trigger(ServerPlayerEntity pPlayer) {
	      this.trigger(pPlayer, (p_226923_1_) -> {
	         return p_226923_1_.matches(pPlayer.getLevel(), pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
	      });
	   }

	   public static class Instance extends ExtendedBenchmarkInstance {
	      private final LocationPredicate location;

	      public Instance(ExtendedEntityPredicate.AndPredicate p_i231661_2_, LocationPredicate p_i231661_3_) {
	         super(ExtendedPositionTrigger.ID, p_i231661_2_);
	         this.location = p_i231661_3_;
	      }

	      public static ExtendedPositionTrigger.Instance located(LocationPredicate pLocation) {
	         return new ExtendedPositionTrigger.Instance(ExtendedEntityPredicate.AndPredicate.ANY, pLocation);
	      }


	      public boolean matches(ServerWorld pLevel, double pX, double pY, double pZ) {
	         return this.location.matches(pLevel, pX, pY, pZ);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	         JsonObject jsonobject = super.serializeToJson(pConditions);
	         jsonobject.add("location", this.location.serializeToJson());
	         return jsonobject;
	      }
	   }
	}
