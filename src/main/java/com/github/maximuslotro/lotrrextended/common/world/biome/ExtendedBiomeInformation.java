package com.github.maximuslotro.lotrrextended.common.world.biome;

import java.lang.reflect.Type;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.enums.BanditChance;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedBiomeInformation {
	public static final ExtendedBiomeInformation EMPTY = new ExtendedBiomeInformation(LotrExtendedMod.EMPTY, 1.0f, BanditChance.NONE);
	private final ResourceLocation biomeID;
	private final float conquestRate;
	private Biome cachedBiome;
	private BanditChance banditSpawnChance;

	public ExtendedBiomeInformation(ResourceLocation pBiomeID, float pConquestRate, BanditChance pBanditSpawnChance) {
		this.biomeID = pBiomeID;
		this.conquestRate = pConquestRate;
		this.banditSpawnChance = pBanditSpawnChance;
	}

	public ResourceLocation getBiomeId() {
		return biomeID;
	}

	public float getConquestRate() {
		return conquestRate;
	}

	public BanditChance getBanditChance() {
		return banditSpawnChance;
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
			BanditChance banditChance = BanditChance.byName(JSONUtils.getAsString(jsonobject, "bandit_chance", "none"));
			return new ExtendedBiomeInformation(new ResourceLocation(id), conquestRate, banditChance);
		}
		@Override
		public JsonElement serialize(ExtendedBiomeInformation biomeInformation, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", biomeInformation.getBiomeId().toString());
			jsonobject.addProperty("conquest_rate", biomeInformation.getConquestRate());
			jsonobject.addProperty("bandit_chance", biomeInformation.getBanditChance().toString());
			return jsonobject;
		}
	}
	public static class Builder {
		private final ResourceLocation biomeID;
		private float conquestRate = 1.0f;
		private BanditChance banditSpawnChance = BanditChance.NONE;

		public Builder(Biome pBiome) {
			this(pBiome.getRegistryName());
		}

		public Builder(ResourceLocation pBiomeID) {
			this.biomeID = pBiomeID;
		}

		public Builder setConquestRate(float rate) {
			conquestRate = rate;
			return this;
		}

		public Builder setBanditChance(BanditChance pChance) {
			banditSpawnChance = pChance;
			return this;
		}

		public ExtendedBiomeInformation save(Consumer<ExtendedBiomeInformation> pConsumer) {
			ExtendedBiomeInformation spawner = new ExtendedBiomeInformation(biomeID, conquestRate, banditSpawnChance);
			pConsumer.accept(spawner);
			return spawner;
		}
	}
	@Nullable
	public static Biome getBiomeFromID(ResourceLocation biomeID) {
		return ForgeRegistries.BIOMES.getValue(biomeID);
	}
}