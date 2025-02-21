package com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedAbstractBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/**
 * Copied to remove connections to several advancement classes
 * */
public class ExtendedPlayerEntityInteractionTrigger extends ExtendedAbstractBenchmarkTrigger<ExtendedPlayerEntityInteractionTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "player_interacted_with_entity");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   protected ExtendedPlayerEntityInteractionTrigger.Instance createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
	      ItemPredicate itempredicate = ItemPredicate.fromJson(pJson.get("item"));
	      ExtendedEntityPredicate.AndPredicate entitypredicate$andpredicate = ExtendedEntityPredicate.AndPredicate.fromJson(pJson, "entity", pConditionsParser);
	      return new ExtendedPlayerEntityInteractionTrigger.Instance(pEntityPredicate, itempredicate, entitypredicate$andpredicate);
	   }

	   public void trigger(ServerPlayerEntity pPlayer, ItemStack pStack, Entity pEntity) {
	      LootContext lootcontext = ExtendedEntityPredicate.createContext(pPlayer, pEntity);
	      this.trigger(pPlayer, (p_241475_2_) -> {
	         return p_241475_2_.matches(pStack, lootcontext);
	      });
	   }

	   public static class Instance extends ExtendedBenchmarkInstance {
	      private final ItemPredicate item;
	      private final ExtendedEntityPredicate.AndPredicate entity;

	      public Instance(ExtendedEntityPredicate.AndPredicate p_i241240_1_, ItemPredicate p_i241240_2_, ExtendedEntityPredicate.AndPredicate p_i241240_3_) {
	         super(ExtendedPlayerEntityInteractionTrigger.ID, p_i241240_1_);
	         this.item = p_i241240_2_;
	         this.entity = p_i241240_3_;
	      }

	      public static ExtendedPlayerEntityInteractionTrigger.Instance itemUsedOnEntity(ExtendedEntityPredicate.AndPredicate pPlayer, ItemPredicate.Builder pStack, ExtendedEntityPredicate.AndPredicate pEntity) {
	         return new ExtendedPlayerEntityInteractionTrigger.Instance(pPlayer, pStack.build(), pEntity);
	      }

	      public boolean matches(ItemStack pStack, LootContext pContext) {
	         return !this.item.matches(pStack) ? false : this.entity.matches(pContext);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	         JsonObject jsonobject = super.serializeToJson(pConditions);
	         jsonobject.add("item", this.item.serializeToJson());
	         jsonobject.add("entity", this.entity.toJson(pConditions));
	         return jsonobject;
	      }
	   }
	}
