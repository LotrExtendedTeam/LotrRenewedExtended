package com.github.maximuslotro.lotrrextended.common.loot;

import net.minecraft.item.Item;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.SetDamage;
import net.minecraft.util.IItemProvider;

public class EItemLootEntry extends ItemLootEntry{

	public EItemLootEntry(Item pItem, int pWeight, int pQuality, ILootCondition[] pConditions, ILootFunction[] pFunctions) {
		super(pItem, pWeight, pQuality, pConditions, pFunctions);
	}

	public static StandaloneLootEntry.Builder<ExtendedBuilder> lootItem(IItemProvider pItem) {
		return simpleExtendedBuilder((p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_) -> {
			return new EItemLootEntry(pItem.asItem(), p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_);
		});
	}

	public static StandaloneLootEntry.Builder<ExtendedBuilder> simpleExtendedBuilder(StandaloneLootEntry.ILootEntryBuilder pEntryBuilder) {
		return new EItemLootEntry.ExtendedBuilder(pEntryBuilder);
	}

	public static class ExtendedBuilder extends StandaloneLootEntry.Builder<EItemLootEntry.ExtendedBuilder> {
		private final StandaloneLootEntry.ILootEntryBuilder constructor;

		public ExtendedBuilder(StandaloneLootEntry.ILootEntryBuilder p_i50485_1_) {
			this.constructor = p_i50485_1_;
		}

		protected EItemLootEntry.ExtendedBuilder getThis() {
			return this;
		}

		public LootEntry build() {
			return this.constructor.build(this.weight, this.quality, this.getConditions(), this.getFunctions());
		}

		public ExtendedBuilder setArmorDamage(float min, float max) {
			this.apply(SetDamage.setDamage(RandomValueRange.between(min, max)));
			return this.getThis();
		}
	}
}
