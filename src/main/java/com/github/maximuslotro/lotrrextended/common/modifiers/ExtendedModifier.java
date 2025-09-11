package com.github.maximuslotro.lotrrextended.common.modifiers;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

/**
 * Represents the complete set of extended modifiers that can be applied to
 * items, weapons, tools, or armor. Each modifier has associated metadata such as:
 * <ul>
 *     <li>{@link #keyNbt} – The NBT save name used for persistence.</li>
 *     <li>{@link #keyLocal} – The localization key used for translations.</li>
 *     <li>{@link #modifierType} – The functional category of the modifier.</li>
 *     <li>{@link #level} – The tier or strength level of the modifier.</li>
 *     <li>{@link #effect} – The numeric effect applied by this modifier.</li>
 *     <li>{@link #rarity} – How the modifier is obtained (crafting, reforging, applied, earned, etc.).</li>
 * </ul>
 *
 * <p>
 * Additionally, a static {@link #MODIFIER_MAP} provides fast lookup of
 * specific modifiers by their NBT key.
 * </p>
 * 
 */
public class ExtendedModifier extends WeightedRandom.Item{

	/** The NBT save key. */
	private final String keyNbt;

	/** The localization/translation key. */
	private final String keyLocal;

	/** Functional category used for compatibility (e.g., only one BANE per item). */
	private final ExtendedModifierTypes modifierType;

	/** Tier or strength level of the modifier. */
	private final int level;

	/** The numeric effect magnitude applied by this modifier. */
	private final float effect;

	/** Acquisition/rarity classification controlling how players obtain it. */
	private final ExtendedModifierRarity rarity;

	/** The scroll texture to use for this modifier*/
	private final ExtendedModifierScroll scroll;

	ExtendedModifier(String pNbtKey, String pLocalKey, ExtendedModifierTypes pModifierType, int pLevel, 
			float pModifierEffect, ExtendedModifierRarity pModifierRarity, ExtendedModifierScroll pScroll, int pWeight) {
		super(pWeight);
		this.keyNbt = pNbtKey;
		this.keyLocal = pLocalKey;
		this.modifierType = pModifierType;
		this.level = pLevel;
		this.effect = pModifierEffect;
		this.rarity = pModifierRarity;
		this.scroll = pScroll;
	}
	/** 
	 * @return the NBT save key for this modifier. 
	 */
	public String getNbtKey() {
		return keyNbt;
	}
	/** 
	 * @return the localization key for translations. 
	 */
	public String getLocalKey() {
		return keyLocal.toLowerCase();
	}
	/** 
	 * @return the functional type of this modifier. 
	 */
	public ExtendedModifierTypes getModifierType() {
		return modifierType;
	}
	/**
	 * Determines if this modifier can be applied to the given item.
	 *
	 * @param item the item to check compatibility against
	 * @return {@code true} if the modifier can be applied to the item, {@code false} otherwise
	 */
	public boolean canApplyToItem(ItemStack item) {
		return modifierType.isItemApplicable(item);
	}
	/**
	 * Determines if this modifier can be applied alongside another modifier
	 *
	 * @param pModifierType the item to check compatibility against
	 * @return {@code true} if the modifier can be applied to the item, {@code false} otherwise
	 */
	public boolean canApplyToModifier(ExtendedModifierTypes pModifierType) {
		return modifierType.isModifierApplicable(pModifierType);
	}
	/** 
	 * @return the tier or strength level of this modifier. 
	 */
	public int getModifierLevel() {
		return level;
	}
	/** 
	 * @return the numeric effect value applied by this modifier. 
	 */
	public float getModifierEffect() {
		return effect;
	}
	/** 
	 * @return the rarity classification of this modifier. 
	 */
	public ExtendedModifierRarity getRarity() {
		return rarity;
	}
	/**
	 * @return the scroll resource/texture associated with this modifier,
	 *         used for rendering or display purposes
	 */
	public ExtendedModifierScroll getScroll() {
		return scroll;
	}
	/**
	 * Indicates if this modifier represents an "unknown" placeholder.
	 * The unknown modifier is used if no valid modifiers are found.
	 *
	 * @return {@code true} if this modifier is the {@link #UNKNOWN} entry,
	 *         {@code false} otherwise
	 */
	public boolean isUnknown() {
		return this==ExtendedModifiers.UNKNOWN;
	}
	/**
	 * @return the maximum level available for this modifier's type,
	 *         based on the global lookup table.
	 */
	public int getMaxLevel() {
		List<ExtendedModifier> list = ExtendedModifiers.MODIFIERS_BY_TYPE.get(modifierType);
		if (list == null || list.isEmpty()) {
			return this.level; // fallback, should not normally happen
		}
		return list.get(list.size() - 1).getModifierLevel();
	}
	/**
	 * @return the minimum level available for this modifier's type,
	 *         based on the global lookup table.
	 */
	public int getMinLevel() {
		List<ExtendedModifier> list = ExtendedModifiers.MODIFIERS_BY_TYPE.get(modifierType);
		if (list == null || list.isEmpty()) {
			return this.level; // fallback, should not normally happen
		}
		return list.get(0).getModifierLevel();
	}

	public boolean isAllowedOnScrolls() {
		return rarity.equals(ExtendedModifierRarity.BASIC) || rarity.equals(ExtendedModifierRarity.SKILLFUL);
	}
}