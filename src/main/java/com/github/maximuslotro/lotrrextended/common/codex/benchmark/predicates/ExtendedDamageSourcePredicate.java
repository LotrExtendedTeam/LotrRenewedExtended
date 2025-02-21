package com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

/**
 * Copied {@Link net.minecraft.advancements.criterion.DamageSourcePredicate} to remove connections to {@Link net.minecraft.advancements.criterion.EntityPredicate}
 * */
public class ExtendedDamageSourcePredicate {
	   public static final ExtendedDamageSourcePredicate ANY = ExtendedDamageSourcePredicate.Builder.damageType().build();
	   private final Boolean isProjectile;
	   private final Boolean isExplosion;
	   private final Boolean bypassesArmor;
	   private final Boolean bypassesInvulnerability;
	   private final Boolean bypassesMagic;
	   private final Boolean isFire;
	   private final Boolean isMagic;
	   private final Boolean isLightning;
	   private final ExtendedEntityPredicate directEntity;
	   private final ExtendedEntityPredicate sourceEntity;

	   public ExtendedDamageSourcePredicate(@Nullable Boolean pIsProjectile, @Nullable Boolean pIsExplosion, @Nullable Boolean pBypassesArmor, @Nullable Boolean pBypassesInvulnerability, @Nullable Boolean pBypassesMagic, @Nullable Boolean pIsFire, @Nullable Boolean pIsMagic, @Nullable Boolean pIsLightning, ExtendedEntityPredicate pDirectEntity, ExtendedEntityPredicate pSourceEntity) {
	      this.isProjectile = pIsProjectile;
	      this.isExplosion = pIsExplosion;
	      this.bypassesArmor = pBypassesArmor;
	      this.bypassesInvulnerability = pBypassesInvulnerability;
	      this.bypassesMagic = pBypassesMagic;
	      this.isFire = pIsFire;
	      this.isMagic = pIsMagic;
	      this.isLightning = pIsLightning;
	      this.directEntity = pDirectEntity;
	      this.sourceEntity = pSourceEntity;
	   }

	   public boolean matches(ServerPlayerEntity pPlayer, DamageSource pSource) {
	      return this.matches(pPlayer.getLevel(), pPlayer.position(), pSource);
	   }

	   public boolean matches(ServerWorld pLevel, Vector3d pVector, DamageSource pSource) {
	      if (this == ANY) {
	         return true;
	      } else if (this.isProjectile != null && this.isProjectile != pSource.isProjectile()) {
	         return false;
	      } else if (this.isExplosion != null && this.isExplosion != pSource.isExplosion()) {
	         return false;
	      } else if (this.bypassesArmor != null && this.bypassesArmor != pSource.isBypassArmor()) {
	         return false;
	      } else if (this.bypassesInvulnerability != null && this.bypassesInvulnerability != pSource.isBypassInvul()) {
	         return false;
	      } else if (this.bypassesMagic != null && this.bypassesMagic != pSource.isBypassMagic()) {
	         return false;
	      } else if (this.isFire != null && this.isFire != pSource.isFire()) {
	         return false;
	      } else if (this.isMagic != null && this.isMagic != pSource.isMagic()) {
	         return false;
	      } else if (this.isLightning != null && this.isLightning != (pSource == DamageSource.LIGHTNING_BOLT)) {
	         return false;
	      } else if (!this.directEntity.matches(pLevel, pVector, pSource.getDirectEntity())) {
	         return false;
	      } else {
	         return this.sourceEntity.matches(pLevel, pVector, pSource.getEntity());
	      }
	   }

	   public static ExtendedDamageSourcePredicate fromJson(@Nullable JsonElement pElement) {
	      if (pElement != null && !pElement.isJsonNull()) {
	         JsonObject jsonobject = JSONUtils.convertToJsonObject(pElement, "damage type");
	         Boolean obool = getOptionalBoolean(jsonobject, "is_projectile");
	         Boolean obool1 = getOptionalBoolean(jsonobject, "is_explosion");
	         Boolean obool2 = getOptionalBoolean(jsonobject, "bypasses_armor");
	         Boolean obool3 = getOptionalBoolean(jsonobject, "bypasses_invulnerability");
	         Boolean obool4 = getOptionalBoolean(jsonobject, "bypasses_magic");
	         Boolean obool5 = getOptionalBoolean(jsonobject, "is_fire");
	         Boolean obool6 = getOptionalBoolean(jsonobject, "is_magic");
	         Boolean obool7 = getOptionalBoolean(jsonobject, "is_lightning");
	         ExtendedEntityPredicate entitypredicate = ExtendedEntityPredicate.fromJson(jsonobject.get("direct_entity"));
	         ExtendedEntityPredicate entitypredicate1 = ExtendedEntityPredicate.fromJson(jsonobject.get("source_entity"));
	         return new ExtendedDamageSourcePredicate(obool, obool1, obool2, obool3, obool4, obool5, obool6, obool7, entitypredicate, entitypredicate1);
	      } else {
	         return ANY;
	      }
	   }

	   @Nullable
	   private static Boolean getOptionalBoolean(JsonObject pObject, String pMemberName) {
	      return pObject.has(pMemberName) ? JSONUtils.getAsBoolean(pObject, pMemberName) : null;
	   }

	   public JsonElement serializeToJson() {
	      if (this == ANY) {
	         return JsonNull.INSTANCE;
	      } else {
	         JsonObject jsonobject = new JsonObject();
	         this.addOptionally(jsonobject, "is_projectile", this.isProjectile);
	         this.addOptionally(jsonobject, "is_explosion", this.isExplosion);
	         this.addOptionally(jsonobject, "bypasses_armor", this.bypassesArmor);
	         this.addOptionally(jsonobject, "bypasses_invulnerability", this.bypassesInvulnerability);
	         this.addOptionally(jsonobject, "bypasses_magic", this.bypassesMagic);
	         this.addOptionally(jsonobject, "is_fire", this.isFire);
	         this.addOptionally(jsonobject, "is_magic", this.isMagic);
	         this.addOptionally(jsonobject, "is_lightning", this.isLightning);
	         jsonobject.add("direct_entity", this.directEntity.serializeToJson());
	         jsonobject.add("source_entity", this.sourceEntity.serializeToJson());
	         return jsonobject;
	      }
	   }

	   /**
	    * Adds a property if the value is not null.
	    */
	   private void addOptionally(JsonObject pObj, String pKey, @Nullable Boolean pValue) {
	      if (pValue != null) {
	         pObj.addProperty(pKey, pValue);
	      }

	   }

	   public static class Builder {
	      private Boolean isProjectile;
	      private Boolean isExplosion;
	      private Boolean bypassesArmor;
	      private Boolean bypassesInvulnerability;
	      private Boolean bypassesMagic;
	      private Boolean isFire;
	      private Boolean isMagic;
	      private Boolean isLightning;
	      private ExtendedEntityPredicate directEntity = ExtendedEntityPredicate.ANY;
	      private ExtendedEntityPredicate sourceEntity = ExtendedEntityPredicate.ANY;

	      public static ExtendedDamageSourcePredicate.Builder damageType() {
	         return new ExtendedDamageSourcePredicate.Builder();
	      }

	      public ExtendedDamageSourcePredicate.Builder isProjectile(Boolean pIsProjectile) {
	         this.isProjectile = pIsProjectile;
	         return this;
	      }

	      public ExtendedDamageSourcePredicate.Builder isLightning(Boolean pIsLightning) {
	         this.isLightning = pIsLightning;
	         return this;
	      }

	      public ExtendedDamageSourcePredicate.Builder direct(ExtendedEntityPredicate.Builder pDirectEntity) {
	         this.directEntity = pDirectEntity.build();
	         return this;
	      }

	      public ExtendedDamageSourcePredicate build() {
	         return new ExtendedDamageSourcePredicate(this.isProjectile, this.isExplosion, this.bypassesArmor, this.bypassesInvulnerability, this.bypassesMagic, this.isFire, this.isMagic, this.isLightning, this.directEntity, this.sourceEntity);
	      }
	   }
	}
