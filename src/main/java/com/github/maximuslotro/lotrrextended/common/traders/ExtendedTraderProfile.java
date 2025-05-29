package com.github.maximuslotro.lotrrextended.common.traders;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.LotrExtendedMod;
import com.github.maximuslotro.lotrrextended.common.enums.ExtendedTradeType;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ExtendedTradeItemSupplierMulti;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ExtendedTradeItemSupplierSingle;
import com.github.maximuslotro.lotrrextended.common.traders.pools.ExtendedTraderPoolItem;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lotr.common.init.LOTRItems;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselDrinkItem.Potency;
import lotr.common.util.DrinkUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedTraderProfile {
	public static final ExtendedTraderProfile EMPTY = new ExtendedTraderProfile(LotrExtendedMod.EMPTY, EntityType.PIG, 1000, 0, 0, new ArrayList<>(), new ArrayList<>(), true, 10, 100000);
	private final ResourceLocation id;
	private final int tradingAlignmentRequired;
	private final EntityType<?> traderEntityType; // TODO maybe remove if not used
	private final int minimumTrades;
	private final int maximumAdditionalTrades;
	private final List<ExtendedTraderPoolItem> buyFromPlayerPool;
	private final List<ExtendedTraderPoolItem> sellToPlayerPool;
	private final boolean shouldRefreshTrades;
	private final int refreshTradesAtValue;
	private final int lockTicksAfterRefresh;

	public ExtendedTraderProfile(ResourceLocation id, EntityType<?> pTraderEntityType, int pTradingAlignmentRequired, 
		int minimumTrades, int maximumAdditionalTrades, List<ExtendedTraderPoolItem> buyFromPlayerPool, List<ExtendedTraderPoolItem> sellToPlayerPool, 
		boolean shouldRefreshTrades, int refreshTradesAtValue, int lockTicksAfterRefresh) {
		this.id = id;
		this.tradingAlignmentRequired = pTradingAlignmentRequired;
		this.traderEntityType = pTraderEntityType;
		this.minimumTrades = minimumTrades;
		this.maximumAdditionalTrades = maximumAdditionalTrades;
		this.buyFromPlayerPool = buyFromPlayerPool;
		this.sellToPlayerPool = sellToPlayerPool;
		this.shouldRefreshTrades = shouldRefreshTrades;
		this.refreshTradesAtValue = refreshTradesAtValue;
		this.lockTicksAfterRefresh = lockTicksAfterRefresh;
	}

	/*
	 * Get the id of this ExtendedTraderProfile
	 * @return A {@link ResourceLocation} of the id
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

	/**
	 * Gets a trade pool list of this trader profile
	 * @param type The trade type {@link ExtendedTradeType}
	 * @return A list of pool trade entries
	 * */
	public List<ExtendedTraderPoolItem> getTraderPool(ExtendedTradeType type){
		if(type==ExtendedTradeType.BUY_FROM_PLAYER) {
			return buyFromPlayerPool;
		}else {
			return sellToPlayerPool;
		}
	}

	/**
	 * Should this profile ever refresh trades
	 * @return True is should refresh
	 * */
	public boolean shouldRefreshTrades() {
		return shouldRefreshTrades;
	}

	/**
	 * At what value should trades refresh
	 * @return The value at which trades should refresh
	 * */
	public int getRefreshTradesAtValue() {
		return refreshTradesAtValue;
	}

	/**
	 * How long should the trader tick before refreshing trades
	 * @return Get the time in ticks
	 * */
	public int getLockTicksAfterRefresh() {
		return lockTicksAfterRefresh;
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
			int minimumTrades = jsonobject.get("minimumTradesOffered").getAsInt();
			int maximumAdditionalTrades = jsonobject.get("maximumAdditionalTradesOffered").getAsInt();
			if(minimumTrades+maximumAdditionalTrades>9) {
				ExtendedLog.error(id.toString()+" has defined a profile with more than 9 max slots, capping at 9!");
				minimumTrades=3;
				maximumAdditionalTrades=6;
			}
			boolean shouldRefreshTrades = jsonobject.get("shouldRefreshTrades").getAsBoolean();
			int refreshTradesAtValue = jsonobject.get("refreshTradesAtValue").getAsInt();
			int lockTicksAfterRefresh = jsonobject.get("lockTicksAfterRefresh").getAsInt();
			List<ExtendedTraderPoolItem> buyFromPlayerPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "buyFromPlayer", new ExtendedTraderPoolItem[0], context, ExtendedTraderPoolItem[].class));
			List<ExtendedTraderPoolItem> sellToPlayerPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "sellToPlayer", new ExtendedTraderPoolItem[0], context, ExtendedTraderPoolItem[].class));
			return new ExtendedTraderProfile(new ResourceLocation(id), entityType, hiringAlignment, minimumTrades, maximumAdditionalTrades, buyFromPlayerPool, 
				sellToPlayerPool, shouldRefreshTrades, refreshTradesAtValue, lockTicksAfterRefresh);
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
			jsonobject.addProperty("shouldRefreshTrades", traderProfile.shouldRefreshTrades());
			jsonobject.addProperty("refreshTradesAtValue", traderProfile.getRefreshTradesAtValue());
			jsonobject.addProperty("lockTicksAfterRefresh", traderProfile.getLockTicksAfterRefresh());
			jsonobject.add("buyFromPlayer", context.serialize(traderProfile.buyFromPlayerPool));
			jsonobject.add("sellToPlayer", context.serialize(traderProfile.sellToPlayerPool));
			return jsonobject;
		}
	}

	public static class Builder {
		private int tradingAlignmentRequired;
		private final EntityType<?> traderEntityType;
		private int minimumTrades = 3; // A minimum of 3 trades
		private int maximumAdditionalTrades = 6; // A maximum of 6 additional trades
		private List<ExtendedTraderPoolItem> buyFromPlayerPool = new ArrayList<>();
		private List<ExtendedTraderPoolItem> sellToPlayerPool = new ArrayList<>();
		private boolean shouldRefreshTrades = true;
		private int refreshTradesAtValue = 5000; // A default refresh at 5000c traded
		private int lockTicksAfterRefresh = 6000; // A default 5 minute cooldown 
		
		public Builder(EntityType<?> pTraderEntityType, int pTradingAlignmentRequired) {
			this.traderEntityType = pTraderEntityType;
			this.tradingAlignmentRequired = pTradingAlignmentRequired;
		}

		/**
		 * Set the minimum number of trades a trader will offer
		 * @param minimumTrades The minimum number of trades the trader can offer
		 * @return The instance of this builder
		 */
		public Builder setMinimumTrades(int minimumTrades) {
			this.minimumTrades = minimumTrades;
			return this;
		}

		/**
		 * Set the minimum number of trades a trader will offer
		 * @param maximumAdditionalTrades The maximum number of additional trades a trader can offer randomly
		 * @return The instance of this builder
		 */
		public Builder setMaximumAdditionalTrades(int maximumAdditionalTrades) {
			this.maximumAdditionalTrades = maximumAdditionalTrades;
			return this;
		}

		/**
		 * Set the amount of trades a trader can offer
		 * @param minimumTrades The minimum number of trades the trader can offer
		 * @param maximumAdditionalTrades The maximum number of additional trades a trader can offer randomly
		 * @return The instance of this builder
		 */
		public Builder setMinMaxTrades(int minimumTrades, int maximumAdditionalTrades) {
			this.minimumTrades = minimumTrades;
			this.maximumAdditionalTrades = maximumAdditionalTrades;
			return this;
		}
		/**
		 * Controls if the trader should refresh trades
		 * @param shouldRefreshTrades Should the trader refresh trades
		 * @return The instance of this builder
		 */
		public Builder setShouldRefreshTrades(boolean shouldRefreshTrades) {
			this.shouldRefreshTrades = shouldRefreshTrades;
			return this;
		}
		/**
		 * Set the maximum value of items a trader can trade before the trades are refreshed
		 * @param refreshTradesAtValue The value of overall trades to refresh at
		 * @return The instance of this builder
		 */
		public Builder setRefreshTradesAtValue(int refreshTradesAtValue) {
			this.refreshTradesAtValue = refreshTradesAtValue;
			return this;
		}
		/**
		 * Set the length of time trader is locked after refresh
		 * @param lockTicksAfterRefresh Length of time in ticks
		 * @return The instance of this builder
		 */
		public Builder setLockTicksAfterRefresh(int lockTicksAfterRefresh) {
			this.lockTicksAfterRefresh = lockTicksAfterRefresh;
			return this;
		}

		/* BUY FROM PLAYER SINGLE METHODS*/
		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addSBuy(Item item, int averageCost) {
			return addSBuy(new ItemStack(item), averageCost);
		}
		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addSBuy(RegistryObject<Item> item, int averageCost) {
			return addSBuy(new ItemStack(item.get()), averageCost);
		}

		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addSBuy(Item item, int weight, int averageCost, int maxTransferQuantity) {
			return addSBuy(new ItemStack(item), weight, averageCost, maxTransferQuantity);
		}

		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addSBuy(ItemStack stack, int averageCost) {
			return addSBuy(stack, 1, averageCost, 200);
		}

		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addSBuy(ItemStack stack, int weight, int averageCost, int maxTransferQuantity) {
			this.buyFromPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierSingle(stack), weight, averageCost, maxTransferQuantity));
			return this;
		}

		/* BUY FROM PLAYER MULTI METHODS*/
		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addMBuy(int averageCost, Item... items) {
			List<ItemStack> itemStacks = Arrays.stream(items).map(ItemStack::new).collect(Collectors.toList());
			return addMBuy(itemStacks, 1, averageCost, 200);
		}
		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addMBuy(int averageCost, ItemStack... stacks) {
			List<ItemStack> itemStacks = Arrays.stream(stacks).collect(Collectors.toList());
			return addMBuy(itemStacks, 1, averageCost, 200);
		}

		/**
		 * Adds to the trader buy from player pool
		 * @return The instance of this builder
		 */
		public Builder addMBuy(List<ItemStack> stacks, int weight, int averageCost, int maxTransferQuantity) {
			this.buyFromPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierMulti(stacks), weight, averageCost, maxTransferQuantity));
			return this;
		}

		/* SELL TO PLAYER SINGLE METHODS*/
		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addSSell(Item item, int averageCost) {
			return addSSell(new ItemStack(item), 1, averageCost, 200);
		}
		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addSSell(RegistryObject<Item> item, int averageCost) {
			return addSSell(new ItemStack(item.get()), 1, averageCost, 200);
		}
		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addSSell(Item item, int weight, int averageCost, int maxTransferQuantity) {
			return addSSell(new ItemStack(item), weight, averageCost, maxTransferQuantity);
		}

		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addSSell(ItemStack stack, int averageCost) {
			return addSSell(stack, 1, averageCost, 200);
		}

		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addSSell(ItemStack stack, int weight, int averageCost, int maxTransferQuantity) {
			this.sellToPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierSingle(stack), weight, averageCost, maxTransferQuantity));
			return this;
		}

		/* SELL TO PLAYER MULTI METHODS*/
		/**
		 * Builds a multi sell pool with three drink potencies. Saves with average cost allowing cost calculations to be done
		 * when the pool is rolled during trader init.
		 * @return The instance of this builder
		 */
		public Builder addDrinkSell(RegistryObject<Item> item, int lightCost, int moderateCost, int strongCost) {
			if(!(item.get() instanceof VesselDrinkItem)) {
				ExtendedLog.warn("Recieved invalid drink from args, SKIPPING!: "+item.get().getRegistryName().toString());
				return this;
			}
			addSSell(DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.LIGHT), 1, lightCost, 200);
			addSSell(DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.MODERATE), 1, moderateCost, 200);
			addSSell(DrinkUtils.drinkOfPotency(LOTRItems.ALE.get(), Potency.STRONG), 1, strongCost, 200);
			return this;
		}
		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addMSell(int averageCost, Item... items) {
			List<ItemStack> itemStacks = Arrays.stream(items).map(ItemStack::new).collect(Collectors.toList());
			return addMSell(itemStacks, 1, averageCost, 200);
		}
		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addMSell(int averageCost, ItemStack... stacks) {
			List<ItemStack> itemStacks = Arrays.stream(stacks).collect(Collectors.toList());
			return addMSell(itemStacks, 1, averageCost, 200);
		}

		/**
		 * Adds to the trader sell to player pool
		 * @return The instance of this builder
		 */
		public Builder addMSell(List<ItemStack> stacks, int weight, int averageCost, int maxTransferQuantity) {
			this.sellToPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierMulti(stacks), weight, averageCost, maxTransferQuantity));
			return this;
		}
	
		/**
		 * Generate a {@link ExtendedTraderProfile}
		 * @param id The internal id of this profile
		 * @return A built {@link ExtendedTraderProfile} instance
		 */
		public ExtendedTraderProfile build(ResourceLocation id) {
			if(minimumTrades+maximumAdditionalTrades>9) {
				ExtendedLog.error(id.toString()+" has defined a profile with more than 9 max slots");
			}
			ExtendedTraderProfile spawner = new ExtendedTraderProfile(id, traderEntityType, tradingAlignmentRequired, minimumTrades, maximumAdditionalTrades, 
				buyFromPlayerPool, sellToPlayerPool, shouldRefreshTrades, refreshTradesAtValue, lockTicksAfterRefresh);
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