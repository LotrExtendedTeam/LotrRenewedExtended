package com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JSONUtils;

/**
 * Copied {@Link net.minecraft.advancements.criterion.DamagePredicate} to remove connections to {@Link net.minecraft.advancements.criterion.DamageSourcePredicate}
 * */
public class ExtendedDamagePredicate {
	   public static final ExtendedDamagePredicate ANY = ExtendedDamagePredicate.Builder.damageInstance().build();
	   private final MinMaxBounds.FloatBound dealtDamage;
	   private final MinMaxBounds.FloatBound takenDamage;
	   private final ExtendedEntityPredicate sourceEntity;
	   private final Boolean blocked;
	   private final ExtendedDamageSourcePredicate type;

	   public ExtendedDamagePredicate() {
	      this.dealtDamage = MinMaxBounds.FloatBound.ANY;
	      this.takenDamage = MinMaxBounds.FloatBound.ANY;
	      this.sourceEntity = ExtendedEntityPredicate.ANY;
	      this.blocked = null;
	      this.type = ExtendedDamageSourcePredicate.ANY;
	   }

	   public ExtendedDamagePredicate(MinMaxBounds.FloatBound p_i49725_1_, MinMaxBounds.FloatBound p_i49725_2_, ExtendedEntityPredicate p_i49725_3_, @Nullable Boolean p_i49725_4_, ExtendedDamageSourcePredicate p_i49725_5_) {
	      this.dealtDamage = p_i49725_1_;
	      this.takenDamage = p_i49725_2_;
	      this.sourceEntity = p_i49725_3_;
	      this.blocked = p_i49725_4_;
	      this.type = p_i49725_5_;
	   }

	   public boolean matches(ServerPlayerEntity pPlayer, DamageSource pSource, float pDealt, float pTaken, boolean pBlocked) {
	      if (this == ANY) {
	         return true;
	      } else if (!this.dealtDamage.matches(pDealt)) {
	         return false;
	      } else if (!this.takenDamage.matches(pTaken)) {
	         return false;
	      } else if (!this.sourceEntity.matches(pPlayer, pSource.getEntity())) {
	         return false;
	      } else if (this.blocked != null && this.blocked != pBlocked) {
	         return false;
	      } else {
	         return this.type.matches(pPlayer, pSource);
	      }
	   }

	   public static ExtendedDamagePredicate fromJson(@Nullable JsonElement pElement) {
	      if (pElement != null && !pElement.isJsonNull()) {
	         JsonObject jsonobject = JSONUtils.convertToJsonObject(pElement, "damage");
	         MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(jsonobject.get("dealt"));
	         MinMaxBounds.FloatBound minmaxbounds$floatbound1 = MinMaxBounds.FloatBound.fromJson(jsonobject.get("taken"));
	         Boolean obool = jsonobject.has("blocked") ? JSONUtils.getAsBoolean(jsonobject, "blocked") : null;
	         ExtendedEntityPredicate entitypredicate = ExtendedEntityPredicate.fromJson(jsonobject.get("source_entity"));
	         ExtendedDamageSourcePredicate damagesourcepredicate = ExtendedDamageSourcePredicate.fromJson(jsonobject.get("type"));
	         return new ExtendedDamagePredicate(minmaxbounds$floatbound, minmaxbounds$floatbound1, entitypredicate, obool, damagesourcepredicate);
	      } else {
	         return ANY;
	      }
	   }

	   public JsonElement serializeToJson() {
	      if (this == ANY) {
	         return JsonNull.INSTANCE;
	      } else {
	         JsonObject jsonobject = new JsonObject();
	         jsonobject.add("dealt", this.dealtDamage.serializeToJson());
	         jsonobject.add("taken", this.takenDamage.serializeToJson());
	         jsonobject.add("source_entity", this.sourceEntity.serializeToJson());
	         jsonobject.add("type", this.type.serializeToJson());
	         if (this.blocked != null) {
	            jsonobject.addProperty("blocked", this.blocked);
	         }

	         return jsonobject;
	      }
	   }

	   public static class Builder {
	      private MinMaxBounds.FloatBound dealtDamage = MinMaxBounds.FloatBound.ANY;
	      private MinMaxBounds.FloatBound takenDamage = MinMaxBounds.FloatBound.ANY;
	      private ExtendedEntityPredicate sourceEntity = ExtendedEntityPredicate.ANY;
	      private Boolean blocked;
	      private ExtendedDamageSourcePredicate type = ExtendedDamageSourcePredicate.ANY;

	      public static ExtendedDamagePredicate.Builder damageInstance() {
	         return new ExtendedDamagePredicate.Builder();
	      }

	      public ExtendedDamagePredicate.Builder blocked(Boolean pBlocked) {
	         this.blocked = pBlocked;
	         return this;
	      }

	      public ExtendedDamagePredicate.Builder type(ExtendedDamageSourcePredicate.Builder pDamageType) {
	         this.type = pDamageType.build();
	         return this;
	      }

	      public ExtendedDamagePredicate build() {
	         return new ExtendedDamagePredicate(this.dealtDamage, this.takenDamage, this.sourceEntity, this.blocked, this.type);
	      }
	   }
	}
