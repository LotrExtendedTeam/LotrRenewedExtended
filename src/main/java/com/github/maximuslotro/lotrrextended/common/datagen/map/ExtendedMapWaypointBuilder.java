package com.github.maximuslotro.lotrrextended.common.datagen.map;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;
import com.google.gson.JsonObject;

import lotr.common.fac.FactionPointer;
import net.minecraft.util.ResourceLocation;

public class ExtendedMapWaypointBuilder {
	private ResourceLocation id;
	private String name;
	private boolean nameTranslate;
	private String lore;
	private boolean loreTranslate;
	private final int x;
	private final int z;
	protected ResourceLocation travelRegion;
	private ResourceLocation faction;

	public ExtendedMapWaypointBuilder(ResourceLocation pId, int pX, int pZ) {
		this.id = pId;
		this.x = pX;
		this.z = pZ;
	}

	public ExtendedMapWaypointBuilder setText(String pName, String pLore) {
		name = pName;
		lore = pLore;
		nameTranslate = false;
		loreTranslate = false;
		return this;
	}
	public ExtendedMapWaypointBuilder setKey(String langKeyNamespace) {
		name = "waypoint."+langKeyNamespace+"."+id.getPath();
		lore = "waypoint."+langKeyNamespace+"."+id.getPath()+".lore";
		nameTranslate = true;
		loreTranslate = true;
		return this;
	}

	public ExtendedMapWaypointBuilder setRegion(ResourceLocation pTravelRegion) {
		travelRegion = pTravelRegion;
		return this;
	}

	public ExtendedMapWaypointBuilder setFaction(FactionPointer pFaction) {
		faction = pFaction.getName();
		return this;
	}

	public ExtendedMapWaypointBuilder setFaction(ResourceLocation pFaction) {
		faction = pFaction;
		return this;
	}

	public ResourceLocation getId() {
		return id;
	}

	public JsonObject serializeWaypoint() {
		JsonObject waypoint = new JsonObject();
		if(name==null||lore==null) {
			setKey(id.getNamespace());
			DataGenValueHolder.waypointText.add(name);
			DataGenValueHolder.waypointText.add(lore);
		}
		JsonObject nameJson = new JsonObject();
		nameJson.addProperty("text", name);
		nameJson.addProperty("translate", nameTranslate);
		waypoint.add("name", nameJson);
		JsonObject lotrJson = new JsonObject();
		lotrJson.addProperty("text", lore);
		lotrJson.addProperty("translate", loreTranslate);
		waypoint.add("lore", lotrJson);
		waypoint.addProperty("x", x);
		waypoint.addProperty("z", z);
		waypoint.addProperty("travel_region", travelRegion.toString());
		waypoint.addProperty("faction", faction!=null ? faction.toString() : "");
		return waypoint;
	}

	public void save(Consumer<ExtendedMapWaypointBuilder> consumer) {
		consumer.accept(this);
	}
}