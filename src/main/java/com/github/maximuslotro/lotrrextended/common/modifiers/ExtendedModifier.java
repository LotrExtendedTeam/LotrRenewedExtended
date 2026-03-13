package com.github.maximuslotro.lotrrextended.common.modifiers;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import lotr.common.init.LOTRMaterial;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

/**
 * Represents the complete set of extended modifiers that can be applied to
 * items, weapons, tools, or armor. Each modifier has associated metadata such as:
 * <ul>
 *     <li>{@link #keyNbt} – The NBT save name used for persistence.</li>
 *     <li>{@link #keyLocal} – The localization key used for translations.</li>
 *     <li>{@link #modifierType} – The functional category of the modifier.</li>
 *     <li>{@link #tier} – The tier or strength level of the modifier.</li>
 *     <li>{@link #effect} – The numeric effect applied by this modifier.</li>
 *     <li>{@link #rarity} – How the modifier is obtained (crafting, reforging, applied, earned, etc.).</li>
 *     <li>{@link #slots} – The valid slots this modifier can be applied from.</li>
 * </ul>
 * 
 */
public class ExtendedModifier extends WeightedRandom.Item{

	/** The NBT save key. */
	private final String keyNbt;

	/** The localization/translation key. */
	private final String keyLocal;

	/** Functional category used for compatibility (e.g., only one BANE per item). */
	private final ExtendedModifierType modifierType;

	/** Tier or strength level of the modifier. */
	private final int tier;

	/** The numeric effect magnitude applied by this modifier. */
	private final float effect;

	/** Acquisition/rarity classification controlling how players obtain it. */
	private final ExtendedModifierRarity rarity;

	/** The scroll texture to use for this modifier*/
	private final ExtendedModifierScroll scroll;

	/** The valid equipment slots for the modifier. */
	private final EquipmentSlotType[] slots;

	/** Value modifier for the modifier. */
	private final int value;

	public ExtendedModifier(String pNbtKey, String pLocalKey, ExtendedModifierType pModifierType, int pLevel, 
			float pModifierEffect, ExtendedModifierRarity pModifierRarity, ExtendedModifierScroll pScroll, int pWeight, int value, EquipmentSlotType[] pApplicableSlots) {
		super(pWeight);
		this.keyNbt = pNbtKey;
		this.keyLocal = pLocalKey;
		this.modifierType = pModifierType;
		this.tier = pLevel;
		this.effect = pModifierEffect;
		this.rarity = pModifierRarity;
		this.scroll = pScroll;
		this.slots = pApplicableSlots;
		this.value = value;
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
	public ExtendedModifierType getModifierType() {
		return modifierType;
	}
	/**
	 * Determines if this modifier can be applied to the given item.
	 *
	 *
	 * Hacky logic is used to check for compatibility for modifiers
	 * 
	 * @param stack the item to check compatibility against
	 * @return {@code true} if the modifier can be applied to the item, {@code false} otherwise
	 */
	public boolean canApplyToItem(ItemStack stack) {
		if(!modifierType.isItemApplicable(stack)) {
			return false;
		}
		if(stack.getItem() instanceof ArmorItem) {
			if(modifierType==ExtendedModifierType.PROTECTION) {
				return ((ArmorItem)stack.getItem()).getDefense() + effect <= LOTRMaterial.MITHRIL.asArmor().getDefenseForSlot(((ArmorItem)stack.getItem()).getSlot());
			}
		}
		return true;
	}
	/**
	 * Determines if this modifier can be applied alongside another modifier
	 *
	 * @param pModifierType the item to check compatibility against
	 * @return {@code true} if the modifier can be applied to the item, {@code false} otherwise
	 */
	public boolean isCompatableModifier(ExtendedModifierType pModifierType) {
		return modifierType.isModifierApplicable(pModifierType);
	}
	/** 
	 * @return the tier or strength level of this modifier. 
	 */
	public int getModifierTeir() {
		return tier;
	}
	/**
	 * Returns the value of this modifier.
	 *
	 * <p>The correct values for this modifier can be determined using the following process:
	 * <ol>
	 *   <li>Open the legacy environment.</li>
	 *   <li>Obtain the desired item from the creative inventory.</li>
	 *   <li>Run the command <code>/lotrEnchant &lt;playername&gt; clear</code> to reset the item and ensure it has no prior modifications.</li>
	 *   <li>Acquire the smith scroll corresponding to the modifier being tested.</li>
	 *   <li>Place both the item and the smith scroll in an anvil to observe the resulting modifier value as the material cost.</li>
	 *   <li>However bows multiply the value by three since string is cheap, so divide by three for bows to get the proper value.</li>
	 * </ol>
	 *
	 * <p>This method works because the item has no previous modifications. As a result,
	 * there are no additional costs from prior repairs, renaming, or other anvil operations—
	 * only the combining cost is applied.
	 *
	 * @return the tier value representing this modifier's cost or strength
	 */
	public int getModifierValue() {
		return tier;
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
	 * @return {@code true} if this modifier is the {@link ExtendedModifierType#UNKNOWN} entry,
	 *         {@code false} otherwise
	 */
	public boolean isUnknown() {
		return this==ExtendedModifiers.UNKNOWN;
	}
	/**
	 * Creates a new map containing all items equipped by an entity in {@linkplain #slots slots that the enchantment
	 * cares about}. These items are not tested for having the enchantment.
	 * @param pEntity The entity to collect equipment for.
	 * @return A map for slots to valid itemstacks
	 */
	public Map<EquipmentSlotType, ItemStack> getSlotItems(LivingEntity pEntity) {
		Map<EquipmentSlotType, ItemStack> map = Maps.newEnumMap(EquipmentSlotType.class);

		for(EquipmentSlotType equipmentslottype : this.slots) {
			ItemStack itemstack = pEntity.getItemBySlot(equipmentslottype);
			if (!itemstack.isEmpty()) {
				map.put(equipmentslottype, itemstack);
			}
		}

		return map;
	}
	/**
	 * @return the maximum level available for this modifier's type,
	 *         based on the global lookup table.
	 */
	public int getMaxLevel() {
		List<ExtendedModifier> list = ExtendedModifiers.MODIFIERS_BY_TYPE.get(modifierType);
		if (list == null || list.isEmpty()) {
			return this.tier; // fallback, should not normally happen
		}
		return list.get(list.size() - 1).getModifierTeir();
	}
	/**
	 * @return the minimum level available for this modifier's type,
	 *         based on the global lookup table.
	 */
	public int getMinLevel() {
		List<ExtendedModifier> list = ExtendedModifiers.MODIFIERS_BY_TYPE.get(modifierType);
		if (list == null || list.isEmpty()) {
			return this.tier; // fallback, should not normally happen
		}
		return list.get(0).getModifierTeir();
	}

	public boolean isAllowedOnScrolls() {
		return rarity.equals(ExtendedModifierRarity.BASIC) || rarity.equals(ExtendedModifierRarity.SKILLFUL);
	}
}