package com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.entity.Entity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Copied to remove connection to {@Link net.minecraft.advancements.criterion.EntityPredicate}
 * */
public class ExtendedEntityHasProperty implements ILootCondition {
   private final ExtendedEntityPredicate predicate;
   private final LootContext.EntityTarget entityTarget;

   private ExtendedEntityHasProperty(ExtendedEntityPredicate pEntityPredicate, LootContext.EntityTarget pEntityTarget) {
      this.predicate = pEntityPredicate;
      this.entityTarget = pEntityTarget;
   }

   public LootConditionType getType() {
      return LootConditionManager.ENTITY_PROPERTIES;
   }

   /**
    * Get the parameters used by this object.
    */
   public Set<LootParameter<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootParameters.ORIGIN, this.entityTarget.getParam());
   }

   public boolean test(LootContext p_test_1_) {
      Entity entity = p_test_1_.getParamOrNull(this.entityTarget.getParam());
      Vector3d vector3d = p_test_1_.getParamOrNull(LootParameters.ORIGIN);
      return this.predicate.matches(p_test_1_.getLevel(), vector3d, entity);
   }

   public static ILootCondition.IBuilder entityPresent(LootContext.EntityTarget pTarget) {
      return hasProperties(pTarget, ExtendedEntityPredicate.Builder.entity());
   }

   public static ILootCondition.IBuilder hasProperties(LootContext.EntityTarget pTarget, ExtendedEntityPredicate.Builder pPredicateBuilder) {
      return () -> {
         return new ExtendedEntityHasProperty(pPredicateBuilder.build(), pTarget);
      };
   }

   public static ILootCondition.IBuilder hasProperties(LootContext.EntityTarget pTarget, ExtendedEntityPredicate pEntityPredicate) {
      return () -> {
         return new ExtendedEntityHasProperty(pEntityPredicate, pTarget);
      };
   }

   public static class Serializer implements ILootSerializer<ExtendedEntityHasProperty> {
      /**
       * Serialize the value by putting its data into the JsonObject.
       */
      public void serialize(JsonObject pJson, ExtendedEntityHasProperty pValue, JsonSerializationContext pSerializationContext) {
         pJson.add("predicate", pValue.predicate.serializeToJson());
         pJson.add("entity", pSerializationContext.serialize(pValue.entityTarget));
      }

      /**
       * Deserialize a value by reading it from the JsonObject.
       */
      public ExtendedEntityHasProperty deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
         ExtendedEntityPredicate entitypredicate = ExtendedEntityPredicate.fromJson(pJson.get("predicate"));
         return new ExtendedEntityHasProperty(entitypredicate, JSONUtils.getAsObject(pJson, "entity", pSerializationContext, LootContext.EntityTarget.class));
      }
   }
}
