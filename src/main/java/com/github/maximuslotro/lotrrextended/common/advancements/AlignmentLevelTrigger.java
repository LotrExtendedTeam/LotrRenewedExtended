package com.github.maximuslotro.lotrrextended.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class AlignmentLevelTrigger extends AbstractCriterionTrigger<AlignmentLevelTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "alignment_changed");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public AlignmentLevelTrigger.Instance createInstance(JsonObject p_230241_1_, EntityPredicate.AndPredicate p_230241_2_, ConditionArrayParser p_230241_3_) {
	      ItemPredicate itempredicate = ItemPredicate.fromJson(p_230241_1_.get("item"));
	      return new AlignmentLevelTrigger.Instance(p_230241_2_, itempredicate);
	   }

	   public void trigger(ServerPlayerEntity p_204817_1_, ItemStack p_204817_2_) {
	      this.trigger(p_204817_1_, (p_226627_1_) -> {
	         return p_226627_1_.matches(p_204817_2_);
	      });
	   }

	   public static class Instance extends CriterionInstance {
	      private final ItemPredicate item;

	      public Instance(EntityPredicate.AndPredicate p_i231585_1_, ItemPredicate p_i231585_2_) {
	         super(AlignmentLevelTrigger.ID, p_i231585_1_);
	         this.item = p_i231585_2_;
	      }

	      public static AlignmentLevelTrigger.Instance filledBucket(ItemPredicate p_204827_0_) {
	         return new AlignmentLevelTrigger.Instance(EntityPredicate.AndPredicate.ANY, p_204827_0_);
	      }

	      public boolean matches(ItemStack p_204826_1_) {
	         return this.item.matches(p_204826_1_);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer p_230240_1_) {
	         JsonObject jsonobject = super.serializeToJson(p_230240_1_);
	         jsonobject.add("item", this.item.serializeToJson());
	         return jsonobject;
	      }
	   }
	}
