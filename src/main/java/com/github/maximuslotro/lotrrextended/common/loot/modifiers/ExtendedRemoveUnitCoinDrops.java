package com.github.maximuslotro.lotrrextended.common.loot.modifiers;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import lotr.common.entity.npc.ExtendedHirableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class ExtendedRemoveUnitCoinDrops implements ILootCondition {

	@Override
	public LootConditionType getType() {
		return ExtendedLootModifiers.REMOVE_UNIT_COINS;
	}

	/**
	 * Get the parameters used by this object.
	 */
	@Override
	public Set<LootParameter<?>> getReferencedContextParams() {
		return ImmutableSet.of(LootParameters.THIS_ENTITY);
	}

	@Override
	public boolean test(LootContext context) {
		Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
		return !(entity instanceof ExtendedHirableEntity);
	}

	public static ILootCondition.IBuilder setRemoveUnitCoinsDropBuilder() {
		return () -> {
			return new ExtendedRemoveUnitCoinDrops();
		};
	}

	public static class Serializer implements ILootSerializer<ExtendedRemoveUnitCoinDrops> {
		/**
		 * Serialize the value by putting its data into the JsonObject.
		 */
		@Override
		public void serialize(JsonObject pJson, ExtendedRemoveUnitCoinDrops pValue, JsonSerializationContext pSerializationContext) {}

		/**
		 * Deserialize a value by reading it from the JsonObject.
		 */
		@Override
		public ExtendedRemoveUnitCoinDrops deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
			return new ExtendedRemoveUnitCoinDrops();
		}
	}
}