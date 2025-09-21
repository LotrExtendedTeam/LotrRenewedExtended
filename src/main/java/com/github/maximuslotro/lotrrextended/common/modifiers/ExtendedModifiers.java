package com.github.maximuslotro.lotrrextended.common.modifiers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.inventory.EquipmentSlotType;

/**
 * <p>
 * Defines and registers all available extended modifiers for items.
 * Each modifier represents a functional enhancement such as durability,
 * damage, protection, or special effects. Modifiers are grouped by type,
 * and each has a level, effect, rarity, associated scroll, and weight for
 * generation purposes.
 * </p>
 *
 * <p>
 * Modifiers are registered statically via the {@link #registerInt} or {@link #registerFloat} methods,
 * and all registered modifiers are stored in the {@link #ALL} list.
 * </p>
 *
 * <p>
 * Provides two key lookup structures:
 * <ul>
 *   <li>{@link #MODIFIER_MAP}: A map from NBT keys to modifiers for fast lookup.</li>
 *   <li>{@link #MODIFIERS_BY_TYPE}: A map grouping modifiers by their functional type, sorted by level.</li>
 * </ul>
 * </p>
 *
 * <p>
 * To initialize these maps, {@link #buildModifierLists()} must be called after all modifiers are registered.
 * </p>
 */
public class ExtendedModifiers {
	/**
     * List of all registered extended modifiers.
     * This list includes every modifier declared in this class.
     */
	public static final List<ExtendedModifier> ALL = new ArrayList<>();
	// === Durability Modifiers ===
	public static final ExtendedModifier ENDURING = registerFloat("durable3", "durability.three", ExtendedModifierTypes.DURABILITY, 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 4, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier LASTING = registerFloat("durable2", "durability.two", ExtendedModifierTypes.DURABILITY, 2, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 8, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier HARDY = registerFloat("durable1", "durability.one", ExtendedModifierTypes.DURABILITY, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 15, EquipmentSlotType.MAINHAND);
	// === Melee Damage Modifiers ===
	public static final ExtendedModifier LEGENDARY = registerFloat("strong4", "melee_damage.four", ExtendedModifierTypes.MELEE_DAMAGE, 4, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier MIGHTY = registerFloat("strong3", "melee_damage.three", ExtendedModifierTypes.MELEE_DAMAGE, 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier KEEN = registerFloat("strong2", "melee_damage.two", ExtendedModifierTypes.MELEE_DAMAGE, 2, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 5, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier SHARP = registerFloat("strong1", "melee_damage.one", ExtendedModifierTypes.MELEE_DAMAGE, 1, 0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN, 10, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier DULL = registerFloat("weak1", "melee_damage.weak_one", ExtendedModifierTypes.MELEE_DAMAGE, 0, -0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 6, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier BLUNT = registerFloat("weak2", "melee_damage.weak_two", ExtendedModifierTypes.MELEE_DAMAGE, -1, -1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier CROOKED = registerFloat("weak3", "melee_damage.weak_three", ExtendedModifierTypes.MELEE_DAMAGE, -2, -2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 2, EquipmentSlotType.MAINHAND);
	// === Melee Reach Modifiers ===
	public static final ExtendedModifier LONG = registerFloat("meleeReach1", "melee_reach.one", ExtendedModifierTypes.MELEE_REACH, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD, 5, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier STUNTED = registerFloat("meleeUnreach1", "melee_reach.short_one", ExtendedModifierTypes.MELEE_REACH, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3, EquipmentSlotType.MAINHAND);
	// === Melee Speed Modifiers ===
	public static final ExtendedModifier SWIFT = registerFloat("meleeSpeed1", "melee_speed.one", ExtendedModifierTypes.MELEE_SPEED, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD, 5, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier SLOW = registerFloat("meleeSlow1", "melee_speed.slow_one", ExtendedModifierTypes.MELEE_SPEED, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3, EquipmentSlotType.MAINHAND);
	// === Melee Knockback Modifiers ===
	public static final ExtendedModifier HULKING = registerInt("knockback2", "melee_knockback.two", ExtendedModifierTypes.MEELE_KNOCKBACK, 2, 2, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 2, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier HEFTY = registerInt("knockback1", "melee_knockback.one", ExtendedModifierTypes.MEELE_KNOCKBACK, 1, 1, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 5, EquipmentSlotType.MAINHAND);
	// === Bane-type Modifiers (mutually exclusive) ===
	public static final ExtendedModifier ORC_BANE = registerInt("baneOrc", "baneorc", ExtendedModifierTypes.BANE, 1, 4, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier DWARF_BANE = registerInt("baneDwarf", "banedwarf", ExtendedModifierTypes.BANE, 1, 4, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier ELF_BANE = registerInt("baneElf", "baneelf", ExtendedModifierTypes.BANE, 1, 4, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier WARG_BANE = registerInt("baneWarg", "banewarg", ExtendedModifierTypes.BANE, 1, 4, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier WIGHT_BANE = registerInt("baneWight", "banewight", ExtendedModifierTypes.BANE, 1, 4, ExtendedModifierRarity.UNIQUE, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	// === Looting Modifiers ===
	public static final ExtendedModifier BLESSED = registerInt("looting3", "looting.three", ExtendedModifierTypes.LOOTING, 3, 3, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 5, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier FORTUNATE = registerInt("looting2", "looting.two", ExtendedModifierTypes.LOOTING, 2, 2, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier LUCKY = registerInt("looting1", "looting.one", ExtendedModifierTypes.LOOTING, 1, 1, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 1, EquipmentSlotType.MAINHAND);
	// === Tool Speed Modifiers ===
	public static final ExtendedModifier MASTERFUL = registerFloat("toolSpeed4", "tool_speed.four", ExtendedModifierTypes.TOOL_SPEED, 4, 4.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 20, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier HASTY = registerFloat("toolSpeed3", "tool_speed.three", ExtendedModifierTypes.TOOL_SPEED, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 10, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier NIMBLE = registerFloat("toolSpeed2", "tool_speed.two", ExtendedModifierTypes.TOOL_SPEED, 2, 2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 5, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier HANDY = registerFloat("toolSpeed1", "tool_speed.one", ExtendedModifierTypes.TOOL_SPEED, 1, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN, 2, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier SLUGGISH = registerFloat("toolSlow1", "tool_speed.slow_one", ExtendedModifierTypes.TOOL_SPEED, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 10, EquipmentSlotType.MAINHAND);
	// === Tool Special Modifier ===
	public static final ExtendedModifier SILKEN = registerInt("toolSilk", "tool_silk", ExtendedModifierTypes.TOOL_SILK, 1, 1, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10, EquipmentSlotType.MAINHAND);
	// === Ranged Damage/Reach Modifiers ===
	public static final ExtendedModifier FIERCE = registerFloat("rangedStrong3", "ranged_speed.three", ExtendedModifierTypes.RANGED_SPEED, 3, 1.3f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier POWERFUL = registerFloat("rangedStrong2", "ranged_speed.two", ExtendedModifierTypes.RANGED_SPEED, 2, 1.2f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 3, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier STRONG = registerFloat("rangedStrong1", "ranged_speed.one", ExtendedModifierTypes.RANGED_SPEED, 1, 1.1f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 1, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier WEAK = registerFloat("rangedWeak1", "ranged_speed.weak_one", ExtendedModifierTypes.RANGED_SPEED, 0, 0.9f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier FEEBLE = registerFloat("rangedWeak2", "ranged_speed.weak_two", ExtendedModifierTypes.RANGED_SPEED, -1, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 10, EquipmentSlotType.MAINHAND);
	// === Ranged Knockback Modifiers ===
	public static final ExtendedModifier STRIKING = registerInt("rangedKnockback2", "ranged_knockback.two", ExtendedModifierTypes.RANGED_KNOCKBACK, 2, 2, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 5, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier PUNCHING = registerInt("rangedKnockback1", "ranged_knockback.one", ExtendedModifierTypes.RANGED_KNOCKBACK, 1, 1, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 2, EquipmentSlotType.MAINHAND);
	// === Applied / Special Modifiers ===
	public static final ExtendedModifier INFERNAL = registerInt("fire", "infernal", ExtendedModifierTypes.INFERNAL, 1, 1, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier CHILLING = registerInt("chill", "chilling", ExtendedModifierTypes.CHILLING, 1, 1, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	public static final ExtendedModifier HEADHUNTING = registerInt("headhunting", "headhunting", ExtendedModifierTypes.HEADHUNTING, 1, 1, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0, EquipmentSlotType.MAINHAND);
	// === Armor Protection Modifiers ===
	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
	public static final ExtendedModifier BELEGOSTIN = registerInt("protectFire3", "armor_protect_fire.three", ExtendedModifierTypes.PROTECTION_FIRE, 3, 4, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1, ARMOR_SLOTS);
	public static final ExtendedModifier UNWITHERING = registerInt("protectFire2", "armor_protect_fire.two", ExtendedModifierTypes.PROTECTION_FIRE, 2, 3, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2, ARMOR_SLOTS);
	public static final ExtendedModifier COOLING = registerInt("protectFire1", "armor_protect_fire.one", ExtendedModifierTypes.PROTECTION_FIRE, 1, 2, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3, ARMOR_SLOTS);
	public static final ExtendedModifier EOLEAN = registerInt("protectRanged3", "armor_protect_ranged.three", ExtendedModifierTypes.PROTECTION_RANGED, 3, 3, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1, ARMOR_SLOTS);
	public static final ExtendedModifier DEFLECTING = registerInt("protectRanged2", "armor_protect_ranged.two", ExtendedModifierTypes.PROTECTION_RANGED, 2, 2, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2, ARMOR_SLOTS);
	public static final ExtendedModifier GLANCING = registerInt("protectRanged1", "armor_protect_ranged.one", ExtendedModifierTypes.PROTECTION_RANGED, 1, 1, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3, ARMOR_SLOTS);
	public static final ExtendedModifier LIGHTFOOTED = registerInt("protectFall3", "armor_protect_fall.three", ExtendedModifierTypes.PROTECTION_FALLING, 3, 9, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 2, ARMOR_SLOTS);
	public static final ExtendedModifier STRONGSHOD = registerInt("protectFall2", "armor_protect_fall.two", ExtendedModifierTypes.PROTECTION_FALLING, 2, 6, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 3, ARMOR_SLOTS);
	public static final ExtendedModifier THICKSOLED = registerInt("protectFall1", "armor_protect_fall.one", ExtendedModifierTypes.PROTECTION_FALLING, 1, 4, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 4, ARMOR_SLOTS);
	public static final ExtendedModifier RUVINANORNA = registerInt("protectBlast3", "armor_protect_blast.three", ExtendedModifierTypes.PROTECTION_BLAST, 3, 3, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1, ARMOR_SLOTS);
	public static final ExtendedModifier SAFEGUARDED = registerInt("protectBlast2", "armor_protect_blast.two", ExtendedModifierTypes.PROTECTION_BLAST, 2, 2, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2, ARMOR_SLOTS);
	public static final ExtendedModifier REINFORCED = registerInt("protectBlast1", "armor_protect_blast.one", ExtendedModifierTypes.PROTECTION_BLAST, 1, 1, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3, ARMOR_SLOTS);
	public static final ExtendedModifier STEADFAST = registerInt("protect2", "armor_protect.two", ExtendedModifierTypes.PROTECTION, 2, 2, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10, ARMOR_SLOTS);
	public static final ExtendedModifier HARDENED = registerInt("protect1", "armor_protect.one", ExtendedModifierTypes.PROTECTION, 1, 1, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 3, ARMOR_SLOTS);
	public static final ExtendedModifier POOR = registerInt("protectWeak1", "armor_protect.weak_one", ExtendedModifierTypes.PROTECTION, 0, -1, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 4, ARMOR_SLOTS);
	public static final ExtendedModifier CRUDE = registerInt("protectWeak2", "armor_protect.weak_two", ExtendedModifierTypes.PROTECTION, -1, -2, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3, ARMOR_SLOTS);
	public static final ExtendedModifier TRUE = registerInt("protectMithril", "armor_protect_mithril", ExtendedModifierTypes.PROTECTION_MITHRIL, 1, 4, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.GOLD, 0, ARMOR_SLOTS);
	// Unknown
	public static final ExtendedModifier UNKNOWN = registerFloat("unknown", "unknown", ExtendedModifierTypes.UNKNOWN, 0, 3.14159f, ExtendedModifierRarity.UNIQUE, ExtendedModifierScroll.WOODEN, 0);
	/**
     * Registers a new modifier and adds it to the master list {@link #ALL}.
     *
     * @param pNbtKey The unique NBT identifier for the modifier.
     * @param pLocalKey The localization key for UI display.
     * @param pModifierType The functional type of the modifier.
     * @param pLevel The tier or level of the modifier (higher is stronger).
     * @param pModifierEffect The numerical effect applied by the modifier.
     * @param pModifierRarity The rarity classification of the modifier.
     * @param pScroll The scroll type used to apply the modifier.
     * @param pWeight The relative chance of occurrence for loot generation.
	 * @param pApplicableSlots The slots that this modifier can be applied from
     * @return The registered {@link ExtendedModifier} instance.
     */
	private static ExtendedModifier registerInt(String pNbtKey, String pLocalKey, ExtendedModifierTypes pModifierType, int pLevel, 
		int pModifierEffect, ExtendedModifierRarity pModifierRarity, ExtendedModifierScroll pScroll, int pWeight, EquipmentSlotType... pApplicableSlots) {
		ExtendedModifier newModifier = new ExtendedModifier(pNbtKey, pLocalKey, pModifierType, pLevel, pModifierEffect, pModifierRarity, pScroll, pWeight, pApplicableSlots);
		ALL.add(newModifier);
		return newModifier;
	}
	/**
     * Registers a new modifier and adds it to the master list {@link #ALL}.
     *
     * @param pNbtKey The unique NBT identifier for the modifier.
     * @param pLocalKey The localization key for UI display.
     * @param pModifierType The functional type of the modifier.
     * @param pLevel The tier or level of the modifier (higher is stronger).
     * @param pModifierEffect The numerical effect applied by the modifier.
     * @param pModifierRarity The rarity classification of the modifier.
     * @param pScroll The scroll type used to apply the modifier.
     * @param pWeight The relative chance of occurrence for loot generation.
     * @param pApplicableSlots The slots that this modifier can be applied from
     * @return The registered {@link ExtendedModifier} instance.
     */
	private static ExtendedModifier registerFloat(String pNbtKey, String pLocalKey, ExtendedModifierTypes pModifierType, int pLevel, 
		float pModifierEffect, ExtendedModifierRarity pModifierRarity, ExtendedModifierScroll pScroll, int pWeight, EquipmentSlotType... pApplicableSlots) {
		ExtendedModifier newModifier = new ExtendedModifier(pNbtKey, pLocalKey, pModifierType, pLevel, pModifierEffect, pModifierRarity, pScroll, pWeight, pApplicableSlots);
		ALL.add(newModifier);
		return newModifier;
	}
	/**
     * Map of modifiers keyed by their NBT identifiers.
     * Provides fast lookup for serialization, deserialization, and compatibility checks.
     */
	public static final Map<String, ExtendedModifier> MODIFIER_MAP = new HashMap<>();

	/**
     * Map grouping modifiers by their functional type.
     * Each type is associated with a list of modifiers sorted from
     * lowest tier (min) to highest tier (max).
     */
	public static final Map<ExtendedModifierTypes, List<ExtendedModifier>> MODIFIERS_BY_TYPE = new HashMap<>();
	/**
	 * Populates the lookup maps for extended modifiers:
	 * <ul>
	 *     <li>{@link #MODIFIER_MAP}: Maps each modifier's NBT key to its corresponding {@link ExtendedModifier} instance
	 *         for fast retrieval during serialization, deserialization, or gameplay logic.</li>
	 *     <li>{@link #MODIFIERS_BY_TYPE}: Groups modifiers by their {@link ExtendedModifierTypes}, 
	 *         where each list is sorted by modifier level from lowest to highest.</li>
	 * </ul>
	 *
	 * <p>
	 * This method should be called once after all modifiers are registered in {@link #ALL}.
	 * It ensures that both maps are correctly populated and ready for use in lookups, grouping, 
	 * and iteration over modifiers by type or NBT key.
	 * </p>
	 * 
	 * Additionally this may be called during init of other mods to add their own modifiers to the sorted maps. 
	 */
	public static void buildModifierLists() {
		Map<String, ExtendedModifier> buildableModifierMap = new HashMap<>();
		for (ExtendedModifier m : ALL) {
			// Use the NBT key
			buildableModifierMap.put(m.getNbtKey(), m);
		}
		MODIFIER_MAP.putAll(buildableModifierMap);

		Map<ExtendedModifierTypes, List<ExtendedModifier>> grouped = new EnumMap<>(ExtendedModifierTypes.class);
		for (ExtendedModifier m : ALL) {
			grouped.computeIfAbsent(m.getModifierType(), k -> new ArrayList<>()).add(m);
		}
		// Sort modifiers within each type by their tier/level
		for (List<ExtendedModifier> list : grouped.values()) {
			list.sort(Comparator.comparingInt(ExtendedModifier::getModifierTeir));
		}
		MODIFIERS_BY_TYPE.putAll(grouped);
	}
}
