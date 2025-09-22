package com.github.maximuslotro.lotrrextended.common.modifiers;

import java.util.function.Predicate;

import com.github.maximuslotro.lotrrextended.common.item.ExtendedThrowableAxeItem;
import com.github.maximuslotro.lotrrextended.common.utils.ExtendedModifierUtils;

import lotr.common.init.LOTRMaterial;
import lotr.common.item.LOTRArmorItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;

/**
 * Represents the different types of extended item modifiers and enchantments
 * available in the LOTR Extended system. Each modifier affects items in unique
 * ways, such as improving durability, granting combat effects, or enhancing
 * protection.
 *
 * <p>Each enum constant contains:</p>
 * <ul>
 *   <li>A localization key for translations and descriptions.</li>
 *   <li>A front-facing value prefix (e.g., "+", "x") to display in tooltip formatting.</li>
 *   <li>A predicate to determine whether the modifier is applicable to a given item type.</li>
 *   <li>A predicate to determine whether the modifier is applicable to a given modifier.</li>
 * </ul>
 */
public enum ExtendedModifierType {
	/** Increases item durability or reduces wear. */
	DURABILITY("enchant.lotrextended.durability.disc", "+", 
			item -> item.canBeDepleted()),

	/** Enhances loot drops from enemies or blocks. */
	LOOTING("enchant.lotrextended.looting.disc", "+", 
			item -> item instanceof SwordItem || item instanceof ToolItem),

	/** Boosts melee attack damage. */
	MELEE_DAMAGE("enchant.lotrextended.melee_damage.disc", "+", 
			item -> item instanceof SwordItem || item instanceof ToolItem || item instanceof ExtendedThrowableAxeItem),

	/** Extends melee attack reach distance. */
	MELEE_REACH("enchant.lotrextended.melee_reach.disc", "x", 
			item -> item instanceof SwordItem || item instanceof ToolItem),

	/** Increases melee attack speed. */
	MELEE_SPEED("enchant.lotrextended.melee_speed.disc", "x", 
			item -> item instanceof SwordItem || item instanceof AxeItem),

	/** Enhances knockback effect in melee combat. */
	MEELE_KNOCKBACK("enchant.lotrextended.melee_knockback.disc", "+", 
			item -> item instanceof SwordItem || item instanceof ToolItem || item instanceof ExtendedThrowableAxeItem),

	/** Increases ranged attack speed or draw speed. */
	RANGED_SPEED("enchant.lotrextended.ranged_speed.disc", "x", 
			item -> item instanceof BowItem),

	/** Enhances knockback effect of ranged attacks. */
	RANGED_KNOCKBACK("enchant.lotrextended.ranged_knockback.disc", "+", 
			item -> item instanceof BowItem),

	/** Grants bonus damage against specific enemy types (e.g., "Bane of..."). */
	BANE("enchant.lotrextended.bane.desc", "+", 
			item -> item instanceof SwordItem || item instanceof BowItem || item instanceof ExtendedThrowableAxeItem),

	/** Applies chilling or slowing effects to enemies. */
	CHILLING("enchant.lotrextended.chilling.disc", "", 
			item -> item instanceof SwordItem || item instanceof BowItem || item instanceof ExtendedThrowableAxeItem),

	/** Inflicts fire or burn effects on enemies. */
	INFERNAL("enchant.lotrextended.infernal.disc", "", 
			item -> item instanceof SwordItem || item instanceof BowItem || item instanceof ExtendedThrowableAxeItem),

	/** Drops enemy heads on death. */
	HEADHUNTING("enchant.lotrextended.headhunting.disc", "", 
			item -> item instanceof SwordItem || item instanceof BowItem || item instanceof AxeItem ),

	/** Increases efficiency and mining speed for tools. */
	TOOL_SPEED("enchant.lotrextended.tool_speed.disc", "x", 
			item -> item instanceof ToolItem || item instanceof ShearsItem),

	/** Grants silk touch-like effects for block harvesting. */
	TOOL_SILK("enchant.lotrextended.tool_silk.disc", "", 
			item -> item instanceof ToolItem || item instanceof ShearsItem),

	/** General protection modifier, reducing overall damage. */
	PROTECTION("enchant.lotrextended.armor_protect.disc", "+", 
			item -> item instanceof ArmorItem),

	/** Reduces fire or lava damage. */
	PROTECTION_FIRE("enchant.lotrextended.armor_protect_fire.disc", "+", 
			item -> item instanceof ArmorItem),

	/** Reduces damage from ranged attacks (arrows, projectiles). */
	PROTECTION_RANGED("enchant.lotrextended.armor_protect_ranged.disc", "+", 
			item -> item instanceof ArmorItem),

	/** Reduces fall damage. */
	PROTECTION_FALLING("enchant.lotrextended.armor_protect_fall.disc", "+", 
			item -> item instanceof ArmorItem && ((ArmorItem) item).getSlot()==EquipmentSlotType.FEET),

	/** Provides special protection for mithril armor. */
	PROTECTION_MITHRIL("enchant.lotrextended.armor_protect_mithril.disc", "+", 
			item -> item instanceof LOTRArmorItem && ((LOTRArmorItem) item).getMaterial()==LOTRMaterial.MITHRIL.asArmor()),

	/** Reduces explosion or blast damage. */
	PROTECTION_BLAST("enchant.lotrextended.armor_protect_blast.disc", "+", 
			item -> item instanceof ArmorItem),

	/** Reduces explosion or blast damage. */
	UNKNOWN("enchant.lotrextended.unknown.disc", "$", 
			item -> false);
	static {
		// wire up after enum construction due to inability to forward reference
		BANE.predicateCompatible = m -> (m != HEADHUNTING);
		HEADHUNTING.predicateCompatible = m -> (m != BANE);

		CHILLING.predicateCompatible = m -> (m != INFERNAL);
		INFERNAL.predicateCompatible = m -> (m != CHILLING);

		LOOTING.predicateCompatible = m -> (m != TOOL_SILK);
		TOOL_SILK.predicateCompatible = m -> (m != LOOTING);

		// multi-OR cases wrapped with parentheses
		PROTECTION.predicateCompatible = m -> (m != PROTECTION_FIRE && m != PROTECTION_RANGED && m != PROTECTION_MITHRIL && m != PROTECTION_BLAST);
		PROTECTION_FIRE.predicateCompatible = m -> (m != PROTECTION && m != PROTECTION_RANGED && m != PROTECTION_MITHRIL && m != PROTECTION_BLAST);
		PROTECTION_RANGED.predicateCompatible = m -> (m != PROTECTION_FIRE && m != PROTECTION && m != PROTECTION_MITHRIL && m != PROTECTION_BLAST);
		PROTECTION_MITHRIL.predicateCompatible = m -> (m != PROTECTION_FIRE && m != PROTECTION_RANGED && m != PROTECTION && m != PROTECTION_BLAST);
		PROTECTION_BLAST.predicateCompatible = m -> (m != PROTECTION_FIRE && m != PROTECTION_RANGED && m != PROTECTION_MITHRIL && m != PROTECTION);
	}

	/** The localization/translation key for item descriptions. */
	private final String keyLocal;
	/** The prefix symbol (e.g., "+", "x") used to represent the modifier in tooltips. */
	private final String frontValue;
	/** Predicate used to determine whether this modifier is valid for a given item. */
	private final Predicate<Item> predicateItem;
	/** Predicate used to determine whether this modifier is valid for a given modifier. */
	private Predicate<ExtendedModifierType> predicateCompatible = (m -> true);
	/**
	 * Constructs a new {@link ExtendedModifierType}.
	 *
	 * @param keyLocal             the localization/translation key for this modifier
	 * @param frontValue           the display prefix (e.g., "+", "x") for formatting
	 * @param pPredicateItem       the condition to check if this modifier can apply to an item
	 */
	ExtendedModifierType(String keyLocal, String frontValue, Predicate<Item> pPredicateItem) {
		this.keyLocal = keyLocal;
		this.frontValue = frontValue;
		this.predicateItem = pPredicateItem;
	}
	/**
	 * Returns the localization/translation key for this modifier.
	 *
	 * @return the localization key string
	 */
	public String getKeyLocal() {
		return keyLocal;
	}
	/**
	 * Returns the front-facing value prefix used for tooltips (e.g., "+", "x").
	 *
	 * @return the tooltip prefix string
	 */
	public String getFrontValue() {
		return frontValue;
	}
	/**
	 * Checks whether this modifier can be applied to a given item.
	 *
	 * @param item the {@link Item} to check
	 * @return {@code true} if the modifier can be applied, {@code false} otherwise
	 */
	public boolean isItemApplicable(ItemStack item) {
		return predicateItem.test(item.getItem());
	}
	/**
	 * Checks whether this modifier can be applied alongside a give modifier.
	 *
	 * @param otherType     the {@link ExtendedModifierType} to check
	 * @return {@code true} if the modifier can be applied, {@code false} otherwise
	 */
	public boolean isModifierApplicable(ExtendedModifierType otherType) {
		return otherType!=this && predicateCompatible.test(otherType);
	}
	/**
	 * Checks whether {@code modifierType} can be applied to the given ItemStack.
	 *
	 * @param stack        the ItemStack to check
	 * @param modifierType the modifier being applied
	 * @return {@code true} if applicable, otherwise {@code false}
	 */
	public static boolean isItemApplicableWithModifier(ItemStack stack, ExtendedModifierType modifierType) {
	    return modifierType.isItemApplicable(stack)
	        || ExtendedModifierUtils.getModifierList(stack).stream().map(ExtendedModifier::getModifierType).allMatch(modifierType::isModifierApplicable);
	}
}