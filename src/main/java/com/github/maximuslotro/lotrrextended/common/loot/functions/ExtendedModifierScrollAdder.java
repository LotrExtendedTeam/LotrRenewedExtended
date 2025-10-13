package com.github.maximuslotro.lotrrextended.common.loot.functions;

import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import lotr.common.init.ExtendedItems;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;

/**
 * A custom loot function that conditionally replaces a generated item
 * with a {@link ExtendedItems#MODIFIER_SCROLL} containing one or more
 * randomly generated modifiers.
 * <p>
 * The replacement is determined by a chance value: if the loot context's
 * random number generator hits zero within the given chance range, the
 * resulting loot item becomes a modifier scroll; otherwise, the original
 * item is returned unchanged.
 * </p>
 *
 * <p>Example: with a {@code chance} of 50, there is a 1-in-50 chance that
 * the original loot will be replaced by a modifier scroll.</p>
 *
 * <p>Additionally, the {@code skillful} property controls whether the
 * generated modifiers on the scroll are applied in "skillful" mode.
 * When {@code skillful} is true, the scroll is generated with enhanced
 * properties according to the {@link ExtendedModifierUtils} logic.</p>
 */
public class ExtendedModifierScrollAdder extends LootFunction {
	/**
	 * The 1-in-N probability of replacing a generated item
	 * with a modifier scroll.
	 * <p>
	 * For example, if {@code chance = 50}, then there is
	 * a 1-in-50 chance of creating a scroll.
	 * </p>
	 */
	private final int chance;
	/**
     * Determines whether the generated scroll applies modifiers in "skillful" mode.
     * <p>
     * If {@code true}, the {@link ExtendedModifierUtils#generateItemModifiers}
     * method applies enhanced modifiers to the scroll.
     * </p>
     */
	private final boolean skillful;
	/**
     * Creates a new modifier scroll adder with the specified loot conditions,
     * probability, and skillful setting.
     *
     * @param conditions loot conditions that must be met before this function applies
     * @param pChance    the 1-in-N chance of replacing the item with a modifier scroll
     * @param pSkillful  whether the generated scroll should apply skillful modifiers
     */
	private ExtendedModifierScrollAdder(ILootCondition[] conditions, int pChance, boolean pSkillful) {
		super(conditions);
		this.chance = pChance;
		this.skillful = pSkillful;
	}
	/**
	 * Gets the loot function type associated with this function.
	 *
	 * @return the registered {@link LootFunctionType} for modifier scroll addition
	 */
	@Override
	public LootFunctionType getType() {
		return ExtendedLootFunctions.MODIFIER_ACROLL_ADDER;
	}
	/**
     * Executes the loot function on the given {@link ItemStack}.
     * <p>
     * With a 1-in-{@code chance} probability, the provided item stack is
     * replaced with a modifier scroll that has random modifiers applied.
     * The {@code skillful} property determines if the scroll modifiers
     * are applied in skillful mode.
     * Otherwise, the original item stack is returned unchanged.
     * </p>
     *
     * @param stack   the item stack being processed by the loot system
     * @param context the current loot context providing randomness and conditions
     * @return the resulting {@link ItemStack}, either unchanged or replaced with a scroll
     */
	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		if(context.getRandom().nextInt(chance)==0) {
			stack = new ItemStack(ExtendedItems.MODIFIER_SCROLL.get());
			ExtendedModifierUtils.generateItemModifiersTagged(context.getRandom(), stack, skillful, true);
		}
		return stack;
	}
	/**
	 * Returns a new {@link Builder} configured for a "Smith" scroll with a
	 * 1-in-40 chance of generating a modifier scroll.
	 *
	 * @return a new builder instance for Smith scrolls
	 */
	public static ExtendedModifierScrollAdder.Builder chanceSmith() {
		return new ExtendedModifierScrollAdder.Builder(40);
	}
	/**
	 * Returns a new {@link Builder} configured for a "Smith Wild" scroll with a
	 * 1-in-45 chance of generating a modifier scroll.
	 *
	 * @return a new builder instance for Smith Wild scrolls
	 */
	public static ExtendedModifierScrollAdder.Builder chanceSmithWild() {
		return new ExtendedModifierScrollAdder.Builder(45);
	}
	/**
	 * Returns a new {@link Builder} configured with the default
	 * 1-in-50 chance of generating a modifier scroll.
	 *
	 * @return a new builder instance with default chance
	 */
	public static ExtendedModifierScrollAdder.Builder chanceDefault() {
		return new ExtendedModifierScrollAdder.Builder(50);
	}
	/**
	 * Returns a new {@link Builder} configured for a "Smith Rare" scroll with a
	 * 1-in-75 chance of generating a modifier scroll.
	 *
	 * @return a new builder instance for Smith Rare scrolls
	 */
	public static ExtendedModifierScrollAdder.Builder chanceSmithRare() {
		return new ExtendedModifierScrollAdder.Builder(75);
	}
	/**
	 * Returns a new {@link Builder} configured with a custom chance.
	 *
	 * @param pChance the 1-in-N chance of generating a modifier scroll
	 * @return a new builder instance with the specified chance
	 */
	public static ExtendedModifierScrollAdder.Builder chanceCustom(int pChance) {
		return new ExtendedModifierScrollAdder.Builder(pChance);
	}
	/**
     * Builder for {@link ExtendedModifierScrollAdder} instances.
     * <p>
     * Allows configuration of the chance value and skillful property
     * for generating modifier scrolls.
     * </p>
     */
	public static class Builder extends LootFunction.Builder<ExtendedModifierScrollAdder.Builder> {
		/**
		 * The 1-in-N probability of creating a modifier scroll.
		 * <p>
		 * Defaults to {@code 50} if not explicitly set.
		 * </p>
		 */
		private final int chance;
		/**
         * Whether the generated scroll should apply skillful modifiers.
         * <p>
         * Defaults to {@code false}.
         * </p>
         */
		private boolean skillful = false;
		/**
		 * Creates a builder with the specified chance.
		 *
		 * @param pChance the 1-in-N chance of creating a modifier scroll
		 */
		public Builder(int pChance) {
			this.chance = pChance;
		}
		/**
         * Sets the scroll to generate skillful modifiers.
         *
         * @return this builder for chaining
         */
		public Builder setSkillful() {
		    this.skillful = true;
		    return this;
		}
		@Override
		protected ExtendedModifierScrollAdder.Builder getThis() {
			return this;
		}
		/**
		 * Builds the {@link ExtendedModifierScrollAdder} instance with
		 * the configured conditions and chance value.
		 *
		 * @return a new {@link ExtendedModifierScrollAdder} loot function
		 */
		@Override
		public ILootFunction build() {
			return new ExtendedModifierScrollAdder(this.getConditions(), chance, skillful);
		}
	}
	/**
     * Handles JSON serialization and deserialization for {@link ExtendedModifierScrollAdder}.
     * <p>
     * Supports {@code chance} and {@code skillful} properties:
     * <ul>
     *   <li>{@code chance} defaults to 50 if not specified.</li>
     *   <li>{@code skillful} defaults to false if not specified.</li>
     * </ul>
     * </p>
     */
	public static class Serializer extends LootFunction.Serializer<ExtendedModifierScrollAdder>  {
		/**
		 * Serializes the scroll adder into JSON format.
		 *
		 * @param pJson                 the target JSON object
		 * @param pFunction             the function instance being serialized
		 * @param pSerializationContext the serialization context
		 */
		@Override
		public void serialize(JsonObject pJson, ExtendedModifierScrollAdder pFunction, JsonSerializationContext pSerializationContext) {
			super.serialize(pJson, pFunction, pSerializationContext);
			if(pFunction.chance!=50) {
				pJson.addProperty("chance", pFunction.chance);
			}
			if(pFunction.skillful) {
		        pJson.addProperty("skillful", true);
		    }
		}
		/**
		 * Deserializes a scroll adder from JSON format.
		 *
		 * @param pObject      the JSON object containing function data
		 * @param pDeserializationContext the deserialization context
		 * @param pConditions  loot conditions attached to this function
		 * @return a new {@link ExtendedModifierScrollAdder} with the parsed chance
		 */
		@Override
		public ExtendedModifierScrollAdder deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, ILootCondition[] pConditions) {
			int chance = JSONUtils.getAsInt(pObject, "chance", 50);
			boolean skillful = JSONUtils.getAsBoolean(pObject, "skillful", false);
			return new ExtendedModifierScrollAdder(pConditions, chance, skillful);
		}
	}
}