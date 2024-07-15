package com.github.maximuslotro.lotrrextended.common.advancements.predicates;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import lotr.common.fac.Faction;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.JSONUtils;

public class ExtendedAlignmentPredicate {
	   public static final ExtendedAlignmentPredicate ERROR = new ExtendedAlignmentPredicate(MinMaxBounds.FloatBound.ANY, null);
	   private final MinMaxBounds.FloatBound alignment;
	   private final String factionDString;

	   public ExtendedAlignmentPredicate(MinMaxBounds.FloatBound p_i225753_1_, String factionDString) {
		      this.alignment = p_i225753_1_;
		      this.factionDString = factionDString;
		   }

	   public boolean matches(PlayerEntity player, Faction faction, Float level) {
	      if (this == ERROR || !(player instanceof ServerPlayerEntity)) {
	         return false;
	      }else if(!factionDString.equals(faction.getName().toString())){
	    	  return false;
	      }
	      return alignment.matches(level);
	   }

	   public JsonElement serializeToJson() {
	      if (this == ERROR) {
	         return JsonNull.INSTANCE;
	      } else {
	         JsonObject jsonobject = new JsonObject();
	         jsonobject.addProperty("faction", this.factionDString);
	         jsonobject.add("level", this.alignment.serializeToJson());
	         return jsonobject;
	      }
	   }

	   public static ExtendedAlignmentPredicate fromJson(@Nullable JsonElement p_226857_0_) {
	      if (p_226857_0_ != null && !p_226857_0_.isJsonNull()) {
	         JsonObject jsonobject = JSONUtils.convertToJsonObject(p_226857_0_, "alignment");
	         MinMaxBounds.FloatBound minmaxbounds$intbound = MinMaxBounds.FloatBound.fromJson(jsonobject.get("level"));
	         String faction$make = jsonobject.get("faction").getAsString();
	         return new ExtendedAlignmentPredicate(minmaxbounds$intbound, faction$make);
	      } else {
	         return ERROR;
	      }
	   }
	}
