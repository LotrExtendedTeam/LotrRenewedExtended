package com.github.maximuslotro.lotrrextended.common.enums;

public enum ExtendedModifierTypes {
	 /** Increases item durability or reduces wear. */
    DURABILITY("enchant.lotrextended.durability.disc", "+"),

    /** Enhances loot drops from enemies or blocks. */
    LOOTING("enchant.lotrextended.looting.disc", "+"),

    /** Boosts melee attack damage. */
    MELEE_DAMAGE("enchant.lotrextended.melee_damage.disc", "+"),

    /** Extends melee attack reach distance. */
    MELEE_REACH("enchant.lotrextended.melee_reach.disc", "x"),

    /** Increases melee attack speed. */
    MELEE_SPEED("enchant.lotrextended.melee_speed.disc", "x"),

    /** Enhances knockback effect in melee combat. */
    MEELE_KNOCKBACK("enchant.lotrextended.melee_knockback.disc", "+"),
    
    /** Increases ranged attack speed or draw speed. */
    RANGED_SPEED("enchant.lotrextended.ranged_speed.disc", "x"),

    /** Enhances knockback effect of ranged attacks. */
    RANGED_KNOCKBACK("enchant.lotrextended.ranged_knockback.disc", "+"),

    /** Grants bonus damage against specific enemy types (e.g., "Bane of..."). */
    BANE("enchant.lotrextended.bane.desc", ""),

    /** Applies chilling or slowing effects to enemies. */
    CHILLING("enchant.lotrextended.chilling.disc", ""),

    /** Inflicts fire or burn effects on enemies. */
    INFERNAL("enchant.lotrextended.infernal.disc", ""),

    /** Drops enemy heads on death. */
    HEADHUNTING("enchant.lotrextended.headhunting.disc", ""),

    /** Increases efficiency and mining speed for tools. */
    TOOL_SPEED("enchant.lotrextended.tool_speed.disc", "x"),

    /** Grants silk touch-like effects for block harvesting. */
    TOOL_SILK("enchant.lotrextended.tool_silk.disc", ""),

    /** General protection modifier, reducing overall damage. */
    PROTECTION("enchant.lotrextended.armor_protect.disc", "+"),

    /** Reduces fire or lava damage. */
    PROTECTION_FIRE("enchant.lotrextended.armor_protect_fire.disc", "+"),

    /** Reduces damage from ranged attacks (arrows, projectiles). */
    PROTECTION_RANGED("enchant.lotrextended.armor_protect_ranged.disc", "+"),

    /** Reduces fall damage. */
    PROTECTION_FALLING("enchant.lotrextended.armor_protect_fall.disc", "+"),

    /** Provides special protection for mithril armor. */
    PROTECTION_MITHRIL("enchant.lotrextended.armor_protect_mithril.disc", "+"),

    /** Reduces explosion or blast damage. */
    PROTECTION_BLAST("enchant.lotrextended.armor_protect_blast.disc", "+");

    /** The localization/translation key for item descriptions. */
    private final String keyLocal;

    private final String frontValue;

    ExtendedModifierTypes(String keyLocal, String frontValue) {
        this.keyLocal = keyLocal;
        this.frontValue = frontValue;
    }

    public String getKeyLocal() {
        return keyLocal;
    }

    public String getFrontValue() {
        return frontValue;
    }
}