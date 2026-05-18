package com.github.maximuslotro.lotrrextended.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class ExtendedDrinkingTrigger extends AbstractCriterionTrigger<ExtendedDrinkingTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "drinking");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ExtendedDrinkingTrigger.Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate playerPredicate, ConditionArrayParser p_230241_3_) {
		return new ExtendedDrinkingTrigger.Instance(playerPredicate, ItemPredicate.fromJson(jsonObject.get("drink_item")),
				MinMaxBounds.IntBound.fromJson(jsonObject.get("drink_duration")), MinMaxBounds.IntBound.fromJson(jsonObject.get("tolerance")));
	}

	public void trigger(ServerPlayerEntity player, ItemStack stack, int drinkDuration, int tolerance) {
		this.trigger(player, (instance) -> instance.matches(player, stack, drinkDuration, tolerance));
	}

	public static class Instance extends CriterionInstance {
		private final ItemPredicate drunkItem;
		private final MinMaxBounds.IntBound drinkDuration;
		private final MinMaxBounds.IntBound tolerance;

		public Instance(EntityPredicate.AndPredicate playerPredicate, ItemPredicate drunkItem, MinMaxBounds.IntBound drinkDuration, MinMaxBounds.IntBound tolerance) {
			super(ExtendedDrinkingTrigger.ID, playerPredicate);
			this.drunkItem = drunkItem;
			this.drinkDuration = drinkDuration;
			this.tolerance = tolerance;
		}

		public static ExtendedDrinkingTrigger.Instance any() {
			return new ExtendedDrinkingTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY);
		}

		public static ExtendedDrinkingTrigger.Instance item(Item drinkDuration) {
			return new ExtendedDrinkingTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(drinkDuration).build(), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY);
		}

		public static ExtendedDrinkingTrigger.Instance drinkStrengeth(int drinkDuration) {
			return new ExtendedDrinkingTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.ANY, MinMaxBounds.IntBound.atLeast(drinkDuration), MinMaxBounds.IntBound.ANY);
		}

		public static ExtendedDrinkingTrigger.Instance tolerance(int tolerance) {
			return new ExtendedDrinkingTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.atLeast(tolerance));
		}

		public boolean matches(ServerPlayerEntity player, ItemStack stack, int drinkDuration, int tolerance) {
			return this.drunkItem.matches(stack) && this.drinkDuration.matches(drinkDuration) && this.tolerance.matches(tolerance);
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.add("drink_item", this.drunkItem.serializeToJson());
			jsonobject.add("drink_duration", this.drinkDuration.serializeToJson());
			jsonobject.add("tolerance", this.tolerance.serializeToJson());
			return jsonobject;
		}
	}
}