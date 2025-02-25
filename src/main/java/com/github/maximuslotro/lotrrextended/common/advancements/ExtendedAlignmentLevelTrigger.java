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
import net.minecraft.util.ResourceLocation;

public class ExtendedAlignmentLevelTrigger extends AbstractCriterionTrigger<ExtendedAlignmentLevelTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "alignment_changed");

	   @Override
	public ResourceLocation getId() {
	      return ID;
	   }

	   @Override
	public ExtendedAlignmentLevelTrigger.Instance createInstance(JsonObject json, AndPredicate predicate, ConditionArrayParser p_230241_3_) {
		  ExtendedAlignmentPredicate alignmentpredicate = ExtendedAlignmentPredicate.fromJson(json.get("alignment"));
	      return new ExtendedAlignmentLevelTrigger.Instance(predicate, alignmentpredicate);
	   }

	   public void trigger(ServerPlayerEntity player, Faction faction, Float level) {
	      this.trigger(player, (p_226627_1_) -> p_226627_1_.matches(player, faction, level));
	   }

	   public static class Instance extends CriterionInstance {
	      private final ExtendedAlignmentPredicate alignment;

	      public Instance(EntityPredicate.AndPredicate p_i231585_1_, ExtendedAlignmentPredicate p_i231585_2_) {
	         super(ExtendedAlignmentLevelTrigger.ID, p_i231585_1_);
	         this.alignment = p_i231585_2_;
	      }

	      public static ExtendedAlignmentLevelTrigger.Instance alignmentLevel(ExtendedAlignmentPredicate p_204827_0_) {
	         return new ExtendedAlignmentLevelTrigger.Instance(EntityPredicate.AndPredicate.ANY, p_204827_0_);
	      }

	      public boolean matches(PlayerEntity player, Faction faction, Float level) {
	         return this.alignment.matches(player, faction, level);
	      }

	      @Override
		public JsonObject serializeToJson(ConditionArraySerializer p_230240_1_) {
	         JsonObject jsonobject = super.serializeToJson(p_230240_1_);
	         jsonobject.add("alignment", this.alignment.serializeToJson());
	         return jsonobject;
	      }
	   }
	}
