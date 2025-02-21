package com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.world.GameType;

/**
 * Copied {@Link net.minecraft.advancements.criterion.PlayerPredicate} to remove connections to achievements
 * */
public class ExtendedPlayerPredicate {
	   public static final ExtendedPlayerPredicate ANY = (new ExtendedPlayerPredicate.Default()).build();
	   private final MinMaxBounds.IntBound level;
	   private final GameType gameType;

	   private ExtendedPlayerPredicate(MinMaxBounds.IntBound p_i225770_1_, GameType p_i225770_2_) {
	      this.level = p_i225770_1_;
	      this.gameType = p_i225770_2_;
	   }

	   public boolean matches(Entity pPlayer) {
	      if (this == ANY) {
	         return true;
	      } else if (!(pPlayer instanceof ServerPlayerEntity)) {
	         return false;
	      } else {
	         ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)pPlayer;
	         if (!this.level.matches(serverplayerentity.experienceLevel)) {
	            return false;
	         } else if (this.gameType != GameType.NOT_SET && this.gameType != serverplayerentity.gameMode.getGameModeForPlayer()) {
	            return false;
	         } else {
	            return true;
	         }
	      }
	   }

	   public static ExtendedPlayerPredicate fromJson(@Nullable JsonElement pElement) {
	      if (pElement != null && !pElement.isJsonNull()) {
	         JsonObject jsonobject = JSONUtils.convertToJsonObject(pElement, "player");
	         MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(jsonobject.get("level"));
	         String s = JSONUtils.getAsString(jsonobject, "gamemode", "");
	         GameType gametype = GameType.byName(s, GameType.NOT_SET);

	         return new ExtendedPlayerPredicate(minmaxbounds$intbound, gametype);
	      } else {
	         return ANY;
	      }
	   }

	   public JsonElement serializeToJson() {
	      if (this == ANY) {
	         return JsonNull.INSTANCE;
	      } else {
	         JsonObject jsonobject = new JsonObject();
	         jsonobject.add("level", this.level.serializeToJson());
	         if (this.gameType != GameType.NOT_SET) {
	            jsonobject.addProperty("gamemode", this.gameType.getName());
	         }
	         return jsonobject;
	      }
	   }

	   public static class Default {
	      private MinMaxBounds.IntBound level = MinMaxBounds.IntBound.ANY;
	      private GameType gameType = GameType.NOT_SET;

	      public ExtendedPlayerPredicate build() {
	         return new ExtendedPlayerPredicate(this.level, this.gameType);
	      }
	   }

	}