package com.github.maximuslotro.lotrrextended.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

/**
 * A custom advancement trigger that activates when a player trades with a specific entity type,
 * and the trade meets criteria such as item type, trade direction (buying or selling), and coin value.
 */
public class ExtendedTraderTradedTrigger extends AbstractCriterionTrigger<ExtendedTraderTradedTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "trader_traded_with");

	/**
	 * @return The unique ID of this criterion trigger.
	 */
	@Override
	public ResourceLocation getId() {
		return ID;
	}

	/**
	 * Creates an instance of the trigger from JSON data.
	 *
	 * @param jsonObject       The JSON object containing trigger data.
	 * @param playerPredicate  Predicate for the player involved in the trade.
	 * @param contextParser      Context parser for deserialization.
	 * @return A new trigger instance based on the provided data.
	 */
	@Override
	public ExtendedTraderTradedTrigger.Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate playerPredicate, ConditionArrayParser contextParser) {
		return new ExtendedTraderTradedTrigger.Instance(playerPredicate, EntityTypePredicate.fromJson(jsonObject.get("entity")),
				jsonObject.get("istraderselling").getAsBoolean(), ItemPredicate.fromJson(jsonObject.get("item")), MinMaxBounds.IntBound.fromJson(jsonObject.get("coins_moved")));
	}

	/**
	 * Fires the trigger with the given trade parameters.
	 * The Count of items in the stack is not valid, could be more for eg. bulk buys
	 * 
	 * @param player                     The player performing the trade.
	 * @param entityType                 The entity type the player is trading with.
	 * @param didPlayerBuyItemFromTrader True if the player is buying (spending coins), false if selling.
	 * @param stack                      The item stack being traded.
	 * @param coinAmount                 The number of coins involved in the trade.
	 */
	public void trigger(ServerPlayerEntity player, EntityType<?> entityType, boolean didPlayerBuyItemFromTrader, ItemStack stack, int coinAmount) {
		this.trigger(player, (instance) -> instance.matches(player, entityType, didPlayerBuyItemFromTrader, stack, coinAmount));
	}

	/**
	 * Represents an instance of the {@link ExtendedTraderTradedTrigger}.
	 * It holds the specific conditions under which the advancement should trigger.
	 */
	public static class Instance extends CriterionInstance {
		private final EntityTypePredicate entityTypePredicate;
		private final boolean didPlayerBuyItemFromTrader;
		private final ItemPredicate item;
		private final MinMaxBounds.IntBound coinsTransphered;

		/**
		 * Constructs a new trigger instance.
		 *
		 * @param playerPredicate               The player predicate.
		 * @param entityType                    The predicate for the entity type traded with.
		 * @param didPlayerBuyItemFromTrader    Whether this instance expects a buying (true) or selling (false) trade.
		 * @param itemProvider                  The item predicate for traded items.
		 * @param coinsTransphered              Bounds for coins involved in the trade.
		 */
		public Instance(EntityPredicate.AndPredicate playerPredicate, EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader, ItemPredicate itemProvider, MinMaxBounds.IntBound coinsTransphered) {
			super(ExtendedTraderTradedTrigger.ID, playerPredicate);
			this.entityTypePredicate = entityType;
			this.didPlayerBuyItemFromTrader = didPlayerBuyItemFromTrader;
			this.item = itemProvider;
			this.coinsTransphered = coinsTransphered;
		}

		/**
		 * Creates an instance that matches a specific item and coin range for a trade with a given entity type.
		 *
		 * @param entityType                 The predicate for the entity type.
		 * @param didPlayerBuyItemFromTrader True if the player is buying, false if selling.
		 * @param possableItem              The item involved in the trade.
		 * @param coinsTransphered          The coin value range.
		 * @return A configured instance of the trigger.
		 */
		public static ExtendedTraderTradedTrigger.Instance tradedWithAndItem(EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader, IItemProvider possableItem, MinMaxBounds.IntBound coinsTransphered) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, entityType, didPlayerBuyItemFromTrader,
					new ItemPredicate(null, possableItem.asItem(), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY,
							EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NBTPredicate.ANY), coinsTransphered);
		}

		/**
		 * Creates an instance that matches any item, for a specific entity type and coin range.
		 *
		 * @param entityType                 The predicate for the entity type.
		 * @param didPlayerBuyItemFromTrader True if the player is buying, false if selling.
		 * @param coinsTransphered          The coin value range.
		 * @return A configured instance of the trigger.
		 */
		public static ExtendedTraderTradedTrigger.Instance tradedWithALLItem(EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader, MinMaxBounds.IntBound coinsTransphered) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, entityType, didPlayerBuyItemFromTrader,
					ItemPredicate.ANY, coinsTransphered);
		}

		/**
		 * Creates an instance that matches any item and any coin amount, for a specific entity type.
		 *
		 * @param entityType                 The predicate for the entity type.
		 * @param didPlayerBuyItemFromTrader True if the player is buying, false if selling.
		 * @return A configured instance of the trigger.
		 */
		public static ExtendedTraderTradedTrigger.Instance tradedWithALLItem(EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, entityType, didPlayerBuyItemFromTrader,
					ItemPredicate.ANY, MinMaxBounds.IntBound.ANY);
		}

		/**
		 * Determines if the provided trade matches the conditions of this trigger instance.
		 *
		 * @param player                     The player involved in the trade.
		 * @param entity                     The entity type traded with.
		 * @param didPlayerBuyItemFromTrader True if the player bought from the trader.
		 * @param stack                      The item involved in the trade.
		 * @param coinAmount                 The number of coins used in the trade.
		 * @return True if all conditions match; false otherwise.
		 */
		public boolean matches(ServerPlayerEntity player, EntityType<?> entity, boolean didPlayerBuyItemFromTrader, ItemStack stack, int coinAmount) {
			if ((this.didPlayerBuyItemFromTrader && didPlayerBuyItemFromTrader) || (!this.didPlayerBuyItemFromTrader && !didPlayerBuyItemFromTrader)) {
				return this.entityTypePredicate.matches(entity) && item.matches(stack) && coinsTransphered.matches(coinAmount);
			}
			return false;
		}

		/**
		 * Serializes this instance into a JSON object for saving in an advancement file.
		 *
		 * @param serializer The advancement condition serializer.
		 * @return A JSON representation of this trigger instance.
		 */
		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.add("entity", this.entityTypePredicate.serializeToJson());
			jsonobject.addProperty("istraderselling", Boolean.toString(didPlayerBuyItemFromTrader));
			jsonobject.add("item", this.item.serializeToJson());
			jsonobject.add("coins_moved", this.coinsTransphered.serializeToJson());
			return jsonobject;
		}
	}
}