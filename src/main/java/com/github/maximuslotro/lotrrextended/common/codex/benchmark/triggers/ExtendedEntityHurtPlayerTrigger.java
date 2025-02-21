package com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedAbstractBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedDamagePredicate;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

/**
 * Copied to remove connections to several advancement classes
 * */
public class ExtendedEntityHurtPlayerTrigger extends ExtendedAbstractBenchmarkTrigger<ExtendedEntityHurtPlayerTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "entity_hurt_player");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public ExtendedEntityHurtPlayerTrigger.Instance createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
		   ExtendedDamagePredicate damagepredicate = ExtendedDamagePredicate.fromJson(pJson.get("damage"));
	      return new ExtendedEntityHurtPlayerTrigger.Instance(pEntityPredicate, damagepredicate);
	   }

	   public void trigger(ServerPlayerEntity pPlayer, DamageSource pSource, float pAmountDealt, float pAmountTaken, boolean pWasBlocked) {
	      this.trigger(pPlayer, (p_226603_5_) -> {
	         return p_226603_5_.matches(pPlayer, pSource, pAmountDealt, pAmountTaken, pWasBlocked);
	      });
	   }

	   public static class Instance extends ExtendedBenchmarkInstance {
	      private final ExtendedDamagePredicate damage;

	      public Instance(ExtendedEntityPredicate.AndPredicate p_i231572_1_, ExtendedDamagePredicate p_i231572_2_) {
	         super(ExtendedEntityHurtPlayerTrigger.ID, p_i231572_1_);
	         this.damage = p_i231572_2_;
	      }

	      public static ExtendedEntityHurtPlayerTrigger.Instance entityHurtPlayer(ExtendedDamagePredicate.Builder pDamageConditionBuilder) {
	         return new ExtendedEntityHurtPlayerTrigger.Instance(ExtendedEntityPredicate.AndPredicate.ANY, pDamageConditionBuilder.build());
	      }

	      public boolean matches(ServerPlayerEntity pPlayer, DamageSource pSource, float pAmountDealt, float pAmountTaken, boolean pWasBlocked) {
	         return this.damage.matches(pPlayer, pSource, pAmountDealt, pAmountTaken, pWasBlocked);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	         JsonObject jsonobject = super.serializeToJson(pConditions);
	         jsonobject.add("damage", this.damage.serializeToJson());
	         return jsonobject;
	      }
	   }
	}
