package com.github.maximuslotro.lotrrextended.common.init;

import com.github.maximuslotro.lotrrextended.common.datagen.ExtendedDataGenValueHolder;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DatagenModLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "lotrextended");
	public static final RegistryObject<Attribute> WARG_JUMP_STRENGTH = registryAttribute("warg.jump_strength", "Warg Jump Strength", 0.7D, 0.0D, 2.0D);

	public static void register(IEventBus modBus) {
		REGISTRY.register(modBus);
	}

	private static final RegistryObject<Attribute> registryAttribute(String name, String defaultName, double defaultValue, double min, double max) {
		if(DatagenModLoader.isRunningDataGen()) {
			ExtendedDataGenValueHolder.attributeNames.put("attribute.name.lotrextended." + name, defaultName);
		}
		return REGISTRY.register(name, () -> new RangedAttribute("attribute.name.lotrextended." + name, defaultValue, min, max));

	}
}