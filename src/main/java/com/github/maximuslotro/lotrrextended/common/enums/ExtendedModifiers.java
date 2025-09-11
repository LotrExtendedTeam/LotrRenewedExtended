package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

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
 * Try not to reference the enum names of the modifiers as they are subject to change in the future
 */
public enum ExtendedModifiers {
	// === Durability Modifiers ===
	ENDURING("durable3", "durability.three", ExtendedModifierTypes.DURABILITY, 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 4),
	LASTING("durable2", "durability.two", ExtendedModifierTypes.DURABILITY, 2, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 8),
	HARDY("durable1", "durability.one", ExtendedModifierTypes.DURABILITY, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 15),
	// === Melee Damage Modifiers ===
	LEGENDARY("strong4", "melee_damage.four", ExtendedModifierTypes.MELEE_DAMAGE, 4, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1),
	MIGHTY("strong3", "melee_damage.three", ExtendedModifierTypes.MELEE_DAMAGE, 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2),
	KEEN("strong2", "melee_damage.two", ExtendedModifierTypes.MELEE_DAMAGE, 2, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 5),
	SHARP("strong1", "melee_damage.one", ExtendedModifierTypes.MELEE_DAMAGE, 1, 0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN, 10),
	DULL("weak1", "melee_damage.weak_one", ExtendedModifierTypes.MELEE_DAMAGE, 0, -0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 6),
	BLUNT("weak2", "melee_damage.weak_two", ExtendedModifierTypes.MELEE_DAMAGE, -1, -1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3),
	CROOKED("weak3", "melee_damage.weak_three", ExtendedModifierTypes.MELEE_DAMAGE, -2, -2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 2),
	// === Melee Reach Modifiers ===
	LONG("meleeReach1", "melee_reach.one", ExtendedModifierTypes.MELEE_REACH, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD, 5),
	STUNTED("meleeUnreach1", "melee_reach.short_one", ExtendedModifierTypes.MELEE_REACH, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3),
	// === Melee Speed Modifiers ===
	SWIFT("meleeSpeed1", "melee_speed.one", ExtendedModifierTypes.MELEE_SPEED, 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD, 5),
	SLOW("meleeSlow1", "melee_speed.slow_one", ExtendedModifierTypes.MELEE_SPEED, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3),
	// === Melee Knockback Modifiers ===
	HULKING("knockback2", "melee_knockback.two", ExtendedModifierTypes.MEELE_KNOCKBACK, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 2),
	HEFTY("knockback1", "melee_knockback.one", ExtendedModifierTypes.MEELE_KNOCKBACK, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 5),
	// === Bane-type Modifiers (mutually exclusive) ===
	ORC_BANE("baneOrc", "baneorc", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0),
	DWARF_BANE("baneDwarf", "banedwarf", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0),
	ELF_BANE("baneElf", "baneelf", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0),
	WARG_BANE("baneWarg", "banewarg", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL, 0),
	WIGHT_BANE("baneWight", "banewight", ExtendedModifierTypes.BANE, 1, 4.0f, ExtendedModifierRarity.UNIQUE, ExtendedModifierScroll.NOSCROLL, 0),
	// === Looting Modifiers ===
	BLESSED("looting3", "looting.three", ExtendedModifierTypes.LOOTING, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 5),
	FORTUNATE("looting2", "looting.two", ExtendedModifierTypes.LOOTING, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2),
	LUCKY("looting1", "looting.one", ExtendedModifierTypes.LOOTING, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 1),
	// === Tool Speed Modifiers ===
	MASTERFUL("toolSpeed4", "tool_speed.four", ExtendedModifierTypes.TOOL_SPEED, 4, 4.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 20),
	HASTY("toolSpeed3", "tool_speed.three", ExtendedModifierTypes.TOOL_SPEED, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 10),
	NIMBLE("toolSpeed2", "tool_speed.two", ExtendedModifierTypes.TOOL_SPEED, 2, 2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 5),
	HANDY("toolSpeed1", "tool_speed.one", ExtendedModifierTypes.TOOL_SPEED, 1, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN, 2),
	SLUGGISH("toolSlow1", "tool_speed.slow_one", ExtendedModifierTypes.TOOL_SPEED, 0, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 10),
	// === Tool Special Modifier ===
	SILKEN("toolSilk", "tool_silk", ExtendedModifierTypes.TOOL_SILK, 1, 1f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10),
	// === Ranged Damage/Reach Modifiers ===
	FIERCE("rangedStrong3", "ranged_speed.three", ExtendedModifierTypes.RANGED_SPEED, 3, 1.3f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10),
	POWERFUL("rangedStrong2", "ranged_speed.two", ExtendedModifierTypes.RANGED_SPEED, 2, 1.2f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 3),
	STRONG("rangedStrong1", "ranged_speed.one", ExtendedModifierTypes.RANGED_SPEED, 1, 1.1f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 1),
	WEAK("rangedWeak1", "ranged_speed.weak_one", ExtendedModifierTypes.RANGED_SPEED, 0, 0.9f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3),
	FEEBLE("rangedWeak2", "ranged_speed.weak_two", ExtendedModifierTypes.RANGED_SPEED, -1, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 10),
	// === Ranged Knockback Modifiers ===
	STRIKING("rangedKnockback2", "ranged_knockback.two", ExtendedModifierTypes.RANGED_KNOCKBACK, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 5),
	PUNCHING("rangedKnockback1", "ranged_knockback.one", ExtendedModifierTypes.RANGED_KNOCKBACK, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 2),
	// === Applied / Special Modifiers ===
	INFERNAL("fire", "fire", ExtendedModifierTypes.INFERNAL, 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0),
	CHILLING("chill", "chill", ExtendedModifierTypes.CHILLING, 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0),
	HEADHUNTING("headhunting", "headhunting", ExtendedModifierTypes.HEADHUNTING, 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL, 0),
	// === Armor Protection Modifiers ===
	BELEGOSTIN("protectFire3", "armor_protect_fire.three", ExtendedModifierTypes.PROTECTION_FIRE, 3, 4.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1),
	UNWITHERING("protectFire2", "armor_protect_fire.two", ExtendedModifierTypes.PROTECTION_FIRE, 2, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2),
	COOLING("protectFire1", "armor_protect_fire.one", ExtendedModifierTypes.PROTECTION_FIRE, 1, 2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3),
	EOLEAN("protectRanged3", "armor_protect_ranged.three", ExtendedModifierTypes.PROTECTION_RANGED, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1),
	DEFLECTING("protectRanged2", "armor_protect_ranged.two", ExtendedModifierTypes.PROTECTION_RANGED, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2),
	GLANCING("protectRanged1", "armor_protect_ranged.one", ExtendedModifierTypes.PROTECTION_RANGED, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3),
	LIGHTFOOTED("protectFall3", "armor_protect_fall.three", ExtendedModifierTypes.PROTECTION_FALLING, 3, 9.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 2),
	STRONGSHOD("protectFall2", "armor_protect_fall.two", ExtendedModifierTypes.PROTECTION_FALLING, 2, 6.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 3),
	THICKSOLED("protectFall1", "armor_protect_fall.one", ExtendedModifierTypes.PROTECTION_FALLING, 1, 4.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 4),
	RUVINANORNA("protectBlast3", "armor_protect_blast.three", ExtendedModifierTypes.PROTECTION_BLAST, 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 1),
	SAFEGUARDED("protectBlast2", "armor_protect_blast.two", ExtendedModifierTypes.PROTECTION_BLAST, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER, 2),
	REINFORCED("protectBlast1", "armor_protect_blast.one", ExtendedModifierTypes.PROTECTION_BLAST, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE, 3),
	STEADFAST("protect2", "armor_protect.two", ExtendedModifierTypes.PROTECTION, 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD, 10),
	HARDENED("protect1", "armor_protect.one", ExtendedModifierTypes.PROTECTION, 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER, 3),
	POOR("protectWeak1", "armor_protect.weak_one", ExtendedModifierTypes.PROTECTION, 0, -1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 4),
	CRUDE("protectWeak2", "armor_protect.weak_two", ExtendedModifierTypes.PROTECTION, -1, -2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL, 3),
	TRUE("protectMithril", "armor_protect_mithril", ExtendedModifierTypes.PROTECTION_MITHRIL, 1, 4.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.GOLD, 0),
	// Unknown
	UNKNOWN("unknown", "unknown", ExtendedModifierTypes.UNKNOWN, 0, 3.14159f, ExtendedModifierRarity.UNIQUE, ExtendedModifierScroll.WOODEN, 0);

	/**
	 * Immutable map of modifiers keyed by their NBT identifiers.
	 * Provides fast lookup for serialization and compatibility checks.
	 */
	public static final Map<String, ExtendedModifiers> MODIFIER_MAP;

	/**
	 * Immutable map grouping modifiers by their functional type.
	 * Each type is associated with a list of modifiers sorted from
	 * lowest tier (min) to highest tier (max).
	 */
	public static final Map<ExtendedModifierTypes, List<ExtendedModifiers>> MODIFIERS_BY_TYPE;

	static {
		Map<String, ExtendedModifiers> buildableModifierMap = new HashMap<>();
		for (ExtendedModifiers m : values()) {
			// Use the NBT key
			buildableModifierMap.put(m.getNbtKey(), m);
		}
		MODIFIER_MAP = Collections.unmodifiableMap(buildableModifierMap);

		Map<ExtendedModifierTypes, List<ExtendedModifiers>> grouped = new EnumMap<>(ExtendedModifierTypes.class);

		for (ExtendedModifiers m : values()) {
			grouped.computeIfAbsent(m.getModifierType(), k -> new ArrayList<>()).add(m);
		}

		// Sort modifiers within each type by their tier/level
		for (List<ExtendedModifiers> list : grouped.values()) {
			list.sort(Comparator.comparingInt(ExtendedModifiers::getModifierLevel));
		}

		MODIFIERS_BY_TYPE = Collections.unmodifiableMap(grouped);
	}

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

	/** Weight of the modifier for random adding. */
	private final int weight;

	ExtendedModifiers(String pNbtKey, String pLocalKey, ExtendedModifierTypes pModifierType, int pLevel, 
			float pModifierEffect, ExtendedModifierRarity pModifierRarity, ExtendedModifierScroll pScroll, int pWeight) {
		this.keyNbt = pNbtKey;
		this.keyLocal = pLocalKey;
		this.modifierType = pModifierType;
		this.level = pLevel;
		this.effect = pModifierEffect;
		this.rarity = pModifierRarity;
		this.scroll = pScroll;
		this.weight = pWeight;
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
		return this==UNKNOWN;
	}
	/**
	 * @return the maximum level available for this modifier's type,
	 *         based on the global lookup table.
	 */
	public int getMaxLevel() {
		List<ExtendedModifiers> list = MODIFIERS_BY_TYPE.get(this.getModifierType());
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
		List<ExtendedModifiers> list = MODIFIERS_BY_TYPE.get(this.getModifierType());
		if (list == null || list.isEmpty()) {
			return this.level; // fallback, should not normally happen
		}
		return list.get(0).getModifierLevel();
	}
}