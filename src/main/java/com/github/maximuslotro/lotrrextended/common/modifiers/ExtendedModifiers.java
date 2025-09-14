package com.github.maximuslotro.lotrrextended.common.modifiers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Modifiers are registered statically via the {@link #register} method,
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
	public static final ExtendedModifier ENDURING = register("durable3", "durability.three", ExtendedModifierTypes.DURABILITY, 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 4);
	public static final ExtendedModifier LASTING = register("durable2", "durability.two", ExtendedModifierTypes.DURABILITY, 2, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 8);
	public static final ExtendedModifier HARDY = register("durable1", "durability.one", ExtendedModifierTypes.DURABILITY, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 15);
	// === Melee Damage Modifiers ===
	public static final ExtendedModifier LEGENDARY = register("strong4", "melee_damage.four", ExtendedModifierTypes.MELEE_DAMAGE, 4, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1);
	public static final ExtendedModifier MIGHTY = register("strong3", "melee_damage.three", ExtendedModifierTypes.MELEE_DAMAGE, 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2);
	public static final ExtendedModifier KEEN = register("strong2", "melee_damage.two", ExtendedModifierTypes.MELEE_DAMAGE, 2, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 5);
	public static final ExtendedModifier SHARP = register("strong1", "melee_damage.one", ExtendedModifierTypes.MELEE_DAMAGE, 1, 0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN, 10);
	public static final ExtendedModifier DULL = register("weak1", "melee_damage.weak_one", ExtendedModifierTypes.MELEE_DAMAGE, 0, -0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 6);
	public static final ExtendedModifier BLUNT = register("weak2", "melee_damage.weak_two", ExtendedModifierTypes.MELEE_DAMAGE, -1, -1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3);
	public static final ExtendedModifier CROOKED = register("weak3", "melee_damage.weak_three", ExtendedModifierTypes.MELEE_DAMAGE, -2, -2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 2);
	// === Melee Reach Modifiers ===
	public static final ExtendedModifier LONG = register("meleeReach1", "melee_reach.one", ExtendedModifierTypes.MELEE_REACH, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD, 5);
	public static final ExtendedModifier STUNTED = register("meleeUnreach1", "melee_reach.short_one", ExtendedModifierTypes.MELEE_REACH, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3);
	// === Melee Speed Modifiers ===
	public static final ExtendedModifier SWIFT = register("meleeSpeed1", "melee_speed.one", ExtendedModifierTypes.MELEE_SPEED, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD, 5);
	public static final ExtendedModifier SLOW = register("meleeSlow1", "melee_speed.slow_one", ExtendedModifierTypes.MELEE_SPEED, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3);
	// === Melee Knockback Modifiers ===
	public static final ExtendedModifier HULKING = register("knockback2", "melee_knockback.two", ExtendedModifierTypes.MEELE_KNOCKBACK, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 2);
	public static final ExtendedModifier HEFTY = register("knockback1", "melee_knockback.one", ExtendedModifierTypes.MEELE_KNOCKBACK, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 5);
	// === Bane-type Modifiers (mutually exclusive) ===
	public static final ExtendedModifier ORC_BANE = register("baneOrc", "baneorc", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0);
	public static final ExtendedModifier DWARF_BANE = register("baneDwarf", "banedwarf", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0);
	public static final ExtendedModifier ELF_BANE = register("baneElf", "baneelf", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0);
	public static final ExtendedModifier WARG_BANE = register("baneWarg", "banewarg", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0);
	public static final ExtendedModifier WIGHT_BANE = register("baneWight", "banewight", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.UNIQUE, ExtendedModifierScroll.NOSCROLL, 0);
	// === Looting Modifiers ===
	public static final ExtendedModifier BLESSED = register("looting3", "looting.three", ExtendedModifierTypes.LOOTING, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 5);
	public static final ExtendedModifier FORTUNATE = register("looting2", "looting.two", ExtendedModifierTypes.LOOTING, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2);
	public static final ExtendedModifier LUCKY = register("looting1", "looting.one", ExtendedModifierTypes.LOOTING, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 1);
	// === Tool Speed Modifiers ===
	public static final ExtendedModifier MASTERFUL = register("toolSpeed4", "tool_speed.four", ExtendedModifierTypes.TOOL_SPEED, 4, 4.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 20);
	public static final ExtendedModifier HASTY = register("toolSpeed3", "tool_speed.three", ExtendedModifierTypes.TOOL_SPEED, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 10);
	public static final ExtendedModifier NIMBLE = register("toolSpeed2", "tool_speed.two", ExtendedModifierTypes.TOOL_SPEED, 2, 2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 5);
	public static final ExtendedModifier HANDY = register("toolSpeed1", "tool_speed.one", ExtendedModifierTypes.TOOL_SPEED, 1, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN, 2);
	public static final ExtendedModifier SLUGGISH = register("toolSlow1", "tool_speed.slow_one", ExtendedModifierTypes.TOOL_SPEED, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 10);
	// === Tool Special Modifier ===
	public static final ExtendedModifier SILKEN = register("toolSilk", "tool_silk", ExtendedModifierTypes.TOOL_SILK, 1, 1f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10);
	// === Ranged Damage/Reach Modifiers ===
	public static final ExtendedModifier FIERCE = register("rangedStrong3", "ranged_speed.three", ExtendedModifierTypes.RANGED_SPEED, 3, 1.3f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10);
	public static final ExtendedModifier POWERFUL = register("rangedStrong2", "ranged_speed.two", ExtendedModifierTypes.RANGED_SPEED, 2, 1.2f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 3);
	public static final ExtendedModifier STRONG = register("rangedStrong1", "ranged_speed.one", ExtendedModifierTypes.RANGED_SPEED, 1, 1.1f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 1);
	public static final ExtendedModifier WEAK = register("rangedWeak1", "ranged_speed.weak_one", ExtendedModifierTypes.RANGED_SPEED, 0, 0.9f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3);
	public static final ExtendedModifier FEEBLE = register("rangedWeak2", "ranged_speed.weak_two", ExtendedModifierTypes.RANGED_SPEED, -1, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 10);
	// === Ranged Knockback Modifiers ===
	public static final ExtendedModifier STRIKING = register("rangedKnockback2", "ranged_knockback.two", ExtendedModifierTypes.RANGED_KNOCKBACK, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 5);
	public static final ExtendedModifier PUNCHING = register("rangedKnockback1", "ranged_knockback.one", ExtendedModifierTypes.RANGED_KNOCKBACK, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 2);
	// === Applied / Special Modifiers ===
	public static final ExtendedModifier INFERNAL = register("fire", "fire", ExtendedModifierTypes.INFERNAL, 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0);
	public static final ExtendedModifier CHILLING = register("chill", "chill", ExtendedModifierTypes.CHILLING, 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0);
	public static final ExtendedModifier HEADHUNTING = register("headhunting", "headhunting", ExtendedModifierTypes.HEADHUNTING, 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0);
	// === Armor Protection Modifiers ===
	public static final ExtendedModifier BELEGOSTIN = register("protectFire3", "armor_protect_fire.three", ExtendedModifierTypes.PROTECTION_FIRE, 3, 4.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1);
	public static final ExtendedModifier UNWITHERING = register("protectFire2", "armor_protect_fire.two", ExtendedModifierTypes.PROTECTION_FIRE, 2, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2);
	public static final ExtendedModifier COOLING = register("protectFire1", "armor_protect_fire.one", ExtendedModifierTypes.PROTECTION_FIRE, 1, 2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3);
	public static final ExtendedModifier EOLEAN = register("protectRanged3", "armor_protect_ranged.three", ExtendedModifierTypes.PROTECTION_RANGED, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1);
	public static final ExtendedModifier DEFLECTING = register("protectRanged2", "armor_protect_ranged.two", ExtendedModifierTypes.PROTECTION_RANGED, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2);
	public static final ExtendedModifier GLANCING = register("protectRanged1", "armor_protect_ranged.one", ExtendedModifierTypes.PROTECTION_RANGED, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3);
	public static final ExtendedModifier LIGHTFOOTED = register("protectFall3", "armor_protect_fall.three", ExtendedModifierTypes.PROTECTION_FALLING, 3, 9.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 2);
	public static final ExtendedModifier STRONGSHOD = register("protectFall2", "armor_protect_fall.two", ExtendedModifierTypes.PROTECTION_FALLING, 2, 6.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 3);
	public static final ExtendedModifier THICKSOLED = register("protectFall1", "armor_protect_fall.one", ExtendedModifierTypes.PROTECTION_FALLING, 1, 4.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 4);
	public static final ExtendedModifier RUVINANORNA = register("protectBlast3", "armor_protect_blast.three", ExtendedModifierTypes.PROTECTION_BLAST, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1);
	public static final ExtendedModifier SAFEGUARDED = register("protectBlast2", "armor_protect_blast.two", ExtendedModifierTypes.PROTECTION_BLAST, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2);
	public static final ExtendedModifier REINFORCED = register("protectBlast1", "armor_protect_blast.one", ExtendedModifierTypes.PROTECTION_BLAST, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3);
	public static final ExtendedModifier STEADFAST = register("protect2", "armor_protect.two", ExtendedModifierTypes.PROTECTION, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10);
	public static final ExtendedModifier HARDENED = register("protect1", "armor_protect.one", ExtendedModifierTypes.PROTECTION, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 3);
	public static final ExtendedModifier POOR = register("protectWeak1", "armor_protect.weak_one", ExtendedModifierTypes.PROTECTION, 0, -1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 4);
	public static final ExtendedModifier CRUDE = register("protectWeak2", "armor_protect.weak_two", ExtendedModifierTypes.PROTECTION, -1, -2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3);
	public static final ExtendedModifier TRUE = register("protectMithril", "armor_protect_mithril", ExtendedModifierTypes.PROTECTION_MITHRIL, 1, 4.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.GOLD, 0);
	// Unknown
	public static final ExtendedModifier UNKNOWN = register("unknown", "unknown", ExtendedModifierTypes.UNKNOWN, 0, 3.14159f, ExtendedModifierRarity.UNIQUE, ExtendedModifierScroll.WOODEN, 0);
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
     * @return The registered {@link ExtendedModifier} instance.
     */
	private static ExtendedModifier register(String pNbtKey, String pLocalKey, ExtendedModifierTypes pModifierType, int pLevel, 
		float pModifierEffect, ExtendedModifierRarity pModifierRarity, ExtendedModifierScroll pScroll, int pWeight) {
		ExtendedModifier newModifier = new ExtendedModifier(pNbtKey, pLocalKey, pModifierType, pLevel, pModifierEffect, pModifierRarity, pScroll, pWeight);
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
			list.sort(Comparator.comparingInt(ExtendedModifier::getModifierLevel));
		}
		MODIFIERS_BY_TYPE.putAll(grouped);
	}
}
