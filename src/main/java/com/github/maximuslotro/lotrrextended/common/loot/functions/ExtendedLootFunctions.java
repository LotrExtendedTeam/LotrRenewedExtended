package com.github.maximuslotro.lotrrextended.common.loot.functions;

import com.github.maximuslotro.lotrrextended.common.modifiers.ExtendedModifier;

import lotr.common.entity.npc.WargEntity;
import lotr.common.init.ExtendedItems;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * Registers and provides access to all custom {@link LootFunctionType}
 * instances used by Extended.
 * <p>
 * These loot functions define custom behavior for item drops,
 * such as applying modifiers or converting drops based on entity type.
 * </p>
 *
 * <p>
 * The functions are registered under the {@code lotrextended}
 * namespace in the loot function registry.
 * </p>
 */
public class ExtendedLootFunctions {
	/**
	 * Loot function type for {@link ExtendedSetWargFurType}.
	 * <p>
	 * Replaces generic fur or helmet items with a variant
	 * matching the {@link WargEntity} that dropped them.
	 * </p>
	 */
	public static LootFunctionType WARG_FUR_TYPE = register("warg_fur_type", new ExtendedSetWargFurType.Serializer());
	/**
	 * Loot function type for {@link ExtendedModifierAdder}.
	 * <p>
	 * Applies random {@link ExtendedModifier}s to an item
	 * during loot generation, with options for reforging
	 * and filtering out negative modifiers.
	 * </p>
	 */
	public static LootFunctionType MODIFIER_ADDER = register("modifier_adder", new ExtendedModifierAdder.Serializer());
	/**
	 * Loot function type for {@link ExtendedModifierScrollAdder}.
	 * <p>
	 * With a configurable probability, replaces a generated item
	 * with a {@link ExtendedItems#MODIFIER_SCROLL} containing
	 * random modifiers.
	 * </p>
	 */
	public static LootFunctionType MODIFIER_ACROLL_ADDER = register("modifier_scroll_adder", new ExtendedModifierScrollAdder.Serializer());
	public static LootFunctionType MODIFIER_ACROLL_SETTER = register("modifier_scroll_setter", new ExtendedModifierScrollSetter.Serializer());

	/**
	 * Helper method to register a new {@link LootFunctionType}.
	 *
	 * @param name       the unique string identifier of the loot function
	 * @param serializer the serializer responsible for reading/writing the function
	 * @return the registered {@link LootFunctionType}
	 */
	private static LootFunctionType register(String name, ILootSerializer<? extends ILootFunction> serializer) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation("lotrextended", name), new LootFunctionType(serializer));
	}
	/**
	 * Initializes the loot function registrations.
	 * <p>
	 * Calling this method ensures all static fields in this class
	 * are loaded and registered with the game. This is invoked during mod setup.
	 * </p>
	 */
	public static void setup() {}
}
