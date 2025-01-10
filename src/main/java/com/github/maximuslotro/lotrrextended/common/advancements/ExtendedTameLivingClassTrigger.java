package com.github.maximuslotro.lotrrextended.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class ExtendedTameLivingClassTrigger extends AbstractCriterionTrigger<ExtendedTameLivingClassTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("extended", "tame_living");

	   @Override
	public ResourceLocation getId() {
	      return ID;
	   }

	   @Override
	public ExtendedTameLivingClassTrigger.Instance createInstance(JsonObject pJson, EntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
	      EntityPredicate.AndPredicate entitypredicate$andpredicate = EntityPredicate.AndPredicate.fromJson(pJson, "entity", pConditionsParser);
	      return new ExtendedTameLivingClassTrigger.Instance(pEntityPredicate, entitypredicate$andpredicate);
	   }

	   public void trigger(ServerPlayerEntity pPlayer, LivingEntity pEntity) {
	      LootContext lootcontext = EntityPredicate.createContext(pPlayer, pEntity);
	      this.trigger(pPlayer, (p_227251_1_) -> {
	         return p_227251_1_.matches(lootcontext);
	      });
	   }

	   public static class Instance extends CriterionInstance {
	      private final EntityPredicate.AndPredicate entity;

	      public Instance(EntityPredicate.AndPredicate p_i231963_1_, EntityPredicate.AndPredicate p_i231963_2_) {
	         super(ExtendedTameLivingClassTrigger.ID, p_i231963_1_);
	         this.entity = p_i231963_2_;
	      }

	      public static ExtendedTameLivingClassTrigger.Instance tamedLiving() {
	         return new ExtendedTameLivingClassTrigger.Instance(EntityPredicate.AndPredicate.ANY, EntityPredicate.AndPredicate.ANY);
	      }

	      public static ExtendedTameLivingClassTrigger.Instance tamedLiving(EntityPredicate pEntityCondition) {
	         return new ExtendedTameLivingClassTrigger.Instance(EntityPredicate.AndPredicate.ANY, EntityPredicate.AndPredicate.wrap(pEntityCondition));
	      }

	      public boolean matches(LootContext pContext) {
	         return this.entity.matches(pContext);
	      }

	      @Override
		public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	         JsonObject jsonobject = super.serializeToJson(pConditions);
	         jsonobject.add("entity", this.entity.toJson(pConditions));
	         return jsonobject;
	      }
	   }
	}