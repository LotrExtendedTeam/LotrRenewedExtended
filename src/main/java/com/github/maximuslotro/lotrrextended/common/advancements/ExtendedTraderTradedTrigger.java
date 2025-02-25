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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class ExtendedTraderTradedTrigger extends AbstractCriterionTrigger<ExtendedTraderTradedTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "trader_traded_with");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ExtendedTraderTradedTrigger.Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate playerPredicate, ConditionArrayParser p_230241_3_) {
		return new ExtendedTraderTradedTrigger.Instance(playerPredicate, EntityTypePredicate.fromJson(jsonObject.get("entity")),
				jsonObject.get("istraderselling").getAsBoolean(), ItemPredicate.fromJson(jsonObject.get("item")), MinMaxBounds.IntBound.fromJson(jsonObject.get("coins_moved")));
	}

	public void trigger(ServerPlayerEntity player, EntityType<?> entityType, boolean didPlayerBuyItemFromTrader, ItemStack stack, int coinAmount) {
		this.trigger(player, (instance) -> instance.matches(player, entityType, didPlayerBuyItemFromTrader, stack, coinAmount));
	}

	public static class Instance extends CriterionInstance {
		private final EntityTypePredicate entityTypePredicate;
		private final boolean didPlayerBuyItemFromTrader;
		private final ItemPredicate item;
		private final MinMaxBounds.IntBound coinsTransphered;

		public Instance(EntityPredicate.AndPredicate playerPredicate, EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader, ItemPredicate itemProvider, MinMaxBounds.IntBound coinsTransphered) {
			super(ExtendedTraderTradedTrigger.ID, playerPredicate);
			this.entityTypePredicate = entityType;
			this.didPlayerBuyItemFromTrader = didPlayerBuyItemFromTrader;
			this.item = itemProvider;
			this.coinsTransphered = coinsTransphered;
		}

		public static ExtendedTraderTradedTrigger.Instance tradedWithAndItem(EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader, IItemProvider possableItem, MinMaxBounds.IntBound coinsTransphered) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, entityType, didPlayerBuyItemFromTrader,
					new ItemPredicate((ITag<Item>) null, possableItem.asItem(), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY,
							EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion) null, NBTPredicate.ANY), coinsTransphered);
		}

		public static ExtendedTraderTradedTrigger.Instance tradedWithALLItem(EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader, MinMaxBounds.IntBound coinsTransphered) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, entityType, didPlayerBuyItemFromTrader,
					ItemPredicate.ANY, coinsTransphered);
		}

		public static ExtendedTraderTradedTrigger.Instance tradedWithALLItem(EntityTypePredicate entityType, boolean didPlayerBuyItemFromTrader) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, entityType, didPlayerBuyItemFromTrader,
					ItemPredicate.ANY, MinMaxBounds.IntBound.ANY);
		}

		public boolean matches(ServerPlayerEntity player, EntityType<?> entity, boolean didPlayerBuyItemFromTrader, ItemStack stack, int coinAmount) {
			if ((this.didPlayerBuyItemFromTrader && didPlayerBuyItemFromTrader) || (!this.didPlayerBuyItemFromTrader && !didPlayerBuyItemFromTrader)) {
				return this.entityTypePredicate.matches(entity) && item.matches(stack) && coinsTransphered.matches(coinAmount);
			}
			return false;
		}

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