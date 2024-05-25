package com.github.maximuslotro.lotrrextended.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class TraderTradedTrigger extends AbstractCriterionTrigger<TraderTradedTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "trader_traded_with");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public TraderTradedTrigger.Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate playerPredicate, ConditionArrayParser p_230241_3_) {
	      return new TraderTradedTrigger.Instance(playerPredicate, EntityTypePredicate.fromJson(jsonObject.get("entity")));
	   }

	   public void trigger(ServerPlayerEntity player, Entity entity) {
	      this.trigger(player, (instance) -> {
	         return instance.matches(player, entity.getType());
	      });
	   }

	   public void trigger(ServerPlayerEntity player, EntityType<?> entityType) {
		      this.trigger(player, (instance) -> {
		         return instance.matches(player, entityType);
		      });
		   }

	   public static class Instance extends CriterionInstance {
	      private final EntityTypePredicate entityTypePredicate;

	      public Instance(EntityPredicate.AndPredicate playerPredicate, EntityTypePredicate entityType) {
	         super(TraderTradedTrigger.ID, playerPredicate);
	         this.entityTypePredicate = entityType;
	      }

	      public static TraderTradedTrigger.Instance tradedWith(EntityType<?> entityType) {
	         return new TraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, EntityTypePredicate.of(entityType));
	      }

	      public boolean matches(ServerPlayerEntity player, EntityType<?> entity) {
	    	  return this.entityTypePredicate.matches(entity);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer serializer) {
	         JsonObject jsonobject = super.serializeToJson(serializer);
	         jsonobject.add("entity", this.entityTypePredicate.serializeToJson());
	         return jsonobject;
	      }
	   }
	}
