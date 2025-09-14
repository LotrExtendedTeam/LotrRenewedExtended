package com.github.maximuslotro.lotrrextended.common.loot.conditions;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * Registers and provides access to all custom {@link LootConditionType}
 * instances used by Extended.
 * <p>
 * Loot conditions determine whether certain loot functions or drops
 * are applied during loot table execution.
 * </p>
 *
 * <p>
 * All conditions are registered under the {@code lotrextended}
 * namespace in the loot condition registry.
 * </p>
 */
public class ExtendedLootConditions {
	/**
	 * Loot condition type for {@link ExtendedRemoveUnitCoinDrops}.
	 * <p>
	 * Prevents unit NPCs on active duty from dropping unit coins,
	 * ensuring coins are only dropped from inactive or non-unit NPCs.
	 * </p>
	 */
	public static LootConditionType REMOVE_UNIT_COINS = register("remove_unit_coins", new ExtendedRemoveUnitCoinDrops.Serializer());
	/**
	 * Registers a new {@link LootConditionType}.
	 *
	 * @param name        the unique string identifier of the loot condition
	 * @param pSerializer the serializer responsible for reading/writing the condition
	 * @return the registered {@link LootConditionType}
	 */
	private static LootConditionType register(String name, ILootSerializer<? extends ILootCondition> pSerializer) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation("lotrextended", name), new LootConditionType(pSerializer));
	}
	/**
	 * Initializes the loot condition registrations.
	 * <p>
	 * Calling this method ensures all static fields in this class
	 * are loaded and registered with the game. This is invoked during mod setup.
	 * </p>
	 */
	public static void setup() {}
}
