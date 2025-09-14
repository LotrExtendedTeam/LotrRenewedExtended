package com.github.maximuslotro.lotrrextended.common.loot.functions;

import java.util.Random;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;

/**
 * A custom loot function that applies random {@link ExtendedModifier}s
 * to an {@link ItemStack} during loot generation.
 * <p>
 * The behavior of this function can be customized with two flags:
 * <ul>
 *   <li>{@code reforging} – determines if reforging-only modifiers
 *       are eligible for selection.</li>
 *   <li>{@code noNegatives} – if true, excludes modifiers with
 *       negative levels from the pool.</li>
 * </ul>
 * </p>
 *
 * <p>
 * For example, this function can be used in loot tables to grant
 * weapons or items with random positive modifiers, or to simulate
 * reforging by including special modifiers otherwise excluded.
 * </p>
 */
public class ExtendedModifierAdder extends LootFunction {
	/**
	 * Whether reforging-only modifiers should be allowed.
	 * <p>
	 * If {@code true}, modifiers marked as "skillful" or normally only
	 * applied during reforging can be included in the random selection.
	 * </p>
	 */
	private final boolean reforging;
	/**
	 * Whether negative modifiers should be excluded.
	 * <p>
	 * If {@code true}, any modifiers with a negative level are skipped
	 * during modifier selection.
	 * </p>
	 */
	private final boolean noNegatives;
	/**
	 * Creates a new modifier adder with the specified loot conditions
	 * and configuration flags.
	 *
	 * @param conditions   loot conditions that must be satisfied before this function applies
	 * @param pReforging   whether reforging-only modifiers are allowed
	 * @param pNoNegatives whether negative modifiers should be excluded
	 */
	private ExtendedModifierAdder(ILootCondition[] conditions, boolean pReforging, boolean pNoNegatives) {
		super(conditions);
		this.reforging = pReforging;
		this.noNegatives = pNoNegatives;
	}
	/**
	 * Gets the loot function type for this function.
	 *
	 * @return the {@link LootFunctionType} registered as {@link ExtendedLootFunctions#MODIFIER_ADDER}
	 */
	@Override
	public LootFunctionType getType() {
		return ExtendedLootFunctions.MODIFIER_ADDER;
	}
	/**
	 * Applies random modifiers to the given item stack.
	 * <p>
	 * Uses {@link ExtendedModifierUtils#generateItemModifiers(Random, ItemStack, boolean, boolean)}
	 * to apply one or more modifiers depending on the configuration flags.
	 * </p>
	 *
	 * @param pStack   the {@link ItemStack} being modified
	 * @param pContext the loot context providing conditions and randomness
	 * @return the modified {@link ItemStack} with applied modifiers
	 */
	@Override
	public ItemStack run(ItemStack pStack, LootContext pContext) {
		return ExtendedModifierUtils.generateItemModifiers(pContext.getRandom(), pStack, reforging, noNegatives);
	}
	/**
	 * Creates a new {@link Builder} for constructing
	 * {@link ExtendedModifierAdder} instances in code or loot JSON.
	 *
	 * @return a new builder instance
	 */
	public static ExtendedModifierAdder.Builder modifiers() {
		return new ExtendedModifierAdder.Builder();
	}
	/**
	 * Builder for {@link ExtendedModifierAdder} instances.
	 * <p>
	 * Allows configuration of the {@code reforging} and {@code noNegatives}
	 * flags when creating a new loot function.
	 * </p>
	 */
	public static class Builder extends LootFunction.Builder<ExtendedModifierAdder.Builder> {
		/**
		 * Whether reforging-only modifiers should be allowed.
		 * Defaults to {@code false}.
		 */
		private boolean reforging = false;
		/**
		 * Whether negative modifiers should be excluded.
		 * Defaults to {@code false}.
		 */
		private boolean noNegatives = false;
		/**
		 * Creates a new builder with default settings
		 * (no reforging, negatives allowed).
		 */
		public Builder() {}

		@Override
		protected ExtendedModifierAdder.Builder getThis() {
			return this;
		}
		/**
		 * Enables reforging mode, allowing reforging-only modifiers
		 * to be included in the modifier pool.
		 *
		 * @return this builder instance
		 */
		public ExtendedModifierAdder.Builder reforging() {
			this.reforging = true;
			return this;
		}
		/**
		 * Disables negative modifiers, ensuring only positive or neutral
		 * modifiers can be applied.
		 *
		 * @return this builder instance
		 */
		public ExtendedModifierAdder.Builder noNegatives() {
			this.noNegatives = true;
			return this;
		}
		/**
		 * Builds a new {@link ExtendedModifierAdder} instance
		 * using the configured options.
		 *
		 * @return a new {@link ExtendedModifierAdder} loot function
		 */
		@Override
		public ILootFunction build() {
			return new ExtendedModifierAdder(this.getConditions(), this.reforging, this.noNegatives);
		}
	}
	/**
	 * Handles JSON serialization and deserialization for
	 * {@link ExtendedModifierAdder}.
	 * <p>
	 * Supports the properties:
	 * <ul>
	 *   <li>{@code reforging} – defaults to {@code false}</li>
	 *   <li>{@code no_negatives} – defaults to {@code false}</li>
	 * </ul>
	 * </p>
	 */
	public static class Serializer extends LootFunction.Serializer<ExtendedModifierAdder> {
		/**
		 * Serializes the modifier adder into JSON.
		 *
		 * @param pJson                   the target JSON object
		 * @param pValue                  the function instance to serialize
		 * @param pSerializationContext   the serialization context
		 */
		@Override
		public void serialize(JsonObject pJson, ExtendedModifierAdder pValue, JsonSerializationContext pSerializationContext) {
			super.serialize(pJson, pValue, pSerializationContext);
			if(pValue.reforging) {
				pJson.addProperty("reforging", pValue.reforging);
			}
			if(pValue.noNegatives) {
				pJson.addProperty("no_negatives", pValue.noNegatives);
			}
		}
		/**
		 * Deserializes a modifier adder from JSON.
		 *
		 * @param pObject       the JSON object containing function data
		 * @param pContext      the deserialization context
		 * @param pConditions   any conditions attached to the loot function
		 * @return a new {@link ExtendedModifierAdder} with the parsed settings
		 */
		@Override
		public ExtendedModifierAdder deserialize(JsonObject pObject, JsonDeserializationContext pContext, ILootCondition[] pConditions) {
			boolean reforging = JSONUtils.getAsBoolean(pObject, "reforging", false);
			boolean noNegatives = JSONUtils.getAsBoolean(pObject, "no_negatives", false);
			return new ExtendedModifierAdder(pConditions, reforging, noNegatives);
		}
	}
}