package com.github.maximuslotro.lotrrextended.common.loot.modifiers;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers global loot modifier serializers for the LOTR Extended mod.
 * <p>
 * Global loot modifiers can modify loot tables across the entire game,
 * affecting items generated from chests, fishing, or other loot sources.
 * </p>
 */
public class ExtendedGlobalLootModifiers {
	/**
     * Deferred register for all {@link GlobalLootModifierSerializer}s
     * used by this mod.
     * <p>
     * All loot modifier serializers are registered through this
     * DeferredRegister to ensure proper timing during mod loading.
     * </p>
     */
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, LotrExtendedMod.MODID);
	//public static final RegistryObject<GlobalLootModifierSerializer<WargFurColorizer>> WARG_FUR = LOOT_MODIFIERS.register("warg_fur_colorizer", WargFurColorizer.Serializer::new);;
	/**
     * Global loot modifier that disables enchanted item drops in
     * mod dimensions and for certain fishing rods.
     * <p>
     * Corresponds to {@link EnchantmentedInMEDisabler}.
     * </p>
     */
	public static final RegistryObject<GlobalLootModifierSerializer<EnchantmentedInMEDisabler>> ENCHANTMENT_DISABLER = LOOT_MODIFIERS.register("me_enchantment_disabler", EnchantmentedInMEDisabler.Serializer::new);
	/**
     * Registers all deferred loot modifier serializers to the given mod event bus.
     *
     * @param bus the mod event bus used for registration
     */
	public static void register(IEventBus bus) {
		LOOT_MODIFIERS.register(bus);
	}
}
