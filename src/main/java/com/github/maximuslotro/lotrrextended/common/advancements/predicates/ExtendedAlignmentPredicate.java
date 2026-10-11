package com.github.maximuslotro.lotrrextended.common.advancements.predicates;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import lotr.common.fac.Faction;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.JSONUtils;

public class ExtendedAlignmentPredicate {
	public static final ExtendedAlignmentPredicate ANY = new ExtendedAlignmentPredicate(MinMaxBounds.FloatBound.ANY, null);
	private final MinMaxBounds.FloatBound alignment;
	private final String factionName;

	public ExtendedAlignmentPredicate(MinMaxBounds.FloatBound p_i225753_1_, @Nullable String factionDString) {
		this.alignment = p_i225753_1_;
		this.factionName = factionDString;
	}

	public boolean matches(PlayerEntity player, Faction faction, float level) {
		if (this == ANY) {
			return true;
		}else if(factionName!=null && (faction == null || !factionName.equals(faction.getName().toString()))){
			return false;
		}
		return alignment.matches(level);
	}

	public JsonElement serializeToJson() {
		if (this == ANY) {
			return JsonNull.INSTANCE;
		} else {
			JsonObject jsonobject = new JsonObject();
			if(factionName!=null) {
				jsonobject.addProperty("faction", this.factionName);
			}
			jsonobject.add("level", this.alignment.serializeToJson());
			return jsonobject;
		}
	}

	public static ExtendedAlignmentPredicate fromJson(@Nullable JsonElement pJson) {
		if (pJson != null && !pJson.isJsonNull()) {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(pJson, "alignment");
			MinMaxBounds.FloatBound minmaxbounds$intbound = MinMaxBounds.FloatBound.fromJson(jsonobject.get("level"));
			String faction$make=null;
			if(jsonobject.has("faction")) {
				faction$make = jsonobject.get("faction").getAsString();
			}
			return new ExtendedAlignmentPredicate(minmaxbounds$intbound, faction$make);
		} else {
			return ANY;
		}
	}
}
