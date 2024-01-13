package com.github.maximuslotro.lotroExtended.common.loot;

import com.github.maximuslotro.lotroExtended.LotrExtendedMod;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedLootModifiers {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS= DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, LotrExtendedMod.MODID);
	public static final RegistryObject<GlobalLootModifierSerializer<WargFurColorizer>> WARG_FUR = LOOT_MODIFIERS.register("warg_fur_colorizer", WargFurColorizer.Serializer::new);;
	
	public static void register() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		LOOT_MODIFIERS.register(bus);
	}
}
