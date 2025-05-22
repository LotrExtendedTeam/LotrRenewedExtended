package com.github.maximuslotro.lotrrextended.common.traders;

import java.lang.reflect.Type;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.entity.EntityType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedTraderProfile {
	public static final ExtendedTraderProfile EMPTY = new ExtendedTraderProfile(LotrExtendedMod.EMPTY, EntityType.PIG, 1000, 0, 0);
	private final ResourceLocation id;
	private final int tradingAlignmentRequired;
	private final EntityType<?> traderEntityType;
	private final int minimumTrades;
	private final int maximumAdditionalTrades;

	public ExtendedTraderProfile(ResourceLocation id, EntityType<?> pTraderEntityType, int pTradingAlignmentRequired, int minimumTrades, int maximumAdditionalTrades) {
		this.id = id;
		this.tradingAlignmentRequired = pTradingAlignmentRequired;
		this.traderEntityType = pTraderEntityType;
		this.minimumTrades = minimumTrades;
		this.maximumAdditionalTrades = maximumAdditionalTrades;
	}

	/*
	 * Get the id of this ExtendedTraderProfile
	 * */
	public ResourceLocation getId() {
		return id;
	}

	/**
	 * The minimum amount of trades the trader will offer
	 * @return the minimum trade count
	 * */
	public int getMinimumTrades() {
		return minimumTrades;
	}

	/**
	 * The maximum amount of additional trades the trader will offer
	 * @return the alignment level
	 * */
	public int getMaximumAdditionalTrades() {
		return maximumAdditionalTrades;
	}

	/**
	 * Trading alignment level for the trader
	 * @return the alignment level
	 * */
	public int getTradingAlignmentRequired() {
		return tradingAlignmentRequired;
	}

	/**
	 * Gets the trader entity type for this profile
	 * @see EntityType
	 * @return The EntityType instance for this profile
	 * */
	public EntityType<?> getTraderEntityType() {
		return traderEntityType;
	}

	/*
	 * Serializer for ExtendedTraderProfile
	 * */
	public static class Serializer implements JsonDeserializer<ExtendedTraderProfile>, JsonSerializer<ExtendedTraderProfile> {
		@Override
		public ExtendedTraderProfile deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedTraderProfile");
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load trader profile due to invalid id: %s!", id);
				return null;
			}
			EntityType<?> entityType = getEntityTypeFromString(jsonobject.get("entity_type").getAsString());
			if (entityType == null) {
				ExtendedLog.warn("Failed to load trader entity type: "+jsonobject.get("entity_type").getAsString());
				return null;
			}
			int hiringAlignment = jsonobject.get("tradingAlignmentRequired").getAsInt();
			int mininmunTrades = jsonobject.get("minimumTradesOffered").getAsInt();
			int maximumAdditionalTrades = jsonobject.get("maximumAdditionalTradesOffered").getAsInt();
			return new ExtendedTraderProfile(new ResourceLocation(id), entityType, hiringAlignment, mininmunTrades, maximumAdditionalTrades);
		}

		@Override
		public JsonElement serialize(ExtendedTraderProfile traderProfile, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", traderProfile.getId().toString());
			jsonobject.addProperty("entity_type", traderProfile.traderEntityType.getRegistryName().toString());
			// Language
			jsonobject.addProperty("tradingAlignmentRequired", traderProfile.getTradingAlignmentRequired());
			jsonobject.addProperty("minimumTradesOffered", traderProfile.getMinimumTrades());
			jsonobject.addProperty("maximumAdditionalTradesOffered", traderProfile.getMaximumAdditionalTrades());

			return jsonobject;
		}
	}

	public static class Builder {
		private int tradingAlignmentRequired;
		private final EntityType<?> traderEntityType;
		private int minimumTrades = 3;
		private int maximumAdditionalTrades = 6;

		public Builder(EntityType<?> pTraderEntityType, int pTradingAlignmentRequired) {
			this.traderEntityType = pTraderEntityType;
			this.tradingAlignmentRequired = pTradingAlignmentRequired;
		}

		/**
		 * Set the minimum number of trades a trader will offer
		 * @param minimumTrades The minimum number of trades the trader can offer
		 */
		public Builder setMinimumTrades(int minimumTrades) {
			this.minimumTrades = minimumTrades;
			return this;
		}

		/**
		 * Set the minimum number of trades a trader will offer
		 * @param maximumAdditionalTrades The maximum number of additional trades a trader can offer randomly
		 */
		public Builder setMaximumAdditionalTrades(int maximumAdditionalTrades) {
			this.maximumAdditionalTrades = maximumAdditionalTrades;
			return this;
		}

		/**
		 * Set the ammount of trades a trader can offer
		 * @param minimumTrades The minimum number of trades the trader can offer
		 * @param maximumAdditionalTrades The maximum number of additional trades a trader can offer randomly
		 */
		public Builder setMinMaxTrades(int minimumTrades, int maximumAdditionalTrades) {
			this.minimumTrades = minimumTrades;
			this.maximumAdditionalTrades = maximumAdditionalTrades;
			return this;
		}
	
		/**
		 * Generate a ExtendedTraderProfile
		 */
		public ExtendedTraderProfile build(ResourceLocation id) {
			ExtendedTraderProfile spawner = new ExtendedTraderProfile(id, traderEntityType, tradingAlignmentRequired, minimumTrades, maximumAdditionalTrades);
			return spawner;
		}

		/**
		 * Generate a ExtendedTraderProfile and save with a Consumer
		 */
		public ExtendedTraderProfile save(Consumer<ExtendedTraderProfile> pConsumer, String pId) {
			return save(pConsumer, new ResourceLocation(pId));
		}

		/**
		 * Generate a ExtendedTraderProfile and save with a Consumer
		 */
		public ExtendedTraderProfile save(Consumer<ExtendedTraderProfile> pConsumer, ResourceLocation pId) {
			ExtendedTraderProfile spawner = this.build(pId);
			pConsumer.accept(spawner);
			return spawner;
		}
	}

	@Nullable
	public static EntityType<?> getEntityTypeFromString(String str) {
		ResourceLocation entityReasourceName = new ResourceLocation(str);
		return ForgeRegistries.ENTITIES.getValue(entityReasourceName);
	}
}