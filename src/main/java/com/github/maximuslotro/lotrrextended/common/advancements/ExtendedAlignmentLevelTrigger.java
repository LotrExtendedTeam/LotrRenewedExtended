package com.github.maximuslotro.lotrrextended.common.advancements;

import com.github.maximuslotro.lotrrextended.common.advancements.predicates.ExtendedAlignmentPredicate;
import com.google.gson.JsonObject;

import lotr.common.fac.Faction;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ExtendedAlignmentLevelTrigger extends AbstractCriterionTrigger<ExtendedAlignmentLevelTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation("lotrextended", "alignment_changed");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public ExtendedAlignmentLevelTrigger.Instance createInstance(JsonObject jsonObject, AndPredicate predicate, ConditionArrayParser contextParser) {
		ExtendedAlignmentPredicate alignmentpredicate = ExtendedAlignmentPredicate.fromJson(jsonObject.get("alignment"));
		return new ExtendedAlignmentLevelTrigger.Instance(predicate, alignmentpredicate, JSONUtils.getAsBoolean(jsonObject, "pledged", false));
	}

	public void trigger(ServerPlayerEntity player, Faction faction, Float level, boolean pledged) {
		this.trigger(player, (p_226627_1_) -> p_226627_1_.matches(player, faction, level, pledged));
	}

	public static class Instance extends CriterionInstance {
		private final ExtendedAlignmentPredicate alignment;
		private final boolean requiresPledge;

		public Instance(EntityPredicate.AndPredicate p_i231585_1_, ExtendedAlignmentPredicate pAlignmentPredicate, boolean pRequiresPledge) {
			super(ExtendedAlignmentLevelTrigger.ID, p_i231585_1_);
			this.alignment = pAlignmentPredicate;
			this.requiresPledge = pRequiresPledge;
		}

		public static ExtendedAlignmentLevelTrigger.Instance alignmentLevel(ExtendedAlignmentPredicate pAlignmentPredicate) {
			return new ExtendedAlignmentLevelTrigger.Instance(EntityPredicate.AndPredicate.ANY, pAlignmentPredicate, false);
		}

		public static ExtendedAlignmentLevelTrigger.Instance alignmentLevelPledged(ExtendedAlignmentPredicate pAlignmentPredicate) {
			return new ExtendedAlignmentLevelTrigger.Instance(EntityPredicate.AndPredicate.ANY, pAlignmentPredicate, true);
		}

		public static ExtendedAlignmentLevelTrigger.Instance pledged() {
			return new ExtendedAlignmentLevelTrigger.Instance(EntityPredicate.AndPredicate.ANY, ExtendedAlignmentPredicate.ANY, true);
		}

		public boolean matches(PlayerEntity player, Faction faction, Float level, boolean pledged) {
			if(requiresPledge && !pledged) {
				return false;
			}
			return this.alignment.matches(player, faction, level);
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer p_230240_1_) {
			JsonObject jsonobject = super.serializeToJson(p_230240_1_);
			jsonobject.add("alignment", this.alignment.serializeToJson());
			if(requiresPledge) {
				jsonobject.addProperty("pledged", Boolean.toString(requiresPledge));
			}
			return jsonobject;
		}
	}
}
