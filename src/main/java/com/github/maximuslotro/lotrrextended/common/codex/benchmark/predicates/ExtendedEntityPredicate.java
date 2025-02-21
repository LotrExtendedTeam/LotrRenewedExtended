package com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.tags.ITag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

/**
 * Copied {@Link net.minecraft.advancements.criterion.EntityPredicate} to remove connections to {@Link net.minecraft.advancements.criterion.PlayerPredicate}
 * */
public class ExtendedEntityPredicate {
	   public static final ExtendedEntityPredicate ANY = new ExtendedEntityPredicate(EntityTypePredicate.ANY, DistancePredicate.ANY, LocationPredicate.ANY, MobEffectsPredicate.ANY, NBTPredicate.ANY, EntityFlagsPredicate.ANY, EntityEquipmentPredicate.ANY, ExtendedPlayerPredicate.ANY, FishingPredicate.ANY, (String)null, (ResourceLocation)null);
	   private final EntityTypePredicate entityType;
	   private final DistancePredicate distanceToPlayer;
	   private final LocationPredicate location;
	   private final MobEffectsPredicate effects;
	   private final NBTPredicate nbt;
	   private final EntityFlagsPredicate flags;
	   private final EntityEquipmentPredicate equipment;
	   private final ExtendedPlayerPredicate player;
	   private final FishingPredicate fishingHook;
	   private final ExtendedEntityPredicate vehicle;
	   private final ExtendedEntityPredicate targetedEntity;
	   @Nullable
	   private final String team;
	   @Nullable
	   private final ResourceLocation catType;

	   private ExtendedEntityPredicate(EntityTypePredicate p_i241236_1_, DistancePredicate p_i241236_2_, LocationPredicate p_i241236_3_, MobEffectsPredicate p_i241236_4_, NBTPredicate p_i241236_5_, EntityFlagsPredicate p_i241236_6_, EntityEquipmentPredicate p_i241236_7_, ExtendedPlayerPredicate p_i241236_8_, FishingPredicate p_i241236_9_, @Nullable String p_i241236_10_, @Nullable ResourceLocation p_i241236_11_) {
	      this.entityType = p_i241236_1_;
	      this.distanceToPlayer = p_i241236_2_;
	      this.location = p_i241236_3_;
	      this.effects = p_i241236_4_;
	      this.nbt = p_i241236_5_;
	      this.flags = p_i241236_6_;
	      this.equipment = p_i241236_7_;
	      this.player = p_i241236_8_;
	      this.fishingHook = p_i241236_9_;
	      this.vehicle = this;
	      this.targetedEntity = this;
	      this.team = p_i241236_10_;
	      this.catType = p_i241236_11_;
	   }

	   private ExtendedEntityPredicate(EntityTypePredicate p_i231578_1_, DistancePredicate p_i231578_2_, LocationPredicate p_i231578_3_, MobEffectsPredicate p_i231578_4_, NBTPredicate p_i231578_5_, EntityFlagsPredicate p_i231578_6_, EntityEquipmentPredicate p_i231578_7_, ExtendedPlayerPredicate p_i231578_8_, FishingPredicate p_i231578_9_, ExtendedEntityPredicate p_i231578_10_, ExtendedEntityPredicate p_i231578_11_, @Nullable String p_i231578_12_, @Nullable ResourceLocation p_i231578_13_) {
	      this.entityType = p_i231578_1_;
	      this.distanceToPlayer = p_i231578_2_;
	      this.location = p_i231578_3_;
	      this.effects = p_i231578_4_;
	      this.nbt = p_i231578_5_;
	      this.flags = p_i231578_6_;
	      this.equipment = p_i231578_7_;
	      this.player = p_i231578_8_;
	      this.fishingHook = p_i231578_9_;
	      this.vehicle = p_i231578_10_;
	      this.targetedEntity = p_i231578_11_;
	      this.team = p_i231578_12_;
	      this.catType = p_i231578_13_;
	   }

	   public boolean matches(ServerPlayerEntity pPlayer, @Nullable Entity pEntity) {
	      return this.matches(pPlayer.getLevel(), pPlayer.position(), pEntity);
	   }

	   public boolean matches(ServerWorld pLevel, @Nullable Vector3d pVector, @Nullable Entity pEntity) {
	      if (this == ANY) {
	         return true;
	      } else if (pEntity == null) {
	         return false;
	      } else if (!this.entityType.matches(pEntity.getType())) {
	         return false;
	      } else {
	         if (pVector == null) {
	            if (this.distanceToPlayer != DistancePredicate.ANY) {
	               return false;
	            }
	         } else if (!this.distanceToPlayer.matches(pVector.x, pVector.y, pVector.z, pEntity.getX(), pEntity.getY(), pEntity.getZ())) {
	            return false;
	         }

	         if (!this.location.matches(pLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ())) {
	            return false;
	         } else if (!this.effects.matches(pEntity)) {
	            return false;
	         } else if (!this.nbt.matches(pEntity)) {
	            return false;
	         } else if (!this.flags.matches(pEntity)) {
	            return false;
	         } else if (!this.equipment.matches(pEntity)) {
	            return false;
	         } else if (!this.player.matches(pEntity)) {
	            return false;
	         } else if (!this.fishingHook.matches(pEntity)) {
	            return false;
	         } else if (!this.vehicle.matches(pLevel, pVector, pEntity.getVehicle())) {
	            return false;
	         } else if (!this.targetedEntity.matches(pLevel, pVector, pEntity instanceof MobEntity ? ((MobEntity)pEntity).getTarget() : null)) {
	            return false;
	         } else {
	            if (this.team != null) {
	               Team team = pEntity.getTeam();
	               if (team == null || !this.team.equals(team.getName())) {
	                  return false;
	               }
	            }

	            return this.catType == null || pEntity instanceof CatEntity && ((CatEntity)pEntity).getResourceLocation().equals(this.catType);
	         }
	      }
	   }

	   public static ExtendedEntityPredicate fromJson(@Nullable JsonElement pElement) {
	      if (pElement != null && !pElement.isJsonNull()) {
	         JsonObject jsonobject = JSONUtils.convertToJsonObject(pElement, "entity");
	         EntityTypePredicate entitytypepredicate = EntityTypePredicate.fromJson(jsonobject.get("type"));
	         DistancePredicate distancepredicate = DistancePredicate.fromJson(jsonobject.get("distance"));
	         LocationPredicate locationpredicate = LocationPredicate.fromJson(jsonobject.get("location"));
	         MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.fromJson(jsonobject.get("effects"));
	         NBTPredicate nbtpredicate = NBTPredicate.fromJson(jsonobject.get("nbt"));
	         EntityFlagsPredicate entityflagspredicate = EntityFlagsPredicate.fromJson(jsonobject.get("flags"));
	         EntityEquipmentPredicate entityequipmentpredicate = EntityEquipmentPredicate.fromJson(jsonobject.get("equipment"));
	         ExtendedPlayerPredicate playerpredicate = ExtendedPlayerPredicate.fromJson(jsonobject.get("player"));
	         FishingPredicate fishingpredicate = FishingPredicate.fromJson(jsonobject.get("fishing_hook"));
	         ExtendedEntityPredicate entitypredicate = fromJson(jsonobject.get("vehicle"));
	         ExtendedEntityPredicate entitypredicate1 = fromJson(jsonobject.get("targeted_entity"));
	         String s = JSONUtils.getAsString(jsonobject, "team", (String)null);
	         ResourceLocation resourcelocation = jsonobject.has("catType") ? new ResourceLocation(JSONUtils.getAsString(jsonobject, "catType")) : null;
	         return (new ExtendedEntityPredicate.Builder()).entityType(entitytypepredicate).distance(distancepredicate).located(locationpredicate).effects(mobeffectspredicate).nbt(nbtpredicate).flags(entityflagspredicate).equipment(entityequipmentpredicate).player(playerpredicate).fishingHook(fishingpredicate).team(s).vehicle(entitypredicate).targetedEntity(entitypredicate1).catType(resourcelocation).build();
	      } else {
	         return ANY;
	      }
	   }

	   public JsonElement serializeToJson() {
	      if (this == ANY) {
	         return JsonNull.INSTANCE;
	      } else {
	         JsonObject jsonobject = new JsonObject();
	         jsonobject.add("type", this.entityType.serializeToJson());
	         jsonobject.add("distance", this.distanceToPlayer.serializeToJson());
	         jsonobject.add("location", this.location.serializeToJson());
	         jsonobject.add("effects", this.effects.serializeToJson());
	         jsonobject.add("nbt", this.nbt.serializeToJson());
	         jsonobject.add("flags", this.flags.serializeToJson());
	         jsonobject.add("equipment", this.equipment.serializeToJson());
	         jsonobject.add("player", this.player.serializeToJson());
	         jsonobject.add("fishing_hook", this.fishingHook.serializeToJson());
	         jsonobject.add("vehicle", this.vehicle.serializeToJson());
	         jsonobject.add("targeted_entity", this.targetedEntity.serializeToJson());
	         jsonobject.addProperty("team", this.team);
	         if (this.catType != null) {
	            jsonobject.addProperty("catType", this.catType.toString());
	         }

	         return jsonobject;
	      }
	   }

	   public static LootContext createContext(ServerPlayerEntity pPlayer, Entity pEntity) {
	      return (new LootContext.Builder(pPlayer.getLevel())).withParameter(LootParameters.THIS_ENTITY, pEntity).withParameter(LootParameters.ORIGIN, pPlayer.position()).withRandom(pPlayer.getRandom()).create(LootParameterSets.ADVANCEMENT_ENTITY);
	   }

	   public static class AndPredicate {
	      public static final ExtendedEntityPredicate.AndPredicate ANY = new ExtendedEntityPredicate.AndPredicate(new ILootCondition[0]);
	      private final ILootCondition[] conditions;
	      private final Predicate<LootContext> compositePredicates;

	      private AndPredicate(ILootCondition[] p_i231580_1_) {
	         this.conditions = p_i231580_1_;
	         this.compositePredicates = LootConditionManager.andConditions(p_i231580_1_);
	      }

	      public static ExtendedEntityPredicate.AndPredicate create(ILootCondition... pConditions) {
	         return new ExtendedEntityPredicate.AndPredicate(pConditions);
	      }

	      public static ExtendedEntityPredicate.AndPredicate fromJson(JsonObject pJsonObject, String pName, ConditionArrayParser pConditions) {
	         JsonElement jsonelement = pJsonObject.get(pName);
	         return fromElement(pName, pConditions, jsonelement);
	      }

	      public static ExtendedEntityPredicate.AndPredicate[] fromJsonArray(JsonObject pJsonObject, String pName, ConditionArrayParser pConditions) {
	         JsonElement jsonelement = pJsonObject.get(pName);
	         if (jsonelement != null && !jsonelement.isJsonNull()) {
	            JsonArray jsonarray = JSONUtils.convertToJsonArray(jsonelement, pName);
	            ExtendedEntityPredicate.AndPredicate[] aentitypredicate$andpredicate = new ExtendedEntityPredicate.AndPredicate[jsonarray.size()];

	            for(int i = 0; i < jsonarray.size(); ++i) {
	               aentitypredicate$andpredicate[i] = fromElement(pName + "[" + i + "]", pConditions, jsonarray.get(i));
	            }

	            return aentitypredicate$andpredicate;
	         } else {
	            return new ExtendedEntityPredicate.AndPredicate[0];
	         }
	      }

	      private static ExtendedEntityPredicate.AndPredicate fromElement(String pName, ConditionArrayParser pConditions, @Nullable JsonElement pElement) {
	         if (pElement != null && pElement.isJsonArray()) {
	            ILootCondition[] ailootcondition = pConditions.deserializeConditions(pElement.getAsJsonArray(), pConditions.getAdvancementId().toString() + "/" + pName, LootParameterSets.ADVANCEMENT_ENTITY);
	            return new ExtendedEntityPredicate.AndPredicate(ailootcondition);
	         } else {
	            ExtendedEntityPredicate entitypredicate = ExtendedEntityPredicate.fromJson(pElement);
	            return wrap(entitypredicate);
	         }
	      }

	      public static ExtendedEntityPredicate.AndPredicate wrap(ExtendedEntityPredicate pEntityCondition) {
	         if (pEntityCondition == ExtendedEntityPredicate.ANY) {
	            return ANY;
	         } else {
	            ILootCondition ilootcondition = ExtendedEntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, pEntityCondition).build();
	            return new ExtendedEntityPredicate.AndPredicate(new ILootCondition[]{ilootcondition});
	         }
	      }

	      public boolean matches(LootContext pContext) {
	         return this.compositePredicates.test(pContext);
	      }

	      public JsonElement toJson(ConditionArraySerializer pSerializer) {
	         return (JsonElement)(this.conditions.length == 0 ? JsonNull.INSTANCE : pSerializer.serializeConditions(this.conditions));
	      }

	      public static JsonElement toJson(ExtendedEntityPredicate.AndPredicate[] pPredicates, ConditionArraySerializer pSerializer) {
	         if (pPredicates.length == 0) {
	            return JsonNull.INSTANCE;
	         } else {
	            JsonArray jsonarray = new JsonArray();

	            for(ExtendedEntityPredicate.AndPredicate entitypredicate$andpredicate : pPredicates) {
	               jsonarray.add(entitypredicate$andpredicate.toJson(pSerializer));
	            }

	            return jsonarray;
	         }
	      }
	   }

	   public static class Builder {
	      private EntityTypePredicate entityType = EntityTypePredicate.ANY;
	      private DistancePredicate distanceToPlayer = DistancePredicate.ANY;
	      private LocationPredicate location = LocationPredicate.ANY;
	      private MobEffectsPredicate effects = MobEffectsPredicate.ANY;
	      private NBTPredicate nbt = NBTPredicate.ANY;
	      private EntityFlagsPredicate flags = EntityFlagsPredicate.ANY;
	      private EntityEquipmentPredicate equipment = EntityEquipmentPredicate.ANY;
	      private ExtendedPlayerPredicate player = ExtendedPlayerPredicate.ANY;
	      private FishingPredicate fishingHook = FishingPredicate.ANY;
	      private ExtendedEntityPredicate vehicle = ExtendedEntityPredicate.ANY;
	      private ExtendedEntityPredicate targetedEntity = ExtendedEntityPredicate.ANY;
	      private String team;
	      private ResourceLocation catType;

	      public static ExtendedEntityPredicate.Builder entity() {
	         return new ExtendedEntityPredicate.Builder();
	      }

	      public ExtendedEntityPredicate.Builder of(EntityType<?> pType) {
	         this.entityType = EntityTypePredicate.of(pType);
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder of(ITag<EntityType<?>> pType) {
	         this.entityType = EntityTypePredicate.of(pType);
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder of(ResourceLocation pCatType) {
	         this.catType = pCatType;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder entityType(EntityTypePredicate pType) {
	         this.entityType = pType;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder distance(DistancePredicate pDistance) {
	         this.distanceToPlayer = pDistance;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder located(LocationPredicate pLocation) {
	         this.location = pLocation;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder effects(MobEffectsPredicate pEffects) {
	         this.effects = pEffects;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder nbt(NBTPredicate pNbt) {
	         this.nbt = pNbt;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder flags(EntityFlagsPredicate pFlags) {
	         this.flags = pFlags;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder equipment(EntityEquipmentPredicate pEquipment) {
	         this.equipment = pEquipment;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder player(ExtendedPlayerPredicate pPlayer) {
	         this.player = pPlayer;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder fishingHook(FishingPredicate pFishing) {
	         this.fishingHook = pFishing;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder vehicle(ExtendedEntityPredicate pMount) {
	         this.vehicle = pMount;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder targetedEntity(ExtendedEntityPredicate pTarget) {
	         this.targetedEntity = pTarget;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder team(@Nullable String pTeam) {
	         this.team = pTeam;
	         return this;
	      }

	      public ExtendedEntityPredicate.Builder catType(@Nullable ResourceLocation pCatType) {
	         this.catType = pCatType;
	         return this;
	      }

	      public ExtendedEntityPredicate build() {
	         return new ExtendedEntityPredicate(this.entityType, this.distanceToPlayer, this.location, this.effects, this.nbt, this.flags, this.equipment, this.player, this.fishingHook, this.vehicle, this.targetedEntity, this.team, this.catType);
	      }
	   }
}