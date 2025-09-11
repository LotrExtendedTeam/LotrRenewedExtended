package com.github.maximuslotro.lotrrextended.common.loot.functions;

import java.util.Random;

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

public class ExtendedModifierAdder extends LootFunction {
	private final boolean reforging;
	private final boolean noNegatives;

	private ExtendedModifierAdder(ILootCondition[] p_i51236_1_, boolean pReforging, boolean pNoNegatives) {
		super(p_i51236_1_);
		this.reforging = pReforging;
		this.noNegatives = pNoNegatives;
	}

	@Override
	public LootFunctionType getType() {
		return ExtendedLootFunctions.MODIFIER_ADDER;
	}

	@Override
	public ItemStack run(ItemStack pStack, LootContext pContext) {
		Random random = pContext.getRandom();
		return ExtendedModifierUtils.generateItemModifiers(random, pStack, reforging, noNegatives);
	}

	public static ExtendedModifierAdder.Builder modifier() {
		return new ExtendedModifierAdder.Builder();
	}

	public static class Builder extends LootFunction.Builder<ExtendedModifierAdder.Builder> {
		private boolean reforging = false;
		private boolean noNegatives = false;

		public Builder() {}

		@Override
		protected ExtendedModifierAdder.Builder getThis() {
			return this;
		}

		public ExtendedModifierAdder.Builder reforging() {
			this.reforging = true;
			return this;
		}

		public ExtendedModifierAdder.Builder noNegatives() {
			this.noNegatives = true;
			return this;
		}

		@Override
		public ILootFunction build() {
			return new ExtendedModifierAdder(this.getConditions(), this.reforging, this.noNegatives);
		}
	}

	public static class Serializer extends LootFunction.Serializer<ExtendedModifierAdder> {
		/**
		 * Serialize the value by putting its data into the JsonObject.
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

		 @Override
		 public ExtendedModifierAdder deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, ILootCondition[] pConditions) {
			 boolean reforging = JSONUtils.getAsBoolean(pObject, "reforging", false);
			 boolean noNegatives = JSONUtils.getAsBoolean(pObject, "no_negatives", false);
			 return new ExtendedModifierAdder(pConditions, reforging, noNegatives);
		 }
	}
}