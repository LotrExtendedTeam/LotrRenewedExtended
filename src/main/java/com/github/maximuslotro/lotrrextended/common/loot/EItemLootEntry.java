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
		public ExtendedBuilder(StandaloneLootEntry.ILootEntryBuilder p_i50485_1_, boolean canDamage) {
			this.constructor = p_i50485_1_;
			this.isDamagable = canDamage;
		}

		protected EItemLootEntry.ExtendedBuilder getThis() {
			return this;
		}

		public LootEntry build() {
			return this.constructor.build(this.weight, this.quality, this.getConditions(), this.getFunctions());
		}

		public ExtendedBuilder setDamageRange(float min, float max) {
			if(!isDamagable) {
				ExtendedLog.error("Tried Setting damage value range to a non-damageable item! "+min+":"+max);
				return this.getThis();
			}
			this.apply(SetDamage.setDamage(RandomValueRange.between(min, max)));
			return this.getThis();
		}

		public ExtendedBuilder setCount(int number) {
			this.apply(SetCount.setCount(ConstantRange.exactly(number)));
			return this.getThis();
		}
	}
}
