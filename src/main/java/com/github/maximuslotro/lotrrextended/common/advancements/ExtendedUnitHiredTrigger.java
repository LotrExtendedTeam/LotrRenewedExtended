package com.github.maximuslotro.lotrrextended.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class ExtendedUnitHiredTrigger extends AbstractCriterionTrigger<ExtendedUnitHiredTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "unit_hired");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ExtendedUnitHiredTrigger.Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate playerPredicate, ConditionArrayParser p_230241_3_) {
		return new ExtendedUnitHiredTrigger.Instance(playerPredicate, EntityTypePredicate.fromJson(jsonObject.get("captain_type")),
			EntityTypePredicate.fromJson(jsonObject.get("unit_type")), MinMaxBounds.IntBound.fromJson(jsonObject.get("coins_moved")));
	}

	public void trigger(ServerPlayerEntity player, EntityType<?> captainType, EntityType<?> unitType, int coinAmount) {
		this.trigger(player, (instance) -> instance.matches(player, captainType, unitType, coinAmount));
	}

	public static class Instance extends CriterionInstance {
		private final EntityTypePredicate captainType;
		private final EntityTypePredicate unitType;
		private final MinMaxBounds.IntBound coinsTransphered;

		public Instance(EntityPredicate.AndPredicate playerPredicate, EntityTypePredicate captainType, EntityTypePredicate unitType, MinMaxBounds.IntBound coinsTransphered) {
			super(ExtendedUnitHiredTrigger.ID, playerPredicate);
			this.captainType = captainType;
			this.unitType = unitType;
			this.coinsTransphered = coinsTransphered;
		}

		public static ExtendedUnitHiredTrigger.Instance tradedWithAndItem(EntityTypePredicate captainType, EntityTypePredicate unitType, MinMaxBounds.IntBound coinsTransphered) {
			return new ExtendedUnitHiredTrigger.Instance(EntityPredicate.AndPredicate.ANY, captainType, unitType, coinsTransphered);
		}

		public static ExtendedUnitHiredTrigger.Instance tradedWithAndItem(EntityTypePredicate captainType, EntityTypePredicate unitType) {
			return new ExtendedUnitHiredTrigger.Instance(EntityPredicate.AndPredicate.ANY, captainType, unitType, MinMaxBounds.IntBound.ANY);
		}

		public boolean matches(ServerPlayerEntity player, EntityType<?> captainType, EntityType<?> unitType, int coinAmount) {
			return this.captainType.matches(captainType) && this.unitType.matches(unitType) && coinsTransphered.matches(coinAmount);
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer) {
			JsonObject jsonobject = super.serializeToJson(serializer);
			jsonobject.add("captain_type", this.captainType.serializeToJson());
			jsonobject.add("unit_type", this.unitType.serializeToJson());
			jsonobject.add("coins_moved", this.coinsTransphered.serializeToJson());
			return jsonobject;
		}
	}
}