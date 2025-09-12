package com.github.maximuslotro.lotrrextended.common.loot.modifiers;

import com.github.maximuslotro.lotrrextended.LotrExtendedMod;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedGlobalLootModifiers {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, LotrExtendedMod.MODID);
	//public static final RegistryObject<GlobalLootModifierSerializer<WargFurColorizer>> WARG_FUR = LOOT_MODIFIERS.register("warg_fur_colorizer", WargFurColorizer.Serializer::new);;
	public static final RegistryObject<GlobalLootModifierSerializer<EnchantmentedInMEDisabler>> ENCHANTMENT_DISABLER = LOOT_MODIFIERS.register("me_enchantment_disabler", EnchantmentedInMEDisabler.Serializer::new);

	public static void register(IEventBus bus) {
		LOOT_MODIFIERS.register(bus);
	}
}
