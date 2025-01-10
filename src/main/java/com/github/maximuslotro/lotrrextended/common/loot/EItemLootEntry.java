package com.github.maximuslotro.lotrrextended.common.loot;

import com.github.maximuslotro.lotrrextended.ExtendedLog;

import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetDamage;
import net.minecraft.util.IItemProvider;

public class EItemLootEntry extends ItemLootEntry{

	public EItemLootEntry(Item pItem, int pWeight, int pQuality, ILootCondition[] pConditions, ILootFunction[] pFunctions) {
		super(pItem, pWeight, pQuality, pConditions, pFunctions);
	}

	public static StandaloneLootEntry.Builder<ExtendedBuilder> lootItem(IItemProvider pItem) {
		return new EItemLootEntry.ExtendedBuilder((p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_) -> {
			return new EItemLootEntry(pItem.asItem(), p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_);
		}, pItem.asItem().canBeDepleted());
	}

	public static class ExtendedBuilder extends StandaloneLootEntry.Builder<EItemLootEntry.ExtendedBuilder> {
		private final StandaloneLootEntry.ILootEntryBuilder constructor;
		private final boolean isDamagable;
		public ExtendedBuilder(StandaloneLootEntry.ILootEntryBuilder lootEntryConstructor, boolean canDamage) {
			this.constructor = lootEntryConstructor;
			this.isDamagable = canDamage;
		}

		@Override
		protected EItemLootEntry.ExtendedBuilder getThis() {
			return this;
		}

		@Override
		public LootEntry build() {
			return this.constructor.build(this.weight, this.quality, this.getConditions(), this.getFunctions());
		}

		/**
		 * minDamagePercent is the smallest damage percent. ie. 0.90 is the item is at least 90% damaged
		 * maxDamagePercent is the largest  damage percent. ie. 0.95 is the item is at most  95% damaged
		 * */
		public ExtendedBuilder setDamageRange(float minDamagePercent, float maxDamagePercent) {
			float modifiedMin = (100-(int)(maxDamagePercent*100)) / 100.0f;
			float modifiedMax = (100-(int)(minDamagePercent*100)) / 100.0f;
			if(!isDamagable) {
				ExtendedLog.error("Tried Setting damage value range to a non-damageable item! "+modifiedMin+":"+modifiedMax);
				return this.getThis();
			}
			if(modifiedMin > modifiedMax) {
				ExtendedLog.error("Tried Setting an inverted damage value range to an item! "+modifiedMin+":"+modifiedMax);
				return this.getThis();
			}
			this.apply(SetDamage.setDamage(RandomValueRange.between(modifiedMin, modifiedMax)));
			return this.getThis();
		}

		public ExtendedBuilder setExactly(int number) {
			this.apply(SetCount.setCount(ConstantRange.exactly(number)));
			return this.getThis();
		}

		public ExtendedBuilder setRange(int min, int max) {
			if(min >= max) {
				ExtendedLog.error("Tried Setting an incorrect item count rage! "+min+">="+max);
				return this.getThis();
			}
			this.apply(SetCount.setCount(RandomValueRange.between(min, max)));
			return this.getThis();
		}
	}
}
