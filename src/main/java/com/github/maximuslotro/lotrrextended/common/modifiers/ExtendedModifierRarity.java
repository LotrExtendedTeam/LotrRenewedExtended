package com.github.maximuslotro.lotrrextended.common.modifiers;

/**
 * Represents the rarity levels for extended modifiers.
 * Rarity determines how a modifier can be obtained and whether it
 * contributes to the standard modifier limit on items.
 */
public enum ExtendedModifierRarity {
    /**
     * Obtainable on items crafted normally, as well as through reforging.
     * Can also be sold by traders, found in chests, or appear on NPC equipment.
     */
    BASIC,

    /**
     * Obtainable only through reforging (not initial crafting or pulling from creative inventory).
     * May also be sold by some traders, or rarely found in chests.
     */
    SKILLFUL,

    /**
     * Cannot be crafted or reforged.
     * Earned directly on equipment by performing a special deed.
     * Does not count toward the 3-modifier limit.
     */
    EARNED,

    /**
     * Cannot be crafted or reforged.
     * Applied to equipment at an anvil or smith using a rare combiner item.
     * Does not count toward the 3-modifier limit.
     */
    APPLIED,

    /**
     * Cannot be crafted or reforged.
     * Exists only on special items obtained in particular ways.
     * Cannot be applied to other items.
     * Does not count toward the 3-modifier limit.
     */
    UNIQUE;
}