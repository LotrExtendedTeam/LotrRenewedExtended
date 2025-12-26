package com.github.maximuslotro.lotrrextended.common.loot.functions;

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


public class ExtendedModifierScrollSetter extends LootFunction {

	private final boolean skillful;

	private ExtendedModifierScrollSetter(ILootCondition[] conditions, boolean pSkillful) {
		super(conditions);
		this.skillful = pSkillful;
	}

	@Override
	public LootFunctionType getType() {
		return ExtendedLootFunctions.MODIFIER_ACROLL_SETTER;
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		ExtendedModifierUtils.generateItemModifiersTagged(context.getRandom(), stack, skillful, true);
		return stack;
	}


	public static ExtendedModifierScrollSetter.Builder random() {
		return new ExtendedModifierScrollSetter.Builder();
	}

	public static ExtendedModifierScrollSetter.Builder skillfull() {
		return new ExtendedModifierScrollSetter.Builder().setSkillful();
	}

	public static class Builder extends LootFunction.Builder<ExtendedModifierScrollSetter.Builder> {
		/**
         * Whether the generated scroll should apply skillful modifiers.
         * <p>
         * Defaults to {@code false}.
         * </p>
         */
		private boolean skillful = false;

		public Builder() {
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
		protected ExtendedModifierScrollSetter.Builder getThis() {
			return this;
		}

		@Override
		public ILootFunction build() {
			return new ExtendedModifierScrollSetter(this.getConditions(), skillful);
		}
	}

	public static class Serializer extends LootFunction.Serializer<ExtendedModifierScrollSetter>  {
		/**
		 * Serializes the scroll adder into JSON format.
		 *
		 * @param pJson                 the target JSON object
		 * @param pFunction             the function instance being serialized
		 * @param pSerializationContext the serialization context
		 */
		@Override
		public void serialize(JsonObject pJson, ExtendedModifierScrollSetter pFunction, JsonSerializationContext pSerializationContext) {
			super.serialize(pJson, pFunction, pSerializationContext);
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
		 * @return a new {@link ExtendedModifierScrollSetter} with the parsed chance
		 */
		@Override
		public ExtendedModifierScrollSetter deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, ILootCondition[] pConditions) {
			boolean skillful = JSONUtils.getAsBoolean(pObject, "skillful", false);
			return new ExtendedModifierScrollSetter(pConditions, skillful);
		}
	}
}