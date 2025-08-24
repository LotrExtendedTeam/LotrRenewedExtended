package com.github.maximuslotro.lotrrextended.common.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the complete set of extended modifiers that can be applied to
 * items, weapons, tools, or armor. Each modifier has associated metadata such as:
 * <ul>
 *     <li>{@link #keyNbt} – The NBT save name used for persistence.</li>
 *     <li>{@link #keyLocal} – The localization key used for translations.</li>
 *     <li>{@link #modifierType} – The functional category of the modifier.</li>
 *     <li>{@link #itemTypes} – The valid item categories it can be applied to.</li>
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
	ENDURING("durable3", "durability.three", ExtendedModifierTypes.DURABILITY, Arrays.asList(ExtendedModifierItemType.ALL), 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	LASTING("durable2", "durability.two", ExtendedModifierTypes.DURABILITY, Arrays.asList(ExtendedModifierItemType.ALL), 2, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER),
	HARDY("durable1", "durability.one", ExtendedModifierTypes.DURABILITY, Arrays.asList(ExtendedModifierItemType.ALL), 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	// === Melee Damage Modifiers ===
	LEGENDARY("strong4", "melee_damage.four", ExtendedModifierTypes.MELEE_DAMAGE, Arrays.asList(ExtendedModifierItemType.MELEE), 4, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	MIGHTY("strong3", "melee_damage.three", ExtendedModifierTypes.MELEE_DAMAGE, Arrays.asList(ExtendedModifierItemType.MELEE), 3, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER),
	KEEN("strong2", "melee_damage.two", ExtendedModifierTypes.MELEE_DAMAGE, Arrays.asList(ExtendedModifierItemType.MELEE), 2, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	SHARP("strong1", "melee_damage.one", ExtendedModifierTypes.MELEE_DAMAGE, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN),
	DULL("weak1", "melee_damage.weak_one", ExtendedModifierTypes.MELEE_DAMAGE, Arrays.asList(ExtendedModifierItemType.MELEE), -1, -0.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	BLUNT("weak2", "melee_damage.weak_two", ExtendedModifierTypes.MELEE_DAMAGE, Arrays.asList(ExtendedModifierItemType.MELEE), -2, -1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	CROOKED("weak3", "melee_damage.weak_three", ExtendedModifierTypes.MELEE_DAMAGE, Arrays.asList(ExtendedModifierItemType.MELEE), -3, -2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	// === Melee Reach Modifiers ===
	LONG("meleeReach1", "melee_reach.one", ExtendedModifierTypes.MELEE_REACH, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD),
	STUNTED("meleeUnreach1", "melee_reach.short_one", ExtendedModifierTypes.MELEE_REACH, Arrays.asList(ExtendedModifierItemType.MELEE), -1, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	// === Melee Speed Modifiers ===
	SWIFT("meleeSpeed1", "melee_speed.one", ExtendedModifierTypes.MELEE_SPEED, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 1.25f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.GOLD),
	SLOW("meleeSlow1", "melee_speed.slow_one", ExtendedModifierTypes.MELEE_SPEED, Arrays.asList(ExtendedModifierItemType.MELEE), -1, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	// === Melee Knockback Modifiers ===
	HULKING("knockback2", "melee_knockback.two", ExtendedModifierTypes.MEELE_KNOCKBACK, Arrays.asList(ExtendedModifierItemType.MELEE), 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	HEFTY("knockback1", "melee_knockback.one", ExtendedModifierTypes.MEELE_KNOCKBACK, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER),
	// === Bane-type Modifiers (mutually exclusive) ===
	ORC_BANE("baneOrc", "baneorc", ExtendedModifierTypes.BANE, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL),
	DWARF_BANE("baneDwarf", "banedwarf", ExtendedModifierTypes.BANE, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL),
	ELF_BANE("baneElf", "baneelf", ExtendedModifierTypes.BANE, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL),
	WARG_BANE("baneWarg", "banewarg", ExtendedModifierTypes.BANE, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 4.0f, ExtendedModifierRarity.EARNED, ExtendedModifierScroll.NOSCROLL),
	WIGHT_BANE("baneWight", "banewight", ExtendedModifierTypes.BANE, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 4.0f, ExtendedModifierRarity.UNIQUE, ExtendedModifierScroll.NOSCROLL),
	// === Looting Modifiers ===
	BLESSED("looting3", "looting.three", ExtendedModifierTypes.LOOTING, Arrays.asList(ExtendedModifierItemType.MELEE, ExtendedModifierItemType.TOOL), 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	FORTUNATE("looting2", "looting.two", ExtendedModifierTypes.LOOTING, Arrays.asList(ExtendedModifierItemType.MELEE, ExtendedModifierItemType.TOOL), 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER),
	LUCKY("looting1", "looting.one", ExtendedModifierTypes.LOOTING, Arrays.asList(ExtendedModifierItemType.MELEE, ExtendedModifierItemType.TOOL), 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	// === Tool Speed Modifiers ===
	MASTERFUL("toolSpeed4", "tool_speed.four", ExtendedModifierTypes.TOOL_SPEED, Arrays.asList(ExtendedModifierItemType.TOOL), 4, 4.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	HASTY("toolSpeed3", "tool_speed.three", ExtendedModifierTypes.TOOL_SPEED, Arrays.asList(ExtendedModifierItemType.TOOL), 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER),
	NIMBLE("toolSpeed2", "tool_speed.two", ExtendedModifierTypes.TOOL_SPEED, Arrays.asList(ExtendedModifierItemType.TOOL), 2, 2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	HANDY("toolSpeed1", "tool_speed.one", ExtendedModifierTypes.TOOL_SPEED, Arrays.asList(ExtendedModifierItemType.TOOL), 1, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.WOODEN),
	SLUGGISH("toolSlow1", "tool_speed.slow_one", ExtendedModifierTypes.TOOL_SPEED, Arrays.asList(ExtendedModifierItemType.TOOL), 1, 1.5f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	// === Tool Special Modifier ===
	SILKEN("toolSilk", "tool_silk", ExtendedModifierTypes.TOOL_SILK, Arrays.asList(ExtendedModifierItemType.TOOL), 1, 1f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	// === Ranged Damage/Reach Modifiers ===
	FIERCE("rangedStrong3", "ranged_speed.three", ExtendedModifierTypes.RANGED_SPEED, Arrays.asList(ExtendedModifierItemType.RANGED), 3, 1.3f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	POWERFUL("rangedStrong2", "ranged_speed.two", ExtendedModifierTypes.RANGED_SPEED, Arrays.asList(ExtendedModifierItemType.RANGED), 2, 1.2f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER),
	STRONG("rangedStrong1", "ranged_speed.one", ExtendedModifierTypes.RANGED_SPEED, Arrays.asList(ExtendedModifierItemType.RANGED), 1, 1.1f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	WEAK("rangedWeak1", "ranged_speed.weak_one", ExtendedModifierTypes.RANGED_SPEED, Arrays.asList(ExtendedModifierItemType.RANGED), -1, 0.9f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	FEEBLE("rangedWeak2", "ranged_speed.weak_two", ExtendedModifierTypes.RANGED_SPEED, Arrays.asList(ExtendedModifierItemType.RANGED), -2, 0.75f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	// === Ranged Knockback Modifiers ===
	STRIKING("rangedKnockback2", "ranged_knockback.two", ExtendedModifierTypes.RANGED_KNOCKBACK, Arrays.asList(ExtendedModifierItemType.RANGED), 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	PUNCHING("rangedKnockback1", "ranged_knockback.one", ExtendedModifierTypes.RANGED_KNOCKBACK, Arrays.asList(ExtendedModifierItemType.RANGED), 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER),
	// === Applied / Special Modifiers ===
	INFERNAL("fire", "fire", ExtendedModifierTypes.INFERNAL, Arrays.asList(ExtendedModifierItemType.MELEE, ExtendedModifierItemType.RANGED), 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL),
	CHILLING("chill", "chill", ExtendedModifierTypes.CHILLING, Arrays.asList(ExtendedModifierItemType.MELEE, ExtendedModifierItemType.RANGED), 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL),
	HEADHUNTING("headhunting", "headhunting", ExtendedModifierTypes.HEADHUNTING, Arrays.asList(ExtendedModifierItemType.MELEE), 1, 1.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.NOSCROLL),
	// === Armor Protection Modifiers ===
	BELEGOSTIN("protectFire3", "armor_protect_fire.three", ExtendedModifierTypes.PROTECTION_FIRE, Arrays.asList(ExtendedModifierItemType.ARMOR), 3, 4.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	UNWITHERING("protectFire2", "armor_protect_fire.two", ExtendedModifierTypes.PROTECTION_FIRE, Arrays.asList(ExtendedModifierItemType.ARMOR), 2, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER),
	COOLING("protectFire1", "armor_protect_fire.one", ExtendedModifierTypes.PROTECTION_FIRE, Arrays.asList(ExtendedModifierItemType.ARMOR), 1, 2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	EOLEAN("protectRanged3", "armor_protect_ranged.three", ExtendedModifierTypes.PROTECTION_RANGED, Arrays.asList(ExtendedModifierItemType.ARMOR), 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	DEFLECTING("protectRanged2", "armor_protect_ranged.two", ExtendedModifierTypes.PROTECTION_RANGED, Arrays.asList(ExtendedModifierItemType.ARMOR), 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER),
	GLANCING("protectRanged1", "armor_protect_ranged.one", ExtendedModifierTypes.PROTECTION_RANGED, Arrays.asList(ExtendedModifierItemType.ARMOR), 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	LIGHTFOOTED("protectFall3", "armor_protect_fall.three", ExtendedModifierTypes.PROTECTION_FALLING, Arrays.asList(ExtendedModifierItemType.ARMOR), 3, 9.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	STRONGSHOD("protectFall2", "armor_protect_fall.two", ExtendedModifierTypes.PROTECTION_FALLING, Arrays.asList(ExtendedModifierItemType.ARMOR), 2, 6.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER),
	THICKSOLED("protectFall1", "armor_protect_fall.one", ExtendedModifierTypes.PROTECTION_FALLING, Arrays.asList(ExtendedModifierItemType.ARMOR), 1, 4.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	RUVINANORNA("protectBlast3", "armor_protect_blast.three", ExtendedModifierTypes.PROTECTION_BLAST, Arrays.asList(ExtendedModifierItemType.ARMOR), 3, 3.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	SAFEGUARDED("protectBlast2", "armor_protect_blast.two", ExtendedModifierTypes.PROTECTION_BLAST, Arrays.asList(ExtendedModifierItemType.ARMOR), 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.SILVER),
	REINFORCED("protectBlast1", "armor_protect_blast.one", ExtendedModifierTypes.PROTECTION_BLAST, Arrays.asList(ExtendedModifierItemType.ARMOR), 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.BRONZE),
	STEADFAST("protect2", "armor_protect.two", ExtendedModifierTypes.PROTECTION, Arrays.asList(ExtendedModifierItemType.ARMOR), 2, 2.0f, ExtendedModifierRarity.SKILLFUL, ExtendedModifierScroll.GOLD),
	HARDENED("protect1", "armor_protect.one", ExtendedModifierTypes.PROTECTION, Arrays.asList(ExtendedModifierItemType.ARMOR), 1, 1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.SILVER),
	POOR("protectWeak1", "armor_protect.weak_one", ExtendedModifierTypes.PROTECTION, Arrays.asList(ExtendedModifierItemType.ARMOR), -1, -1.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	CRUDE("protectWeak2", "armor_protect.weak_two", ExtendedModifierTypes.PROTECTION, Arrays.asList(ExtendedModifierItemType.ARMOR), -2, -2.0f, ExtendedModifierRarity.BASIC, ExtendedModifierScroll.NOSCROLL),
	TRUE("protectMithril", "armor_protect_mithril", ExtendedModifierTypes.PROTECTION_MITHRIL, Arrays.asList(ExtendedModifierItemType.ARMOR), 1, 4.0f, ExtendedModifierRarity.APPLIED, ExtendedModifierScroll.GOLD);

	/**
     * Immutable map of modifiers keyed by their NBT identifiers.
     * Provides fast lookup for serialization and compatibility checks.
     */
	public static final Map<String, ExtendedModifiers> MODIFIER_MAP;

	static {
		Map<String, ExtendedModifiers> buildableModifierMap = new HashMap<>();
        for (ExtendedModifiers m : values()) {
            // Use the NBT key
            buildableModifierMap.put(m.getNbtKey(), m);
        }
        MODIFIER_MAP = Collections.unmodifiableMap(buildableModifierMap);
	}

	/** The NBT save key. */
    private String keyNbt;

    /** The localization/translation key. */
    private String keyLocal;

    /** Functional category used for compatibility (e.g., only one BANE per item). */
    private ExtendedModifierTypes modifierType;

    /** Allowed item categories for this modifier. */
    private List<ExtendedModifierItemType> itemTypes;

    /** Tier or strength level of the modifier. */
    private int level;

    /** The numeric effect magnitude applied by this modifier. */
    private float effect;

    /** Acquisition/rarity classification controlling how players obtain it. */
    private ExtendedModifierRarity rarity;

    /** The scroll texture to use for this modifier*/
    private ExtendedModifierScroll scroll;

	ExtendedModifiers(String pNbtKey, String pLocalKey, ExtendedModifierTypes pModifierType, List<ExtendedModifierItemType> pItemTypes, int pLevel, 
		float pModifierEffect, ExtendedModifierRarity pModifierRarity, ExtendedModifierScroll pScroll) {
		this.keyNbt = pNbtKey;
		this.keyLocal = pLocalKey;
		this.modifierType = pModifierType;
		this.itemTypes = pItemTypes;
		this.level = pLevel;
		this.effect = pModifierEffect;
		this.rarity = pModifierRarity;
		this.scroll = pScroll;
	}

	/** @return the NBT save key for this modifier. */
	public String getNbtKey() {
		return keyNbt;
	}

	/** @return the localization key for translations. */
	public String getLocalKey() {
		return keyLocal.toLowerCase();
	}

	/** @return the functional type of this modifier. */
	public ExtendedModifierTypes getModifierType() {
		return modifierType;
	}

	/** @return the valid item categories this modifier can be applied to. */
	public List<ExtendedModifierItemType> getItemType() {
		return itemTypes;
	}

	/** @return the tier or strength level of this modifier. */
	public int getModifierLevel() {
		return level;
	}

	/** @return the numeric effect value applied by this modifier. */
	public float getModifierEffect() {
		return effect;
	}

	/** @return the rarity classification of this modifier. */
	public ExtendedModifierRarity getRarity() {
		return rarity;
	}
}