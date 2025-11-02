package com.github.maximuslotro.lotrrextended.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class ExtendedAbstractStringTrigger extends AbstractCriterionTrigger<ExtendedAbstractStringTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "string_trigger");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ExtendedAbstractStringTrigger.Instance createInstance(JsonObject json, AndPredicate predicate, ConditionArrayParser p_230241_3_) {
		return new ExtendedAbstractStringTrigger.Instance(predicate, json.get("triggerName").getAsString());
	}

	public void trigger(ServerPlayerEntity player, String stringTrigger) {
		this.trigger(player, (p_226627_1_) -> p_226627_1_.matches(player, stringTrigger));
	}

	public static class Instance extends CriterionInstance {
		private final String triggerName;

		public Instance(EntityPredicate.AndPredicate p_i231585_1_, String pTriggerName) {
			super(ExtendedAbstractStringTrigger.ID, p_i231585_1_);
			this.triggerName = pTriggerName;
		}

		public static ExtendedAbstractStringTrigger.Instance stringTrigger(String stringTrigger) {
			return new ExtendedAbstractStringTrigger.Instance(EntityPredicate.AndPredicate.ANY, stringTrigger);
		}

		public boolean matches(PlayerEntity player, String stringTrigger) {
			return (player instanceof ServerPlayerEntity) && triggerName.matches(stringTrigger);
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer p_230240_1_) {
			JsonObject jsonobject = super.serializeToJson(p_230240_1_);
			jsonobject.addProperty("triggerName", this.triggerName);
			return jsonobject;
		}
	}
}
