package com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedAbstractBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedDamagePredicate;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonObject;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

/**
 * Copied to remove connections to several advancement classes
 * */
public class ExtendedPlayerHurtEntityTrigger extends ExtendedAbstractBenchmarkTrigger<ExtendedPlayerHurtEntityTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "player_hurt_entity");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public ExtendedPlayerHurtEntityTrigger.Instance createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
	      ExtendedDamagePredicate damagepredicate = ExtendedDamagePredicate.fromJson(pJson.get("damage"));
	      ExtendedEntityPredicate.AndPredicate entitypredicate$andpredicate = ExtendedEntityPredicate.AndPredicate.fromJson(pJson, "entity", pConditionsParser);
	      return new ExtendedPlayerHurtEntityTrigger.Instance(pEntityPredicate, damagepredicate, entitypredicate$andpredicate);
	   }

	   public void trigger(ServerPlayerEntity pPlayer, Entity pEntity, DamageSource pSource, float pAmountDealt, float pAmountTaken, boolean pBlocked) {
	      LootContext lootcontext = ExtendedEntityPredicate.createContext(pPlayer, pEntity);
	      this.trigger(pPlayer, (p_226956_6_) -> {
	         return p_226956_6_.matches(pPlayer, lootcontext, pSource, pAmountDealt, pAmountTaken, pBlocked);
	      });
	   }

	   public static class Instance extends ExtendedBenchmarkInstance {
	      private final ExtendedDamagePredicate damage;
	      private final ExtendedEntityPredicate.AndPredicate entity;

	      public Instance(ExtendedEntityPredicate.AndPredicate p_i241190_1_, ExtendedDamagePredicate p_i241190_2_, ExtendedEntityPredicate.AndPredicate p_i241190_3_) {
	         super(ExtendedPlayerHurtEntityTrigger.ID, p_i241190_1_);
	         this.damage = p_i241190_2_;
	         this.entity = p_i241190_3_;
	      }

	      public static ExtendedPlayerHurtEntityTrigger.Instance playerHurtEntity(ExtendedDamagePredicate.Builder pBuilder) {
	         return new ExtendedPlayerHurtEntityTrigger.Instance(ExtendedEntityPredicate.AndPredicate.ANY, pBuilder.build(), ExtendedEntityPredicate.AndPredicate.ANY);
	      }

	      public boolean matches(ServerPlayerEntity pPlayer, LootContext pContext, DamageSource pDamage, float pDealt, float pTaken, boolean pBlocked) {
	         if (!this.damage.matches(pPlayer, pDamage, pDealt, pTaken, pBlocked)) {
	            return false;
	         } else {
	            return this.entity.matches(pContext);
	         }
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	         JsonObject jsonobject = super.serializeToJson(pConditions);
	         jsonobject.add("damage", this.damage.serializeToJson());
	         jsonobject.add("entity", this.entity.toJson(pConditions));
	         return jsonobject;
	      }
	   }
	}