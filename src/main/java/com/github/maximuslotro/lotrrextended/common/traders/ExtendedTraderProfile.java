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

/**
 * Represents an extended trader profile with detailed trade configurations,
 * including trade pools, trade counts, refresh behavior, and alignment requirements.
 */
public class ExtendedTraderProfile {
	/** An empty default trader profile instance. */
	public static final ExtendedTraderProfile EMPTY = new ExtendedTraderProfile(LotrExtendedMod.EMPTY, 1000, 0, 0, new ArrayList<>(), new ArrayList<>(), true, 10, 100000, false);
	/** The unique identifier of this trader profile. */
	private final ResourceLocation id;
	/** Required trading alignment level to access this trader. */
	private final int tradingAlignmentRequired;
	/** Minimum number of trades this trader offers. */
	private final int minimumTrades;
	/** Maximum number of additional trades offered beyond minimum. */
	private final int maximumAdditionalTrades;
	/** List of trade pool items the trader buys from the player. */
	private final List<ExtendedTraderPoolItem> buyFromPlayerPool;
	/** List of trade pool items the trader sells to the player. */
	private final List<ExtendedTraderPoolItem> sellToPlayerPool;
	/** Whether this trader profile should refresh trades over time. */
	private final boolean shouldRefreshTrades;
	/** The trade value threshold after which trades refresh. */
	private final int refreshTradesAtValue;
	/** Cooldown duration in ticks after a trade refresh before new trades become available. */
	private final int lockTicksAfterRefresh;
	/** Whether this trader profile is generally a wandering trader. */
	private final boolean isTypicallyWandering;

	/**
	 * Constructs a new {@code ExtendedTraderProfile} instance.
	 *
	 * @param id Unique identifier for this profile.
	 * @param pTradingAlignmentRequired Required alignment to trade.
	 * @param minimumTrades Minimum trades offered by this trader.
	 * @param maximumAdditionalTrades Maximum additional trades offered randomly.
	 * @param buyFromPlayerPool List of items the trader buys from players.
	 * @param sellToPlayerPool List of items the trader sells to players.
	 * @param shouldRefreshTrades Indicates if trades should refresh after thresholds.
	 * @param refreshTradesAtValue The trade value threshold triggering a refresh.
	 * @param lockTicksAfterRefresh The cooldown time in ticks after refresh.
	 * @param isTypicallyWandering Indicates if trader is usually wandering.
	 */
	public ExtendedTraderProfile(ResourceLocation id, int pTradingAlignmentRequired, 
		int minimumTrades, int maximumAdditionalTrades, List<ExtendedTraderPoolItem> buyFromPlayerPool, List<ExtendedTraderPoolItem> sellToPlayerPool, 
		boolean shouldRefreshTrades, int refreshTradesAtValue, int lockTicksAfterRefresh, boolean isTypicallyWandering) {
		this.id = id;
		this.tradingAlignmentRequired = pTradingAlignmentRequired;
		this.minimumTrades = minimumTrades;
		this.maximumAdditionalTrades = maximumAdditionalTrades;
		this.buyFromPlayerPool = buyFromPlayerPool;
		this.sellToPlayerPool = sellToPlayerPool;
		this.shouldRefreshTrades = shouldRefreshTrades;
		this.refreshTradesAtValue = refreshTradesAtValue;
		this.lockTicksAfterRefresh = lockTicksAfterRefresh;
		this.isTypicallyWandering = isTypicallyWandering;
	}

	/**
	 * Gets the unique identifier of this trader profile.
	 *
	 * @return The {@link ResourceLocation} identifier.
	 */
	public ResourceLocation getId() {
		return id;
	}

	/**
	 * Gets the minimum number of trades the trader will offer.
	 *
	 * @return Minimum trade count.
	 */
	public int getMinimumTrades() {
		return minimumTrades;
	}

	/**
	 * Gets the maximum number of additional trades the trader can offer beyond minimum.
	 *
	 * @return Maximum additional trade count.
	 */
	public int getMaximumAdditionalTrades() {
		return maximumAdditionalTrades;
	}

	/**
	 * Gets the required trading alignment level for this trader.
	 *
	 * @return The alignment level required.
	 */
	public int getTradingAlignmentRequired() {
		return tradingAlignmentRequired;
	}

	/**
	 * Gets the list of trade pool items based on trade type.
	 *
	 * @param type The {@link ExtendedTradeType} indicating buy or sell pool.
	 * @return The list of {@link ExtendedTraderPoolItem} for the specified trade type.
	 */
	public List<ExtendedTraderPoolItem> getTraderPool(ExtendedTradeType type){
		if(type==ExtendedTradeType.BUY_FROM_PLAYER) {
			return getTraderBoughtPool();
		}else {
			return getTraderSellPool();
		}
	}
	/**
	 * Gets the list of trade pool items for bought from player.
	 *
	 * @return The list of {@link ExtendedTraderPoolItem} for bought items.
	 */
	public List<ExtendedTraderPoolItem> getTraderBoughtPool(){
		return buyFromPlayerPool;
	}
	/**
	 * Gets the list of trade pool items for sold to player.
	 *
	 * @return The list of {@link ExtendedTraderPoolItem} for sold items.
	 */
	public List<ExtendedTraderPoolItem> getTraderSellPool(){
		return sellToPlayerPool;
	}
	/**
	 * Checks if this profile should refresh trades periodically.
	 *
	 * @return {@code true} if trades should refresh; {@code false} otherwise.
	 */
	public boolean shouldRefreshTrades() {
		return shouldRefreshTrades;
	}

	/**
	 * Gets the trade value threshold at which trades refresh.
	 *
	 * @return The refresh value threshold.
	 */
	public int getRefreshTradesAtValue() {
		return refreshTradesAtValue;
	}

	/**
	 * Gets the cooldown duration (in ticks) after trades refresh before new trades can occur.
	 *
	 * @return The cooldown duration in ticks.
	 */
	public int getLockTicksAfterRefresh() {
		return lockTicksAfterRefresh;
	}

	/**
	 * Checks if this profile is typically wandering. Does not mean the current instance is wandering.
	 * Only internally used to fix wandering trader data if wandering ticks are greater than zero, but npc is not wandering.
	 *
	 * @return {@code true} if usually a wanderer; {@code false} otherwise.
	 */
	public boolean isTypicallyWandering() {
		return isTypicallyWandering;
	}

	/**
	 * Handles JSON serialization and deserialization of {@link ExtendedTraderProfile} objects.
	 */
	public static class Serializer implements JsonDeserializer<ExtendedTraderProfile>, JsonSerializer<ExtendedTraderProfile> {
		/**
		 * Deserializes a JSON representation of an {@link ExtendedTraderProfile} object.
		 * Validates ID format, enforces a maximum of 9 trade slots, and populates trader pools.
		 *
		 * @param json The input JSON element
		 * @param p_deserialize_2_ The target type (ignored)
		 * @param context The deserialization context used for parsing nested objects
		 * @return A constructed {@link ExtendedTraderProfile} or {@code null} if ID is invalid
		 * @throws JsonParseException If any parsing issues occur
		 */
		@Override
		public ExtendedTraderProfile deserialize(JsonElement json, Type p_deserialize_2_, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonobject = JSONUtils.convertToJsonObject(json, "ExtendedTraderProfile");
			String id = jsonobject.get("id").getAsString();
			if(id==null||!id.contains(":")) {
				ExtendedLog.warn("Failed to load trader profile due to invalid id: %s!", id);
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
			boolean isTypicallyWandering = JSONUtils.getAsBoolean(jsonobject, "isTypicallyWandering", false);
			List<ExtendedTraderPoolItem> buyFromPlayerPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "buyFromPlayer", new ExtendedTraderPoolItem[0], context, ExtendedTraderPoolItem[].class));
			List<ExtendedTraderPoolItem> sellToPlayerPool = Lists.newArrayList(JSONUtils.getAsObject(jsonobject, "sellToPlayer", new ExtendedTraderPoolItem[0], context, ExtendedTraderPoolItem[].class));
			return new ExtendedTraderProfile(new ResourceLocation(id), hiringAlignment, minimumTrades, maximumAdditionalTrades, buyFromPlayerPool, 
				sellToPlayerPool, shouldRefreshTrades, refreshTradesAtValue, lockTicksAfterRefresh, isTypicallyWandering);
		}
		/**
		 * Serializes an {@link ExtendedTraderProfile} instance into its JSON representation.
		 * Includes core trade logic fields and trade pools for selling and buying.
		 *
		 * @param traderProfile The profile to serialize
		 * @param p_serialize_2_ The type (ignored)
		 * @param context The serialization context for nested elements
		 * @return A {@link JsonElement} representing the serialized profile
		 */
		@Override
		public JsonElement serialize(ExtendedTraderProfile traderProfile, Type p_serialize_2_, JsonSerializationContext context) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("id", traderProfile.getId().toString());
			// Language
			jsonobject.addProperty("tradingAlignmentRequired", traderProfile.getTradingAlignmentRequired());
			if(traderProfile.isTypicallyWandering()) {
				jsonobject.addProperty("isTypicallyWandering", traderProfile.isTypicallyWandering());
			}
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
	/**
	 * Builder pattern for constructing {@link ExtendedTraderProfile} instances with configurable options.
	 */
	public static class Builder {
		private int tradingAlignmentRequired;
		private int minimumTrades = 3; // A minimum of 3 trades
		private int maximumAdditionalTrades = 6; // A maximum of 6 additional trades
		private List<ExtendedTraderPoolItem> buyFromPlayerPool = new ArrayList<>();
		private List<ExtendedTraderPoolItem> sellToPlayerPool = new ArrayList<>();
		private boolean shouldRefreshTrades = true;
		private int refreshTradesAtValue = 5000; // A default refresh at 5000c traded
		private int lockTicksAfterRefresh = 6000; // A default 5 minute cooldown 
		private boolean isTypicallyWandering = false; // Traders should not wander by default unless set
		/**
		 * Creates a builder for {@link ExtendedTraderProfile} with required trading alignment.
		 *
		 * @param pTradingAlignmentRequired Required alignment to trade.
		 */
		public Builder(int pTradingAlignmentRequired) {
			this.tradingAlignmentRequired = pTradingAlignmentRequired;
		}

		/**
		 * Sets the minimum number of trades offered.
		 *
		 * @param minimumTrades Minimum trades.
		 * @return This builder instance.
		 */
		public Builder setMinimumTrades(int minimumTrades) {
			this.minimumTrades = minimumTrades;
			return this;
		}

		/**
		 * Sets the maximum number of additional trades offered randomly.
		 *
		 * @param maximumAdditionalTrades Maximum additional trades.
		 * @return This builder instance.
		 */
		public Builder setMaximumAdditionalTrades(int maximumAdditionalTrades) {
			this.maximumAdditionalTrades = maximumAdditionalTrades;
			return this;
		}

		/**
		 * Sets both minimum and maximum additional trades.
		 *
		 * @param minimumTrades Minimum trades.
		 * @param maximumAdditionalTrades Maximum additional trades.
		 * @return This builder instance.
		 */
		public Builder setMinMaxTrades(int minimumTrades, int maximumAdditionalTrades) {
			this.minimumTrades = minimumTrades;
			this.maximumAdditionalTrades = maximumAdditionalTrades;
			return this;
		}
		/**
		 * Sets whether trades should refresh over time.
		 *
		 * @param shouldRefreshTrades {@code true} if trades refresh; {@code false} otherwise.
		 * @return This builder instance.
		 */
		public Builder setShouldRefreshTrades(boolean shouldRefreshTrades) {
			this.shouldRefreshTrades = shouldRefreshTrades;
			return this;
		}
		/**
		 * Sets the trade value threshold triggering refresh.
		 *
		 * @param refreshTradesAtValue The refresh value threshold.
		 * @return This builder instance.
		 */
		public Builder setRefreshTradesAtValue(int refreshTradesAtValue) {
			this.refreshTradesAtValue = refreshTradesAtValue;
			return this;
		}
		/**
		 * Sets the cooldown duration (in ticks) after trades refresh.
		 *
		 * @param lockTicksAfterRefresh Cooldown ticks.
		 * @return This builder instance.
		 */
		public Builder setLockTicksAfterRefresh(int lockTicksAfterRefresh) {
			this.lockTicksAfterRefresh = lockTicksAfterRefresh;
			return this;
		}
		/**
		 * Sets the trader as a wanderer for management purposes.
		 *
		 * @return This builder instance.
		 */
		public Builder setTypicallyWandering() {
			this.isTypicallyWandering = true;
			return this;
		}
		// BUY FROM PLAYER Single item methods

		/**
		 * Adds a single item to the buy-from-player pool with average cost.
		 *
		 * @param item The item to add.
		 * @param averageCost Average cost of the item.
		 * @return This builder instance.
		 */
		public Builder addSBuy(Item item, int averageCost) {
			return addSBuy(new ItemStack(item), averageCost);
		}
		/**
		 * Adds a single item (wrapped in RegistryObject) to the buy-from-player pool with average cost.
		 *
		 * @param item RegistryObject of the item.
		 * @param averageCost Average cost of the item.
		 * @return This builder instance.
		 */
		public Builder addSBuy(RegistryObject<Item> item, int averageCost) {
			return addSBuy(new ItemStack(item.get()), averageCost);
		}

		/**
		 * Adds a single item with weight, average cost, and max transfer quantity.
		 *
		 * @param item The item to add.
		 * @param weight Weight of this trade entry.
		 * @param averageCost Average cost.
		 * @param maxTransferQuantity Maximum transfer quantity
		 * @return This builder instance.
		 */
		public Builder addSBuy(Item item, int weight, int averageCost, int maxTransferQuantity) {
			return addSBuy(new ItemStack(item), weight, averageCost, maxTransferQuantity);
		}

		/**
		 * Adds a single-item entry to the trader's buy-from-player pool.
		 * Uses default weight and max transfer quantity.
		 *
		 * @param stack The item stack the trader will buy
		 * @param averageCost The average cost the trader will pay for the item
		 * @return The instance of this builder
		 */
		public Builder addSBuy(ItemStack stack, int averageCost) {
			return addSBuy(stack, 1, averageCost, 200);
		}

		/**
		 * Adds a single-item entry to the trader's buy-from-player pool.
		 *
		 * @param stack The item stack the trader will buy
		 * @param weight The relative weight for trade roll selection
		 * @param averageCost The average cost the trader will pay
		 * @param maxTransferQuantity The max quantity per trade transaction
		 * @return The instance of this builder
		 */
		public Builder addSBuy(ItemStack stack, int weight, int averageCost, int maxTransferQuantity) {
			this.buyFromPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierSingle(stack), weight, averageCost, maxTransferQuantity));
			return this;
		}

		// BUY FROM PLAYER MULTI METHODS
		/**
		 * Adds a multi-item entry to the buy-from-player pool using Item objects.
		 * Defaults to weight 1 and max transfer quantity 200.
		 *
		 * @param averageCost The average price for the trade pool
		 * @param items The items the trader will buy
		 * @return The instance of this builder
		 */
		public Builder addMBuy(int averageCost, Item... items) {
			List<ItemStack> itemStacks = Arrays.stream(items).map(ItemStack::new).collect(Collectors.toList());
			return addMBuy(itemStacks, 1, averageCost, 200);
		}
		/**
		 * Adds a multi-item entry to the buy-from-player pool using item stacks.
		 * Defaults to weight 1 and max transfer quantity 200.
		 *
		 * @param averageCost The average price for the trade pool
		 * @param stacks The item stacks the trader will buy
		 * @return The instance of this builder
		 */
		public Builder addMBuy(int averageCost, ItemStack... stacks) {
			List<ItemStack> itemStacks = Arrays.stream(stacks).collect(Collectors.toList());
			return addMBuy(itemStacks, 1, averageCost, 200);
		}

		/**
		 * Adds a multi-item entry to the buy-from-player pool.
		 *
		 * @param stacks The item stacks the trader will buy
		 * @param weight Relative chance of this entry being selected
		 * @param averageCost Average cost the trader pays
		 * @param maxTransferQuantity Max amount of items bought per trade
		 * @return The instance of this builder
		 */
		public Builder addMBuy(List<ItemStack> stacks, int weight, int averageCost, int maxTransferQuantity) {
			this.buyFromPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierMulti(stacks), weight, averageCost, maxTransferQuantity));
			return this;
		}

		// SELL TO PLAYER SINGLE METHODS
		/**
		 * Adds a single-item sale to the trader's sell-to-player pool.
		 * Uses default weight and max transfer quantity.
		 *
		 * @param item The item to be sold
		 * @param averageCost The average cost to the player
		 * @return The instance of this builder
		 */
		public Builder addSSell(Item item, int averageCost) {
			return addSSell(new ItemStack(item), 1, averageCost, 200);
		}

		/**
		 * Adds a single-item sale using a {@link RegistryObject}.
		 * Uses default weight and max transfer quantity.
		 *
		 * @param item The registry object representing the item
		 * @param averageCost The average cost to the player
		 * @return The instance of this builder
		 */
		public Builder addSSell(RegistryObject<Item> item, int averageCost) {
			return addSSell(new ItemStack(item.get()), 1, averageCost, 200);
		}
		/**
		 * Adds a single-item sale with full configuration.
		 *
		 * @param item The item to be sold
		 * @param weight The relative weight of the offer
		 * @param averageCost The average cost to the player
		 * @param maxTransferQuantity Maximum quantity per trade
		 * @return The instance of this builder
		 */
		public Builder addSSell(Item item, int weight, int averageCost, int maxTransferQuantity) {
			return addSSell(new ItemStack(item), weight, averageCost, maxTransferQuantity);
		}

		/**
		 * Adds a single-item sale using an ItemStack.
		 * Uses default weight and max transfer quantity.
		 *
		 * @param stack The item stack to sell
		 * @param averageCost The average cost to the player
		 * @return The instance of this builder
		 */
		public Builder addSSell(ItemStack stack, int averageCost) {
			return addSSell(stack, 1, averageCost, 200);
		}

		/**
		 * Adds a single-item sale using an ItemStack.
		 *
		 * @param stack The item stack to sell
		 * @param weight The relative weight of the offer
		 * @param averageCost The average cost to the player
		 * @param maxTransferQuantity Maximum quantity per trade
		 * @return The instance of this builder
		 */
		public Builder addSSell(ItemStack stack, int weight, int averageCost, int maxTransferQuantity) {
			this.sellToPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierSingle(stack), weight, averageCost, maxTransferQuantity));
			return this;
		}

		/* SELL TO PLAYER MULTI METHODS*/
		/**
		 * Adds a drink item to the sell-to-player pool with three potencies (Light, Moderate, Strong).
		 * Each potency has its own average cost and will be sold separately.
		 *
		 * @param item The registry object of a {@link VesselDrinkItem}
		 * @param lightCost Cost of the light potency version
		 * @param moderateCost Cost of the moderate potency version
		 * @param strongCost Cost of the strong potency version
		 * @return The instance of this builder
		 */
		public Builder addDrinkSell(RegistryObject<Item> item, int lightCost, int moderateCost, int strongCost) {
			if(!(item.get() instanceof VesselDrinkItem)) {
				ExtendedLog.warn("Recieved invalid drink from args, SKIPPING!: "+item.get().getRegistryName().toString());
				return this;
			}
			addSSell(DrinkUtils.drinkOfPotency(item.get(), Potency.LIGHT), 1, lightCost, 200, true);
			addSSell(DrinkUtils.drinkOfPotency(item.get(), Potency.MODERATE), 1, moderateCost, 200, true);
			addSSell(DrinkUtils.drinkOfPotency(item.get(), Potency.STRONG), 1, strongCost, 200, true);
			return this;
		}
		/**
		 * Adds a multi-item sale pool to the trader's sell-to-player pool using individual items.
		 * Uses default weight and max transfer quantity.
		 *
		 * @param averageCost The average cost of the items in the pool
		 * @param items The items to be offered for sale
		 * @return The instance of this builder
		 */
		public Builder addMSell(int averageCost, Item... items) {
			List<ItemStack> itemStacks = Arrays.stream(items).map(ItemStack::new).collect(Collectors.toList());
			return addMSell(itemStacks, 1, averageCost, 200);
		}
		/**
		 * Builds a multi sell pool with multiple item stacks
		 * 
		 * @param averageCost The average value of the items
		 * @param stacks The item stacks to include in the trade pool
		 * @return The instance of this builder
		 */
		public Builder addMSell(int averageCost, ItemStack... stacks) {
			List<ItemStack> itemStacks = Arrays.stream(stacks).collect(Collectors.toList());
			return addMSell(itemStacks, 1, averageCost, 200);
		}

		/**
		 * Adds a multi-item sell pool entry with explicit control over weight and quantity
		 * 
		 * @param stacks The item stacks to include
		 * @param weight The relative weight of this trade entry in the pool
		 * @param averageCost The average cost of the trade
		 * @param maxTransferQuantity The maximum quantity of items transferred in one trade
		 * @return The instance of this builder
		 */
		public Builder addMSell(List<ItemStack> stacks, int weight, int averageCost, int maxTransferQuantity) {
			this.sellToPlayerPool.add(new ExtendedTraderPoolItem(new ExtendedTradeItemSupplierMulti(stacks), weight, averageCost, maxTransferQuantity));
			return this;
		}

		/**
		 * Builds the {@link ExtendedTraderProfile} with the current configuration.
		 * 
		 * @param id The {@link ResourceLocation} id for this profile
		 * @return A fully constructed {@link ExtendedTraderProfile}
		 */
		public ExtendedTraderProfile build(ResourceLocation id) {
			if(minimumTrades+maximumAdditionalTrades>9) {
				ExtendedLog.error(id.toString()+" has defined a profile with more than 9 max slots");
			}
			ExtendedTraderProfile spawner = new ExtendedTraderProfile(id, tradingAlignmentRequired, minimumTrades, maximumAdditionalTrades, 
				buyFromPlayerPool, sellToPlayerPool, shouldRefreshTrades, refreshTradesAtValue, lockTicksAfterRefresh, isTypicallyWandering);
			return spawner;
		}

		/**
		 * Builds and saves the {@link ExtendedTraderProfile} using the given consumer.
		 * 
		 * @param pConsumer A consumer to accept the built profile
		 * @param pId The string id to be converted into a {@link ResourceLocation}
		 * @return The built {@link ExtendedTraderProfile}
		 */
		public ExtendedTraderProfile save(Consumer<ExtendedTraderProfile> pConsumer, String pId) {
			return save(pConsumer, new ResourceLocation(pId));
		}

		/**
		 * Builds and saves the {@link ExtendedTraderProfile} using the given consumer.
		 * 
		 * @param pConsumer A consumer to accept the built profile
		 * @param pId The {@link ResourceLocation} id of the profile
		 * @return The built {@link ExtendedTraderProfile}
		 */
		public ExtendedTraderProfile save(Consumer<ExtendedTraderProfile> pConsumer, ResourceLocation pId) {
			ExtendedTraderProfile spawner = this.build(pId);
			pConsumer.accept(spawner);
			return spawner;
		}
	}
	/**
	 * Attempts to get an {@link EntityType} from a string identifier.
	 * Used primarily to resolve entity types referenced in trader configurations.
	 * 
	 * @param str A string representing the {@link ResourceLocation} of the entity
	 * @return The corresponding {@link EntityType}, or null if not found
	 */
	@Nullable
	public static EntityType<?> getEntityTypeFromString(String str) {
		ResourceLocation entityReasourceName = new ResourceLocation(str);
		return ForgeRegistries.ENTITIES.getValue(entityReasourceName);
	}
}