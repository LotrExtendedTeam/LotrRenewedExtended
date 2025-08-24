package com.github.maximuslotro.lotrrextended.common.enums;

/**
 * Defines which category of item a modifier can be applied to.
 */
public enum ExtendedModifierItemType {
    /** Modifiers for melee weapons (e.g., swords, axes). */
    MELEE,

    /** Modifiers for ranged weapons (e.g., bows, crossbows). */
    RANGED,

    /** Modifiers for tools (e.g., pickaxes, shovels). */
    TOOL,

    /** Modifiers for armor pieces (helmets, chestplates, etc.). */
    ARMOR,

    /** Universal modifiers that apply to all item categories. */
    ALL;
}