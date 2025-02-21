package com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedAbstractBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonObject;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

/**
 * Copied to remove connections to several advancement classes
 * */
public class ExtendedConsumeItemTrigger extends ExtendedAbstractBenchmarkTrigger<ExtendedConsumeItemTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "consume_item");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public ExtendedConsumeItemTrigger.Instance createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
	      return new ExtendedConsumeItemTrigger.Instance(pEntityPredicate, ItemPredicate.fromJson(pJson.get("item")));
	   }

	   public void trigger(ServerPlayerEntity pPlayer, ItemStack pItem) {
	      this.trigger(pPlayer, (p_226325_1_) -> {
	         return p_226325_1_.matches(pItem);
	      });
	   }

	   public static class Instance extends ExtendedBenchmarkInstance {
	      private final ItemPredicate item;

	      public Instance(ExtendedEntityPredicate.AndPredicate p_i231522_1_, ItemPredicate p_i231522_2_) {
	         super(ExtendedConsumeItemTrigger.ID, p_i231522_1_);
	         this.item = p_i231522_2_;
	      }

	      public static ExtendedConsumeItemTrigger.Instance usedItem() {
	         return new ExtendedConsumeItemTrigger.Instance(ExtendedEntityPredicate.AndPredicate.ANY, ItemPredicate.ANY);
	      }

	      public static ExtendedConsumeItemTrigger.Instance usedItem(IItemProvider pItem) {
	         return new ExtendedConsumeItemTrigger.Instance(ExtendedEntityPredicate.AndPredicate.ANY, new ItemPredicate((ITag<Item>)null, pItem.asItem(), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion)null, NBTPredicate.ANY));
	      }

	      public boolean matches(ItemStack pItem) {
	         return this.item.matches(pItem);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	         JsonObject jsonobject = super.serializeToJson(pConditions);
	         jsonobject.add("item", this.item.serializeToJson());
	         return jsonobject;
	      }
	   }
	}
