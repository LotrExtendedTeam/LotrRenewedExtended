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

	public ResourceLocation getId() {
		return ID;
	}

	public ExtendedTraderTradedTrigger.Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate playerPredicate, ConditionArrayParser p_230241_3_) {
		return new ExtendedTraderTradedTrigger.Instance(playerPredicate, EntityTypePredicate.fromJson(jsonObject.get("entity")), 
				jsonObject.get("istraderselling").getAsBoolean(), ItemPredicate.fromJson(jsonObject.get("item")));
	}

	public void trigger(ServerPlayerEntity player, EntityType<?> entityType, boolean isTraderSelling, ItemStack stack) {
		this.trigger(player, (instance) -> {
			return instance.matches(player, entityType, isTraderSelling, stack);
		});
	}

	public static class Instance extends CriterionInstance {
		private final EntityTypePredicate entityTypePredicate;
		private final boolean isTraderSelling;
		private final ItemPredicate item;

		public Instance(EntityPredicate.AndPredicate playerPredicate, EntityTypePredicate entityType, boolean isTraderSelling, ItemPredicate itemProvider) {
			super(ExtendedTraderTradedTrigger.ID, playerPredicate);
			this.entityTypePredicate = entityType;
			this.isTraderSelling = isTraderSelling;
			this.item = itemProvider;
		}

		public static ExtendedTraderTradedTrigger.Instance tradedWithAndItem(EntityType<?> entityType, boolean isTraderSelling, IItemProvider possableItem) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, EntityTypePredicate.of(entityType), isTraderSelling,
					new ItemPredicate((ITag<Item>) null, possableItem.asItem(),MinMaxBounds.IntBound.ANY,MinMaxBounds.IntBound.ANY,
							EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion) null, NBTPredicate.ANY));
		}

		public static ExtendedTraderTradedTrigger.Instance tradedWithALLItem(EntityType<?> entityType, boolean isTraderSelling) {
			return new ExtendedTraderTradedTrigger.Instance(EntityPredicate.AndPredicate.ANY, EntityTypePredicate.of(entityType), isTraderSelling,
					ItemPredicate.ANY);
		}

		public boolean matches(ServerPlayerEntity player, EntityType<?> entity, boolean isTraderSelling, ItemStack stack) {
			if (this.isTraderSelling && isTraderSelling) {
				return this.entityTypePredicate.matches(entity) && item.matches(stack);
			}
			if (!this.isTraderSelling && !isTraderSelling) {
				return this.entityTypePredicate.matches(entity) && item.matches(stack);
			}
			return false;
		}

		public JsonObject serializeToJson(ConditionArraySerializer serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.add("entity", this.entityTypePredicate.serializeToJson());
			jsonobject.addProperty("istraderselling", Boolean.toString(isTraderSelling));
			jsonobject.add("item", this.item.serializeToJson());
			return jsonobject;
		}
	}
}