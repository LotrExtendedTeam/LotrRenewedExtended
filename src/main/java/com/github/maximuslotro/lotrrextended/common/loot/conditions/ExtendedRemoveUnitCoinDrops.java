package com.github.maximuslotro.lotrrextended.common.loot.conditions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import lotr.common.entity.npc.ExtendedNPCEntity;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

/**
 * Loot condition that prevents NPC unit entities on active duty
 * from dropping unit coins when killed.
 * <p>
 * This condition is used in loot tables to filter coin drops
 * so that only inactive or non-unit NPCs can provide them.
 * </p>
 */
public class ExtendedRemoveUnitCoinDrops implements ILootCondition {
	/**
	 * Returns the registered type of this loot condition.
	 *
	 * @return the {@link LootConditionType} for this condition
	 */
	@Override
	public LootConditionType getType() {
		return ExtendedLootConditions.REMOVE_UNIT_COINS;
	}
	/**
	 * Returns the set of loot context parameters used by this condition.
	 * <p>
	 * In this case, it depends on {@link LootParameters#THIS_ENTITY},
	 * since the condition checks the entity's NPC state.
	 * </p>
	 *
	 * @return the referenced context parameters
	 */
	@Override
	public Set<LootParameter<?>> getReferencedContextParams() {
		return ImmutableSet.of(LootParameters.THIS_ENTITY);
	}
	/**
	 * Tests whether the loot condition is satisfied.
	 * <p>
	 * This condition evaluates the current loot context entity:
	 * <ul>
	 *   <li>If the entity is an {@link NPCEntity} and an {@link ExtendedNPCEntity}
	 *       whose unit info reports that it is <b>active duty</b>, the test fails
	 *       (no unit coin drops).</li>
	 *   <li>Otherwise, the condition passes.</li>
	 * </ul>
	 * </p>
	 *
	 * @param context the current loot context
	 * @return {@code true} if the entity is allowed to drop coins,
	 *         {@code false} if coin drops should be removed
	 */
	@Override
	public boolean test(LootContext context) {
		Entity entity = context.getParamOrNull(LootParameters.THIS_ENTITY);
		return !(entity instanceof NPCEntity && ((ExtendedNPCEntity)entity).getUnitInfo().isNpcActiveDuty());
	}
	/**
	 * Creates a new loot condition builder for this condition type.
	 *
	 * @return a builder for {@link ExtendedRemoveUnitCoinDrops}
	 */
	public static ILootCondition.IBuilder setRemoveUnitCoinsDropBuilder() {
		return ExtendedRemoveUnitCoinDrops::new;
	}
	/**
	 * Handles JSON serialization and deserialization of
	 * {@link ExtendedRemoveUnitCoinDrops} for loot tables.
	 */
	public static class Serializer implements ILootSerializer<ExtendedRemoveUnitCoinDrops> {
		/**
		 * Serializes the loot condition into a JSON object.
		 * <p>
		 * This implementation has no additional properties
		 * and therefore writes nothing beyond the base type.
		 * </p>
		 *
		 * @param pJson the target JSON object
		 * @param pValue the condition instance being serialized
		 * @param pSerializationContext the serialization context
		 */
		@Override
		public void serialize(JsonObject pJson, ExtendedRemoveUnitCoinDrops pValue, JsonSerializationContext pSerializationContext) {}
		/**
		 * Deserializes the loot condition from a JSON object.
		 * <p>
		 * Since this condition has no parameters, it always
		 * returns a new instance of {@link ExtendedRemoveUnitCoinDrops}.
		 * </p>
		 *
		 * @param pJson the JSON data
		 * @param pSerializationContext the deserialization context
		 * @return a new {@link ExtendedRemoveUnitCoinDrops} instance
		 */
		@Override
		public ExtendedRemoveUnitCoinDrops deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
			return new ExtendedRemoveUnitCoinDrops();
		}
	}
}