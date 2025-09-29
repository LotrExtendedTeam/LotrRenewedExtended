package com.github.maximuslotro.lotrrextended.common.loot;

import com.github.maximuslotro.lotrrextended.ExtendedLog;
import com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedModifierAdder;

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

/**
 * Extended version of {@link ItemLootEntry} that adds additional
 * functionality for configuring loot items, including:
 * <ul>
 *     <li>Damage ranges for damageable items</li>
 *     <li>Automatic addition of modifiers</li>
 *     <li>Custom count or range of items dropped</li>
 * </ul>
 */
public class EItemLootEntry extends ItemLootEntry{
	/**
	 * Creates a new extended item loot entry.
	 *
	 * @param pItem       the item to drop
	 * @param pWeight     the weight of this loot entry
	 * @param pQuality    the quality modifier of this loot entry
	 * @param pConditions the loot conditions that must be satisfied for this entry
	 * @param pFunctions  the loot functions to apply to this entry
	 */
	public EItemLootEntry(Item pItem, int pWeight, int pQuality, ILootCondition[] pConditions, ILootFunction[] pFunctions) {
		super(pItem, pWeight, pQuality, pConditions, pFunctions);
	}
	/**
	 * Creates a builder for an {@link EItemLootEntry} for a given item.
	 *
	 * @param pItem the item provider to construct the loot entry for
	 * @return a new {@link ExtendedBuilder} for chaining configuration
	 */
	public static StandaloneLootEntry.Builder<ExtendedBuilder> lootItem(IItemProvider pItem) {
		return new EItemLootEntry.ExtendedBuilder(
			(p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_) -> new EItemLootEntry(pItem.asItem(), p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_), 
			pItem.asItem().canBeDepleted()
			);
	}
	/**
	 * Builder class for {@link EItemLootEntry}, providing extended functionality
	 * for damageable items, count ranges, and automatic modifier application.
	 */
	public static class ExtendedBuilder extends StandaloneLootEntry.Builder<EItemLootEntry.ExtendedBuilder> {
		/**
		 * The constructor function used to build the final {@link EItemLootEntry}.
		 * <p>
		 * This is invoked during {@link #build()} to create the loot entry
		 * with the configured weight, quality, conditions, and functions.
		 * </p>
		 */
		private final StandaloneLootEntry.ILootEntryBuilder constructor;
		/**
		 * Whether the item is damageable.
		 * <p>
		 * Only damageable items can have their durability ranges set
		 * and automatically receive modifiers via {@link ExtendedModifierAdder}.
		 * </p>
		 */
		private final boolean isDamagable;
		/**
		 * Builder for automatically applying item modifiers to this loot entry.
		 * <p>
		 * Initialized for damageable items and can be further customized
		 * with {@link ExtendedModifierAdder} options such as {@link com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedModifierAdder.Builder#noNegatives()}.
		 * </p>
		 */
		private ExtendedModifierAdder.Builder modifierBuilder;
		/**
		 * Creates a new extended builder.
		 *
		 * @param lootEntryConstructor a function that constructs the final loot entry
		 * @param canDamage            whether the item is damageable
		 */
		public ExtendedBuilder(StandaloneLootEntry.ILootEntryBuilder lootEntryConstructor, boolean canDamage) {
			this.constructor = lootEntryConstructor;
			this.isDamagable = canDamage;
			if(isDamagable) {
				modifierBuilder = ExtendedModifierAdder.modifiers();
			}
		}
		@Override
		protected EItemLootEntry.ExtendedBuilder getThis() {
			return this;
		}
		/**
		 * Builds the final {@link LootEntry} using the configured settings
		 * from this builder.
		 * <p>
		 * If the item is damageable and a {@link com.github.maximuslotro.lotrrextended.common.loot.functions.ExtendedModifierAdder.Builder}
		 * was initialized, it automatically applies the configured modifiers
		 * before building the loot entry.
		 * </p>
		 * <p>
		 * The resulting {@link LootEntry} includes:
		 * <ul>
		 *     <li>Configured weight and quality</li>
		 *     <li>All loot conditions added to this builder</li>
		 *     <li>All loot functions added to this builder, including modifiers if applicable</li>
		 * </ul>
		 * </p>
		 *
		 * @return a fully constructed {@link LootEntry} representing this item
		 *         with all specified configurations applied
		 */
		@Override
		public LootEntry build() {
			if(modifierBuilder!=null && isDamagable) {
				this.apply(modifierBuilder);
			}
			return this.constructor.build(this.weight, this.quality, this.getConditions(), this.getFunctions());
		}
		/**
		 * Sets a damage range for this item.
		 *
		 * @param minDamagePercent the minimum damage percent (e.g., 0.90 for at least 90% damaged)
		 * @param maxDamagePercent the maximum damage percent (e.g., 0.95 for at most 95% damaged)
		 * @return this builder instance for chaining
		 */
		public ExtendedBuilder setDamageRange(float minDamagePercent, float maxDamagePercent) {
			float modifiedMin = (100-(int)(maxDamagePercent*100)) / 100.0f;
			float modifiedMax = (100-(int)(minDamagePercent*100)) / 100.0f;
			if(!isDamagable) {
				ExtendedLog.error("Tried Setting damage value range to a non-damageable item! "+minDamagePercent+":"+maxDamagePercent);
				return this.getThis();
			}
			if(modifiedMin > modifiedMax) {
				ExtendedLog.error("Tried Setting an inverted damage value range to an item! "+minDamagePercent+":"+maxDamagePercent);
				return this.getThis();
			}
			this.apply(SetDamage.setDamage(RandomValueRange.between(modifiedMin, modifiedMax)));
			return this.getThis();
		}
		/**
		 * Sets the maximum remaining durability of the item as a percent.
		 *
		 * @param maxPercentLeft the maximum percent of durability left
		 * @return this builder instance for chaining
		 */
		public ExtendedBuilder setMaxLootDurability(float maxPercentLeft) {
			return setDamageRange(1.0f-maxPercentLeft, 1.0f);
		}
		/**
		 * Enables automatic addition of modifiers to this item.
		 *
		 * @return this builder instance for chaining
		 */
		public ExtendedBuilder addModifiers() {
			modifierBuilder = ExtendedModifierAdder.modifiers();
			return this.getThis();
		}
		/**
		 * Enables automatic addition of only non-negative modifiers.
		 *
		 * @return this builder instance for chaining
		 */
		public ExtendedBuilder addModifiersNoNegative() {
			modifierBuilder = ExtendedModifierAdder.modifiers().noNegatives();
			return this.getThis();
		}
		/**
		 * Sets the exact count of items to drop.
		 *
		 * @param number the exact number of items
		 * @return this builder instance for chaining
		 */
		public ExtendedBuilder setExactly(int number) {
			this.apply(SetCount.setCount(ConstantRange.exactly(number)));
			return this.getThis();
		}
		/**
		 * Sets a range for the count of items to drop.
		 *
		 * @param min the minimum number of items
		 * @param max the maximum number of items
		 * @return this builder instance for chaining
		 */
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
