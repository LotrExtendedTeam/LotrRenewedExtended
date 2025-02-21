package com.github.maximuslotro.lotrrextended.common.codex.benchmark.triggers;

import java.util.List;

import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedAbstractBenchmarkTrigger;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.ExtendedBenchmarkInstance;
import com.github.maximuslotro.lotrrextended.common.codex.benchmark.predicates.ExtendedEntityPredicate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

/**
 * Copied to remove connections to several advancement classes
 * */
public class ExtendedInventoryChangeTrigger extends ExtendedAbstractBenchmarkTrigger<ExtendedInventoryChangeTrigger.Instance> {
	   private static final ResourceLocation ID = new ResourceLocation("lotrextended", "inventory_changed");

	   public ResourceLocation getId() {
	      return ID;
	   }

	   public ExtendedInventoryChangeTrigger.Instance createInstance(JsonObject pJson, ExtendedEntityPredicate.AndPredicate pEntityPredicate, ConditionArrayParser pConditionsParser) {
	      JsonObject jsonobject = JSONUtils.getAsJsonObject(pJson, "slots", new JsonObject());
	      MinMaxBounds.IntBound minmaxbounds$intbound = MinMaxBounds.IntBound.fromJson(jsonobject.get("occupied"));
	      MinMaxBounds.IntBound minmaxbounds$intbound1 = MinMaxBounds.IntBound.fromJson(jsonobject.get("full"));
	      MinMaxBounds.IntBound minmaxbounds$intbound2 = MinMaxBounds.IntBound.fromJson(jsonobject.get("empty"));
	      ItemPredicate[] aitempredicate = ItemPredicate.fromJsonArray(pJson.get("items"));
	      return new ExtendedInventoryChangeTrigger.Instance(pEntityPredicate, minmaxbounds$intbound, minmaxbounds$intbound1, minmaxbounds$intbound2, aitempredicate);
	   }

	   public void trigger(ServerPlayerEntity pPlayer, PlayerInventory pInventory, ItemStack pStack) {
	      int i = 0;
	      int j = 0;
	      int k = 0;

	      for(int l = 0; l < pInventory.getContainerSize(); ++l) {
	         ItemStack itemstack = pInventory.getItem(l);
	         if (itemstack.isEmpty()) {
	            ++j;
	         } else {
	            ++k;
	            if (itemstack.getCount() >= itemstack.getMaxStackSize()) {
	               ++i;
	            }
	         }
	      }

	      this.trigger(pPlayer, pInventory, pStack, i, j, k);
	   }

	   private void trigger(ServerPlayerEntity pPlayer, PlayerInventory pInventory, ItemStack pStack, int pFull, int pEmpty, int pOccupied) {
	      this.trigger(pPlayer, (p_234802_5_) -> {
	         return p_234802_5_.matches(pInventory, pStack, pFull, pEmpty, pOccupied);
	      });
	   }

	   public static class Instance extends ExtendedBenchmarkInstance {
	      private final MinMaxBounds.IntBound slotsOccupied;
	      private final MinMaxBounds.IntBound slotsFull;
	      private final MinMaxBounds.IntBound slotsEmpty;
	      private final ItemPredicate[] predicates;

	      public Instance(ExtendedEntityPredicate.AndPredicate p_i231597_1_, MinMaxBounds.IntBound p_i231597_2_, MinMaxBounds.IntBound p_i231597_3_, MinMaxBounds.IntBound p_i231597_4_, ItemPredicate[] p_i231597_5_) {
	         super(ExtendedInventoryChangeTrigger.ID, p_i231597_1_);
	         this.slotsOccupied = p_i231597_2_;
	         this.slotsFull = p_i231597_3_;
	         this.slotsEmpty = p_i231597_4_;
	         this.predicates = p_i231597_5_;
	      }

	      public static ExtendedInventoryChangeTrigger.Instance hasItems(ItemPredicate... pItemConditions) {
	         return new ExtendedInventoryChangeTrigger.Instance(ExtendedEntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, pItemConditions);
	      }

	      public static ExtendedInventoryChangeTrigger.Instance hasItems(IItemProvider... pItems) {
	         ItemPredicate[] aitempredicate = new ItemPredicate[pItems.length];

	         for(int i = 0; i < pItems.length; ++i) {
	            aitempredicate[i] = new ItemPredicate((ITag<Item>)null, pItems[i].asItem(), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion)null, NBTPredicate.ANY);
	         }

	         return hasItems(aitempredicate);
	      }

	      public JsonObject serializeToJson(ConditionArraySerializer pConditions) {
	         JsonObject jsonobject = super.serializeToJson(pConditions);
	         if (!this.slotsOccupied.isAny() || !this.slotsFull.isAny() || !this.slotsEmpty.isAny()) {
	            JsonObject jsonobject1 = new JsonObject();
	            jsonobject1.add("occupied", this.slotsOccupied.serializeToJson());
	            jsonobject1.add("full", this.slotsFull.serializeToJson());
	            jsonobject1.add("empty", this.slotsEmpty.serializeToJson());
	            jsonobject.add("slots", jsonobject1);
	         }

	         if (this.predicates.length > 0) {
	            JsonArray jsonarray = new JsonArray();

	            for(ItemPredicate itempredicate : this.predicates) {
	               jsonarray.add(itempredicate.serializeToJson());
	            }

	            jsonobject.add("items", jsonarray);
	         }

	         return jsonobject;
	      }

	      public boolean matches(PlayerInventory pInventory, ItemStack pStack, int pFull, int pEmpty, int pOccupied) {
	         if (!this.slotsFull.matches(pFull)) {
	            return false;
	         } else if (!this.slotsEmpty.matches(pEmpty)) {
	            return false;
	         } else if (!this.slotsOccupied.matches(pOccupied)) {
	            return false;
	         } else {
	            int i = this.predicates.length;
	            if (i == 0) {
	               return true;
	            } else if (i != 1) {
	               List<ItemPredicate> list = new ObjectArrayList<>(this.predicates);
	               int j = pInventory.getContainerSize();

	               for(int k = 0; k < j; ++k) {
	                  if (list.isEmpty()) {
	                     return true;
	                  }

	                  ItemStack itemstack = pInventory.getItem(k);
	                  if (!itemstack.isEmpty()) {
	                     list.removeIf((p_234806_1_) -> {
	                        return p_234806_1_.matches(itemstack);
	                     });
	                  }
	               }

	               return list.isEmpty();
	            } else {
	               return !pStack.isEmpty() && this.predicates[0].matches(pStack);
	            }
	         }
	      }
	   }
	}
