package com.github.maximuslotro.lotrrextended.common.world.biome;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedBanditChance;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedInvasionChance;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lotr.common.world.ExtendedInvasions;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedBiomeInformation {
	public static final ExtendedBiomeInformation EMPTY = new ExtendedBiomeInformation(LotrExtendedMod.EMPTY, 1.0f, ExtendedBanditChance.NONE, new HashMap<>());
	private final ResourceLocation biomeID;
	private final float conquestRate;
	private Biome cachedBiome;
	private ExtendedBanditChance banditSpawnChance;
	private final Map<ExtendedInvasions, ExtendedInvasionChance> invasionData;

	public ExtendedBiomeInformation(ResourceLocation pBiomeID, float pConquestRate, ExtendedBanditChance pBanditSpawnChance,
			Map<ExtendedInvasions, ExtendedInvasionChance> pInvasionData) {
		this.biomeID = pBiomeID;
		this.conquestRate = pConquestRate;
		this.banditSpawnChance = pBanditSpawnChance;
		this.invasionData = pInvasionData;
	}

	public ResourceLocation getBiomeId() {
		return biomeID;
	}

	public float getConquestRate() {
		return conquestRate;
	}

	public ExtendedBanditChance getBanditChance() {
		return banditSpawnChance;
	}

	public Map<ExtendedInvasions, ExtendedInvasionChance> getInvasionMap() {
		return invasionData;
	}

	public boolean isDummy() {
		return biomeID==LotrExtendedMod.EMPTY;
	}

	public Biome getBiome() {
		if(biomeID==LotrExtendedMod.EMPTY) {
			throw new IllegalArgumentException("Cannot retrieve biome for dummy biome information! Use isDummy()");
		}
		if(cachedBiome==null) {
			cachedBiome = getBiomeFromID(biomeID);
		}
		return cachedBiome;
	}

	public static class Serializer implements JsonDeserializer<ExtendedBiomeInformation>, JsonSerializer<ExtendedBiomeInformation> {
		@Override
		public ExtendedBiomeInformation deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedBiomeInformation");
			String id = jsonobject.get("id").getAsString();
			float conquestRate = JSONUtils.getAsFloat(jsonobject, "conquest_rate", 1.0f);
			ExtendedBanditChance banditChance = ExtendedBanditChance.byName(JSONUtils.getAsString(jsonobject, "bandit_chance", "none"));
			Map<ExtendedInvasions, ExtendedInvasionChance> invasionData = new HashMap<>();
			if(jsonobject.has("invasion_data")) {
				JsonObject invasionMapObject = JSONUtils.getAsJsonObject(jsonobject, "invasion_data");
				for(Entry<String, JsonElement> entry : invasionMapObject.entrySet()) {
					Optional<ExtendedInvasions> invasion = ExtendedInvasions.fromTypeKey(new ResourceLocation(entry.getKey()));
					if(invasion.isPresent()) {
						ExtendedInvasionChance chance = ExtendedInvasionChance.valueOf(entry.getValue().getAsString().toUpperCase());
						invasionData.put(invasion.get(), chance);
					}
				}
			}
			return new ExtendedBiomeInformation(new ResourceLocation(id), conquestRate, banditChance, invasionData);
		}
		@Override
		public JsonElement serialize(ExtendedBiomeInformation biomeInformation, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", biomeInformation.getBiomeId().toString());
			jsonObject.addProperty("conquest_rate", biomeInformation.getConquestRate());
			jsonObject.addProperty("bandit_chance", biomeInformation.getBanditChance().toString());
			JsonObject invasionMapObject = new JsonObject();
			for(Entry<ExtendedInvasions, ExtendedInvasionChance> invasion : biomeInformation.getInvasionMap().entrySet()) {
				String invasionKey = invasion.getKey().getTypeKey().toString();
				String chanceValue = invasion.getValue().toString();
				invasionMapObject.addProperty(invasionKey, chanceValue);
			}
			jsonObject.add("invasion_data", invasionMapObject);
			return jsonObject;
		}
	}
	public static class Builder {
		private final ResourceLocation biomeID;
		private float conquestRate = 1.0f;
		private ExtendedBanditChance banditSpawnChance = ExtendedBanditChance.NONE;
		private Map<ExtendedInvasions, ExtendedInvasionChance> invasionData = new TreeMap<>();

		public Builder(Biome pBiome) {
			this(pBiome.getRegistryName());
		}

		public Builder(ResourceLocation pBiomeID) {
			this.biomeID = pBiomeID;
		}

		public Builder parent(ExtendedBiomeInformation biomeInformation) {
			conquestRate = biomeInformation.getConquestRate();
			banditSpawnChance = biomeInformation.getBanditChance();
			invasionData = biomeInformation.getInvasionMap();
			return this;
		}

		public Builder setConquestRate(float rate) {
			conquestRate = rate;
			return this;
		}

		public Builder setBanditChance(ExtendedBanditChance pChance) {
			banditSpawnChance = pChance;
			return this;
		}

		public Builder clearInvasions() {
			invasionData.clear();
			return this;
		}

		public Builder addInvasion(ExtendedInvasions invasion, ExtendedInvasionChance chance) {
			invasionData.put(invasion, chance);
			return this;
		}

		public ExtendedBiomeInformation save(Consumer<ExtendedBiomeInformation> pConsumer) {
			ExtendedBiomeInformation spawner = new ExtendedBiomeInformation(biomeID, conquestRate, banditSpawnChance, invasionData);
			pConsumer.accept(spawner);
			return spawner;
		}
	}
	@Nullable
	public static Biome getBiomeFromID(ResourceLocation biomeID) {
		return ForgeRegistries.BIOMES.getValue(biomeID);
	}
}