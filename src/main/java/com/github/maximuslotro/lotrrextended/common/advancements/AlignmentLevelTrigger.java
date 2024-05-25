package com.github.maximuslotro.lotrrextended.common.advancements;

import com.github.maximuslotro.lotrrextended.common.advancements.predicates.AlignmentPredicate;
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

public class AlignmentLevelTrigger extends AbstractCriterionTrigger<AlignmentLevelTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "alignment_changed");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public AlignmentLevelTrigger.Instance createInstance(JsonObject json, AndPredicate predicate, ConditionArrayParser p_230241_3_) {
		  AlignmentPredicate alignmentpredicate = AlignmentPredicate.fromJson(json.get("alignment"));
	      return new AlignmentLevelTrigger.Instance(predicate, alignmentpredicate);
	   }

	   public void trigger(ServerPlayerEntity player, Faction faction, Float level) {
	      this.trigger(player, (p_226627_1_) -> {
	         return p_226627_1_.matches(player, faction, level);
	      });
	   }

	   public static class Instance extends CriterionInstance {
	      private final AlignmentPredicate alignment;

	      public Instance(EntityPredicate.AndPredicate p_i231585_1_, AlignmentPredicate p_i231585_2_) {
	         super(AlignmentLevelTrigger.ID, p_i231585_1_);
	         this.alignment = p_i231585_2_;
	      }

	      public static AlignmentLevelTrigger.Instance alignmentLevel(AlignmentPredicate p_204827_0_) {
	         return new AlignmentLevelTrigger.Instance(EntityPredicate.AndPredicate.ANY, p_204827_0_);
	      }

	      public boolean matches(PlayerEntity player, Faction faction, Float level) {
	         return this.alignment.matches(player, faction, level);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer p_230240_1_) {
	         JsonObject jsonobject = super.serializeToJson(p_230240_1_);
	         jsonobject.add("alignment", this.alignment.serializeToJson());
	         return jsonobject;
	      }
	   }
	}
